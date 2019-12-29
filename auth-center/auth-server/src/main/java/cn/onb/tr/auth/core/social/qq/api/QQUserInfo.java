package cn.onb.tr.auth.core.social.qq.api;

import lombok.Data;

/**
 * @Description: hbanana--
 * @Author: 、心
 * @Date: 2019/11/3 14:29
 */
@Data
public class QQUserInfo {
    /**
     * 返回码
     */
    private String ret;

    private String msg;

    private String is_lost;

    private String nickname;
    private String gender;
    private String province;
    private String city;
    private String year;
    private String constellation;
    private String figureurl;
    private String figureurl_1;
    private String figureurl_2;
    private String figureurl_qq_1;
    private String figureurl_qq_2;
    private String figureurl_qq;
    private String figureurl_type;
    private String is_yellow_vip;
    private String vip;
    private String yellow_vip_level;
    private String level;
    private String is_yellow_year_vip;
    private String openId;

}
