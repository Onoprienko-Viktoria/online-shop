package com.onoprienko.onlineshop.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordEncoderTest {
    //already checked password, encoded password and sole
    PasswordEncoder.EncodeInfo verifiedEncodeInfo = PasswordEncoder.EncodeInfo.builder()
            .encodedPassword("70bdeae2a2b72c7991d36ee1b6f792a1")
            .sole("21675f33-8b4c-45b5-83a0-0e5f32ec364b")
            .decodedPassword("1234")
            .build();

    //rendom password and sole
    PasswordEncoder.EncodeInfo randomEncodeInfo = PasswordEncoder.EncodeInfo.builder()
            .encodedPassword("dsnfjdsnfjdsnfjsdnf213ddsfds")
            .sole("12314fvf-8b4c-45b5-83a0-0e5f32ec364b")
            .decodedPassword("1234")
            .build();

    @Test
    void generateSoleAndPass() {
        PasswordEncoder.EncodeInfo result = PasswordEncoder.generateSoleAndPass("1234");

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