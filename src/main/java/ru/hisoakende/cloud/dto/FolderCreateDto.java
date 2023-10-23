package ru.hisoakende.cloud.dto;

import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.hisoakende.cloud.validator.constraint.FolderCreateDtoConstraint;
import ru.hisoakende.cloud.validator.validation_group.FirstOrder;

import java.util.UUID;

@Data
@GroupSequence({FirstOrder.class, FolderCreateDto.class})
@FolderCreateDtoConstraint
public class FolderCreateDto {

    @NotNull(groups = FirstOrder.class)
    private String name;

    @NotNull(groups = FirstOrder.class)
    private UUID parentFolderId;

}
