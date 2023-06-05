package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private final File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File key) {
        return key.exists();
    }

    @Override
    protected void doSave(Resume resume, File key) {
        try {
            key.createNewFile();
        } catch (IOException e) {
            throw new StorageException("IO error", key.getName(), e);
        }
        doUpdate(resume, key);
    }

    @Override
    protected void doUpdate(Resume resume, File key) {
        try {
            doWrite(resume, new BufferedOutputStream(new FileOutputStream(key)));
        } catch (IOException e) {
            throw new StorageException("IO error", key.getName(), e);
        }
    }

    @Override
    protected Resume doGet(File key) {
        try {
            return doRead(new BufferedInputStream(new FileInputStream(key)));
        } catch (IOException e) {
            throw new StorageException("IO error", key.getName(), e);
        }
    }

    @Override
    protected void doDelete(File key) {
        if (!key.delete()) {
            throw new StorageException("Delete file error", key.getName());
        }
    }

    @Override
    protected List<Resume> doGetAll() {
        File[] files = getFilesList();
        List<Resume> resumes = new ArrayList<>(files.length);
        for (File file : files) {
            resumes.add(doGet(file));
        }
        return resumes;
    }

    @Override
    public void clear() {
        File[] files = getFilesList();
        if (files != null) {
            for (File file : files) {
                doDelete(file);
            }
        }
    }

    @Override
    public int size() {
        return getFilesList().length;
    }

    private File[] getFilesList() {
        File[] list = directory.listFiles();
        if (list == null) {
            throw new StorageException("Directory error");
        }
        return list;
    }

    protected abstract void doWrite(Resume r, OutputStream os) throws IOException;

    protected abstract Resume doRead(InputStream is) throws IOException;
}
