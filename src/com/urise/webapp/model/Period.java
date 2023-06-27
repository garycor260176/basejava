package com.urise.webapp.model;

import com.urise.webapp.util.DateUtil;
import com.urise.webapp.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import static com.urise.webapp.util.DateUtil.NOW;

@XmlAccessorType(XmlAccessType.FIELD)
public class Period implements Serializable {
    private static final long serialVersionUID = 1L;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate dateFrom;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate dateTo;
    private String position;
    private String description;

    public Period() {
    }

    public Period(LocalDate dateFrom, String position, String description) {
        this(dateFrom, NOW, position);
        setDescription(description);
    }

    public Period(LocalDate dateFrom, LocalDate dateTo, String position, String description) {
        this(dateFrom, dateTo, position);
        setDescription(description);
    }

    public Period(LocalDate dateFrom, LocalDate dateTo, String position) {
        Objects.requireNonNull(dateFrom, "dateFrom must not be null");
        Objects.requireNonNull(dateTo, "dateTo must not be null");
        Objects.requireNonNull(position, "title must not be null");
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.position = position;
        setDescription("");
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public String getDateFromFormat() {
        return DateUtil.format(dateFrom);
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public String getDateToFormat() {
        return DateUtil.format(dateTo);
    }

    public String getPosition() {
        return position;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? "" : description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Period period = (Period) o;

        if (!dateFrom.equals(period.dateFrom)) return false;
        if (!dateTo.equals(period.dateTo)) return false;
        if (!position.equals(period.position)) return false;
        return Objects.equals(getDescription(), period.getDescription());
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
