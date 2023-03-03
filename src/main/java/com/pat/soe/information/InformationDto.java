package com.pat.soe.information;

import lombok.Data;

@Data
public class InformationDto {
    private Long id;
    private String name;
    private ClassMessageDto classMessage;
    private String message;
    private String note;
    private boolean isActive;
}
