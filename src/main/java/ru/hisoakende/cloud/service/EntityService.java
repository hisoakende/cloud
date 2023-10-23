package ru.hisoakende.cloud.service;

import ru.hisoakende.cloud.exception.EntityNotFoundException;

public interface EntityService<Entity, IdType> {

    Entity getById(IdType id) throws EntityNotFoundException;

    Entity create(Entity entity);

    void delete(Entity entity);
}
