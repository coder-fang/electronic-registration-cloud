package zone.jiefei.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * @author Wangmingcan
 * @date 2021/8/9 20:34
 * @description
 */
@Configuration
public class MyAuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenStore tokenStore;
    /**
     * ??????????????????clientDetailsServiceConfigurer?????????Spring?????????
     */
    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    /**
     * ???????????????????????????
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // oauth/token_key??????
        security.tokenKeyAccess("permitAll()")
                // pauth/check_token ??????
                .checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients(); //???????????????????????????
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory() //????????????
                //client_id
                .withClient("100485241")
                .secret(new BCryptPasswordEncoder().encode("secret"))
                //??????????????????????????????
                .resourceIds("data","salary")
                .authorizedGrantTypes("authorization_code", "password", "client_credentials",
                        "implicit", "refresh_token")
                //??????????????????????????????????????????
                .scopes("all")
                //?????????????????????
                .autoApprove(false)
                //??????????????????????????????????????????????????????????????????????????????
                .redirectUris("https://www.processon.com");
//                .and().withClient()
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
               // .pathMapping("/oauth/confirm_access")  //??????????????????
                //???????????????
                .authenticationManager(authenticationManager)
                //?????????????????????????????????
                .userDetailsService(userDetailsService)
                //???????????????
                .authorizationCodeServices(authorizationCodeServices)
                //??????????????????
                .tokenServices(tokenServices())
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);
    }

    public AuthorizationServerTokenServices tokenServices() {
        DefaultTokenServices services = new DefaultTokenServices();
        //?????????????????????
        services.setClientDetailsService(clientDetailsService);
        //????????????????????????
        services.setSupportRefreshToken(true);
        //??????????????????-??????
        services.setTokenStore(tokenStore);
        //??????jwt????????????
        services.setTokenEnhancer(jwtAccessTokenConverter);
        //?????????????????????2??????
        services.setAccessTokenValiditySeconds(7200);
        // ???????????????????????????3???
        services.setRefreshTokenValiditySeconds(259200);
        return services;
    }

    /**
     * ????????????????????????????????????????????????????????????????????????
     * @return
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new InMemoryAuthorizationCodeServices();
        //JdbcAuthorizationCodeServices
    }
}
