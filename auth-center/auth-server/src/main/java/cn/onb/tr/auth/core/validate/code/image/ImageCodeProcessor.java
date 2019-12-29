package cn.onb.tr.auth.core.validate.code.image;

import cn.onb.tr.auth.core.validate.code.AbstractValidateCode;
import cn.onb.tr.auth.core.validate.code.AbstractValidateCodeProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;

/**
 * @Description: hbanana--图像验证码发送器
 * @Author: 、心
 * @Date: 2019/10/31 17:34
 */
@Component("imageValidateCodeProcessor")
public class ImageCodeProcessor extends AbstractValidateCodeProcessor {
    @Override
    protected void send(ServletWebRequest request, AbstractValidateCode validateCode) throws Exception {
        request.getResponse().setHeader("Content-Type", "image/png");
        ImageIO.write(((ImageValidateCode)validateCode).getImage(), "JPEG", request.getResponse().getOutputStream());
    }
}
