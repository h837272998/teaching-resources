package cn.onb.tr.auth.core.validate.code;

import cn.onb.tr.auth.core.properties.CoreConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.concurrent.TimeUnit;

/**
 * @Description: hbanana--基于redis的验证码存取器，避免由于没有session导致无法存取验证码的问题
 * @Author: 、心
 * @Date: 2019/10/31 17:29
 */
@Slf4j
@Component
public class RedisValidateCodeRepository implements ValidateCodeRepository {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;
//    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Override
    public void save(ServletWebRequest request, AbstractValidateCode code, ValidateCodeType validateCodeType) {
        String key = buildKey(request, validateCodeType);
        if (StringUtils.isBlank(key)) {
            throw new ValidateCodeException("验证码请求错误！");
        }
        redisTemplate.opsForValue().set(key, code, 10, TimeUnit.MINUTES);
        log.info("redis写入{} : {}", key, code);
    }

    private String buildKey(ServletWebRequest request, ValidateCodeType validateCodeType) {
//        String deviceId = request.getHeader("deviceId");
//        log.info("保存的请求 {}", request.toString());
        String key = "";
        String formatKey = "code:%s:%s";
        //手机验证码
        if (validateCodeType.equals(ValidateCodeType.SMS)) {
            String mobel = request.getParameter(CoreConstant.DEFAULT_PARAMETER_NAME_MOBILE);
            if (StringUtils.isBlank(mobel)) {
                throw new ValidateCodeException("没有获得手机号!");
            }
            key = String.format(formatKey, validateCodeType.toString().toLowerCase(), mobel);
            //图形验证码
        } else if (validateCodeType.equals(ValidateCodeType.IMAGE)) {
            String tmpKey = request.getParameter("key");
            key = String.format(formatKey, validateCodeType.toString().toLowerCase(), tmpKey);
        } else {
            throw new ValidateCodeException("未能识别的验证码类型!");
        }
        return key;
    }

    @Override
    public AbstractValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType) {
        Object value = redisTemplate.opsForValue().get(buildKey(request, validateCodeType));
        if (value == null) {
            return null;
        }
        return (AbstractValidateCode) value;
    }

    @Override
    public void remove(ServletWebRequest request, ValidateCodeType codeType) {
        redisTemplate.delete(buildKey(request, codeType));
    }
}
