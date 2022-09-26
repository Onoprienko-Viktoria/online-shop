package com.onoprienko.onlineshop.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {
    private String name;
    private String email;
    private String password;
    private String role;
    private String sole;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", sole='" + sole + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(getName(), user.getName()) && Objects.equals(getEmail(), user.getEmail()) && Objects.equals(getPassword(), user.getPassword()) && Objects.equals(getRole(), user.getRole()) && Objects.equals(getSole(), user.getSole());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getEmail(), getPassword(), getRole(), getSole());
    }
}
