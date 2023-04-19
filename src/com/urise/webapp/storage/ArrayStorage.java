package com.urise.webapp.storage;
/*
 * Array based storage for Resumes
 */

import com.urise.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {
    @Override
    protected void camelCase(Resume resume, Integer index) {
        storage[size] = resume;
    }

    @Override
    protected Integer findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void deleteByIndex(Integer index) {
        storage[index] = storage[size - 1];
    }
}
