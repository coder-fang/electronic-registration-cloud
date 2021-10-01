package zone.jiefei.config;


import com.google.common.base.Predicates;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * api页面 /doc.html
 * swagger-ui.html
 *
 * @author Zheng Jie
 * @date 2018-11-23
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${swagger.enabled}")
    private Boolean enabled;

    @Value("${token.requestHeader}")
    private String requestHeader;

    @Value("${token.startWith}")
    private String startWith;

    @Value("${swagger.host}")
    private String swaggerHost;

    @Bean
    @SuppressWarnings("all")
    public Docket createRestApi() {

        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        ticketPar.name(requestHeader).description("token")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .defaultValue(startWith)
                .required(true)
                .build();
        pars.add(ticketPar.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .enable(enabled)
                .apiInfo(apiInfo())
                .host(swaggerHost)
                .select()
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build()
                .globalOperationParameters(pars);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .description("新生电子报到系统")
                .title("新生电子报到系统后端api")
                .version("1.0")
                .build();
    }

}
