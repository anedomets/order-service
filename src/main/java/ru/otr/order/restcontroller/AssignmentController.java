package ru.otr.order.restcontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otr.order.dto.AssignmentDto;
import ru.otr.order.service.AssignmentService;

import java.util.List;

import static ru.otr.order.mapper.AssignmentMapper.ASSIGMENT_MAPPER;

@RestController
@RequestMapping(path = "/api/assignment")
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentService assignmentService;

    @GetMapping
    public ResponseEntity<List<AssignmentDto>> findAll() {
        return ResponseEntity.ok(ASSIGMENT_MAPPER.toAssignmentDtoList(assignmentService.findAll()));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<AssignmentDto> findById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(ASSIGMENT_MAPPER.toAssignmentDto(assignmentService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<AssignmentDto> save(@RequestBody AssignmentDto assignmentDto) {
        return ResponseEntity.ok(ASSIGMENT_MAPPER.toAssignmentDto(assignmentService.save(ASSIGMENT_MAPPER.toAssignment(assignmentDto))));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteById(@PathVariable(name = "id") Long id) {
        assignmentService.deleteById(id);
        return ResponseEntity.ok("ok");
    }
}
