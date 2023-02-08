package ru.otr.order.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.otr.order.dto.AssignmentDto;
import ru.otr.order.entity.Assignment;

import java.util.Collection;
import java.util.List;

@Mapper
public interface AssignmentMapper {

    AssignmentMapper ASSIGMENT_MAPPER = Mappers.getMapper(AssignmentMapper.class);

    Assignment toAssignment(AssignmentDto assignmentDto);

    AssignmentDto toAssignmentDto(Assignment assignment);

    List<AssignmentDto> toAssignmentDtoList(Collection<Assignment> collection);

}
