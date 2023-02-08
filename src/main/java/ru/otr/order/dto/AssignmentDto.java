package ru.otr.order.dto;


import lombok.Data;

@Data
public class AssignmentDto {

    private Long id;
    private String fromUser;
    private String toUser;
    private String description;

}
