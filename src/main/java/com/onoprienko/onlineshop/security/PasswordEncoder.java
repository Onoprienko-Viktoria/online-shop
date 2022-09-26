package com.onoprienko.onlineshop.security;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Objects;
import java.util.UUID;

@Slf4j
public class PasswordEncoder {

    public static EncodeInfo generateSoleAndPass(String password) {
        String sole = UUID.randomUUID().toString();
        return EncodeInfo.builder()
                .sole(sole)
                .encodedPassword(DigestUtils.md5Hex(sole + password))
                .build();
    }

    public static boolean verifyUserPass(EncodeInfo encodeInfo) {
        String hashedPassword = DigestUtils.md5Hex(encodeInfo.getSole() + encodeInfo.getDecodedPassword());
        return Objects.equals(hashedPassword, encodeInfo.getEncodedPassword());
    }

    @Getter
    @Setter
    @Builder
    public static class EncodeInfo {
        private String sole;
        private String encodedPassword;
        private String decodedPassword;
    }
}
