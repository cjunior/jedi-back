package com.ifce.jedi.dto.BannerMultiplo;

import java.util.List;

public class BannerMultiploOrderDto {
    private List<Long> orderedIds;

    public List<Long> getOrderedIds() {
        return orderedIds;
    }

    public void setOrderedIds(List<Long> orderedIds) {
        this.orderedIds = orderedIds;
    }
}
