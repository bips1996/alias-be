package com.incogping.group.controller;

import com.incogping.group.dto.AddMemberRequest;
import com.incogping.group.dto.GroupRequest;
import com.incogping.group.entity.Group;
import com.incogping.group.service.GroupService;
import com.incogping.group.service.impl.GroupServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping("/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping
    public ResponseEntity<Group> createGroup(@RequestBody GroupRequest request) {
        Group group = groupService.createGroup(request);
        return ResponseEntity.ok(group);
    }

    @PostMapping("/{groupId}/add-member")
    public ResponseEntity<Group> addMember(@PathVariable UUID groupId, @RequestBody AddMemberRequest request) {
        Group group = groupService.addMember(groupId, request);
        return ResponseEntity.ok(group);
    }

    @GetMapping
    public ResponseEntity<List<Group>> getAllGroups() {
        return ResponseEntity.ok(groupService.getAllGroups());
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<Group> getGroup(@PathVariable UUID groupId) {
        return ResponseEntity.ok(groupService.getGroup(groupId));
    }
}
