package com.epam.esm.entity.dto;

import java.util.Objects;

public class SortDataDto {

    private String sortingParameter;

    private Boolean descending;

    private Integer limit;

    private Integer offset;

    public String getSortingParameter() {
        return sortingParameter;
    }

    public void setSortingParameter(String sortingParameter) {
        this.sortingParameter = sortingParameter;
    }

    public Boolean getDescending() {
        return descending;
    }

    public void setDescending(Boolean descending) {
        this.descending = descending;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SortDataDto that = (SortDataDto) o;
        return Objects.equals(sortingParameter, that.sortingParameter)
                && Objects.equals(descending, that.descending)
                && Objects.equals(limit, that.limit)
                && Objects.equals(offset, that.offset);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sortingParameter, descending, limit, offset);
    }

    @Override
    public String toString() {
        return "SortDataDto{" +
                "sortingParameter='" + sortingParameter + '\'' +
                ", descending=" + descending +
                ", limit=" + limit +
                ", offset=" + offset +
                '}';
    }

}
