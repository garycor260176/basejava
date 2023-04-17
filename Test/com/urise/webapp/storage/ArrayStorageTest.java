package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AbstractArrayStorageTest {
    private Storage storage = new ArrayStorage();
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String UUID_NOT_EXIST = "dummy";

    @Before
    public void Setup() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void save() throws Exception {
        storage.save(new Resume(UUID_4));
        assertSize(4);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() throws Exception {
        for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT + 1; i++) {
            storage.save(new Resume());
        }
    }

    @Test
    public void update() throws Exception {
        Resume resume = new Resume(UUID_2);
        storage.update(resume);
        Assert.assertEquals(resume, storage.get(UUID_2));
    }
    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        Resume resume = new Resume(UUID_NOT_EXIST);
        storage.update(resume);
    }

    @Test
    public void get() throws Exception {
        storage.get(UUID_2);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get(UUID_NOT_EXIST);
    }

    @Test
    public void delete() throws Exception {
        storage.delete(UUID_2);
        assertSize(2);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete(UUID_NOT_EXIST);
    }

    @Test
    public void getAll() throws Exception {
        Assert.assertEquals(3, storage.getAll().length);
    }

    @Test
    public void size() throws Exception {
        assertSize(3);
    }

    private void assertSize(int size){
        Assert.assertEquals(size, storage.size());
    }
}