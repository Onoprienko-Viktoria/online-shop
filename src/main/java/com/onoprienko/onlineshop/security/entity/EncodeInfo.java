package com.onoprienko.onlineshop.security.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EncodeInfo {
    private String sole;
    private String encodedPassword;
    private String decodedPassword;
}
