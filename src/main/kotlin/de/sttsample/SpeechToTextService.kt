package de.sttsample

import com.microsoft.cognitiveservices.speech.OutputFormat
import com.microsoft.cognitiveservices.speech.PropertyId
import com.microsoft.cognitiveservices.speech.SpeechConfig
import com.microsoft.cognitiveservices.speech.SpeechRecognizer
import com.microsoft.cognitiveservices.speech.audio.AudioConfig
import com.microsoft.cognitiveservices.speech.audio.AudioInputStream
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.concurrent.ExecutionException


@Service
class SpeechToTextService(val configProperties: ConfigProperties) {

    private val LOGGER = LoggerFactory.getLogger(SpeechToTextService::class.java)

    private val LOCALE = "de-DE"

    fun stt(pcm: ByteArray): String {
        LOGGER.info("TIME {} stt.config.init", System.nanoTime())

        val speechConfig: SpeechConfig = SpeechConfig.fromSubscription(configProperties.STT_KEY, configProperties.AZURE_REGION)
        speechConfig.speechRecognitionLanguage = LOCALE
        speechConfig.outputFormat = OutputFormat.Detailed
        speechConfig.setProperty(PropertyId.SpeechServiceResponse_RequestDetailedResultTrueFalse.name, "True")
        val language = speechConfig.speechRecognitionLanguage
        LOGGER.info("Microsoft STT ${pcm.size} bytes, language = $language")

        val pushStream = AudioInputStream.createPushStream()
        val audioInput = AudioConfig.fromStreamInput(pushStream)

        LOGGER.info("TIME {} stt.recognizer.init", System.nanoTime())
        val recognizer = SpeechRecognizer(speechConfig, audioInput)
        LOGGER.info("TIME {} stt.recognizer.start", System.nanoTime())
        val f = recognizer.recognizeOnceAsync()

        LOGGER.info("TIME {} stt.push.start", System.nanoTime())
        pushStream.write(pcm)
        pushStream.close()
        LOGGER.info("TIME {} stt.push.done", System.nanoTime())

        try {
            val speechRecognitionResult = f.get()
            LOGGER.info("TIME {} stt.received '{}'", System.nanoTime(), speechRecognitionResult.text)
            LOGGER.info("Microsoft STT ${pcm.size} bytes -> '${speechRecognitionResult.text}'")
            return speechRecognitionResult.text
        } catch (e: InterruptedException) {
            LOGGER.error("Microsoft STT ${pcm.size} bytes -> Exception:", e)
            throw e
        } catch (e: ExecutionException) {
            LOGGER.error("Microsoft STT ${pcm.size} bytes -> Exception:", e)
            throw e
        }
    }
}
