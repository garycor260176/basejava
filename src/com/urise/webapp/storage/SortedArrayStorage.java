package com.urise.webapp.storage;
/*
 * Array based storage for Resumes
 */

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    protected void InsertResume(Resume resume) {
        int insertIndex = -findIndex(resume.getUuid()) - 1;
        System.arraycopy(storage, insertIndex, storage, insertIndex + 1, size - insertIndex);
        storage[insertIndex] = resume;
    }

    protected void doAfterDelete(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
    }

    protected int findIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
