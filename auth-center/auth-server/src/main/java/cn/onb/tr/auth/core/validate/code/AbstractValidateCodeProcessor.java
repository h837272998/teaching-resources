package cn.onb.tr.auth.core.validate.code;

import cn.onb.tr.auth.core.validate.code.sms.SmsValidateCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

/**
 * @Description: hbanana--抽象的验证码处理器
 * @Author: 、心
 * @Date: 2019/10/31 17:28
 */
@Slf4j
public abstract class AbstractValidateCodeProcessor<T extends AbstractValidateCode> implements ValidateCodeProcessor {


    /**
     * 收集系统中所有的 {@link ValidateCodeGenerator} 接口的实现。Spring依赖查找
     */
    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGenerators;


    @Autowired
    private ValidateCodeRepository validateCodeRepository;

    @Override
    public void create(ServletWebRequest request) throws Exception {
        //创建Code
        T validateCode = generate(request);
        //保存验证码（redis？ session）
        save(request, validateCode);
        //发送验证码
        send(request, validateCode);
    }

    @Override
    public void validate(ServletWebRequest servletWebRequest) {

        ValidateCodeType codeType = getValidateCodeType(servletWebRequest);

        T codeInSession = (T) validateCodeRepository.get(servletWebRequest, codeType);

        String codeInRequest;

        try {
            codeInRequest = ServletRequestUtils.getStringParameter(servletWebRequest.getRequest(),
                    codeType.getParamNameOnValidate());
        } catch (ServletRequestBindingException e) {
            throw new ValidateCodeException("获取验证码的值失败");
        }

        //FIXME 开发中验证码9999就通过
        if ("9999".equals(codeInRequest)) {
            return;
        }

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException(codeType + "验证码的值不能为空");
        }

        if (codeInSession == null) {
            throw new ValidateCodeException(codeType + "验证码不存在");
        }

        if (codeInSession.isExpried()) {
            validateCodeRepository.remove(servletWebRequest, codeType);
            throw new ValidateCodeException(codeType + "验证码已过期");
        }

        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException(codeType + "验证码不匹配");
        }

        log.info("移除image验证");
        validateCodeRepository.remove(servletWebRequest, codeType);
    }

    /**
     * 发送校检码，抽象方法，子类自己实现
     * @param request
     * @param validateCode
     * @throws Exception
     */
    protected abstract void send(ServletWebRequest request, T validateCode) throws Exception;

    /**
     * 保存校检码
     * @param request
     * @param validateCode
     */
    private void save(ServletWebRequest request, T validateCode) {
        //使用sms 不需要储存iamge 只需要校检码和过期时间
        AbstractValidateCode code = new SmsValidateCode(validateCode.getCode(), validateCode.getExpireTime());
        validateCodeRepository.save(request, code, getValidateCodeType(request));
    }


    /**
     * @Description:寻找对应校检码生成器，并生成验证码对象
     * @Author: HJH
     * @Date: 2019-08-20 21:21
     * @Param: [request]
     * @Return: C
     */
    private T generate(ServletWebRequest request) throws ValidateCodeException {
        String type = getValidateCodeType(request).toString().toLowerCase();
        String generatorName = type + ValidateCodeGenerator.class.getSimpleName();
        ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generatorName);
        if (null == validateCodeGenerator) {
            throw new ValidateCodeException("验证码生成器：" + generatorName + "不存在");
        }
        return (T) validateCodeGenerator.generator(request);
    }

    /**
     * 根据生成类名。判断校检码类型
     * @param request
     * @return
     */
    private ValidateCodeType getValidateCodeType(ServletWebRequest request) {
        String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
        return ValidateCodeType.valueOf(type.toUpperCase());
    }
}
