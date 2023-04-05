/**
 * Array based storage for Resumes
 */

import java.util.Arrays;

public class ArrayStorage {
    private int numberOfResume;

    Resume[] storage = new Resume[10000];

    void clear() {
        Arrays.fill(storage, null);
        numberOfResume = 0;
    }

    void save(Resume resume) {
        storage[numberOfResume++] = resume;
    }

    Resume get(String uuid) {
        for (int i = 0; i < numberOfResume; i++)
            if (uuid.equals(storage[i].uuid)) return storage[i];
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < numberOfResume; i++)
            if (uuid.equals(storage[i].uuid)) {
                System.arraycopy(storage, i + 1, storage, i, numberOfResume - i);
                numberOfResume--;
            }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        if (numberOfResume > 0) {
            Resume[] new_storage = new Resume[numberOfResume];
            System.arraycopy(storage, 0, new_storage, 0, numberOfResume);
            return new_storage;
        }
        return new Resume[0];
    }

    int size() {
        return numberOfResume;
    }
}
