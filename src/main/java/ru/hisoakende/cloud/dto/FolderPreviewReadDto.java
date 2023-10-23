package ru.hisoakende.cloud.dto;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class FolderPreviewReadDto {

    private UUID uuid;

    private String name;

    private Date createdAt;

    private Date updatedAt;
}
