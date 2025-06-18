package com.ifce.jedi.dto.Contents;

import java.util.List;

public class ContentUpdateWrapperDto {
    private List<ContentItemUpdateDto> items;

    public List<ContentItemUpdateDto> getItems() {
        return items;
    }

    public void setItems(List<ContentItemUpdateDto> items) {
        this.items = items;
    }
}
