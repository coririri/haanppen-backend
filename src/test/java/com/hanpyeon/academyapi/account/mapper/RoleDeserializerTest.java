package com.hanpyeon.academyapi.account.mapper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.hanpyeon.academyapi.security.Role;
import com.hanpyeon.academyapi.security.RoleDeserializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class RoleDeserializerTest {
    @Mock
    private JsonParser jsonParser;
    @Mock
    DeserializationContext context;
    private RoleDeserializer deserializer;

    @BeforeEach
    void setDeserializer() {
        deserializer = new RoleDeserializer();
    }

    @Test
    void deserializeStudentTest() throws IOException {
        Mockito.when(jsonParser.getValueAsString()).thenReturn("student");

        assertThat(deserializer.deserialize(jsonParser, context)).isEqualTo(Role.STUDENT);
    }

    @Test
    void deserializeTeacherTest() throws IOException {
        Mockito.when(jsonParser.getValueAsString()).thenReturn("teacher");

        assertThat(deserializer.deserialize(jsonParser, context)).isEqualTo(Role.TEACHER);
    }

    @Test
    void deserializedManagerTest() throws IOException {
        Mockito.when(jsonParser.getValueAsString()).thenReturn("manager");

        assertThat(deserializer.deserialize(jsonParser, context)).isEqualTo(Role.MANAGER);
    }

    @ParameterizedTest
    @ValueSource(strings = {"students", "manage", "ROLE_STUDENT"})
    void IllegalDeserializeTest(String input) throws IOException {
        Mockito.when(jsonParser.getValueAsString()).thenReturn(input);

        assertThat(deserializer.deserialize(jsonParser, context)).isEqualTo(null);
    }

    @Test
    void NullDeserializeTest() throws IOException {
        Mockito.when(jsonParser.getValueAsString()).thenReturn(null);

        assertThat(deserializer.deserialize(jsonParser, context)).isEqualTo(null);
    }

}