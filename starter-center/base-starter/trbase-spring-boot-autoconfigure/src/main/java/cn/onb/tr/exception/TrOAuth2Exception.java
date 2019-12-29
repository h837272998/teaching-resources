package cn.onb.tr.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

import java.io.IOException;

/**
 * @Description: (onb)->
 * @Author: 、心
 * @Date: 19/12/22 12:50
 */
@JsonSerialize(using = TrOAuth2Exception.MyOAuthExceptionJacksonSerializer.class)
public class TrOAuth2Exception extends OAuth2Exception {

    private static final long serialVersionUID = -760790243416178722L;

    public TrOAuth2Exception(String msg, Throwable t) {
        super(msg, t);
    }

    public TrOAuth2Exception(String msg) {
        super(msg);
    }

    class MyOAuthExceptionJacksonSerializer extends StdSerializer<TrOAuth2Exception> {

        protected MyOAuthExceptionJacksonSerializer() {
            super(TrOAuth2Exception.class);
        }

        @Override
        public void serialize(TrOAuth2Exception value, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException {
            jgen.writeStartObject();
            jgen.writeObjectField("code", value.getHttpErrorCode());
            jgen.writeStringField("msg", value.getSummary());
            jgen.writeEndObject();
        }
    }
}
