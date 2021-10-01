package zone.jiefei.util;


import zone.jiefei.entity.Admin;
import zone.jiefei.response.ResponseResult;
import zone.jiefei.response.enums.ResponseEnum;

/**
 * @author huzy
 * @date 2020/9/121:02
 */
public class AdminUtils {


    private static ThreadLocal<Admin> userThreadLocal = new ThreadLocal<Admin>();

    public static Admin getLoginUser() {
        Admin admin = userThreadLocal.get();
        return admin;
    }
    public static Object isAdmin() {
        Admin admin = userThreadLocal.get();
        if(admin != null) {
             if(admin.getStatus() != 2){
                return  ResponseResult.error(ResponseEnum.REQUEST_IS_FAIL);
            }
            return admin;
        }
        return ResponseResult.error(ResponseEnum.REQUEST_IS_FAIL);
    }

    /**
     * 获取当前登录用户的ID
     * 未登录返回null
     *
     * @return
     */
    public static Integer getLoginUserId() {

        Admin adminUser = userThreadLocal.get();
        if (adminUser != null && adminUser.getId() != null) {
            return adminUser.getId();
        }
        return null;
    }

    public static void setLoginUser( Admin admin ) {
        userThreadLocal.set(admin);
    }

    public static void removeUser() {
        userThreadLocal.remove();
    }
}
