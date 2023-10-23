package ru.hisoakende.cloud.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.hisoakende.cloud.dto.FolderCreateDto;
import ru.hisoakende.cloud.dto.FolderReadDto;
import ru.hisoakende.cloud.entity.Folder;

import java.util.ArrayList;


@Component
public class FolderMapper {
    private final ModelMapper modelMapper;

    public FolderMapper() {
        modelMapper = new ModelMapper();
    }

    public Folder FolderCreateDtoToFolder(FolderCreateDto folderCreateDto) {
        Folder folder = modelMapper.map(folderCreateDto, Folder.class);
        Folder mockParentFolder = new Folder();
        mockParentFolder.setUuid(folderCreateDto.getParentFolderId());
        folder.setParentFolder(mockParentFolder);
        return folder;
    }

    public FolderReadDto FolderToFolderReadDto(Folder folder) {
        FolderReadDto folderReadDto = modelMapper.map(folder, FolderReadDto.class);
        Folder parentFolder = folder.getParentFolder();
        if (parentFolder != null) {
            folderReadDto.setParentFolderId(parentFolder.getUuid());
        }
        if (folder.getChildFolders() == null) {
            folderReadDto.setChildFolders(new ArrayList<>());
        }
        return folderReadDto;
    }
}