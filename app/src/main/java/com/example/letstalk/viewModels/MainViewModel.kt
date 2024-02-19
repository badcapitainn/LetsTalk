package com.example.letstalk.viewModels

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.letstalk.states.MainScreenState
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions

class MainViewModel: ViewModel() {
    private val screenState = mutableStateOf(
        MainScreenState()
    )
    val state:State<MainScreenState> = screenState

    //function that will update the dataclass when value in text field is changed
    fun onInputtedText(
        text: String
    ){
        screenState.value = state.value.copy(
            inputtedText = text
        )
    }

    //function to handle clicked button which will hold the translation logic
    fun onButtonClick(
        text: String,
        context: Context
    ){
        //this is the option that selects the languages you are translating
        val options = TranslatorOptions
            .Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(TranslateLanguage.CHINESE)
            .build()

        val languageTranslator = Translation
            .getClient(options)

        languageTranslator.translate(text)
            .addOnSuccessListener {translatedText ->
                screenState.value = state.value.copy(
                    translatedText = translatedText
                )

            }
            .addOnFailureListener{
                // if translation fails try and download the required model
                Toast.makeText(
                    context,
                    "Model Download Started.",
                    Toast.LENGTH_SHORT

                ).show()

                downloadModel(languageTranslator,context)

            }

    }

    //this function downloads the model once and for all on the device so that the translation can be fast
    private fun downloadModel(
        languageTranslator: Translator,
        context: Context
    ){
        screenState.value = state.value.copy(
            buttonEnabled = false // disable button when downloading
        )

        val conditions = DownloadConditions
            .Builder()
            .requireWifi()
            .build()

        languageTranslator
            .downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                Toast.makeText(
                    context,
                    "Model Downloaded Successfully.",
                    Toast.LENGTH_SHORT

                ).show()
                screenState.value = state.value.copy(
                    buttonEnabled = true //enable button when the translation is complete
                )
            }
            .addOnFailureListener {
                Toast.makeText(
                    context,
                    "Model Download Failed.",
                    Toast.LENGTH_SHORT

                ).show()
            }

    }
}