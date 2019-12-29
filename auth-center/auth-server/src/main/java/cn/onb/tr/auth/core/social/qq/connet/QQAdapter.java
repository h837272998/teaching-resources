package cn.onb.tr.auth.core.social.qq.connet;

import cn.onb.tr.auth.core.social.qq.api.QQ;
import cn.onb.tr.auth.core.social.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * @Description: hbanana--
 * @Author: 、心
 * @Date: 2019/11/3 15:07
 */
public class QQAdapter implements ApiAdapter<QQ> {
    /**
     * 测试是否连通
     * @param qq
     * @return
     */
    @Override
    public boolean test(QQ qq) {
        return true;
    }

    @Override
    public void setConnectionValues(QQ qq, ConnectionValues connectionValues) {
        QQUserInfo userInfo = qq.getUserInfo();
        connectionValues.setDisplayName(userInfo.getNickname());
        connectionValues.setImageUrl(userInfo.getFigureurl_qq_1());
        connectionValues.setProfileUrl(null);
        connectionValues.setProviderUserId(userInfo.getOpenId());

    }

    /**
     * 解绑
     * @param qq
     * @return
     */
    @Override
    public UserProfile fetchUserProfile(QQ qq) {
        return null;
    }

    /**
     * 更新
     * @param qq
     * @param s
     */
    @Override
    public void updateStatus(QQ qq, String s) {

    }
}
