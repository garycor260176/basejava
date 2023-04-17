package com.urise.webapp.storage;
/*
 * Array based storage for Resumes
 */

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    protected int size;

    protected final Resume[] storage = new Resume[STORAGE_LIMIT];

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    final public void save(Resume resume) {
        if (size >= storage.length) {
            System.out.println("The maximum number of resumes has been reached. Cannot add.");
        } else {
            int index = findIndex(resume.getUuid());
            if (index >= 0) {
                System.out.println("Resume " + resume.getUuid() + " already exists.");
            } else {
                camelCase(resume, index);
                size++;
            }
        }
    }

    protected abstract void camelCase(Resume resume, int index);

    final public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index < 0) {
            System.out.println("Resume " + resume.getUuid() + " not found.");
            return;
        }
        storage[index] = resume;
    }

    final public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index < 0) {
            System.out.println("Resume " + uuid + " not found.");
            return null;
        }
        return storage[index];
    }

    final public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index < 0) {
            System.out.println("Resume " + uuid + " not found.");
            return;
        }
        deleteByIndex(index);
        size--;
        storage[size] = null;
    }

    protected abstract void deleteByIndex(int index);

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    protected abstract int findIndex(String uuid);
}