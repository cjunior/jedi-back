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
import java.text.Normalizer;
import java.util.List;
import java.util.Locale;
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

        String slug = resolveSlug(dto.getSlug(), dto.getNome());
        validarSlugUnico(parent, slug, null);

        Pasta pasta = new Pasta();
        pasta.setNome(dto.getNome().trim());
        pasta.setDescricao(normalizeDescricao(dto.getDescricao()));
        pasta.setSlug(slug);
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
    public PastaResponseDto buscarPorCaminhoSlug(String slugPath) {
        if (slugPath == null || slugPath.isBlank()) {
            throw new IllegalArgumentException("Caminho de slug eh obrigatorio.");
        }

        String[] partes = slugPath.split("/");
        Pasta atual = null;

        for (String parte : partes) {
            if (parte == null || parte.isBlank()) {
                continue;
            }
            String slug = slugify(parte);
            if (slug.isBlank()) {
                throw new IllegalArgumentException("Slug invalido no caminho.");
            }

            if (atual == null) {
                atual = pastaRepository.findByParentIsNullAndSlug(slug)
                        .orElseThrow(() -> new IllegalArgumentException("Pasta nao encontrada."));
            } else {
                Pasta parent = atual;
                atual = pastaRepository.findByParentAndSlug(parent, slug)
                        .orElseThrow(() -> new IllegalArgumentException("Pasta nao encontrada."));
            }
        }

        if (atual == null) {
            throw new IllegalArgumentException("Pasta nao encontrada.");
        }

        return toResponse(atual);
    }

    @Transactional
    public PastaResponseDto atualizar(Long id, PastaUpdateDto dto) {
        if (dto == null || dto.nome() == null || dto.nome().isBlank()) {
            throw new IllegalArgumentException("Nome da pasta eh obrigatorio.");
        }
        if (dto.slug() != null && dto.slug().isBlank()) {
            throw new IllegalArgumentException("Slug da pasta eh obrigatorio.");
        }

        Pasta pasta = buscarPorId(id);
        pasta.setNome(dto.nome().trim());

        if (dto.descricao() != null) {
            pasta.setDescricao(normalizeDescricao(dto.descricao()));
        }

        if (dto.slug() != null) {
            String slug = slugify(dto.slug());
            if (slug.isBlank()) {
                throw new IllegalArgumentException("Slug da pasta eh obrigatorio.");
            }
            validarSlugUnico(pasta.getParent(), slug, pasta.getId());
            pasta.setSlug(slug);
        }

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
        return new PastaResponseDto(pasta.getId(), pasta.getNome(), pasta.getDescricao(), pasta.getSlug(), parentId);
    }

    private String resolveSlug(String slugRaw, String nome) {
        if (slugRaw != null && slugRaw.isBlank()) {
            throw new IllegalArgumentException("Slug da pasta eh obrigatorio.");
        }
        String base = (slugRaw != null && !slugRaw.isBlank()) ? slugRaw : nome;
        String slug = slugify(base);
        if (slug.isBlank()) {
            throw new IllegalArgumentException("Slug da pasta eh obrigatorio.");
        }
        return slug;
    }

    private void validarSlugUnico(Pasta parent, String slug, Long idIgnorar) {
        boolean existe;
        if (parent == null) {
            existe = (idIgnorar == null)
                    ? pastaRepository.existsByParentIsNullAndSlug(slug)
                    : pastaRepository.existsByParentIsNullAndSlugAndIdNot(slug, idIgnorar);
        } else {
            existe = (idIgnorar == null)
                    ? pastaRepository.existsByParentAndSlug(parent, slug)
                    : pastaRepository.existsByParentAndSlugAndIdNot(parent, slug, idIgnorar);
        }
        if (existe) {
            throw new IllegalArgumentException("Slug da pasta ja existe neste nivel.");
        }
    }

    private String slugify(String valor) {
        if (valor == null) {
            return "";
        }
        String normalizado = Normalizer.normalize(valor, Normalizer.Form.NFD);
        String semAcentos = normalizado.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        return semAcentos
                .toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("(^-|-$)", "");
    }

    private String normalizeDescricao(String descricao) {
        if (descricao == null) {
            return null;
        }
        String trimmed = descricao.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
