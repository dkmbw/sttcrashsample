package de.sttsample

import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestMethod.POST

@RestController
class SpeechController(private val speechToTextService: SpeechToTextService) {

    @ApiOperation(value = "Convert Speech to Text (raw audio)", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ApiResponses(value = [(ApiResponse(code = 200, message = "Successfully converted speech to text."))])
    @RequestMapping(value = ["/stt"], method = [POST])
    fun stt(@ApiParam("Audio PCM, 16000 Hz, Mono, 16 bits/sample", required = true)
            @RequestBody pcm: ByteArray): String {
        return speechToTextService.stt(pcm)
    }

    @ApiOperation(value = "Convert Speech to Text (wave audio)", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @RequestMapping(value = ["/sttwav"], method = [POST])
    fun sttwav(@ApiParam("Audio WAVE, 16000 Hz, Mono, 16 bits/sample", required = true)
               @RequestBody wav: ByteArray): String {
        // strip off WAVE header
        val pcm = ByteArray(wav.size - 44)
        System.arraycopy(wav, 44, pcm, 0, pcm.size)
        return speechToTextService.stt(pcm)
    }

}