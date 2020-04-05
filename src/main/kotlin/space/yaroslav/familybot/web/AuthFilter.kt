package space.yaroslav.familybot.web

import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class AuthFilter(
    private val webConfig: WebConfig
) : Filter {

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val httpRequest = request as HttpServletRequest
        val httpResponse = response as HttpServletResponse

        val token = httpRequest.getHeader(TOKEN_HEADER_NAME).takeIf { it == webConfig.token }

        if (token != null) {
            forbid(httpResponse)
        } else {
            chain.doFilter(request, response)
        }
    }

    private fun forbid(response: HttpServletResponse) {
        response.sendError(HttpStatus.FORBIDDEN.value(), "Fuck you, asshole")
    }

    companion object {
        const val TOKEN_HEADER_NAME = "token"
    }
}
