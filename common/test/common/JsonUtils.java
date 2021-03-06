package common;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class JsonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static JsonNode readJsonNodeFromResource(final String resourcePath) throws IOException {
        try(final InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath)) {
            return MAPPER.readTree(new InputStreamReader(resourceAsStream, StandardCharsets.UTF_8.name()));
        }
    }
}
