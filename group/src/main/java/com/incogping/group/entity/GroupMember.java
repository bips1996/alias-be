package com.incogping.group.entity;

import jakarta.persistence.*;

@Entity(name = "group_members")
public class GroupMember extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", referencedColumnName = "id", nullable = false)
    private Group group;

    @Column(nullable = false)
    private Long userId;


    public GroupMember() {
    }

    public GroupMember(Long userId, String anonymousName, Group group) {
        this.userId = userId;
        this.group = group;
    }

    // Getters and Setters

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
