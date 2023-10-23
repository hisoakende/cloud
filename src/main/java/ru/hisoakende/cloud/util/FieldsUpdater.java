package ru.hisoakende.cloud.util;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;

public class FieldsUpdater<Entity> {

    private final Entity entity;
    private final Map<String, Object> fields;

    public FieldsUpdater(Entity entity, Map<String, Object> fields) {
        this.entity = entity;
        this.fields = fields;
    }

    public Entity update() {
        for (Map.Entry<String, Object> entry : fields.entrySet()) {
            String fieldName = entry.getKey();
            Object fieldValue = entry.getValue();

            Field field = ReflectionUtils.findField(entity.getClass(), fieldName);
            field.setAccessible(true);
            ReflectionUtils.setField(field, entity, fieldValue);
        }
        return entity;
    }
}

