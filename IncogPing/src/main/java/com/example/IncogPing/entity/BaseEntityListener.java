package com.example.IncogPing.entity;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;


public class BaseEntityListener {

    @PrePersist
    public void onPrePersist(BaseEntity entity) {
        entity.prePersist();
    }

    @PreUpdate
    public void onPreUpdate(BaseEntity entity) {
        entity.preUpdate();
    }
}
