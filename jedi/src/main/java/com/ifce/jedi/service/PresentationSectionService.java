package com.ifce.jedi.service;

import com.ifce.jedi.dto.PresentationSection.PresentationSectionDto;
import com.ifce.jedi.dto.PresentationSection.PresentationSectionResponseDto;
import com.ifce.jedi.model.SecoesSite.PresentationSection;
import com.ifce.jedi.repository.PresentationSectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PresentationSectionService {

    @Autowired
    private PresentationSectionRepository repository;

    public PresentationSectionResponseDto getPresentationSection() {
        List<PresentationSection> sections = repository.findAll();
        if (sections.isEmpty()) return null;
        return new PresentationSectionResponseDto(sections.get(0));
    }

    public PresentationSectionResponseDto updatePresentationSection(PresentationSectionDto dto) {
        PresentationSection section = repository.findAll().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Presentation Section não encontrada"));

        section.setDescricao(dto.descricao());
        section.setItemAzul(dto.itemAzul());
        section.setItemVerde(dto.itemVerde());
        section.setImageUrl(dto.imageUrl());
        section.setTexto(dto.texto());

        repository.save(section);

        return new PresentationSectionResponseDto(section);
    }

    public PresentationSection createPresentationSection(PresentationSectionDto dto) {
        PresentationSection section = new PresentationSection();
        section.setDescricao(dto.descricao());
        section.setItemAzul(dto.itemAzul());
        section.setItemVerde(dto.itemVerde());
        section.setImageUrl(dto.imageUrl());
        section.setTexto(dto.texto());
        return repository.save(section);
    }
}
