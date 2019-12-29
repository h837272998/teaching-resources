package cn.onb.tr.autoconfigure;

import cn.onb.tr.support.RespBean;
import cn.onb.tr.support.RespUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Description: (onb)->全局的异常处理
 * @Author: 、心
 * @Date: 19/12/11 13:54
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理表达验证。javax...valid
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RespBean handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return RespUtil.failed(ex.getBindingResult().getFieldError().getDefaultMessage());
    }

    /**
     * Exception
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RespBean handleException(Exception ex) {
        log.info(ex.getMessage());
        return RespUtil.failed("服务器繁忙！");
    }
}
