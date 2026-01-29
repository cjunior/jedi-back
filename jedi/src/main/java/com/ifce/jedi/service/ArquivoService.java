package com.ifce.jedi.service;

import com.ifce.jedi.dto.Arquivos.ArquivoResponseDto;
import com.ifce.jedi.dto.Arquivos.ArquivoUpdateDto;
import com.ifce.jedi.model.Arquivos.Arquivo;
import com.ifce.jedi.model.Arquivos.Pasta;
import com.ifce.jedi.repository.ArquivoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArquivoService {

    @Autowired
    private ArquivoRepository arquivoRepository;

    @Autowired
    private PastaService pastaService;

    @Autowired
    private LocalStorageService localStorageService;

    @Value("${app.base-url}")
    private String baseUrl;

    @Transactional
    public List<ArquivoResponseDto> upload(Long pastaId, MultipartFile[] files, List<String> nomes) throws IOException {
        if (files == null || files.length == 0) {
            throw new IllegalArgumentException("Insira ao menos um arquivo.");
        }

        Pasta pasta = pastaService.buscarPorId(pastaId);
        List<ArquivoResponseDto> response = new ArrayList<>();

        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            if (file == null || file.isEmpty()) {
                throw new IllegalArgumentException("Arquivo invalido na posicao " + i + ".");
            }

            String nomeLogico = resolveNomeLogico(file, nomes, i);
            String fileName = localStorageService.salvarEmDiretorio(file, pasta.getPath());

            Arquivo arquivo = new Arquivo();
            arquivo.setNome(nomeLogico);
            arquivo.setFileName(fileName);
            arquivo.setPasta(pasta);

            Arquivo saved = arquivoRepository.save(arquivo);
            response.add(toResponse(saved, pasta));
        }

        return response;
    }

    @Transactional
    public List<ArquivoResponseDto> listarPorPasta(Long pastaId) {
        Pasta pasta = pastaService.buscarPorId(pastaId);
        return arquivoRepository.findByPasta(pasta).stream()
                .map(arquivo -> toResponse(arquivo, pasta))
                .toList();
    }

    @Transactional
    public ArquivoResponseDto buscarPorId(Long arquivoId) {
        Arquivo arquivo = arquivoRepository.findById(arquivoId)
                .orElseThrow(() -> new IllegalArgumentException("Arquivo nao encontrado."));
        Pasta pasta = arquivo.getPasta();
        return toResponse(arquivo, pasta);
    }

    @Transactional
    public ArquivoResponseDto atualizarNome(Long arquivoId, ArquivoUpdateDto dto) {
        if (dto == null || dto.nome() == null || dto.nome().isBlank()) {
            throw new IllegalArgumentException("Nome do arquivo eh obrigatorio.");
        }
        Arquivo arquivo = arquivoRepository.findById(arquivoId)
                .orElseThrow(() -> new IllegalArgumentException("Arquivo nao encontrado."));
        arquivo.setNome(dto.nome().trim());
        Arquivo saved = arquivoRepository.save(arquivo);
        return toResponse(saved, saved.getPasta());
    }

    @Transactional
    public void deletar(Long arquivoId) {
        Arquivo arquivo = arquivoRepository.findById(arquivoId)
                .orElseThrow(() -> new IllegalArgumentException("Arquivo nao encontrado."));
        // Regra de negocio: nao remover o arquivo fisico do storage.
        arquivoRepository.delete(arquivo);
    }

    private String resolveNomeLogico(MultipartFile file, List<String> nomes, int index) {
        if (nomes != null && nomes.size() > index) {
            String nome = nomes.get(index);
            if (nome != null && !nome.isBlank()) {
                return nome.trim();
            }
        }
        String original = file.getOriginalFilename();
        if (original == null || original.isBlank()) {
            return "arquivo";
        }
        return original;
    }

    private ArquivoResponseDto toResponse(Arquivo arquivo, Pasta pasta) {
        String url = baseUrl + "/publicos/" + pasta.getPath() + "/" + arquivo.getFileName();
        String urlSanitizada = url.replaceAll("\\s+", "_");
        return new ArquivoResponseDto(
                arquivo.getId(),
                arquivo.getNome(),
                urlSanitizada,
                arquivo.getUploadedAt()
        );
    }
}
