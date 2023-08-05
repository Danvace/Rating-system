package com.danylo.braslavets.studentRating.Ratingsystem.service;

import com.danylo.braslavets.studentRating.Ratingsystem.exception.GroupNotFoundException;
import com.danylo.braslavets.studentRating.Ratingsystem.model.Group;
import com.danylo.braslavets.studentRating.Ratingsystem.model.Student;
import com.danylo.braslavets.studentRating.Ratingsystem.repository.GroupRepository;
import com.danylo.braslavets.studentRating.Ratingsystem.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository, StudentRepository studentRepository) {
        this.groupRepository = groupRepository;
        this.studentRepository = studentRepository;
    }

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    public Group getGroupById(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new GroupNotFoundException(String.format("There is no group with id = %s", id)));
    }

    public Group saveGroup(Group group) {
        return groupRepository.save(group);
    }

    public void deleteGroup(Long id) {
        groupRepository.deleteById(id);
    }

    private Group getGroup(Long groupId) {
        return groupRepository.findById(groupId).orElseThrow();
    }

    private Student getStudent(Long studentId) {
        return studentRepository.findById(studentId).orElseThrow();
    }

    public void addStudentToGroup(Long groupId, Long studentId) {
        var studentGroup = getGroup(groupId);
        Student student = getStudent(studentId);

        studentGroup.addStudent(student);
        groupRepository.save(studentGroup);
    }

    public void removeStudentFromGroup(Long groupId, Long studentId) {
        var studentGroup = getGroup(groupId);
        var student = getStudent(studentId);

        studentGroup.removeStudent(student);
        groupRepository.save(studentGroup);
    }

    public void setGroupLeader(Long groupId, Long newGroupLeaderId) {
        var studentGroup = getGroup(groupId);
        var groupLeader = getStudent(newGroupLeaderId);

        studentGroup.setGroupLeader(groupLeader);
        groupRepository.save(studentGroup);
    }

    public double calculateGroupRating(Long groupId) {
        Group group = getGroupById(groupId);
        if (group == null) {
            throw new IllegalArgumentException("Group not found with ID: " + groupId);
        }

        return group.groupRating();
    }

    public Group updateGroup(Long id, Group group) {
        Group groupToUpdate = groupRepository.findById(id).orElseThrow();
        groupToUpdate.setGroupName(group.getGroupName());
        groupToUpdate.setGroupLeader(group.getGroupLeader());
        groupToUpdate.setStudents(group.getStudents());
        return groupRepository.save(groupToUpdate);
    }

    public List<Student> getStudentsByGroupId(Long id) {
        var group = getGroup(id);
        return group.getStudents();
    }
}
