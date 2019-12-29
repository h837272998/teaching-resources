package cn.onb.tr.auth.core.validate.code;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description: hbanana--抽象的验证码对象
 * @Author: 、心
 * @Date: 2019/10/31 17:28
 */
@Data
public abstract class AbstractValidateCode implements Serializable {
    private String code;
    private LocalDateTime expireTime;

    public AbstractValidateCode(String code, LocalDateTime expireTime) {
        this.code = code;
        this.expireTime = expireTime;
    }

    public AbstractValidateCode(String code, int expireIn) {
        this.code=code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    public boolean isExpried() {
        return LocalDateTime.now().isAfter(expireTime);
    }
}
