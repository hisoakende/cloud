package ru.hisoakende.cloud.validator;

import java.util.Map;
import java.util.Objects;

public class ObjectUpdateDtoValidator<Entity, Service> {

    protected final Service entityService;

    public ObjectUpdateDtoValidator(Service entityService) {
        this.entityService = entityService;
    }

    public boolean isValid(Entity entity, Map<String, Object> fields) {
        if (!validateExtraField(entity, fields)) {
            return false;
        }
        return validate(entity, fields);
    }

    private boolean validateExtraField(Entity entity, Map<String, Object> fields) {
        for (String key : fields.keySet()) {
            try {
                entity.getClass().getDeclaredField(key);
            } catch (NoSuchFieldException e) {
                return false;
            }
        }
        return true;
    }

    protected boolean validate(Entity entity, Map<String, Object> fields) {
        return true;
    }
}
