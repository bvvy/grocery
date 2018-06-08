package com.bvvy.grocery.auth.oauth

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.util.StringUtils
import java.util.*
import javax.annotation.PostConstruct

@ConfigurationProperties(prefix = "spring.oauth2.client")
class OAuth2ClientProperties {

    /**
     * OAuth client registrations.
     */
    private val registration: Map<String, Registration> = HashMap()

    @PostConstruct
    fun validate() {
        this.registration.values.forEach { this.validateRegistration(it) }
    }

    private fun validateRegistration(registration: Registration) {
        if (!StringUtils.hasText(registration.clientId)) {
            throw IllegalStateException("Client id must not be empty.")
        }
        if (!StringUtils.hasText(registration.clientSecret)) {
            throw IllegalStateException("Client secret must not be empty.")
        }
    }

    /**
     * A single client registration.
     */
    class Registration {

        /**
         * Reference to the OAuth 2.0 provider to use. May reference an element from the
         * 'provider' property or used one of the commonly used providers (google, github,
         * facebook, okta).
         */
        var provider: String? = null

        /**
         * Client ID for the registration.
         */
        var clientId: String? = null

        /**
         * Client secret of the registration.
         */
        var clientSecret: String? = null

        /**
         * Client authentication method. May be left blank then using a pre-defined
         * provider.
         */
        var clientAuthenticationMethod: String? = null

        /**
         * Authorization grant type. May be left blank then using a pre-defined provider.
         */
        var authorizationGrantType: String? = null

        /**
         * Redirect URI. May be left blank then using a pre-defined provider.
         */
        var redirectUri: String? = null

        /**
         * Authorization scopes. May be left blank then using a pre-defined provider.
         */
        var scope: Set<String>? = null

        /**
         * Client name. May be left blank then using a pre-defined provider.
         */
        var clientName: String? = null

    }
}