package com.ifce.jedi.service;

import com.ifce.jedi.dto.Rede.RedeJediImageDto;
import com.ifce.jedi.model.SecoesSite.Rede.RedeJediImage;
import com.ifce.jedi.model.SecoesSite.Rede.RedeJediSection;
import com.ifce.jedi.repository.RedeJediImageRepository;
import com.ifce.jedi.repository.RedeJediSectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RedeJediImageService {

    @Autowired
    private RedeJediImageRepository imageRepository;

    @Autowired
    private RedeJediSectionRepository sectionRepository;

    @Autowired
    private LocalStorageService localStorageService;

    public List<RedeJediImageDto> uploadMultiplas(MultipartFile[] imagens) throws IOException {
        List<RedeJediImageDto> dtos = new ArrayList<>();

        RedeJediSection section = sectionRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Seção Rede Jedi não encontrada"));

        for (MultipartFile imagem : imagens) {
            var uploadResult = localStorageService.salvar(imagem);

            RedeJediImage entity = new RedeJediImage();
            entity.setUrl(localStorageService.carregar(uploadResult).toString());
            entity.setFileName(uploadResult);
            entity.setSection(section);

            RedeJediImage saved = imageRepository.save(entity);

            RedeJediImageDto dto = new RedeJediImageDto();
            dto.setId(saved.getId());
            dto.setUrl(saved.getUrl());
            dto.setPublicId(saved.getFileName());

            dtos.add(dto);
        }

        return dtos;
    }

    public void delete(Long id) throws IOException {
        RedeJediImage imagem = imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Imagem não encontrada"));
        localStorageService.deletar(imagem.getFileName());
        imageRepository.delete(imagem);
    }

    public List<RedeJediImageDto> listAll() {
        return imageRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private RedeJediImageDto mapToDto(RedeJediImage img) {
        RedeJediImageDto dto = new RedeJediImageDto();
        dto.setId(img.getId());
        dto.setUrl(img.getUrl());
        dto.setPublicId(img.getFileName());
        return dto;
    }


    public List<RedeJediImageDto> updateMultipleImages(List<Long> ids, List<MultipartFile> novasImagens) throws IOException {
        List<RedeJediImageDto> atualizadas = new ArrayList<>();
        if(novasImagens.isEmpty()){
            return atualizadas;
        }
        for (int i = 0; i < ids.size(); i++) {
            Long id = ids.get(i);
            MultipartFile novaImagem = novasImagens.get(i);

            RedeJediImage imagemExistente = imageRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Imagem com ID " + id + " não encontrada"));

            if(novaImagem != null){
                var uploadResult = localStorageService.salvar(novaImagem);

                if(imagemExistente.getFileName() != null){
                    localStorageService.deletar(imagemExistente.getFileName());
                }
                imagemExistente.setUrl(localStorageService.carregar(uploadResult).toString());
                imagemExistente.setFileName(uploadResult);

            }

            RedeJediImage salva = imageRepository.save(imagemExistente);
            atualizadas.add(mapToDto(salva));
        }

        return atualizadas;
    }

}
