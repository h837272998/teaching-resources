package cn.onb.tr.auth.core.validate.code;

import cn.onb.tr.auth.core.properties.CoreConstant;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description: hbanana--
 * @Author: 、心
 * @Date: 2019/10/31 17:30
 */
@RestController
public class ValidateCodeController {

    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;

    /**
     * 获得短信验证码接口
     * @param request
     * @param response
     * @param type
     * @throws Exception
     */
    @ApiOperation(value = "获得验证码")
    @ApiImplicitParam(name = "type" , value = "验证码类型",dataType = "String",required = true)
    @GetMapping(CoreConstant.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/{type}")
    public void createCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type) throws Exception {
        validateCodeProcessorHolder.findValidateCodeProcessor(type)
                .create(new ServletWebRequest(request,response));
    }

}
