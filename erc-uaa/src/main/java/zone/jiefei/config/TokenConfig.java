package zone.jiefei.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @author Wangmingcan
 * @date 2021/8/10 10:29
 * @description
 */
@Configuration
public class TokenConfig {

    private static final String SIGN_KEY = "uaa";

//    @Bean
//    public TokenStore tokenStore(){
//        //使用基于内存的普通令牌
//        return new InMemoryTokenStore();
//    }

    @Bean
    public TokenStore tokenStore(){
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(SIGN_KEY);
        return converter;
    }
}
