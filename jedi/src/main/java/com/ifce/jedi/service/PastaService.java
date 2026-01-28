package com.ifce.jedi.service;

import com.ifce.jedi.dto.Arquivos.PastaCreateDto;
import com.ifce.jedi.dto.Arquivos.PastaResponseDto;
import com.ifce.jedi.dto.Arquivos.PastaUpdateDto;
import com.ifce.jedi.model.Arquivos.Pasta;
import com.ifce.jedi.repository.ArquivoRepository;
import com.ifce.jedi.repository.PastaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class PastaService {

    @Autowired
    private PastaRepository pastaRepository;

    @Autowired
    private LocalStorageService localStorageService;

    @Autowired
    private ArquivoRepository arquivoRepository;

    @Transactional
    public PastaResponseDto criar(PastaCreateDto dto) throws IOException {
        if (dto == null || dto.getNome() == null || dto.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome da pasta eh obrigatorio.");
        }

        Pasta parent = null;
        if (dto.getParentId() != null) {
            parent = pastaRepository.findById(dto.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("Pasta pai nao encontrada."));
        }

        Pasta pasta = new Pasta();
        pasta.setNome(dto.getNome().trim());
        pasta.setParent(parent);

        // Usa um identificador interno para o diretorio fisico, evitando conflitos com nomes arbitrarios.
        String pathSegment = UUID.randomUUID().toString();
        String path = parent == null ? pathSegment : parent.getPath() + "/" + pathSegment;
        pasta.setPath(path);

        localStorageService.criarDiretorio(pasta.getPath());
        Pasta saved = pastaRepository.save(pasta);

        return toResponse(saved);
    }

    @Transactional
    public List<PastaResponseDto> listar(Long parentId) {
        List<Pasta> pastas;
        if (parentId != null) {
            Pasta parent = buscarPorId(parentId);
            pastas = pastaRepository.findByParent(parent);
        } else {
            pastas = pastaRepository.findAll();
        }
        return pastas.stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public PastaResponseDto buscarDto(Long id) {
        return toResponse(buscarPorId(id));
    }

    @Transactional
    public PastaResponseDto atualizar(Long id, PastaUpdateDto dto) {
        if (dto == null || dto.nome() == null || dto.nome().isBlank()) {
            throw new IllegalArgumentException("Nome da pasta eh obrigatorio.");
        }

        Pasta pasta = buscarPorId(id);
        pasta.setNome(dto.nome().trim());
        Pasta saved = pastaRepository.save(pasta);
        return toResponse(saved);
    }

    @Transactional
    public void deletar(Long id) {
        Pasta pasta = buscarPorId(id);
        deletarRecursivo(pasta);
    }

    public Pasta buscarPorId(Long id) {
        return pastaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pasta nao encontrada."));
    }

    private void deletarRecursivo(Pasta pasta) {
        List<Pasta> filhas = pastaRepository.findByParent(pasta);
        for (Pasta filha : filhas) {
            deletarRecursivo(filha);
        }

        // Regra de negocio: remover apenas os registros, mantendo os arquivos fisicos.
        arquivoRepository.deleteByPasta(pasta);
        pastaRepository.delete(pasta);
    }

    private PastaResponseDto toResponse(Pasta pasta) {
        Long parentId = pasta.getParent() != null ? pasta.getParent().getId() : null;
        return new PastaResponseDto(pasta.getId(), pasta.getNome(), parentId);
    }
}
