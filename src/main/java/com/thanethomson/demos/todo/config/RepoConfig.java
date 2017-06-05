package com.thanethomson.demos.todo.config;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.ser.impl.UnwrappingBeanSerializer;
import com.fasterxml.jackson.databind.ser.std.BeanSerializerBase;
import com.fasterxml.jackson.databind.util.NameTransformer;
import com.thanethomson.demos.todo.models.User;
import com.thanethomson.demos.todo.serializers.UserSerializer;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.stereotype.Component;

@Component
public class RepoConfig extends RepositoryRestConfigurerAdapter {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.setBasePath("/api");
    }

    @Override
    public void configureJacksonObjectMapper(ObjectMapper objectMapper) {
        objectMapper.registerModule(new Module() {
            @Override
            public String getModuleName() {
                return "com.thanethomson.demos.todo.UserSerializerModule";
            }

            @Override
            public Version version() {
                return Version.unknownVersion();
            }

            @Override
            public void setupModule(SetupContext context) {
                context.addBeanSerializerModifier(
                        new BeanSerializerModifier() {
                            @Override
                            public JsonSerializer<?> modifySerializer(SerializationConfig config, BeanDescription beanDesc, JsonSerializer<?> serializer) {
                                if (beanDesc.getBeanClass().equals(User.class)) {
                                    return new UserSerializer((BeanSerializerBase)serializer, NameTransformer.NOP);
                                }
                                return serializer;
                            }
                        }
                );
            }
        });
    }

}
