package com.ifce.jedi.controllers;

import com.ifce.jedi.dto.Ciclo.CicloRequestDto;
import com.ifce.jedi.dto.Ciclo.CicloResponseDto;
import com.ifce.jedi.dto.Ciclo.MunicipiosAtivosResponseDto;
import com.ifce.jedi.service.CicloService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/management/ciclo")
@Tag(name = "Gerenciamento de Ciclos", description = "Endpoints administrativos para CRUD de ciclos de inscrição")
public class CicloController {

    @Autowired
    private CicloService cicloService;

    @Operation(
            summary = "Criar ciclo",
            description = "Cria um novo ciclo de inscrição. Requer permissão de ADMIN."
    )
    @ApiResponse(responseCode = "200", description = "Ciclo criado com sucesso")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public CicloResponseDto criar(@RequestBody CicloRequestDto dto) {
        return cicloService.criar(dto);
    }

    @Operation(
            summary = "Editar ciclo",
            description = "Edita os dados de um ciclo existente, identificado pelo seu ID. Requer permissão de ADMIN."
    )
    @ApiResponse(responseCode = "200", description = "Ciclo editado com sucesso")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public CicloResponseDto editar(@PathVariable UUID id, @RequestBody CicloRequestDto dto) {
        return cicloService.editar(id, dto);
    }

    @Operation(
            summary = "Deletar ciclo",
            description = "Remove um ciclo existente pelo seu ID. Requer permissão de ADMIN."
    )
    @ApiResponse(responseCode = "204", description = "Ciclo deletado com sucesso")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deletar(@PathVariable UUID id) {
        cicloService.deletar(id);
    }

    @Operation(
            summary = "Listar todos os ciclos",
            description = "Retorna uma lista com todos os ciclos cadastrados. Requer permissão de ADMIN."
    )
    @ApiResponse(responseCode = "200", description = "Lista de ciclos retornada com sucesso")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<CicloResponseDto> listarTodos() {
        return cicloService.listarTodos();
    }

    @Operation(
            summary = "Municípios ativos no ciclo atual",
            description = "Retorna a data de referência e a lista de municípios que estão ativos no ciclo atual."
    )
    @ApiResponse(responseCode = "200", description = "Municípios retornados com sucesso")
    @GetMapping("/municipios-ativos")
    public MunicipiosAtivosResponseDto getMunicipiosCicloAtual() {
        return cicloService.getMunicipiosCicloAtualComData();
    }
}
