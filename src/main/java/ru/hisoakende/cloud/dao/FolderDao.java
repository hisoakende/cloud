package ru.hisoakende.cloud.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import ru.hisoakende.cloud.entity.Folder;

import java.util.List;
import java.util.UUID;

@Repository
public class FolderDao {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;
    private final CriteriaQuery<Folder> criteriaQuery;
    private final Root<Folder> root;

    public FolderDao(EntityManager entityManager) {
        this.entityManager = entityManager;
        criteriaBuilder = entityManager.getCriteriaBuilder();
        criteriaQuery = criteriaBuilder.createQuery(Folder.class);
        root = criteriaQuery.from(Folder.class);
    }

    public List<Folder> findByParentFolder(UUID parentFolderId) {
        Predicate condition = criteriaBuilder.equal(root.get("parentFolder").get("uuid"), parentFolderId);
        criteriaQuery.where(condition);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

}
