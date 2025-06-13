package com.ifce.jedi.service;

import com.ifce.jedi.dto.Header.HeaderDto;
import com.ifce.jedi.dto.Header.HeaderResponseDto;
import com.ifce.jedi.model.SecoesSite.Header;
import com.ifce.jedi.repository.HeaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HeaderService {

    @Autowired
    private HeaderRepository headerRepository;

    public HeaderResponseDto getHeader() {
        List<Header> headers = headerRepository.findAll();
        if (headers.isEmpty()) return null;
        return new HeaderResponseDto(headers.get(0));
    }

    public HeaderResponseDto updateHeader(HeaderDto dto) {
        Header header = headerRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Header não encontrado"));

        header.setText1(dto.text1());
        header.setText2(dto.text2());
        header.setText3(dto.text3());
        header.setText4(dto.text4());
        header.setButtonText(dto.buttonText());
        header.setLogoUrl(dto.logoUrl());

        headerRepository.save(header);

        return new HeaderResponseDto(header);
    }

    public Header createHeader(HeaderDto dto) {
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
