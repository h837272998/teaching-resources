package cn.onb.tr.auth.core.properties;

import lombok.Data;

/**
 * @Description: hbanana--继承SmsCodeProperties
 * @Author: 、心
 * @Date: 2019/10/31 16:57
 */
@Data
public class ImageCodeProperties extends SmsCodeProperties{
    public ImageCodeProperties() {
        setLength(4);
    }

    /**
     * 图片宽
     */
    private int width = 67;
    /**
     * 图片高
     */
    private int height = 23;
}
