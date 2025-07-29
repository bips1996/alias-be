package com.incogping.group.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "groups") // Explicitly specify table name
public class Group extends BaseEntity {

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Size(max = 500)
    @Column(name = "description", length = 500)
    private String description;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "group") // Use 'mappedBy' to indicate the relationship
    private List<GroupMember> members = new ArrayList<>();

    // Getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GroupMember> getMembers() {
        return members;
    }

    public void setMembers(List<GroupMember> members) {
        this.members = members;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Optional: Add convenience methods to manage members
    public void addMember(GroupMember member) {
        members.add(member);
        member.setGroup(this); // Set the reverse side of the relationship
    }

    public void removeMember(GroupMember member) {
        members.remove(member);
        member.setGroup(null); // Set the reverse side of the relationship to null
    }
}
