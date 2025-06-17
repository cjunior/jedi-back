package com.ifce.jedi.service;

import com.ifce.jedi.dto.Header.HeaderDto;
import com.ifce.jedi.dto.Header.HeaderResponseDto;
import com.ifce.jedi.dto.Header.HeaderUrlDto;
import com.ifce.jedi.model.SecoesSite.Header.Header;
import com.ifce.jedi.repository.HeaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class HeaderService {

    @Autowired
    private HeaderRepository headerRepository;
    @Autowired
    private CloudinaryService cloudinaryService;

    public HeaderResponseDto getHeader() {
        List<Header> headers = headerRepository.findAll();
        if (headers.isEmpty()) return null;
        return new HeaderResponseDto(headers.get(0));
    }

    public HeaderResponseDto updateHeader(HeaderDto dto) throws IOException {
        Header header = headerRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Header não encontrado"));

        if (dto.getFile() != null && !dto.getFile().isEmpty()) {
            if (header.getCloudinaryPublicId() != null) {
                cloudinaryService.deleteImage(header.getCloudinaryPublicId());
            }
            var uploadResult = cloudinaryService.uploadImage(dto.getFile());
            header.setLogoUrl(uploadResult.get("url"));
            header.setCloudinaryPublicId(uploadResult.get("public_id"));
        }

        header.setText1(dto.getText1());
        header.setText2(dto.getText2());
        header.setText3(dto.getText3());
        header.setText4(dto.getText4());
        header.setButtonText(dto.getButtonText());

        headerRepository.save(header);

        return new HeaderResponseDto(header);
    }

    public Header createHeader(HeaderUrlDto dto) {
        Header header = new Header();
        header.setLogoUrl(dto.logoUrl());
        header.setText1(dto.text1());
        header.setText2(dto.text2());
        header.setText3(dto.text3());
        header.setText4(dto.text4());
        header.setButtonText(dto.buttonText());
        return headerRepository.save(header);
    }
}
