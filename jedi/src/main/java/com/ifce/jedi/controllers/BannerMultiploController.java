package com.ifce.jedi.controllers;

import com.ifce.jedi.dto.BannerMultiplo.BannerMultiploOrderDto;
import com.ifce.jedi.dto.BannerMultiplo.BannerMultiploResponseDto;
import com.ifce.jedi.dto.BannerMultiplo.BannerMultiploUpdateDto;
import com.ifce.jedi.dto.BannerMultiplo.BannerMultiploUploadDto;
import com.ifce.jedi.service.BannerMultiploService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/bannermultiplo")
public class BannerMultiploController {

    @Autowired
    private BannerMultiploService bannerMultiploService;

    @GetMapping
    @Operation(
            summary = "Listar banners multiplos",
            description = "Retorna todos os banners multiplos ordenados por posicao."
    )
    public ResponseEntity<List<BannerMultiploResponseDto>> getAll() {
        return ResponseEntity.ok(bannerMultiploService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar banner multiplo por id",
            description = "Retorna um banner multiplo especifico pelo id."
    )
    public ResponseEntity<BannerMultiploResponseDto> getById(@PathVariable Long id) {
        BannerMultiploResponseDto item = bannerMultiploService.getById(id);
        return item == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(item);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @Operation(
            summary = "Criar banners multiplos",
            description = "Faz upload de um ou mais banners com titulo e link e cria os itens no final da lista."
    )
    public ResponseEntity<?> create(@ModelAttribute BannerMultiploUploadDto form) throws IOException {
        MultipartFile[] files = form.getFile();
        List<String> linkUrls = form.getLinkUrl();
        List<String> titles = form.getTitle();

        if (files == null || files.length == 0) {
            return ResponseEntity.badRequest().body("Insira ao menos um arquivo.");
        }
        for (MultipartFile file : files) {
            if (file == null || file.isEmpty()) {
                return ResponseEntity.badRequest().body("Arquivo vazio nao e permitido.");
            }
        }
        if (linkUrls == null || linkUrls.size() != files.length) {
            return ResponseEntity.badRequest().body("Informe um link para cada banner.");
        }
        for (String linkUrl : linkUrls) {
            if (linkUrl == null || linkUrl.isBlank()) {
                return ResponseEntity.badRequest().body("Informe um link valido para cada banner.");
            }
        }
        if (titles == null || titles.size() != files.length) {
            return ResponseEntity.badRequest().body("Informe um titulo para cada banner.");
        }
        for (String title : titles) {
            if (title == null || title.isBlank()) {
                return ResponseEntity.badRequest().body("Informe um titulo valido para cada banner.");
            }
        }

        return ResponseEntity.ok(bannerMultiploService.create(files, linkUrls, titles));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @Operation(
            summary = "Atualizar banner multiplo",
            description = "Atualiza imagem, titulo e/ou link de um banner existente."
    )
    public ResponseEntity<?> update(@PathVariable Long id, @ModelAttribute BannerMultiploUpdateDto form)
            throws IOException {
        MultipartFile file = form.getFile();
        String linkUrl = form.getLinkUrl();
        String title = form.getTitle();

        if ((file == null || file.isEmpty()) && linkUrl == null && title == null) {
            return ResponseEntity.badRequest().body("Informe ao menos um campo para atualizar.");
        }
        if (linkUrl != null && linkUrl.isBlank()) {
            return ResponseEntity.badRequest().body("Informe um link valido.");
        }
        if (title != null && title.isBlank()) {
            return ResponseEntity.badRequest().body("Informe um titulo valido.");
        }

        return ResponseEntity.ok(bannerMultiploService.update(id, file, linkUrl, title));
    }

    @PutMapping("/order")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @Operation(
            summary = "Atualizar ordem dos banners",
            description = "Substitui a ordem atual pela lista completa de ids informada."
    )
    public ResponseEntity<?> updateOrder(@RequestBody BannerMultiploOrderDto form) {
        List<Long> orderedIds = form.getOrderedIds();
        if (orderedIds == null || orderedIds.isEmpty()) {
            return ResponseEntity.badRequest().body("Informe uma lista de ids para ordenar.");
        }
        Set<Long> uniqueIds = new HashSet<>(orderedIds);
        if (uniqueIds.size() != orderedIds.size()) {
            return ResponseEntity.badRequest().body("Lista de ids nao pode conter duplicatas.");
        }

        return ResponseEntity.ok(bannerMultiploService.updateOrder(orderedIds));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @Operation(
            summary = "Remover banner multiplo",
            description = "Remove o banner e exclui o arquivo associado."
    )
    public ResponseEntity<?> delete(@PathVariable Long id) throws IOException {
        bannerMultiploService.delete(id);
        return ResponseEntity.ok("Banner removido com sucesso.");
    }
}
