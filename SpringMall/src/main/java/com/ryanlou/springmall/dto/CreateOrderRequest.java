package com.ryanlou.springmall.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class CreateOrderRequest {
    @NotEmpty // 只加在list or map 類型
    private List<BuyItem> buyItemList;

    public List<BuyItem> getBuyItemList() {
        return buyItemList;
    }

    public void setBuyItemList(List<BuyItem> buyItemList) {
        this.buyItemList = buyItemList;
    }
}
