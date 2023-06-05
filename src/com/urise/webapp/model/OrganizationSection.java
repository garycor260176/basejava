package com.urise.webapp.model;

import java.util.Arrays;
import java.util.List;

public class OrganizationSection extends Section {
    private static final long serialVersionUID = 1L;
    private List<Organization> items;

    public OrganizationSection(Organization... organizations) {
        this(Arrays.asList(organizations));
    }

    public OrganizationSection(List<Organization> organizations) {
        this.items = organizations;
    }

    public OrganizationSection() {
    }

    public List<Organization> getItems() {
        return items;
    }

    public List<Organization> getOrganizations() {
        return items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationSection that = (OrganizationSection) o;

        return items.equals(that.items);
    }

    @Override
    public int hashCode() {
        return items.hashCode();
    }

    @Override
    public String toString() {
        return items.toString();
    }

}
