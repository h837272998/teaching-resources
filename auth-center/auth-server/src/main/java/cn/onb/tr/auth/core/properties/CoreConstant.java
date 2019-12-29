package cn.onb.tr.auth.core.properties;

/**
 * @Description: hbanana--
 * @Author: 、心
 * @Date: 2019/10/31 16:56
 */
public interface CoreConstant {
    /**
     * 默认jwtSignInKey
     */
    String DEFAULT_JWT_SIGN_IN_KEY = "HJH_614_LYS";

    /**
     * 保存client的redis key 。使用hash结构存储
     */
    String DEFAULT_CACHE_CLIENT_KEY = "oauth_client_details";

    /**
     * 登录页请求。 一个未登录跳转
     */
    String DEFAULT_UNANTHENTICATION_PAGE_URL = "/auth/require";

    /**
     * 登录post url,使用security默认的
     */
    String DEFAULT_SIGN_IN_PROCESSING_URL = "/login";

    /**
     * 获得验证码请求前缀
     */
    String DEFAULT_VALIDATE_CODE_URL_PREFIX = "/code";

    /**
     * 发送短信验证码，传递手机号的参数的名称
     */
    String DEFAULT_PARAMETER_NAME_MOBILE = "mobile";

    /**
     * 登录post的form的参数名
     */
    String DEFAULT_PARAMETER_NAME_CODE_SMS = "smsCode";
    /**
     * 登录post的form的参数名
     */
    String DEFAULT_PARAMETER_NAME_CODE_IMAGE = "imageCode";
    /**
     * 密码登录表单提交页(用于验证码拦截）
     */
    String DEFAULT_LOGIN_PROCESSING_URL_FORM = "/login";
    /**
     * 手机登录表单url(登录拦截)
     */
    String DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE = "/loginMobile";
}
