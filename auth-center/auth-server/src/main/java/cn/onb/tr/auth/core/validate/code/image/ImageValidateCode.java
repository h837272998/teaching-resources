package cn.onb.tr.auth.core.validate.code.image;

import cn.onb.tr.auth.core.validate.code.AbstractValidateCode;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @Description: hbanana--
 * @Author: 、心
 * @Date: 2019/10/31 17:34
 */
public class ImageValidateCode extends AbstractValidateCode {

    private BufferedImage image;

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public ImageValidateCode(String code, LocalDateTime expireTime, BufferedImage image) {
        super(code, expireTime);
        this.image = image;
    }

    public ImageValidateCode(String code, int expireIn, BufferedImage image) {
        super(code, expireIn);
        this.image = image;
    }
}
