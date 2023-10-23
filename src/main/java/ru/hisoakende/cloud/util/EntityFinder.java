package ru.hisoakende.cloud.util;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.hisoakende.cloud.exception.EntityNotFoundException;
import ru.hisoakende.cloud.service.EntityService;

@Component
public class EntityFinder<Entity, IdType> {

    public Entity findEntityOr404(EntityService<Entity, IdType> service, IdType id){
        try {
            return service.getById(id);
        } catch (EntityNotFoundException e) {
            System.out.println("134");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found");
        }
    }
}
