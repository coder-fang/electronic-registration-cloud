package zone.jiefei.response.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * description:返回信息枚举
 * @author RenShiWei
 * Date: 2020/7/9 21:41
 **/
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ResponseEnum {

    /**
     *      成功
     */
    SUCCESS(200, "SUCCESS"),


    SYSUC_OF(201,"访问成功"),
    
    /**
     * 错误（默认返回状态码）
     */
    ERROR(0, "ERROR"),
    
    /**
     * 全局未知异常--后台
     */
    SEVER_ERROR(500, "服务器异常,请稍后重试"),

    
    REQUEST_IS_FAIL(401, "请求失败，请重新登录"),

    LOGIN_IS_FAIL(402, "账号名或密码错误"),

    Exec_FAILTER(417,"执行失败"),

    QRCODE_FAIL(405,"二维码失效"),

    INSUFFICIENT_PRIVILEGEX(428,"权限不足"),

    ILLEGAL_ARGUMENT(406,"参数传递错误"),

    SYDYE_OK(418, "该学生已报道"),

    UNKONW_IDCARD(421,"没有您的身份信息"),

    CARD_ERROR(422,"身份信息错误"),

    ALREADY_REGISTER(419,"该生已注册"),

    NO_STUDENT(420,"暂时查询不到学生信息"),





    /**
     * code无效
     */
    CODEISFAIL(408, "code无效"),

    /**
     * 没有该openid
     */
    NOOPENID(409, "没有获得该微信用户的openid"),

    /**
     * 只能为好友祝福一次哦！
     */
    HASWISHED(407, "只能为好友祝福一次哦！"),


    ;


    private int code;
    private String msg;


}
