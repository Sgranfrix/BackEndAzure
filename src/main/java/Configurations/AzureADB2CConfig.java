/*package Configurations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

@PropertySource("classpath:application.properties")
public class AzureADB2CConfig {

    @Value("${spring.cloud.azure.active-directory.credential.client-id}")
    private static String CLIENT_ID;

    @Value("${spring.cloud.azure.active-directory.credential.client-secret}")
    private static String CLIENT_SECRET;

    @Value("${spring.cloud.azure.active-directory.credential.client-id}")
    private static String AUTHORITY;

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(clientRegistration());
    }

    private ClientRegistration clientRegistration() {
        return ClientRegistration.withRegistrationId("azure")
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                //.authorizationGrantType("authorization_code")
                .redirectUriTemplate("http://localhost:49489/#/welcome")
                .authorizationUri(AUTHORITY + "/oauth2/v2.0/authorize")
                .tokenUri(AUTHORITY + "/oauth2/v2.0/token")
                .userInfoUri(AUTHORITY + "/oauth2/v2.0/userinfo")
                .scope("openid")
                .clientName("Azure AD B2C")
                .build();
    }

}
*/