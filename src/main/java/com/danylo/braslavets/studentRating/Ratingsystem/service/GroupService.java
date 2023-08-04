package com.danylo.braslavets.studentRating.Ratingsystem.service;

import com.danylo.braslavets.studentRating.Ratingsystem.model.StudentGroup;
import com.danylo.braslavets.studentRating.Ratingsystem.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    private final GroupRepository groupRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public List<StudentGroup> getAllGroups() {
        return groupRepository.findAll();
    }

    public StudentGroup getGroupById(Long id) {
        return groupRepository.findById(id).orElse(null);
    }

    public StudentGroup saveGroup(StudentGroup studentGroup) {
        return groupRepository.save(studentGroup);
    }

    public void deleteGroup(Long id) {
        groupRepository.deleteById(id);
    }

    public double calculateGroupRating(Long groupId) {
        StudentGroup studentGroup = getGroupById(groupId);
        if (studentGroup == null) {
            throw new IllegalArgumentException("Group not found with ID: " + groupId);
        }

        return studentGroup.groupRating();
    }
}
