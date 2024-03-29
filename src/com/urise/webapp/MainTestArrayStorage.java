package com.urise.webapp;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.Storage;
import com.urise.webapp.storage.SortedArrayStorage;

/**
 * Test for your com.urise.webapp.storage.ArrayStorage implementation
 */
public class MainTestArrayStorage {
    static final Storage ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume("uuid1", "name_1");
        Resume r2 = new Resume("uuid2", "name_2");
        Resume r3 = new Resume("uuid3", "name_3");

        ARRAY_STORAGE.save(r3);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r1);

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());
        try {
            System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));
        } catch (NotExistStorageException e) {
            System.out.println(e.toString());
        }

        printAll();
        ARRAY_STORAGE.delete(r2.getUuid());
        printAll();

        Resume rUpdate = new Resume("uuid9", "name_9");
        System.out.println("\nUpdate resume " + rUpdate.getUuid());
        try {
            ARRAY_STORAGE.update(rUpdate);
        } catch (NotExistStorageException e) {
            System.out.println(e.toString());
        }
        printAll();

        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAllSorted()) {
            System.out.println(r);
        }
    }
}
