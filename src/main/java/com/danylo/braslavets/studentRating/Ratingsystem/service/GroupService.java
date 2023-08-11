package com.danylo.braslavets.studentRating.Ratingsystem.service;

import com.danylo.braslavets.studentRating.Ratingsystem.exception.GroupNotFoundException;
import com.danylo.braslavets.studentRating.Ratingsystem.exception.StudentNotFoundException;
import com.danylo.braslavets.studentRating.Ratingsystem.model.Group;
import com.danylo.braslavets.studentRating.Ratingsystem.model.Student;
import com.danylo.braslavets.studentRating.Ratingsystem.repository.GroupRepository;
import com.danylo.braslavets.studentRating.Ratingsystem.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupService {

    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    public Group getGroupById(final Long id) throws GroupNotFoundException {
        return groupRepository.findById(id)
                .orElseThrow(() -> new GroupNotFoundException(
                        String.format("There is no group with id = %s", id)));
    }

    public Group saveGroup(final Group group) {
        return groupRepository.save(group);
    }

    public void deleteGroup(final Long id) throws GroupNotFoundException {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new GroupNotFoundException(
                        String.format("There is no group with id = %s", id)));

        List<Student> students = group.getStudents();

        for (Student student : students) {
            student.setGroup(null);
            studentRepository.save(student);
        }

        groupRepository.deleteById(id);
    }


    private Group getGroup(final Long groupId) throws GroupNotFoundException {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException(
                        String.format("There is no group with id = %s", groupId)));
    }

    private Student getStudent(final Long studentId) throws StudentNotFoundException {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(
                        String.format("There is no student with id = %s", studentId)));
    }

    public void addStudentToGroup(final Long groupId, final Long studentId)
            throws GroupNotFoundException, StudentNotFoundException {
        var studentGroup = getGroup(groupId);
        Student student = getStudent(studentId);

        if (studentGroup.getStudents().contains(student)) {
            throw new IllegalArgumentException("Student already in group");
        }

        studentGroup.addStudent(student);
        groupRepository.save(studentGroup);
    }

    public void removeStudentFromGroup(final Long groupId, final Long studentId)
            throws StudentNotFoundException, GroupNotFoundException {
        Group studentGroup = getGroup(groupId);
        Student student = studentGroup.getStudentById(studentId);

        studentGroup.removeStudent(student);
        groupRepository.save(studentGroup);
    }

    public void setGroupLeader(final Long groupId, final Long newGroupLeaderId)
            throws GroupNotFoundException, StudentNotFoundException {
        var studentGroup = getGroup(groupId);
        var groupLeader = studentGroup.getStudentById(newGroupLeaderId);

        studentGroup.setGroupLeader(groupLeader);
        groupRepository.save(studentGroup);
    }

    public List<Student> getStudentsByGroupId(final Long id) throws GroupNotFoundException {
        var group = getGroup(id);
        return group.getStudents();
    }

    public double calculateGroupRating(final Long groupId) throws GroupNotFoundException {
        Group group = getGroupById(groupId);
        if (group == null) {
            throw new IllegalArgumentException("Group not found with ID: " + groupId);
        }

        return group.groupRating();
    }

    public Group updateGroup(final Long id, final Group group) {
        Group groupToUpdate = groupRepository.findById(id).orElseThrow();
        groupToUpdate.setGroupName(group.getGroupName());
        groupToUpdate.setGroupLeader(group.getGroupLeader());
        groupToUpdate.setStudents(group.getStudents());
        return groupRepository.save(groupToUpdate);
    }

    @Transactional
    public void deleteAllGroups() throws GroupNotFoundException {
        for (Group group : groupRepository.findAll()) {
            deleteGroup(group.getId());
        }
    }

    public void removeGroupLeader(final Long groupId) throws GroupNotFoundException {
        Group group = getGroup(groupId);
        group.setGroupLeader(null);
        groupRepository.save(group);
    }
}
