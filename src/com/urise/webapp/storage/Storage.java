package com.urise.webapp.storage;
/*
 * Array based storage for Resumes
 */

import com.urise.webapp.model.Resume;

import java.util.List;

public interface Storage {
    public void clear();

    public void save(Resume resume);

    public void update(Resume resume);

    public Resume get(String uuid);

    public void delete(String uuid);

    public List<Resume> getAll();

    public int size();
}
