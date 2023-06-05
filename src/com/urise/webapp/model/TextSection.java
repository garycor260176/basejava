package com.urise.webapp.model;

public class TextSection extends Section {
    private static final long serialVersionUID = 1L;
    private String content;

    public TextSection(String content) {
        this.content = content;
    }

    public TextSection() {
    }

    public String getDescription() {
        return content;
    }

    @Override
    public String toString() {
        return content.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextSection that = (TextSection) o;
        return content.equals(that.content);
    }

    @Override
    public int hashCode() {
        return content.hashCode();
    }
}
