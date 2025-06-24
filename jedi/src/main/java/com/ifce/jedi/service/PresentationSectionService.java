package com.ifce.jedi.service;

import com.ifce.jedi.dto.PresentationSection.*;
import com.ifce.jedi.model.SecoesSite.PresentationSection;
import com.ifce.jedi.repository.PresentationSectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.Optional;

@Service
public class PresentationSectionService {

    @Autowired
    private PresentationSectionRepository repository;

    @Autowired
    private LocalStorageService localStorageService;

    @Transactional
    public PresentationSectionResponseDto get() {
        return repository.findFirstByOrderByIdAsc()
                .map(this::toResponse)
                .orElse(null);
    }

    @Transactional
    public PresentationSectionResponseDto create(PresentationSectionDto dto) {
        if (repository.findFirstByOrderByIdAsc().isPresent()) {
            throw new RuntimeException("Presentation section already exists");
        }

        PresentationSection entity = new PresentationSection();
        updateEntityFromDto(entity, dto);
        entity.setImgUrl(dto.imgUrl());

        PresentationSection saved = repository.save(entity);
        return toResponse(saved);
    }

    @Transactional
    public PresentationSectionResponseDto update(PresentationSectionUpdateDto dto) {
        PresentationSection entity = repository.findFirstByOrderByIdAsc()
                .orElseThrow(() -> new RuntimeException("Presentation section not found"));

        entity.setTitle(dto.title());
        entity.setFirstDescription(dto.firstDescription());
        entity.setSecondDescription(dto.secondDescription());
        entity.setFirstStatistic(dto.firstStatistic());
        entity.setSecondStatistic(dto.secondStatistic());
        entity.setImgDescription(dto.imgDescription());

        PresentationSection updated = repository.save(entity);
        return toResponse(updated);
    }

    @Transactional
    public PresentationSectionResponseDto updateImage(MultipartFile file) throws IOException {
        PresentationSection entity = repository.findFirstByOrderByIdAsc()
                .orElseThrow(() -> new RuntimeException("Presentation section not found"));

        if(file != null && !file.isEmpty()){
            var uploadResult = localStorageService.salvar(file);
            if(entity.getFileName() != null){
                localStorageService.deletar(entity.getFileName());
            }
            entity.setImgUrl(localStorageService.carregar(uploadResult).toString());
            entity.setFileName(uploadResult);
        }

        PresentationSection updated = repository.save(entity);
        return toResponse(updated);
    }

    private void updateEntityFromDto(PresentationSection entity, PresentationSectionDto dto) {
        entity.setTitle(dto.title());
        entity.setFirstDescription(dto.firstDescription());
        entity.setSecondDescription(dto.secondDescription());
        entity.setFirstStatistic(dto.firstStatistic());
        entity.setSecondStatistic(dto.secondStatistic());
        entity.setImgDescription(dto.imgDescription());
    }

    private PresentationSectionResponseDto toResponse(PresentationSection entity) {
        return new PresentationSectionResponseDto(
                entity.getId(),
                entity.getTitle(),
                entity.getFirstDescription(),
                entity.getSecondDescription(),
                entity.getFirstStatistic(),
                entity.getSecondStatistic(),
                entity.getImgUrl(),
                entity.getImgDescription()
        );
    }
}