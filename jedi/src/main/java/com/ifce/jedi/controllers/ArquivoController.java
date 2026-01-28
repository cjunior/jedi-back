package com.ifce.jedi.controllers;

import com.ifce.jedi.dto.Arquivos.ArquivoResponseDto;
import com.ifce.jedi.dto.Arquivos.ArquivoUpdateDto;
import com.ifce.jedi.service.ArquivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pastas/arquivos")
public class ArquivoController {

    @Autowired
    private ArquivoService arquivoService;

    @GetMapping("/{arquivoId}")
    public ResponseEntity<ArquivoResponseDto> buscarArquivo(@PathVariable Long arquivoId) {
        return ResponseEntity.ok(arquivoService.buscarPorId(arquivoId));
    }

    @PutMapping("/{arquivoId}")
    public ResponseEntity<ArquivoResponseDto> atualizarArquivo(
            @PathVariable Long arquivoId,
            @RequestBody ArquivoUpdateDto dto
    ) {
        return ResponseEntity.ok(arquivoService.atualizarNome(arquivoId, dto));
    }

    @DeleteMapping("/{arquivoId}")
    public ResponseEntity<Void> deletarArquivo(@PathVariable Long arquivoId) {
        arquivoService.deletar(arquivoId);
        return ResponseEntity.noContent().build();
    }
}
