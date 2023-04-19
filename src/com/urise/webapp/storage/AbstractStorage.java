package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.List;

public abstract class AbstractStorage<K> implements Storage {
    @Override
    public void save(Resume resume) {
        K key = getNotExistKey(resume.getUuid());
        doSave(resume, key);
    }

    public void update(Resume resume) {
        K key = getExistKey(resume.getUuid());
        doUpdate(resume, key);
    }

    public Resume get(String uuid) {
        K key = getExistKey(uuid);
        return doGet(key);
    }

    public void delete(String uuid) {
        K key = getExistKey(uuid);
        doDelete(key);
    }

    public List<Resume> getAll() {
        List<Resume> list = doGetAll();
        return list;
    }

    private K getNotExistKey(String uuid) {
        K key = findIndex(uuid);
        if (isExist(key)) {
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    private K getExistKey(String uuid) {
        K key = findIndex(uuid);
        if (!isExist(key)) {
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    protected abstract K findIndex(String uuid);

    protected abstract boolean isExist(K key);

    protected abstract void doSave(Resume resume, K key);

    protected abstract void doUpdate(Resume resume, K key);

    protected abstract Resume doGet(K key);

    protected abstract void doDelete(K key);

    protected abstract List<Resume> doGetAll();
}
