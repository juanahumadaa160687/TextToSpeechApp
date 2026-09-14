package com.textspech.texttospeechapp.`class`

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SpeechToTextManager(
    private val context: Context
) {

    private val _state = MutableStateFlow(SpeechState())
    val state : StateFlow<SpeechState> = _state.asStateFlow()

    private var speechRecognizer: SpeechRecognizer? = null

    init {
        if (SpeechRecognizer.isRecognitionAvailable(context)) {
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context).apply {
                setRecognitionListener(SpeechRecognitionCallback())
            }
        } else{
            _state.value = SpeechState(error = "El reconocimiento de voz no está disponible en este dispositivo")
        }
    }

    fun startListening() {
        if (speechRecognizer == null) return

        _state.value = SpeechState(isListening = true)
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-CL")
            putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, context.packageName)
        }
        speechRecognizer?.startListening(intent)
    }

    fun stopListening() {
        speechRecognizer?.stopListening()
        _state.value = SpeechState(isListening = false)
    }
    fun cleanUp() {
        speechRecognizer?.destroy()
        speechRecognizer = null
    }

    private inner class SpeechRecognitionCallback : RecognitionListener {

        override fun onReadyForSpeech(params: Bundle?) {}
        override fun onBeginningOfSpeech() {}
        override fun onRmsChanged(rmsdB: Float) {}
        override fun onBufferReceived(buffer: ByteArray?) {}
        override fun onEndOfSpeech() {
            _state.value = SpeechState(isListening = false)
        }

        override fun onError(error: Int) {
            val errorMessage = when(error) {
                SpeechRecognizer.ERROR_AUDIO -> "Error de audio"
                SpeechRecognizer.ERROR_CLIENT -> "Error del cliente"
                SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "Error de permisos insuficientes"
                SpeechRecognizer.ERROR_NETWORK -> "Error de red"
                SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "Error de tiempo de red"
                SpeechRecognizer.ERROR_NO_MATCH -> "Error de no coincidencia"
                else -> "Error desconocido"
            }
            _state.value = SpeechState(error = errorMessage, isListening = false)
        }

        override fun onResults(params: Bundle?) {
            _state.value = SpeechState(isListening = true)
        }
        override fun onPartialResults(params: Bundle?) {}
        override fun onEvent(eventType: Int, params: Bundle?) {}
    }
}

data class SpeechState(
    val text: String = "",
    val isListening: Boolean = false,
    val error: String? = null
)