package com.incogping.group.dto;

import java.util.UUID;

public class AddMemberRequest {
    private Long userId;
    private String anonymousName;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAnonymousName() {
        return anonymousName;
    }

    public void setAnonymousName(String anonymousName) {
        this.anonymousName = anonymousName;
    }
}