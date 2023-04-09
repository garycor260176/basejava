package com.urise.webapp.storage;

/*
 * Array based storage for Resumes
 */

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class ArrayStorage {
    private int size;

    Resume[] storage = new Resume[10000];

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        if (size >= storage.length) {
            System.out.println("The maximum number of resumes has been reached. Cannot add.");
            return;
        }
        if (findResume(resume.getUuid()) >= 0) {
            System.out.println("Resume " + resume.getUuid() + " already exists.");
            return;
        }
        storage[size++] = resume;
    }

    public void update(Resume resume) {
        int idx = findResume(resume.getUuid());
        if (idx < 0) {
            System.out.println("Resume " + resume.getUuid() + " not found.");
            return;
        }
        storage[idx] = resume;
    }

    public Resume get(String uuid) {
        int idx = findResume(uuid);
        if (idx < 0) System.out.println("Resume " + uuid + " not found.");
        return (idx < 0 ? null : storage[idx]);
    }

    public void delete(String uuid) {
        int idx = findResume(uuid);
        if (idx < 0) {
            System.out.println("Resume " + uuid + " not found.");
            return;
        }
        size--;
        System.arraycopy(storage, idx + 1, storage, idx, size - idx);
        storage[size] = null;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    private int findResume(String uuid) {
        for (int i = 0; i < size; i++)
            if (uuid.equals(storage[i].getUuid())) return i;
        return -1;
    }
}
