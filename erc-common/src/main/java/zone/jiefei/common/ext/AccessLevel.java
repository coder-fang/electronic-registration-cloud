package zone.jiefei.common.ext;

import lombok.Getter;

/**
 * 访问权限等级
 *
 * @author zhangpeitao
 */

@Getter
public enum AccessLevel {

    /*
        所有均可访问
     */
    ALL(0, "all"),

    /*
        普通管理可访问
     */
    LOGIN(1, "admin");




    int code;
    String msg;

    AccessLevel( int code, String msg ) {
        this.code = code;
        this.msg = msg;
    }
}
