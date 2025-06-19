package com.ifce.jedi.service;

import com.ifce.jedi.dto.Banner.*;
import com.ifce.jedi.model.SecoesSite.Banner.Banner;
import com.ifce.jedi.model.SecoesSite.Banner.BannerItem;
import com.ifce.jedi.repository.BannerItemRepository;
import com.ifce.jedi.repository.BannerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BannerService {

    @Autowired
    private BannerRepository bannerRepository;
    @Autowired
    private BannerItemRepository bannerItemRepository;
    @Autowired
    private CloudinaryService cloudinaryService;

    @Transactional
    public BannerResponseDto createBanner(BannerDto dto) {
        Banner banner = new Banner();
        banner.setTitle(dto.title());
        banner.setDescription(dto.description());

        List<BannerItem> items = dto.items().stream().map(s -> {
            BannerItem item = new BannerItem();
            item.setImgUrl(s.imgUrl());
            item.setButtonText(s.buttonText());
            item.setButtonUrl(s.buttonUrl());
            item.setBanner(banner);
            return item;
        }).toList();

        banner.setItems(items);
        Banner saved = bannerRepository.save(banner);

        return toResponse(saved);
    }

    @Transactional
    public BannerResponseDto updateBanner(BannerUpdateDto dto) {
        Banner banner = bannerRepository.findAll().stream().findFirst().orElseThrow(
                () -> new RuntimeException("Banner não encontrado.")
        );

        banner.setTitle(dto.title());
        banner.setDescription(dto.description());

        Banner updated = bannerRepository.save(banner);
        return toResponse(updated);
    }

    @Transactional
    public BannerItemResponseDto updateSlide(Long slideId, MultipartFile file, BannerItemDto dto) throws IOException {
        // Validação básica
        if (dto == null) {
            throw new IllegalArgumentException("DTO não pode ser nulo");
        }

        // Busca direta do item (mais eficiente)
        BannerItem item = bannerItemRepository.findById(slideId)
                .orElseThrow(() -> new RuntimeException("Slide não encontrado."));

        // Atualiza textos (se fornecidos)
        if (dto.buttonText() != null) {
            item.setButtonText(dto.buttonText());
        }
        if (dto.buttonUrl() != null) {
            item.setButtonUrl(dto.buttonUrl());
        }

        // Atualiza imagem (se fornecida)
        if (file != null && !file.isEmpty()) {
            // 1. Faz upload da nova imagem
            var uploadResult = cloudinaryService.uploadImage(file);

            // 2. Deleta a antiga (se existir)
            if (item.getCloudinaryPublicId() != null) {
                cloudinaryService.deleteImage(item.getCloudinaryPublicId());
            }

            // 3. Atualiza campos
            item.setImgUrl(uploadResult.get("url"));
            item.setCloudinaryPublicId(uploadResult.get("public_id"));
        }

        // Salva apenas o item (não o banner inteiro)
        bannerItemRepository.save(item);

        return new BannerItemResponseDto(
                item.getId(),
                item.getImgUrl(),
                item.getButtonText(),
                item.getButtonUrl()
        );
    }



    @Transactional
    public BannerResponseDto getBanner() {
        return bannerRepository.findAll().stream()
                .findFirst()
                .map(this::toResponse)
                .orElse(null);
    }

    @Transactional
    public BannerResponseDto addSlide(MultipartFile file, BannerItemDto dto) throws IOException {
        Banner banner = bannerRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Banner não encontrado"));


        BannerItem item = new BannerItem();
        var uploadResult = cloudinaryService.uploadImage(file);
        item.setImgUrl(uploadResult.get("url"));
        item.setCloudinaryPublicId(uploadResult.get("public_id"));
        item.setButtonText(dto.buttonText());
        item.setButtonUrl(dto.buttonUrl());
        item.setBanner(banner);
        banner.getItems().add(item);


        bannerRepository.save(banner);
        return toResponse(banner);
    }

    @Transactional
    public BannerResponseDto deleteSlide(long id) throws IOException {
        Banner banner = bannerRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Banner não encontrado"));

        BannerItem itemToRemove = banner.getItems().stream()
                .filter(item -> item.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Slide não encontrado"));

        cloudinaryService.deleteImage(itemToRemove.getCloudinaryPublicId());
        banner.getItems().remove(itemToRemove);

        bannerRepository.save(banner);

        return toResponse(banner);
    }

    private BannerResponseDto toResponse(Banner banner) {
        List<BannerItemResponseDto> slides = banner.getItems().stream().map(s ->
                new BannerItemResponseDto(s.getId(), s.getImgUrl(), s.getButtonText(), s.getButtonUrl())
        ).collect(Collectors.toList());

        return new BannerResponseDto(
                banner.getId(),
                banner.getTitle(),
                banner.getDescription(),
                slides
        );
    }
}