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
     * 会通过之前的clientDetailsServiceConfigurer注入到Spring容器中
     */
    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    /**
     * 令牌端点的安全约束
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // oauth/token_key公开
        security.tokenKeyAccess("permitAll()")
                // pauth/check_token 公开
                .checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients(); //表单认证，申请令牌
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory() //内存方式
                //client_id
                .withClient("100485241")
                .secret(new BCryptPasswordEncoder().encode("secret"))
                //客户端拥有的资源列表
                .resourceIds("data","salary")
                .authorizedGrantTypes("authorization_code", "password", "client_credentials",
                        "implicit", "refresh_token")
                //允许的授权范围，自定义字符串
                .scopes("all")
                //跳转到授权页面
                .autoApprove(false)
                //回调地址。授权服务会往该回调地址推送此客户端相关信息
                .redirectUris("https://www.processon.com");
//                .and().withClient()
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
               // .pathMapping("/oauth/confirm_access")  //定制授权路径
                //认证管理器
                .authenticationManager(authenticationManager)
                //密码模式的用户信息管理
                .userDetailsService(userDetailsService)
                //授权码服务
                .authorizationCodeServices(authorizationCodeServices)
                //令牌管理服务
                .tokenServices(tokenServices())
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);
    }

    public AuthorizationServerTokenServices tokenServices() {
        DefaultTokenServices services = new DefaultTokenServices();
        //客户端详情服务
        services.setClientDetailsService(clientDetailsService);
        //允许令牌自动刷新
        services.setSupportRefreshToken(true);
        //令牌存储策略-内存
        services.setTokenStore(tokenStore);
        //使用jwt令牌格式
        services.setTokenEnhancer(jwtAccessTokenConverter);
        //令牌默认有效期2小时
        services.setAccessTokenValiditySeconds(7200);
        // 刷新令牌默认有效期3天
        services.setRefreshTokenValiditySeconds(259200);
        return services;
    }

    /**
     * 设置授权码模式的授权码如何存取，暂时使用内存方式
     * @return
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new InMemoryAuthorizationCodeServices();
        //JdbcAuthorizationCodeServices
    }
}
