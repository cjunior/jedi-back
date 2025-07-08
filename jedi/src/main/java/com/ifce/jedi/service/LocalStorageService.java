package com.ifce.jedi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Service
public class LocalStorageService {

    @Value("${storage.path}")
    private String storagePath;

    public String salvar(MultipartFile arquivo) throws IOException {
        Files.createDirectories(Paths.get(storagePath));

        String nomeOriginalSanitizado = Objects.requireNonNull(arquivo.getOriginalFilename())
                .replaceAll("\\s+", "_")
                .replaceAll("[^a-zA-Z0-9.\\-_]", "");

        String nomeArquivo = UUID.randomUUID() + "_" + nomeOriginalSanitizado;
        Path destino = Paths.get(storagePath).resolve(nomeArquivo);

        System.out.println("Salvando arquivo em: " + destino.toAbsolutePath());

        Files.copy(arquivo.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);

        return nomeArquivo;
    }


    public boolean deletar(String nomeArquivo) {
        try {
            return Files.deleteIfExists(Paths.get(storagePath).resolve(nomeArquivo));
        } catch (IOException e) {
            return false;
        }
    }

    public Path carregar(String nomeArquivo) {
        return Paths.get(storagePath).resolve(nomeArquivo);
    }
}