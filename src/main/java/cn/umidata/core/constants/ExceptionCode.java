package cn.umidata.core.constants;

public enum ExceptionCode {
    
	 //---------------异常枚举列表 开始--------------
    //服务器状态
    SERVICE_SERVER_BUSY("S#10000","服务器繁忙"),
    SERVICE_NETWORK_TIMEOUT("S#10001","网络连接超时"),
    SERVICE_ILLEGAL_ACCESS("S#10002","非法访问"),
    SERVICE_UNAVAILABLE("S#10003","服务不可用"),
    SERVICE_INTERNAL_EXCEPTION("S#10004","服务器内部异常"),
    SERVICE_SESSION_EXPIRED("S#10005","您访问的页面已过期，请重新登录"),
    //请求参数
    REQUEST_PARAMS_MISSING("S#20000","缺少参数"),
    REQUEST_PARAMS_INVALID("S#20001","请求参数无效"),
    REQUEST_PIGINATION_PARAMS_INVALID("S#20003","分页参数无效"),
    //用户登录
	USER_USERNAME_INVALID("B#30000","用户名或密码错误"),
	USER_VERIFY_CODE_INVALID("B#30001","验证码错误，请重新获取"),
	USER_ALREADY_EXIST("B#30002","账号已存在"),
	USER_PASSWORD_INVALID("B#30003","密码错误"),
	USER_NOT_EXIST("B#30004","用户不存在"),
	USER_STATUS_DISABLED("B#30005","账号被禁用"),
	//权限验证
	AUTH_MAC_INCONFORMITY("A#40000","对不起，服务器的MAC地址和授权的MAC地址不一致，请联系厂商授权服务！"),
	AUTH_SERVER_OUT_DATE("A#40001","对不起，服务已经过期，请联系厂商授权服务！"),
	AUTH_EXCEED("A#40002","对不起，服务越权，请联系厂商授权服务！"),
	AUTH_ILLEGAL_LICENSE("A#40003","对不起，非法的授权，请联系厂商授权服务！");
	
    //构造器
    private final String code;
    private final String msg;

    private ExceptionCode(String code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
