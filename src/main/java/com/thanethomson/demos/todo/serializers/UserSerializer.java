package com.thanethomson.demos.todo.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.impl.UnwrappingBeanSerializer;
import com.fasterxml.jackson.databind.ser.std.BeanSerializerBase;
import com.fasterxml.jackson.databind.util.NameTransformer;
import com.thanethomson.demos.todo.models.User;
import com.thanethomson.demos.todo.utils.DateTimeUtil;

import java.io.IOException;

public class UserSerializer extends UnwrappingBeanSerializer {

    public UserSerializer(BeanSerializerBase src, NameTransformer transformer) {
        super(src, transformer);
    }

    @Override
    public JsonSerializer<Object> unwrappingSerializer(NameTransformer transformer) {
        return new UserSerializer(this, transformer);
    }

    @Override
    protected void serializeFields(Object bean, JsonGenerator gen, SerializerProvider provider) throws IOException {
        User user = (User)bean;
        gen.writeNumberField("id", user.id);
        gen.writeStringField("created", DateTimeUtil.ISO8601_FORMAT.format(user.created));
        gen.writeStringField("updated", DateTimeUtil.ISO8601_FORMAT.format(user.updated));
        gen.writeStringField("email", user.email);
        gen.writeStringField("firstName", user.firstName);
        gen.writeStringField("lastName", user.lastName);
    }

    @Override
    public boolean isUnwrappingSerializer() {
        return true;
    }

}
