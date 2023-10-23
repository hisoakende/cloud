package ru.hisoakende.cloud.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.hisoakende.cloud.dao.FolderDao;
import ru.hisoakende.cloud.entity.Folder;
import ru.hisoakende.cloud.exception.EntityNotFoundException;
import ru.hisoakende.cloud.repository.FolderRepository;
import ru.hisoakende.cloud.util.FieldsUpdater;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
@Service
public class FolderService implements EntityService<Folder, UUID> {

    private final FolderRepository folderRepository;
    private final FolderDao folderDao;

    public FolderService(FolderRepository folderRepository, FolderDao folderDao) {
        this.folderRepository = folderRepository;
        this.folderDao = folderDao;
    }

    public Folder getById(UUID uuid) throws EntityNotFoundException {
        Optional<Folder> folder = folderRepository.findById(uuid);
        if (folder.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return folder.get();
    }

    public Folder create(Folder folder) {
        return folderRepository.save(folder);
    }

    public Folder update(Folder folder, Map<String, Object> fields) {
        folder = new FieldsUpdater<>(folder, fields).update();
        return folderRepository.save(folder);
    }

    public void delete(Folder folder) {
        folderRepository.delete(folder);
    }

    public boolean isUniqueDirectoryFromParent(String name, UUID parentFolderId) {
        for (Folder childFolder : findByParentFolder(parentFolderId)) {
            if (name.equals(childFolder.getName())) {
                return false;
            }
        }
        return true;
    }

    public List<Folder> findByParentFolder(UUID parentFolderId) {
        return folderDao.findByParentFolder(parentFolderId);
    }
}
