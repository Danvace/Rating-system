package com.danylo.braslavets.studentRating.Ratingsystem.controller;

import com.danylo.braslavets.studentRating.Ratingsystem.exception.GroupNotFoundException;
import com.danylo.braslavets.studentRating.Ratingsystem.exception.StudentNotFoundException;
import com.danylo.braslavets.studentRating.Ratingsystem.model.Group;
import com.danylo.braslavets.studentRating.Ratingsystem.model.Student;
import com.danylo.braslavets.studentRating.Ratingsystem.service.GroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupController {

    private final GroupService groupService;

    @GetMapping
    public ResponseEntity<List<Group>> getAllGroups() {
        List<Group> groups = groupService.getAllGroups();
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Group> getGroupById(@PathVariable final Long id) throws GroupNotFoundException {
        var group = groupService.getGroupById(id);
        return new ResponseEntity<>(group, HttpStatus.OK);
    }

    @GetMapping("/{id}/rating")
    public double calculateGroupRating(@PathVariable final Long id) throws GroupNotFoundException {
        return groupService.calculateGroupRating(id);
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<List<Student>> getStudentsByGroupId(@PathVariable final  Long id) throws GroupNotFoundException {
        var students = groupService.getStudentsByGroupId(id);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @PostMapping("/{groupId}/student/{studentId}")
    public void addStudentToGroup(@PathVariable final Long groupId, @PathVariable final Long studentId)
            throws StudentNotFoundException, GroupNotFoundException {
        groupService.addStudentToGroup(groupId, studentId);
    }

    @PostMapping("/{groupId}/groupLeader/{studentId}")
    public void addGroupLeaderToGroup(@PathVariable final Long groupId, @PathVariable final  Long studentId)
            throws StudentNotFoundException, GroupNotFoundException {
        groupService.setGroupLeader(groupId, studentId);
    }

    @PostMapping
    public Group saveGroup(@Valid @RequestBody final  Group group) {
        return groupService.saveGroup(group);
    }

    @PutMapping("/{id}")
    public Group updateGroup(@PathVariable final  Long id, @Valid @RequestBody final Group group) {
        return groupService.updateGroup(id, group);
    }

    @DeleteMapping("/{groupId}/student/{studentId}")
    public void removeStudentFromGroup(@PathVariable final Long groupId, @PathVariable final Long studentId)
            throws StudentNotFoundException, GroupNotFoundException {
        groupService.removeStudentFromGroup(groupId, studentId);
    }

    @DeleteMapping("/{id}")
    public void deleteGroup(@PathVariable final Long id) throws GroupNotFoundException {
        groupService.deleteGroup(id);
    }

    @DeleteMapping("/admin/deleteAll")
    public void deleteAllGroups() throws GroupNotFoundException {
        groupService.deleteAllGroups();
    }

    @DeleteMapping("/{groupId}/groupLeader")
    public void deleteGroupLeader(@PathVariable final Long groupId) throws GroupNotFoundException {
        groupService.removeGroupLeader(groupId);
    }
}
