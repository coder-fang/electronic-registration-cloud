package zone.jiefei.response;



import lombok.Data;
import zone.jiefei.response.enums.ResponseEnum;

/**
 * description:对返回前端数据进行封装
 *
 * @author RenShiWei
 * Date: 2020/7/9 22:09
 **/
@Data
public class ResponseResult<T> {

    /**
     * 状态码
     */
    private Integer status;

    /**
     * 状态信息说明
     */
    private String message;

    /**
     * 返回的数据
     */
    private T data;

    public ResponseResult(ResponseEnum responseEnum, T data ) {
        this.status = responseEnum.getCode();
        this.message = responseEnum.getMsg();
        this.data = data;
    }

    public ResponseResult( T data ) {
        this.status = ResponseEnum.SUCCESS.getCode();
        this.message = ResponseEnum.SUCCESS.getMsg();
        this.data = data;
}

    public ResponseResult() {
        this.status = ResponseEnum.SUCCESS.getCode();
        this.message = ResponseEnum.SUCCESS.getMsg();
    }

    public ResponseResult( ResponseEnum responseEnum ) {
        this.status = responseEnum.getCode();
        this.message = responseEnum.getMsg();
    }

    /**
     * description: 抛出异常时调用（默认状态码为500）
     *
     * @param msg 异常信息
     * @return 封装后的异常信息
     * @author RenShiWei
     * Date: 2020/7/10 20:06
     */
    public static ResponseResult<ResponseEnum> error ( String msg ) {
        ResponseResult<ResponseEnum> responseResult = new ResponseResult<>();
        responseResult.setMessage(msg);
        responseResult.setStatus(500);
        return responseResult;
    }

    /**
     * description: 抛出异常时调用（自定义状态码）
     *
     * @param msg 异常信息
     * @return 封装后的异常信息
     * @author RenShiWei
     * Date: 2020/7/10 20:06
     */
    public static ResponseResult<ResponseEnum> error ( Integer status, String msg ) {
        ResponseResult<ResponseEnum> responseResult = new ResponseResult<>();
        responseResult.setMessage(msg);
        responseResult.setStatus(status);
        return responseResult;
    }
    
    /**
     * fetch
     * @Author HuZhiYong
     * @Description
     * @Data 9:04 2020/9/1
     * @param data 异常信息
     * @return 封装后的异常信息
     */
    public static <E> ResponseResult<E> error ( E data ) {
        return new ResponseResult<>(ResponseEnum.SUCCESS, data);
    }


    public static <E> ResponseResult<E> error ( ResponseEnum responseEnum ) {
        return new ResponseResult<>(responseEnum);
    }


    /**
     * fetch
     * @Author HuZhiYong
     * @Description
     * @Data 8:59 2020/9/1
     * @param responseEnum 已知错误
     * @param data 数据
     * @return com.marchsoft.response.ResponseResult<E>
     */
    public static <E> ResponseResult<E> error ( ResponseEnum responseEnum, E data ) {
        return new ResponseResult<>(responseEnum, data);
    }

    public static <E> ResponseResult<E> error () {
        ResponseResult result = new ResponseResult();
        result.setStatus(ResponseEnum.ERROR.getCode());
        result.setMessage(ResponseEnum.ERROR.getMsg());
        return new ResponseResult(){
        };
    }

    /**
     * description: 接口调用成功，返回枚举中自定义的状态码及数据
     *
     * @param responseEnum 自定义枚举 状态码和信息
     * @param data         返回数据
     * @return 枚举中自定义的状态码及数据
     * @author RenShiWei
     * Date: 2020/7/10 19:57
     */
    public static <E> ResponseResult<E> ok ( ResponseEnum responseEnum, E data ) {
        return new ResponseResult<>(responseEnum, data);
    }

    /**
     * description: 接口调用成功，封装返回数据
     *
     * @param data 返回数据
     * @return 封装返回的数据
     * @author RenShiWei
     * Date: 2020/7/10 19:57
     */
    public static <E> ResponseResult<E> ok ( E data ) {
        return new ResponseResult<>(ResponseEnum.SUCCESS, data);
    }

    /**
     * description: 接口调用成功，只返回状态码和msg，没有返回数据
     *
     * @return 只返回状态码和msg，没有返回数据
     * @author RenShiWei
     * Date: 2020/7/10 19:57
     */
    public static <E> ResponseResult<E> ok () {
        return new ResponseResult<>();
    }

    /**
     * description: 接口调用成功，只返回状态码和msg，没有返回数据
     *
     * @return 只返回状态码和msg，没有返回数据
     * @author RenShiWei
     * Date: 2020/7/10 19:57
     */
    public static <E> ResponseResult<E> ok ( ResponseEnum responseEnum) {
        return new ResponseResult<>(responseEnum);
    }



}

