package ru.hisoakende.cloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hisoakende.cloud.entity.Folder;

import java.util.UUID;

public interface FolderRepository extends JpaRepository<Folder, UUID> {

}
