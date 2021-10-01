package zone.jiefei.common.interceptor;



import zone.jiefei.common.ext.Access;
import zone.jiefei.common.ext.AccessLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import zone.jiefei.utils.TokenUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;


/**
 * @author huzy
 * @date 2020/9/120:52
 */
@Component
public class AccessInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    TokenUtil tokenUtil;

    //在方法被调用之前进行

    /**
     * @param request  在该参数中可以获取到和请求相关的信息。比如是否为get请求等。
     * @param response 在该参数中可以获取对象的响应信息。
     * @param handler  该参数中包含了对应方法的信息。比如：方法中的参数类型、参数的注解、方法的注解等信息。
     * @return true(放行)false（抛出异常）
     */
    @Override
    public boolean preHandle( HttpServletRequest request, HttpServletResponse response, Object handler )
            throws Exception {

        //如果handler是否是HandlerMethod的实例
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        //获取方法
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        //获取注解
        Access access = method.getAnnotation(Access.class);
        if (access == null) {
            // 如果注解为null, 说明不需要拦截, 直接放过
            return true;
        }


        // 如果是所有都能访问权限直接放行
        if (access.level() == AccessLevel.ALL) {
            return true;
        }

        //比较权限
//        all ==0   login ==1
        if (access.level().getCode() > AccessLevel.ALL.getCode()) {
            //获取当前用户信息
//            Admin user = AdminUtils.getLoginUser();
//            //如果UserUtils中没有数据或者权限不足
//            if (user == null || user.getId() == null) {
//                throw new BaseException(ResponseEnum.REQUEST_IS_FAIL);
//            }
        }
        return true;
    }
}
