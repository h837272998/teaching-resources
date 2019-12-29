package cn.onb.tr.auth.core.properties;

import lombok.Data;

/**
 * @Description: hbanana--浏览器配置
 * @Author: 、心
 * @Date: 2019/10/31 16:56
 */
@Data
public class BrowserProperties {
    /**
     * 登录页。默认是一个未验证请求。放回json、提示未登录
     */
    private String loginPage = CoreConstant.DEFAULT_UNANTHENTICATION_PAGE_URL;

    /**
     * 登录 post url
     */
    private String signInProcessingUrl = CoreConstant.DEFAULT_SIGN_IN_PROCESSING_URL;

}
