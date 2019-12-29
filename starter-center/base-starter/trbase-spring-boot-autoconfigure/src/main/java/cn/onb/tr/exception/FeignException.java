package cn.onb.tr.exception;

/**
 * @Description: (onb)->
 * @Author: 、心
 * @Date: 19/12/19 15:39
 */
public class FeignException extends RuntimeException{
    private static final long serialVersionUID = 6025157060827881137L;

    public FeignException(String message) {
        super(message);
    }
}
