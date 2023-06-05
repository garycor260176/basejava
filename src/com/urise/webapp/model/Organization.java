package com.urise.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;
    private Link website;
    private List<Period> periods;

    public Organization(Link website, Period... periods) {
        this(website, Arrays.asList(periods));
    }

    public Organization(Link website, List<Period> periods) {
        this.website = website;
        this.periods = periods;
    }

    public Organization(String name, String url, Period... periods) {
        this(name, url, Arrays.asList(periods));
    }

    public Organization(String name, String url, List<Period> periods) {
        this.website = new Link(name, url);
        this.periods = periods;
    }

    public Organization() {
    }

    public Link getWebsite() {
        return website;
    }

    public List<Period> getPeriod() {
        return periods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!website.equals(that.website)) return false;

        boolean f = periods.equals(that.periods);
        return f;
    }

    @Override
    public int hashCode() {
        int result = website.hashCode();
        result = 31 * result + periods.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "header=" + website +
                ", period=" + periods +
                '}';
    }
}
