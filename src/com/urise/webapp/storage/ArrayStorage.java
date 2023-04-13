package com.urise.webapp.storage;
/*
 * Array based storage for Resumes
 */

import com.urise.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {
    protected void InsertResume(Resume resume, int index) {
        storage[size] = resume;
    }

    protected void doAfterDelete(int index) {
        storage[index] = storage[size - 1];
    }

    protected int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}
