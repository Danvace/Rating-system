package com.danylo.braslavets.studentRating.Ratingsystem.controller;

import com.danylo.braslavets.studentRating.Ratingsystem.model.Group;
import com.danylo.braslavets.studentRating.Ratingsystem.model.Student;
import com.danylo.braslavets.studentRating.Ratingsystem.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public ResponseEntity<List<Group>> getAllGroups() {
        List<Group> groups = groupService.getAllGroups();
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Group> getGroupById(@PathVariable Long id) {
        var group = groupService.getGroupById(id);
        return new ResponseEntity<>(group, HttpStatus.OK);
    }

    @PostMapping("/{groupId}/students/{studentId}")
    public void addStudentToGroup(@PathVariable Long groupId, @PathVariable Long studentId) {
        groupService.addStudentToGroup(groupId, studentId);
    }

    @PostMapping("/{groupId}/groupLeader/{studentId}")
    public void addGroupLeaderToGroup(@PathVariable Long groupId, @PathVariable Long studentId) {
        groupService.setGroupLeader(groupId, studentId);
    }


    @DeleteMapping("/{groupId}/students/{studentId}")
    public void removeStudentFromGroup(@PathVariable Long groupId, @PathVariable Long studentId) {
        groupService.removeStudentFromGroup(groupId, studentId);
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<List<Student>> getStudentsByGroupId(@PathVariable Long id) {
        var students = groupService.getStudentsByGroupId(id);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @PostMapping
    public Group saveGroup(@RequestBody Group group) {
        return groupService.saveGroup(group);
    }

    @PutMapping("/{id}")
    public Group updateGroup(@PathVariable Long id, @RequestBody Group group) {
        return groupService.updateGroup(id, group);
    }

    @DeleteMapping("/{id}")
    public void deleteGroup(@PathVariable Long id) {
        groupService.deleteGroup(id);
    }

    @GetMapping("/{id}/rating")
    public double calculateGroupRating(@PathVariable Long id) {
        return groupService.calculateGroupRating(id);
    }
}
