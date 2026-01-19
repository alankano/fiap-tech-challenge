package br.com.fiap.techchallenge.loader;

import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class SqlQueryLoader {

    private final ResourceLoader resourceLoader;

    public SqlQueryLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public String loadQuery(String filePath) {
        try {
            Resource resource = resourceLoader.getResource("classpath:sql/" + filePath + ".sql");
            return new String(Files.readAllBytes(Paths.get(resource.getURI())));
        } catch (Exception e) {
            throw new RuntimeException("Failed to load SQL file: " + filePath, e);
        }
    }
}
