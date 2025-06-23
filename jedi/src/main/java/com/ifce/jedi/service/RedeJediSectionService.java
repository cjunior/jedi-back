package com.ifce.jedi.service;

import com.ifce.jedi.dto.Rede.RedeJediImageDto;
import com.ifce.jedi.dto.Rede.RedeJediSectionDto;
import com.ifce.jedi.model.SecoesSite.Rede.RedeJediImage;
import com.ifce.jedi.model.SecoesSite.Rede.RedeJediSection;
import com.ifce.jedi.repository.RedeJediImageRepository;
import com.ifce.jedi.repository.RedeJediSectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RedeJediSectionService {

    @Autowired
    private RedeJediSectionRepository sectionRepository;

    @Autowired
    private RedeJediImageRepository imageRepository;

    public RedeJediSectionDto atualizarTitulo(Long id, String novoTitulo) {
        RedeJediSection section = sectionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Seção não encontrada"));
        section.setTitulo(novoTitulo);
        sectionRepository.save(section);

        return mapToDto(section);
    }

    public RedeJediSectionDto getSection(Long id) {
        RedeJediSection section = sectionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Seção não encontrada"));
        return mapToDto(section);
    }

    private RedeJediSectionDto mapToDto(RedeJediSection section) {
        RedeJediSectionDto dto = new RedeJediSectionDto();
        dto.setId(section.getId());
        dto.setTitulo(section.getTitulo());

        List<RedeJediImage> imagens = imageRepository.findBySection(section);
        List<RedeJediImageDto> imagemDtos = imagens.stream().map(img -> {
            RedeJediImageDto imgDto = new RedeJediImageDto();
            imgDto.setId(img.getId());
            imgDto.setUrl(img.getUrl());
            imgDto.setPublicId(img.getFileName());
            return imgDto;
        }).toList();

        dto.setImagens(imagemDtos);

        return dto;
    }
}
