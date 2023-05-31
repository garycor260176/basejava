package com.urise.webapp.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import static com.urise.webapp.util.DateUtil.NOW;

public class Period implements Serializable {
    private static final long serialVersionUID = 1L;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String position;
    private String description;

    public Period() {
    }

    public Period(LocalDate dateFrom, String position, String description) {
        this(dateFrom, NOW, position);
        this.description = description;
    }

    public Period(LocalDate dateFrom, LocalDate dateTo, String position, String description) {
        this(dateFrom, dateTo, position);
        this.description = description;
    }

    public Period(LocalDate dateFrom, LocalDate dateTo, String position) {
        Objects.requireNonNull(dateFrom, "dateFrom must not be null");
        //Objects.requireNonNull(dateTo, "dateTo must not be null");
        Objects.requireNonNull(position, "title must not be null");
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.position = position;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public String getPosition() {
        return position;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Period period = (Period) o;

        if (!dateFrom.equals(period.dateFrom)) return false;
        if (!dateTo.equals(period.dateTo)) return false;
        if (!position.equals(period.position)) return false;
        return Objects.equals(description, period.description);
    }

    @Override
    public int hashCode() {
        int result = dateFrom.hashCode();
        result = 31 * result + dateTo.hashCode();
        result = 31 * result + position.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Period{" +
                "dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", title='" + position + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
