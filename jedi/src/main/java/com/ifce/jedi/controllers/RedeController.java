package com.ifce.jedi.controllers;

import com.ifce.jedi.dto.Rede.RedeJediImageDto;
import com.ifce.jedi.dto.Rede.RedeJediSectionDto;
import com.ifce.jedi.dto.Rede.imagemRedeJedDto;
import com.ifce.jedi.dto.Rede.imagemRedeJedWrapperDto;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/rede-jedi")
public class RedeController {

    @Autowired
    private RedeJediImageService imageService;

    @Autowired
    private RedeJediSectionService sectionService;


    @PostMapping(value = "/multiplas", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Fazer upload de múltiplas imagens para a Rede Jedi")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public List<RedeJediImageDto> uploadMultiplas(@RequestPart MultipartFile[] arquivos) throws IOException {
        return imageService.uploadMultiplas(arquivos);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar uma imagem da Rede Jedi pelo ID")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws IOException {
        imageService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/titulo")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public RedeJediSectionDto atualizarTitulo(@RequestBody String novoTitulo) {
        // Supondo que você sempre usa a seção com id 1
        return sectionService.atualizarTitulo(1L, novoTitulo);
    }

    @PutMapping(
            value = "/imagens",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @Operation(summary = "Atualiza múltiplas imagens da Rede Jedi")
    public List<RedeJediImageDto> updateMultiplasImagens(@ModelAttribute imagemRedeJedWrapperDto dto
    ) throws IOException {
        List<Long> ids = new ArrayList<>();
        List<MultipartFile> imagens = new ArrayList<>();
        for (imagemRedeJedDto obj : dto.getImages()){
            ids.add(obj.getId());
            if(obj.getFile()!= null){
                imagens.add(obj.getFile());
            }
        }
        return imageService.updateMultipleImages(ids, imagens);
    }



    @GetMapping
    public RedeJediSectionDto getSection() {
        return sectionService.getSection(1L);
    }
}
