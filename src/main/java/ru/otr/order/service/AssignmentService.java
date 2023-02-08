package ru.otr.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otr.order.entity.Assignment;
import ru.otr.order.repository.AssignmentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;

    public List<Assignment> findAll() {
        return assignmentRepository.findAll();
    }

    public Assignment findById(Long id) {
        return assignmentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found entity by id = " + id));
    }

    public Assignment save(Assignment assignment) {
        return assignmentRepository.save(assignment);
    }

    public void deleteById(Long id) {
        if (assignmentRepository.existsById(id)) {
            assignmentRepository.deleteById(id);
        }
    }
}
