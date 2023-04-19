package com.urise.webapp.storage;
/*
 * Array based storage for Resumes
 */

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected boolean isExist(Integer key) {
        return key >= 0;
    }

    @Override
    protected void doSave(Resume resume, Integer key) {
        if (size >= storage.length) {
            throw new StorageException("The maximum number of resumes has been reached. Cannot add.", resume.getUuid());
        } else {
            int index = findIndex(resume.getUuid());
            if (index >= 0) {
                throw new ExistStorageException(resume.getUuid());
            } else {
                camelCase(resume, index);
                size++;
            }
        }
    }

    @Override
    protected void doUpdate(Resume resume, Integer key) {
        storage[key] = resume;
    }

    @Override
    protected Resume doGet(Integer key) {
        return storage[key];
    }

    @Override
    protected void doDelete(Integer key) {
        deleteByIndex(key);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected List<Resume> doGetAll() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

    @Override
    protected abstract Integer findIndex(String uuid);

    protected abstract void deleteByIndex(Integer index);

    protected abstract void camelCase(Resume resume, Integer index);

}
