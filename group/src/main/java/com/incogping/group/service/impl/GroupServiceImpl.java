package com.incogping.group.service.impl;
import com.incogping.group.dto.AddMemberRequest;
import com.incogping.group.dto.GroupRequest;
import com.incogping.group.entity.Group;
import com.incogping.group.entity.GroupMember;
import com.incogping.group.repository.GroupRepository;
import com.incogping.group.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GroupServiceImpl implements GroupService {
    @Autowired
    private GroupRepository groupRepository;

    public Group createGroup(GroupRequest request) {
        Group group = new Group();
        group.setName(request.getName());
        return groupRepository.save(group);
    }

    public Group addMember(UUID groupId, AddMemberRequest request) {
        Optional<Group> optionalGroup = groupRepository.findById(groupId);
        if (optionalGroup.isPresent()) {
            Group group = optionalGroup.get();
            GroupMember member = new GroupMember(request.getUserId(), request.getAnonymousName(),group);
            group.getMembers().add(member);
            return groupRepository.save(group);
        }
        throw new RuntimeException("Group not found");
    }

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    public Group getGroup(UUID groupId) {
        return groupRepository.findById(groupId).orElseThrow(() -> new RuntimeException("Group not found"));
    }
}

