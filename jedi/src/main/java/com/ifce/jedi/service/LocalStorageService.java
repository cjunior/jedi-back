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
        return salvarEmDiretorio(arquivo, null);
    }

    public String salvarEmDiretorio(MultipartFile arquivo, String subDiretorio) throws IOException {
        Path diretorio = resolveSafeDirectory(subDiretorio);
        Files.createDirectories(diretorio);

        String nomeOriginalSanitizado = sanitizeFileName(Objects.requireNonNull(arquivo.getOriginalFilename()));
        String nomeArquivo = UUID.randomUUID() + "_" + nomeOriginalSanitizado;
        Path destino = diretorio.resolve(nomeArquivo).normalize();

        // Garante que o destino final permanece dentro do storagePath.
        Path raiz = Paths.get(storagePath).toAbsolutePath().normalize();
        if (!destino.startsWith(raiz)) {
            throw new IOException("Destino invalido para upload.");
        }

        System.out.println("Salvando arquivo em: " + destino.toAbsolutePath());

        Files.copy(arquivo.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);

        return nomeArquivo;
    }

    public void criarDiretorio(String subDiretorio) throws IOException {
        Path diretorio = resolveSafeDirectory(subDiretorio);
        Files.createDirectories(diretorio);
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

    private Path resolveSafeDirectory(String subDiretorio) {
        Path raiz = Paths.get(storagePath).toAbsolutePath().normalize();
        if (subDiretorio == null || subDiretorio.isBlank()) {
            return raiz;
        }
        Path diretorio = raiz.resolve(subDiretorio).normalize();
        if (!diretorio.startsWith(raiz)) {
            throw new IllegalArgumentException("Diretorio invalido.");
        }
        return diretorio;
    }

    private String sanitizeFileName(String nomeOriginal) {
        String sanitizado = nomeOriginal
                .replaceAll("\\s+", "_")
                .replaceAll("[^a-zA-Z0-9.\\-_]", "");
        if (sanitizado.isBlank()) {
            return "arquivo";
        }
        return sanitizado;
    }
}
