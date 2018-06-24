package com.bvvy.grocery.auth.config


import com.bvvy.grocery.auth.user.UserRepository
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.NestedConfigurationProperty
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.event.AuthenticationSuccessEvent
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.client.OAuth2ClientContext
import org.springframework.security.oauth2.client.OAuth2RestTemplate
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.web.filter.CompositeFilter
import java.util.*
import javax.servlet.Filter
import javax.sql.DataSource


/**
 * Created by bvvy on 2018/3/18.
 * com.divide2.auth.config
 */
class MyUserDetailService : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        return User.builder().username("").authorities("").roles("").build()
    }
}

@Configuration
class LoginConfig(val dataSource: DataSource,
                  val oauth2ClientContext: OAuth2ClientContext,
                  val userRepository: UserRepository) : WebSecurityConfigurerAdapter() {
    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Bean
    override fun userDetailsServiceBean(): UserDetailsService {
        return super.userDetailsServiceBean()
    }
//
//    override fun userDetailsService(): UserDetailsService {
//        return MyUserDetailService()
//    }


    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery(
                        "select username,password,enabled from sys_user u where u.username = ?"
                )
                .authoritiesByUsernameQuery(
                        "select u.username,'ROLE_USER' from sys_user u where u.username = ?"
                )
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        // @formatter:off
        http.antMatcher("/**").authorizeRequests().antMatchers("/", "/login**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(LoginUrlAuthenticationEntryPoint("/"))
                .and()
                .logout()
                .and()
                .csrf().disable()
                .addFilterBefore(ssoFilter(), BasicAuthenticationFilter::class.java)
        // @formatter:on
    }

    @Bean
    fun oauth2ClientFilterRegistration(filter: OAuth2ClientContextFilter): FilterRegistrationBean<OAuth2ClientContextFilter> {
        val registration = FilterRegistrationBean<OAuth2ClientContextFilter>()
        registration.filter = filter
        registration.order = -100
        return registration
    }

    @Bean
    @ConfigurationProperties("github")
    fun github(): ClientResources {
        return ClientResources()
    }

    @Bean
    @ConfigurationProperties("facebook")
    fun facebook(): ClientResources {
        return ClientResources()
    }

    private fun ssoFilter(): Filter {
        val filter = CompositeFilter()
        val filters = ArrayList<Filter>()
        filters.add(ssoFilter(facebook(), "/login/facebook"))
        filters.add(ssoFilter(github(), "/login/github"))
        filter.setFilters(filters)
        return filter
    }

    private fun ssoFilter(client: ClientResources, path: String): Filter {
        val filter = OAuth2ClientAuthenticationProcessingFilter(path)
        val template = OAuth2RestTemplate(client.client, oauth2ClientContext)
        filter.setRestTemplate(template)
        val tokenServices = UserInfoTokenServices(client.resource.userInfoUri, client.client.clientId)
        tokenServices.setRestTemplate(template)
        filter.setTokenServices(tokenServices)
        filter.setApplicationEventPublisher(MyApplicationEventPublisher())
        return filter
    }
}

class MyApplicationEventPublisher : ApplicationEventPublisher {
    override fun publishEvent(event: Any) {
        if (event is AuthenticationSuccessEvent) {
            println(event.authentication)
        }
    }

}

class ClientResources {

    @NestedConfigurationProperty
    var client = AuthorizationCodeResourceDetails()

    @NestedConfigurationProperty
    var resource = ResourceServerProperties()

}



