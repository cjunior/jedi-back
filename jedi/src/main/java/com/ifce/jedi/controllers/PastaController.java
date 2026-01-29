package com.ifce.jedi.controllers;

import com.ifce.jedi.dto.Arquivos.ArquivoResponseDto;
import com.ifce.jedi.dto.Arquivos.ArquivoUploadDto;
import com.ifce.jedi.dto.Arquivos.PastaCreateDto;
import com.ifce.jedi.dto.Arquivos.PastaResponseDto;
import com.ifce.jedi.dto.Arquivos.PastaUpdateDto;
import com.ifce.jedi.service.ArquivoService;
import com.ifce.jedi.service.PastaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/pastas")
public class PastaController {

    @Autowired
    private PastaService pastaService;

    @Autowired
    private ArquivoService arquivoService;

    @PostMapping
    public ResponseEntity<PastaResponseDto> criarPasta(@RequestBody PastaCreateDto dto) throws IOException {
        return ResponseEntity.ok(pastaService.criar(dto));
    }

    @GetMapping
    public ResponseEntity<List<PastaResponseDto>> listarPastas(
            @RequestParam(name = "parentId", required = false) Long parentId
    ) {
        return ResponseEntity.ok(pastaService.listar(parentId));
    }

    @GetMapping("/{pastaId}")
    public ResponseEntity<PastaResponseDto> buscarPasta(@PathVariable Long pastaId) {
        return ResponseEntity.ok(pastaService.buscarDto(pastaId));
    }

    @GetMapping("/caminho")
    public ResponseEntity<PastaResponseDto> buscarPastaPorCaminho(
            @RequestParam(name = "slugPath") String slugPath
    ) {
        return ResponseEntity.ok(pastaService.buscarPorCaminhoSlug(slugPath));
    }

    @PutMapping("/{pastaId}")
    public ResponseEntity<PastaResponseDto> atualizarPasta(
            @PathVariable Long pastaId,
            @RequestBody PastaUpdateDto dto
    ) {
        return ResponseEntity.ok(pastaService.atualizar(pastaId, dto));
    }

    @DeleteMapping("/{pastaId}")
    public ResponseEntity<Void> deletarPasta(@PathVariable Long pastaId) {
        pastaService.deletar(pastaId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{pastaId}/arquivos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<ArquivoResponseDto>> uploadArquivos(
            @PathVariable Long pastaId,
            @ModelAttribute ArquivoUploadDto form
    ) throws IOException {
        return ResponseEntity.ok(arquivoService.upload(pastaId, form.getFiles(), form.getNomes()));
    }

    @GetMapping("/{pastaId}/arquivos")
    public ResponseEntity<List<ArquivoResponseDto>> listarArquivos(@PathVariable Long pastaId) {
        return ResponseEntity.ok(arquivoService.listarPorPasta(pastaId));
    }
}
