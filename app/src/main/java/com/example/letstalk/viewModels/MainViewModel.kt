package com.example.letstalk.viewModels

import android.content.Context
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.letstalk.states.MainScreenState
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.launch
import java.util.Locale

class MainViewModel: ViewModel() {
    private val screenState = mutableStateOf(
        MainScreenState()
    )
    val state:State<MainScreenState> = screenState

    var textToSpeech: TextToSpeech? = null

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

        //translated text is saved in  state
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
                    "Model Download Started. This might take a while",
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
                    buttonEnabled = true
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

    //function that performs the text to speech thingy
    fun textToSpeech(context: Context, text: String){
        screenState.value = state.value.copy(
            buttonEnabled = false // disable button when using the text to speech translator
        )
        textToSpeech = TextToSpeech(
            context

        ){
            //error handle the success or failure
            if(it == TextToSpeech.SUCCESS){
                textToSpeech?.let { txtToSpeech ->
                    txtToSpeech.language = Locale.CHINESE
                    //for speed of audio
                    txtToSpeech.setSpeechRate(1.0f)

                    txtToSpeech.speak(
                        text,// will pass the translated text on this parameter
                        TextToSpeech.QUEUE_ADD,
                        null,
                        null
                    )
                }
            }
            screenState.value = state.value.copy(
                buttonEnabled = true
            )
        }
    }

    fun changeTextValue(text:String){
        viewModelScope.launch {
            screenState.value = screenState.value.copy(
                recordedText = text
            )
        }
    }
}