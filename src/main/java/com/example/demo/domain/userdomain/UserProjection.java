package com.example.demo.domain.userdomain;
import java.util.UUID;

public interface UserProjection {
    public UUID getId();
    public String getFirstname();
    public String getLastname();
    public String getEmail();
    public String getPassword();
}