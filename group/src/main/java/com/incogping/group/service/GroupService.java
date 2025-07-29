package com.incogping.group.service;

import com.incogping.group.dto.AddMemberRequest;
import com.incogping.group.dto.GroupRequest;
import com.incogping.group.entity.Group;

import java.util.List;
import java.util.UUID;

public interface GroupService {
    public Group createGroup(GroupRequest request);
    Group addMember(UUID groupId, AddMemberRequest request);
    List<Group> getAllGroups();
    Group getGroup(UUID groupId);
}
