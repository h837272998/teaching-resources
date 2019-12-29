package cn.onb.tr.auth.core.validate.code;

import cn.onb.tr.auth.core.properties.CoreConstant;

/**
 * @Description: hbanana--
 * @Author: 、心
 * @Date: 2019/10/31 17:33
 */
public enum ValidateCodeType {

    /**
     * 短信验证码请求枚举
     */
    SMS{
        @Override
        public String getParamNameOnValidate() {
            return CoreConstant.DEFAULT_PARAMETER_NAME_CODE_SMS;
        }
    },
    /**
     * 图形验证码请求枚举
     */
    IMAGE{
        @Override
        public String getParamNameOnValidate(){
            return CoreConstant.DEFAULT_PARAMETER_NAME_CODE_IMAGE;
        }
    };

    /**
     * 校验时从请求中获取的参数的名字
     * @return
     */
    public abstract String getParamNameOnValidate();
}
