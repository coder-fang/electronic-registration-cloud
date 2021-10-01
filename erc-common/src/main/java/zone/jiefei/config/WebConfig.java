package zone.jiefei.config;

import zone.jiefei.common.interceptor.AccessInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author huzy
 * @date 2020/9/120:51
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors( InterceptorRegistry registry ) {
        registry.addInterceptor(new AccessInterceptor());
    }

}
