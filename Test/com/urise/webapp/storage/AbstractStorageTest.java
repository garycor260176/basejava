package com.urise.webapp.storage;

import com.urise.webapp.ResumeTestData;
import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = new File("C:\\java\\basejava\\storage");
    protected final Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final Resume RESUME_1 = ResumeTestData.createResume(UUID_1, "fullName_1");
    private static final String UUID_2 = "uuid2";
    private static final Resume RESUME_2 = ResumeTestData.createResume(UUID_2, "fullName_2");
    private static final String UUID_3 = "uuid3";
    private static final Resume RESUME_3 = ResumeTestData.createResume(UUID_3, "fullName_3");
    private static final String UUID_4 = "uuid4";
    private static final Resume RESUME_4 = ResumeTestData.createResume(UUID_4, "fullName_4");
    private static final String UUID_NOT_EXIST = "dummy";

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void Setup() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void save() throws Exception {
        storage.save(RESUME_4);
        assertGet(RESUME_4);
        assertSize(4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(RESUME_2);
    }

    @Test
    public void update() throws Exception {
        Resume resume = new Resume(UUID_2, "rename_2");
        storage.update(resume);
        assertEquals(resume, storage.get(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(RESUME_4);
    }

    @Test
    public void get() throws Exception {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get(UUID_NOT_EXIST);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_2);
        assertSize(2);
        storage.get(UUID_2);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete(UUID_NOT_EXIST);
    }

    @Test
    public void getAll() throws Exception {
        assertSize(3);
        List<Resume> list = storage.getAllSorted();
        Assert.assertEquals(Arrays.asList(RESUME_1, RESUME_2, RESUME_3), list);
    }

    @Test
    public void size() throws Exception {
        assertSize(3);
    }

    private void assertGet(Resume r) {
        Assert.assertEquals(storage.get(r.getUuid()), r);
    }

    private void assertSize(int size) {
        Assert.assertEquals(size, storage.size());
    }
}