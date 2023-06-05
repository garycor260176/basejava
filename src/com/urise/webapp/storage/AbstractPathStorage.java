package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private final Path directory;

    protected AbstractPathStorage(String dir) {
        Objects.requireNonNull(dir, "directory must not be null");
        directory = Paths.get(dir);
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected boolean isExist(Path key) {
        return Files.isRegularFile(key);
    }

    @Override
    protected void doSave(Resume resume, Path key) {
        try {
            Files.createFile(key);
        } catch (IOException e) {
            throw new StorageException("IO error", getFileName(key), e);
        }
        doUpdate(resume, key);
    }

    @Override
    protected void doUpdate(Resume resume, Path key) {
        try {
            doWrite(resume, new BufferedOutputStream(Files.newOutputStream(key)));
        } catch (IOException e) {
            throw new StorageException("IO error", getFileName(key), e);
        }
    }

    @Override
    protected Resume doGet(Path key) {
        try {
            return doRead(new BufferedInputStream(Files.newInputStream(key)));
        } catch (IOException e) {
            throw new StorageException("IO error", getFileName(key), e);
        }
    }

    @Override
    protected void doDelete(Path key) {
        try {
            Files.delete(key);
        } catch (IOException e) {
            throw new StorageException("Delete Path error", getFileName(key), e);
        }
    }

    @Override
    protected List<Resume> doGetAll() {
        return getPathsList().map(this::doGet).collect(Collectors.toList());
    }

    @Override
    public void clear() {
        getPathsList().forEach(this::doDelete);
    }

    @Override
    public int size() {
        return (int) getPathsList().count();
    }

    private Stream<Path> getPathsList() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Read files error", e);
        }
    }

    private String getFileName(Path path) {
        return path.getFileName().toString();
    }

    protected abstract void doWrite(Resume r, OutputStream os) throws IOException;

    protected abstract Resume doRead(InputStream is) throws IOException;
}
