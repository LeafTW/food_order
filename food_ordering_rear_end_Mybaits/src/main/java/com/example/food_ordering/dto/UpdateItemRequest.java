package com.example.food_ordering.dto;

import java.util.List;

public class UpdateItemRequest {
    private List<String> upDataList;
    private boolean show;

    // Constructors, getters, and setters
    public UpdateItemRequest() {}

    public UpdateItemRequest(List<String> upDataList, boolean show) {
        this.upDataList = upDataList;
        this.show = show;
    }

    public List<String> getUpDataList() {
        return upDataList;
    }

    public void setUpDataList(List<String> upDataList) {
        this.upDataList = upDataList;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }
}
