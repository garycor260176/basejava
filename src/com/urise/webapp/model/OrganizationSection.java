package com.urise.webapp.model;

import java.util.List;

public class OrganizationSection extends Section {
    private List<Organization> items;

    public OrganizationSection(List<Organization> organizations) {
        this.items = organizations;
    }

    public OrganizationSection() {
    }

    public List<Organization> getItems() {
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
