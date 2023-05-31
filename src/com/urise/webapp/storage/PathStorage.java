package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.serializer.StreamSerializer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private final StreamSerializer streamSerializer;

    protected PathStorage(String dir, StreamSerializer streamSerializer) {
        Objects.requireNonNull(dir, "directory must not be null");
        this.streamSerializer = streamSerializer;
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
            streamSerializer.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(key)));
        } catch (IOException e) {
            throw new StorageException("IO error", getFileName(key), e);
        }
    }

    @Override
    protected Resume doGet(Path key) {
        try {
            return streamSerializer.doRead(new BufferedInputStream(Files.newInputStream(key)));
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

    private String getFileName(Path key) {
        return key.getFileName().toString();
    }
}
