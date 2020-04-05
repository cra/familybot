package space.yaroslav.familybot.web

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("web")
class WebConfig {
    var token: String? = null
}
