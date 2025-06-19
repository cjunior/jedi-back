package com.ifce.jedi.controllers;

import com.ifce.jedi.dto.Rede.RedeJediImageDto;
import com.ifce.jedi.dto.Rede.RedeJediSectionDto;
import com.ifce.jedi.service.RedeJediImageService;
import com.ifce.jedi.service.RedeJediSectionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/rede-jedi")
public class RedeController {

    @Autowired
    private RedeJediImageService imageService;

    @Autowired
    private RedeJediSectionService sectionService;


    @PostMapping(consumes = "multipart/form-data")
    @Operation(summary = "Fazer upload de uma nova imagem para a Rede Jedi")
    @PreAuthorize("hasRole('ADMIN')")
    public RedeJediImageDto upload(@RequestPart MultipartFile arquivo) throws IOException {
        return imageService.upload(arquivo);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar uma imagem da Rede Jedi pelo ID")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws IOException {
        imageService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/titulo")
    @PreAuthorize("hasRole('ADMIN')")
    public RedeJediSectionDto atualizarTitulo(@RequestBody String novoTitulo) {
        // Supondo que você sempre usa a seção com id 1
        return sectionService.atualizarTitulo(1L, novoTitulo);
    }

    @PutMapping(
            value = "/imagens",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atualiza múltiplas imagens da Rede Jedi")
    public List<RedeJediImageDto> updateMultiplasImagens(
            @RequestParam List<Long> ids,
            @RequestPart(name = "imagens") MultipartFile[] imagens
    ) throws IOException {
        return imageService.updateMultipleImages(ids, Arrays.asList(imagens));
    }



    @GetMapping
    public RedeJediSectionDto getSection() {
        return sectionService.getSection(1L);
    }
}
