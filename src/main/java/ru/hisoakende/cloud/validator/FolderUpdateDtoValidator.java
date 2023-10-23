package ru.hisoakende.cloud.validator;

import org.springframework.stereotype.Component;
import ru.hisoakende.cloud.entity.Folder;
import ru.hisoakende.cloud.exception.EntityNotFoundException;
import ru.hisoakende.cloud.service.FolderService;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Component
public class FolderUpdateDtoValidator extends ObjectUpdateDtoValidator<Folder, FolderService> {

    public FolderUpdateDtoValidator(FolderService folderService) {
        super(folderService);
    }

    @Override
    protected boolean validate(Folder folder, Map<String, Object> fields) {
        if (!fields.containsKey("name") && !fields.containsKey("parentFolderId")) {
            return true;
        }

        if ((fields.containsKey("name") && fields.get("name") == null) ||
                (fields.containsKey("parentFolderId") && fields.get("parentFolderId") == null)) {
            return false;
        }

        String name = !fields.containsKey("name") ? folder.getName() : fields.get("name").toString();
        System.out.println(name);
        if (!fields.containsKey("parentFolderId")) {
            UUID parentFolderId = folder.getParentFolder().getUuid();
            return entityService.isUniqueDirectoryFromParent(name, parentFolderId);
        }

        UUID parentFolderId = UUID.fromString(fields.get("parentFolderId").toString());
        try {
            entityService.getById(parentFolderId);
        } catch (EntityNotFoundException e) {
            return false;
        }

        return entityService.isUniqueDirectoryFromParent(name, parentFolderId);
    }
}
