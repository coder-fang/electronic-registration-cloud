package zone.jiefei.common.ext;

import java.lang.annotation.*;

/**
 * 权限控制
 *
 * @author sangjinchao
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented     // 在生成javac时显示该注解的信息
@Inherited
public @interface Access {
    AccessLevel level() default AccessLevel.ALL;
}
