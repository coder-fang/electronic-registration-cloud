package zone.jiefei.gateway.config;

import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;

import java.util.Collections;
import java.util.List;

/**
 * sentinel 阿里的开源框架
 *  网关的sentinel限流、降级以及限流后的容错配置类，
 *  项目中融入sentinel，可在sentinel控制平台进行限流、降级、簇点链路（所有接口的请求统计）的实时观察，以及热点链接的实时查看以及配置限制
 *
 * @author weijb
 * @date 2020/11/24 11:55
 * @param
 * @return
 */

@Slf4j
@Configuration
public class GatewaySentinelConfiguration {

    private final List<ViewResolver> viewResolvers;
    private final ServerCodecConfigurer serverCodecConfigurer;

    public GatewaySentinelConfiguration(ObjectProvider<List<ViewResolver>> viewResolversProvider,
                                        ServerCodecConfigurer serverCodecConfigurer) {
        this.viewResolvers = viewResolversProvider.getIfAvailable(Collections::emptyList);
        this.serverCodecConfigurer = serverCodecConfigurer;
    }
    /**
     * 配置 限流后异常处理  使用 SentinelGatewayBlockExceptionHandler
     * @return
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler() {
        // Register the block exception handler for Spring Cloud Gateway.
        return new SentinelGatewayBlockExceptionHandler(viewResolvers, serverCodecConfigurer);
    }

    /**
     * 配置SentinelGatewayFilter
     * @return
     */
    @Bean
    @Order(-1)
    public GlobalFilter sentinelGatewayFilter() {
        return new SentinelGatewayFilter();
    }

    /**
     *  自定义异常返回
     *  限流异常重新定义BlockExceptionHandler的返回
     *  对应接口为 BlockRequestHandler。默认实现为 DefaultBlockRequestHandler，
     *  当被限流时会返回类似于下面的错误信息：Blocked by Sentinel: FlowException
     * @author weijb
     * @date 2020/11/24 13:24
     * @param
     * @return void
     */
//    @PostConstruct
//    public void initBlockRequestHandler(){
//        BlockRequestHandler blockRequestHandler = (serverWebExchange, throwable) -> ServerResponse.status(HttpStatus.OK)
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(BodyInserters.fromValue(Result.custom(ResultCode.SYSTEM_RESOURCE_EXHAUSTION)));
//        GatewayCallbackManager.setBlockHandler(blockRequestHandler);
//    }

}
