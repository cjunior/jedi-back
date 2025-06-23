package com.ifce.jedi.controllers;

import com.ifce.jedi.service.LocalStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/arquivos")
public class FileController {

    @Autowired
    private LocalStorageService localStorageService;

    @GetMapping("/{nomeArquivo}")
    public ResponseEntity<Resource> servir(@PathVariable String nomeArquivo) throws IOException {
        Path caminho = localStorageService.carregar(nomeArquivo);
        Resource resource = new UrlResource(caminho.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            return ResponseEntity.notFound().build();
        }

        String contentType = Files.probeContentType(caminho);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType != null ? contentType : "application/octet-stream"))
                .body(resource);
    }
}
