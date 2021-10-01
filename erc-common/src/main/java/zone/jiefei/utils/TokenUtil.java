package zone.jiefei.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ClassName TokenUtil
 * @Description TODO
 * @Author zhangpeitao
 * @Date 2020/8/23 23:15
 * @VERSION 1.0
 */
@Component
public class TokenUtil {


    @Value("${token.startWith}")
    private String startWith;


    public String createToken() {
        String encode = Base64Util.encode(System.currentTimeMillis() + "");
        return encode;
    }

    public Boolean isExpire( String token ) {
        //时间为一个月
        long monthTime = 2592000000L;
        //如果token字符串发生错误
        try {
            Long tokenDate = Long.valueOf(Base64Util.decode(token));
            return (System.currentTimeMillis() - tokenDate) >= monthTime;
        }catch (Exception ignored){
        }
        return true;
//        return (System.currentTimeMillis() - tokenDate) >= monthTime;
    }

}
