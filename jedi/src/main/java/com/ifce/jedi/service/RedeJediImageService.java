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
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RedeJediImageService {

    @Autowired
    private RedeJediImageRepository imageRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private RedeJediSectionRepository sectionRepository;

    public RedeJediImageDto upload(MultipartFile imagem) throws IOException {
        Map<String, String> uploadResult = cloudinaryService.uploadImage(imagem);

        RedeJediImage entity = new RedeJediImage();
        entity.setUrl(uploadResult.get("url"));
        entity.setPublicId(uploadResult.get("public_id"));

        RedeJediSection section = sectionRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Seção Rede Jedi não encontrada"));
        entity.setSection(section);

        RedeJediImage saved = imageRepository.save(entity);

        RedeJediImageDto dto = new RedeJediImageDto();
        dto.setId(saved.getId());
        dto.setUrl(saved.getUrl());
        dto.setPublicId(saved.getPublicId());
        return dto;
    }

    public void delete(Long id) throws IOException {
        RedeJediImage imagem = imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Imagem não encontrada"));
        cloudinaryService.deleteImage(imagem.getPublicId());
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
        dto.setPublicId(img.getPublicId());
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
                var uploadResult = cloudinaryService.uploadImage(novaImagem);

                if(imagemExistente.getPublicId() != null){
                    cloudinaryService.deleteImage(imagemExistente.getPublicId());
                }
                imagemExistente.setUrl(uploadResult.get("url"));
                imagemExistente.setPublicId(uploadResult.get("public_id"));

            }

            RedeJediImage salva = imageRepository.save(imagemExistente);
            atualizadas.add(mapToDto(salva));
        }

        return atualizadas;
    }

}
