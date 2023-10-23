package ru.hisoakende.cloud.controller;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.hisoakende.cloud.dto.FolderCreateDto;
import ru.hisoakende.cloud.dto.FolderReadDto;
import ru.hisoakende.cloud.entity.Folder;
import ru.hisoakende.cloud.mapper.FolderMapper;
import ru.hisoakende.cloud.service.FolderService;
import ru.hisoakende.cloud.util.EntityFinder;
import ru.hisoakende.cloud.util.URIBuilder;
import ru.hisoakende.cloud.validator.FolderUpdateDtoValidator;

import java.net.URI;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("folders/")
@Validated
public class FolderController {

    private final FolderService folderService;
    private final FolderMapper folderMapper;
    private final EntityFinder<Folder, UUID> entityFinder;
    private final FolderUpdateDtoValidator folderUpdateDtoValidator;

    public FolderController(FolderService folderService,
                            FolderMapper folderMapper,
                            EntityFinder<Folder, UUID> entityFinder,
                            FolderUpdateDtoValidator folderUpdateDtoValidator) {
        this.folderService = folderService;
        this.folderMapper = folderMapper;
        this.entityFinder = entityFinder;
        this.folderUpdateDtoValidator = folderUpdateDtoValidator;
    }

    @GetMapping("/{uuid}/")
    public ResponseEntity<FolderReadDto> getFolder(@PathVariable UUID uuid) {
        Folder folder = entityFinder.findEntityOr404(folderService, uuid);
        FolderReadDto folderReadDto = folderMapper.FolderToFolderReadDto(folder);
        return ResponseEntity.ok().body(folderReadDto);
    }

    @PostMapping()
    public ResponseEntity<FolderReadDto> createFolder(@Valid @RequestBody FolderCreateDto folderCreateDto) {
        Folder folder = folderMapper.FolderCreateDtoToFolder(folderCreateDto);
        folder = folderService.create(folder);
        FolderReadDto folderReadDto = folderMapper.FolderToFolderReadDto(folder);
        URI location = new URIBuilder<>(folderReadDto.getUuid()).build();
        return ResponseEntity.created(location).body(folderReadDto);
    }

    @PatchMapping("/{uuid}/")
    public ResponseEntity<FolderReadDto> updateFolder(@PathVariable UUID uuid,
                                               @RequestBody Map<String, Object> fields) {
        // TODO: Запретить изменение корневой директории
        // TODO: Переделать валидацию на dto
        // TODO: Перенос папки вынести в отдельный метод
        Folder folder = entityFinder.findEntityOr404(folderService, uuid);
        if (!folderUpdateDtoValidator.isValid(folder, fields)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        folder = folderService.update(folder, fields);
        FolderReadDto folderReadDto = folderMapper.FolderToFolderReadDto(folder);
        return ResponseEntity.ok().body(folderReadDto);
    }

    @DeleteMapping("/{uuid}/")
    public ResponseEntity<?> deleteFolder(@PathVariable UUID uuid) {
        // TODO: Запретить удаление корневой директории
        // TODO: Рекурсивно удалять все файлы
        Folder folder = entityFinder.findEntityOr404(folderService, uuid);
        folderService.delete(folder);
        return ResponseEntity.noContent().build();
    }
}
