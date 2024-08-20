package com.himedia.rentmon_back.dto;

public class FnumDTO {
    private Integer sseq;
    private String[] numbers;

    // Getters and Setters
    public Integer getSseq() {
        return sseq;
    }

    public void setSseq(Integer sseq) {
        this.sseq = sseq;
    }

    public String[] getNumbers() {
        return numbers;
    }

    public void setNumbers(String[] numbers) {
        this.numbers = numbers;
    }
}
