package com.danylo.braslavets.studentRating.Ratingsystem.controller;

import com.danylo.braslavets.studentRating.Ratingsystem.model.StudentGroup;
import com.danylo.braslavets.studentRating.Ratingsystem.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<StudentGroup> getAllGroups() {
        return groupService.getAllGroups();
    }

    @GetMapping("/{id}")
    public StudentGroup getGroupById(@PathVariable Long id) {
        return groupService.getGroupById(id);
    }

    @PostMapping
    public StudentGroup saveGroup(@RequestBody StudentGroup studentGroup) {
        return groupService.saveGroup(studentGroup);
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
