package ru.hisoakende.cloud.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class FolderReadDto {

    private UUID uuid;

    private String name;

    private UUID parentFolderId;

    private Date createdAt;

    private Date updatedAt;

    private List<FolderPreviewReadDto> childFolders;
}
