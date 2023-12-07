package com.carrental.agency.util;

import com.carrental.lib.exception.CarRentalException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

    public static String writeValueAsString(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new CarRentalException(e);
        }
    }

    public static <T> T getResourceAsJson(String resourceName, Class<T> valueType) {
        try {
            return OBJECT_MAPPER.readValue(getResourceAsString(resourceName), valueType);
        } catch (JsonProcessingException e) {
            throw new CarRentalException("Failed getting resource: " + resourceName + ", cause: " + e.getMessage());
        }
    }

    private static String getResourceAsString(String resourceName) {
        URL resource = TestUtils.class.getResource(resourceName);

        if (resource == null) {
            throw new CarRentalException("Failed getting resource: " + resourceName);
        }

        try {
            return new String(Files.readAllBytes(Paths.get(resource.toURI())));
        } catch (Exception e) {
            throw new CarRentalException("Failed getting resource: " + resourceName);
        }
    }

}
