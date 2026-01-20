package com.ifce.jedi.service;

import com.ifce.jedi.dto.BannerMultiplo.BannerMultiploResponseDto;
import com.ifce.jedi.model.SecoesSite.BannerMultiplo.BannerMultiplo;
import com.ifce.jedi.repository.BannerMultiploRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BannerMultiploService {

    @Autowired
    private BannerMultiploRepository bannerMultiploRepository;
    @Autowired
    private LocalStorageService localStorageService;

    @Value("${app.base-url}")
    private String baseUrl;

    @Transactional
    public List<BannerMultiploResponseDto> create(MultipartFile[] files, List<String> linkUrls, List<String> titles)
            throws IOException {
        List<BannerMultiplo> items = new ArrayList<>();
        Integer maxPosition = bannerMultiploRepository.findMaxPosition();
        int startPosition = maxPosition == null ? 0 : maxPosition;

        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            BannerMultiplo item = new BannerMultiplo();
            var uploadResult = localStorageService.salvar(file);
            var linkCru = baseUrl + "/publicos/" + uploadResult;
            var linkSanitizado = linkCru.replaceAll("\\s+", "_");
            item.setImgUrl(linkSanitizado);
            item.setFileName(uploadResult);
            item.setLinkUrl(linkUrls.get(i));
            item.setTitle(titles.get(i));
            item.setPosition(startPosition + i + 1);
            items.add(item);
        }

        List<BannerMultiplo> saved = bannerMultiploRepository.saveAll(items);
        return saved.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<BannerMultiploResponseDto> getAll() {
        Sort sort = Sort.by(
                Sort.Order.asc("position"),
                Sort.Order.asc("id")
        );
        return bannerMultiploRepository.findAll(sort).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public BannerMultiploResponseDto getById(Long id) {
        return bannerMultiploRepository.findById(id)
                .map(this::toResponse)
                .orElse(null);
    }

    @Transactional
    public BannerMultiploResponseDto update(Long id, MultipartFile file, String linkUrl, String title)
            throws IOException {
        BannerMultiplo item = bannerMultiploRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Banner multiplo nao encontrado."));

        if (linkUrl != null) {
            item.setLinkUrl(linkUrl);
        }
        if (title != null) {
            item.setTitle(title);
        }

        if (file != null && !file.isEmpty()) {
            if (item.getFileName() != null) {
                localStorageService.deletar(item.getFileName());
            }
            var uploadResult = localStorageService.salvar(file);
            var linkCru = baseUrl + "/publicos/" + uploadResult;
            var linkSanitizado = linkCru.replaceAll("\\s+", "_");
            item.setImgUrl(linkSanitizado);
            item.setFileName(uploadResult);
        }

        BannerMultiplo updated = bannerMultiploRepository.save(item);
        return toResponse(updated);
    }

    @Transactional
    public List<BannerMultiploResponseDto> updateOrder(List<Long> orderedIds) {
        long totalItems = bannerMultiploRepository.count();
        if (orderedIds.size() != totalItems) {
            throw new IllegalArgumentException("A lista deve conter todos os banners.");
        }

        List<BannerMultiplo> items = bannerMultiploRepository.findAllById(orderedIds);
        Map<Long, BannerMultiplo> itemsById = items.stream()
                .collect(Collectors.toMap(BannerMultiplo::getId, Function.identity()));

        for (int i = 0; i < orderedIds.size(); i++) {
            BannerMultiplo item = itemsById.get(orderedIds.get(i));
            if (item == null) {
                throw new IllegalArgumentException("Banner multiplo nao encontrado.");
            }
            item.setPosition(i + 1);
        }

        bannerMultiploRepository.saveAll(items);
        return getAll();
    }

    @Transactional
    public void delete(Long id) throws IOException {
        BannerMultiplo item = bannerMultiploRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Banner multiplo nao encontrado."));

        if (item.getFileName() != null) {
            localStorageService.deletar(item.getFileName());
        }

        bannerMultiploRepository.delete(item);
    }

    private BannerMultiploResponseDto toResponse(BannerMultiplo item) {
        return new BannerMultiploResponseDto(
                item.getId(),
                item.getImgUrl(),
                item.getLinkUrl(),
                item.getTitle(),
                item.getPosition()
        );
    }
}
