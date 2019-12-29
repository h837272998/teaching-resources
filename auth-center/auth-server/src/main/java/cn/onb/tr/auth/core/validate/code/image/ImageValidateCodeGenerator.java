package cn.onb.tr.auth.core.validate.code.image;

import cn.onb.tr.auth.core.properties.CoreProperties;
import cn.onb.tr.auth.core.validate.code.AbstractValidateCode;
import cn.onb.tr.auth.core.validate.code.ValidateCodeGenerator;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @Description: hbanana--
 * @Author: 、心
 * @Date: 2019/10/31 17:34
 */
public class ImageValidateCodeGenerator implements ValidateCodeGenerator {

    @Autowired
    private CoreProperties coreProperties;


    @Override
    public AbstractValidateCode generator(ServletWebRequest request) {
        //获得请求的宽度和高度
        int width = ServletRequestUtils.getIntParameter(request.getRequest(),"width",coreProperties.getCode().getImage().getWidth());
        int height = ServletRequestUtils.getIntParameter(request.getRequest(),"height",coreProperties.getCode().getImage().getHeight());

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);

        Graphics g = image.getGraphics();

        Random random = new Random();
        g.setColor(getRandColor(220,250));
        g.fillRect(0,0,width,height);
        g.setFont(new Font("Fixedsys", Font.ITALIC, height-5));
        g.setColor(getRandColor(160,200));
        //设置干扰
        for (int i = 0; i < RandomUtils.nextInt(100,150); i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int x1 = random.nextInt(12);
            int y1 = random.nextInt(12);
            g.setColor(getRandColor(1, 255));
            g.drawLine(x, y, x+x1, y+y1);
        }
        String sRand = "";
        for (int i = 0; i < coreProperties.getCode().getImage().getLength(); i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
            g.setColor(new Color(20 + random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
            g.drawString(rand,13*i+6 ,16);
        }

        g.dispose();

        return new ImageValidateCode(sRand, coreProperties.getCode().getImage().getExpireIn(), image);
    }

    private Color getRandColor(int i, int i1) {
        Random random = new Random();
        if (i>255){
            i = 255;
        }
        if (i1>255){
            i1=255;
        }
        int r = random.nextInt(i1-i)+i;
        int g = random.nextInt(i1-i)+i;
        int b = random.nextInt(i1-i)+i;
        return new Color(r, g, b);
    }

}
