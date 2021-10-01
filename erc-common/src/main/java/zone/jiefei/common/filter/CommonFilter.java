package zone.jiefei.common.filter;

import cn.hutool.core.util.StrUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import zone.jiefei.utils.AdminUtils;
import zone.jiefei.utils.TokenUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Author zhangpeitao
 * @Description 过滤器
 * @Data 15:58 2020/8/24
 */
@WebFilter(filterName = "testFilter", urlPatterns = {"/api/*"})
@Slf4j
public class CommonFilter implements Filter {

    @Value("${token.requestHeader}")
    private String requestHeader;

    @Value("${token.startWith}")
    private String startWith;

//    @Autowired
//    IAdminService adminService;

    @Autowired
    TokenUtil tokenUtil;

    /**
     * 当一个 Filter 对象能够拦截访问请求时，Servlet 容器将调用 Filter 对象的 doFilter 方法
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain )
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        //获取请求头的Authorization对应的token数据
        String token = request.getHeader(requestHeader);
        log.info("【token为：】" + token);
        AdminUtils.setLoginUser(null);
        //判断某字符串是否为空或长度为0或由空白符(whitespace)构成
        if (StrUtil.isNotEmpty(token) && token.startsWith(startWith)) {
            token = token.substring(startWith.length());
            if (StrUtil.isNotEmpty(token) && !tokenUtil.isExpire(token)) {
//                QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
//                //获取LambdaQueryWrapper，查询是否有token对应的数据
//                queryWrapper.lambda().eq(Admin::getToken, token);
//                //查询
//                Admin admin = adminService.getOne(queryWrapper);
//                log.info("【当前用户信息为】" + admin);
//                //存储user信息
//                AdminUtils.setLoginUser(admin);
            }
        }
        //执行，放行
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
