package com.ifce.jedi.dto.Header;

import com.ifce.jedi.model.SecoesSite.Header;

import java.util.UUID;

public record HeaderResponseDto(
        UUID id,
        String logoUrl,
        String text1,
        String text2,
        String text3,
        String text4,
        String buttonText
) {
    public HeaderResponseDto(Header header) {
        this(
                header.getId(),
                header.getLogoUrl(),
                header.getText1(),
                header.getText2(),
                header.getText3(),
                header.getText4(),
                header.getButtonText()
        );
    }
}
