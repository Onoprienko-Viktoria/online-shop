package com.onoprienko.onlineshop.security;

import com.onoprienko.onlineshop.security.entity.EncodeInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordEncoderTest {
    //already checked password, encoded password and sole
    EncodeInfo verifiedEncodeInfo = EncodeInfo.builder()
            .encodedPassword("70bdeae2a2b72c7991d36ee1b6f792a1")
            .sole("21675f33-8b4c-45b5-83a0-0e5f32ec364b")
            .decodedPassword("1234")
            .build();

    //random password and sole
    EncodeInfo randomEncodeInfo = EncodeInfo.builder()
            .encodedPassword("dsnfjdsnfjdsnfjsdnf213ddsfds")
            .sole("12314fvf-8b4c-45b5-83a0-0e5f32ec364b")
            .decodedPassword("1234")
            .build();

    @Test
    void generateSoleAndPass() {
        EncodeInfo result = PasswordEncoder.generateSoleAndPass("1234");

        assertNotNull(result);

        assertNotNull(result.getSole());
        assertNotNull(result.getEncodedPassword());
        assertNull(result.getDecodedPassword());

        result.setDecodedPassword("1234");
        assertTrue(PasswordEncoder.verifyUserPass(result));
    }

    @Test
    void verifyUserPassReturnsTrue() {
        assertTrue(PasswordEncoder.verifyUserPass(verifiedEncodeInfo));
    }

    @Test
    void verifyUserPassReturnsFalse() {
        assertFalse(PasswordEncoder.verifyUserPass(randomEncodeInfo));
    }
}