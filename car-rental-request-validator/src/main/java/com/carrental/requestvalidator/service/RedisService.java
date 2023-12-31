package com.carrental.requestvalidator.service;

import com.carrental.exception.CarRentalException;
import com.carrental.requestvalidator.model.SwaggerFolder;
import com.carrental.requestvalidator.repository.SwaggerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisService {

    private final SwaggerRepository swaggerRepository;
    private final SwaggerExtractorService swaggerExtractorService;

    public void addSwaggerFolderToRedis() {
        try {
            Map<String, String> swaggerIdentifierAndContent = swaggerExtractorService.getSwaggerIdentifierAndContent();

            List<SwaggerFolder> swaggerFolders = swaggerIdentifierAndContent.entrySet()
                    .stream()
                    .map(swaggerIdAndContent -> SwaggerFolder.builder()
                            .id(swaggerIdAndContent.getKey())
                            .swaggerContent(swaggerIdAndContent.getValue())
                            .build())
                    .toList();

            swaggerRepository.saveAll(swaggerFolders);
        } catch (Exception e) {
            log.error("Error while setting swagger folder in Redis: {}", e.getMessage());

            throw new CarRentalException(e);
        }
    }

    public void repopulateRedisWithSwaggerFolder(String microserviceName) {
        SwaggerFolder swaggerFolder;

        try {
            swaggerRepository.deleteById(microserviceName);
            String swaggerContent = swaggerExtractorService.getSwaggerFileForMicroservice(microserviceName)
                    .get(microserviceName);

            swaggerFolder = SwaggerFolder.builder()
                    .id(microserviceName)
                    .swaggerContent(swaggerContent)
                    .build();
        } catch (Exception e) {
            log.error("Error while repopulating swagger folder in Redis: {}", e.getMessage());

            throw new CarRentalException(e);
        }

        swaggerRepository.save(swaggerFolder);
    }

}
