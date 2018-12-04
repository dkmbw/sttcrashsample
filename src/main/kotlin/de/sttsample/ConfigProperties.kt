package de.sttsample

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

/**
 * @author Alexander Kölmel, MaibornWolff GmbH on 04.12.2018.
 */
@Configuration
class ConfigProperties
{

    @Value("\${azure-region}")
    lateinit var AZURE_REGION : String

    @Value("\${stt-key}")
    lateinit var STT_KEY : String

}