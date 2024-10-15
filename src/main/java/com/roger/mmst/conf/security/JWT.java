package com.roger.mmst.conf.security;

import cn.hutool.core.convert.NumberWithFormat;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.JWTValidator;
import cn.hutool.jwt.signers.AlgorithmUtil;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;
import com.roger.mmst.exception.VerifyException;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 @author Roger Liu
 @date 2024/03/06
 */
@Component
public class JWT {

    private static final byte[] JWT_KEY = "TMST_Trouvaille_Maple_Story_Text".getBytes(StandardCharsets.UTF_8);
    private static final JWTSigner SIGNER = JWTSignerUtil.hs512(JWT_KEY);
    private static final String ID = "id";
    private static final String EXPIRE = "expire_time";

    //有效期30天
    private static final long VALID_TIME = 30 * 24 * 60 * 60 * 1000L;

    public String sign(Long id) {
        return JWTUtil.createToken(Map.of(ID, id, EXPIRE, System.currentTimeMillis() + VALID_TIME), SIGNER);
    }

    public Long verify(String token) {
        final cn.hutool.jwt.JWT jwt = cn.hutool.jwt.JWT.of(token);
        if (!jwt.verify(SIGNER)) {
            throw new VerifyException("token validate failed");
        }
        long expireTime = ((NumberWithFormat) jwt.getPayload(EXPIRE)).longValue();
        if (expireTime < System.currentTimeMillis()) {
            throw new VerifyException("token expired");
        }
        return ((NumberWithFormat) jwt.getPayload(EXPIRE)).longValue();
    }
}
