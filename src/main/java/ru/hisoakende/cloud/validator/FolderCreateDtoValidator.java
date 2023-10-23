package ru.hisoakende.cloud.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
import ru.hisoakende.cloud.dto.FolderCreateDto;
import ru.hisoakende.cloud.exception.EntityNotFoundException;
import ru.hisoakende.cloud.service.FolderService;
import ru.hisoakende.cloud.validator.constraint.FolderCreateDtoConstraint;

import java.util.UUID;

@Component
public class FolderCreateDtoValidator implements ConstraintValidator<FolderCreateDtoConstraint, FolderCreateDto> {

    private final FolderService folderService;

    public FolderCreateDtoValidator(FolderService folderService) {
        this.folderService = folderService;
    }

    @Override
    public boolean isValid(FolderCreateDto FolderCreateDto, ConstraintValidatorContext constraintValidatorContext) {
        UUID parentFolderId = FolderCreateDto.getParentFolderId();
        try {
            folderService.getById(parentFolderId);
        } catch (EntityNotFoundException e) {
            return false;
        }
        return folderService.isUniqueDirectoryFromParent(FolderCreateDto.getName(), parentFolderId);
    }

}
