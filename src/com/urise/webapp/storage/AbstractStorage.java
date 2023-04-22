package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.List;
import java.util.Comparator;

public abstract class AbstractStorage<T> implements Storage {
    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    @Override
    public void save(Resume resume) {
        T key = getNotExistKey(resume.getUuid());
        doSave(resume, key);
    }

    public void update(Resume resume) {
        T key = getExistKey(resume.getUuid());
        doUpdate(resume, key);
    }

    public Resume get(String uuid) {
        T key = getExistKey(uuid);
        return doGet(key);
    }

    public void delete(String uuid) {
        T key = getExistKey(uuid);
        doDelete(key);
    }

    public List<Resume> getAllSorted() {
        List<Resume> list = doGetAll();
        list.sort(RESUME_COMPARATOR);
        return list;
    }

    private T getNotExistKey(String uuid) {
        T key = getSearchKey(uuid);
        if (isExist(key)) {
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    private T getExistKey(String uuid) {
        T key = getSearchKey(uuid);
        if (!isExist(key)) {
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    protected abstract T getSearchKey(String uuid);

    protected abstract boolean isExist(T key);

    protected abstract void doSave(Resume resume, T key);

    protected abstract void doUpdate(Resume resume, T key);

    protected abstract Resume doGet(T key);

    protected abstract void doDelete(T key);

    protected abstract List<Resume> doGetAll();
}
