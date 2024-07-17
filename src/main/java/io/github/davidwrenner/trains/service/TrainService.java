/* * * * * Copyright (c) 2024 David Wrenner * * * * */
package io.github.davidwrenner.trains.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract sealed class TrainService permits StandardRouteService, StationListService, TrainPositionService {

    private static final Logger logger = LogManager.getLogger();

    public <T> Optional<T> readAndUnmarshal(String filename, Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();

        InputStream is = ClassLoader.getSystemResourceAsStream(filename);
        if (is == null) return Optional.empty();

        logger.info("Reading {}", filename);

        try (InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
                BufferedReader reader = new BufferedReader(streamReader)) {
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) builder.append(line);
            T obj = objectMapper.readValue(builder.toString(), clazz);
            return Optional.ofNullable(obj);
        } catch (Exception e) {
            logger.error("Unable to read {}: {}", filename, e.toString());
            return Optional.empty();
        }
    }

    public <T> Optional<T> doHttpRequest(HttpRequest request, int wantStatus, Class<T> clazz) {

        if (request == null) return Optional.empty();

        ObjectMapper objectMapper = new ObjectMapper();
        HttpResponse<String> response;
        final int status;

        logger.info("{} {}", request.method(), request.uri());

        try (HttpClient client = HttpClient.newHttpClient()) {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if ((status = response.statusCode()) != wantStatus) {
                logger.error("bad response code {}", status);
                return Optional.empty();
            }
            T obj = objectMapper.readValue(response.body(), clazz);
            return Optional.ofNullable(obj);
        } catch (Exception e) {
            logger.error("Error in request {}", e.toString());
            return Optional.empty();
        }
    }

    public Optional<URI> buildUri(String str) {
        try {
            return Optional.of(new URI(str));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
