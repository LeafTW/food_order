package com.example.food_ordering.dto;

import java.util.List;

public class Page<T> {
    private List<T> content;
    private boolean first;
    private boolean last;
    private int number;
    private int totalPages;

    public Page() {
    }

    public Page(List<T> content, boolean first, boolean last, int number, int totalPages) {
        this.content = content;
        this.first = first;
        this.last = last;
        this.number = number;
        this.totalPages = totalPages;
    }

    // Getters and setters
    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}