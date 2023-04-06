/**
 * Array based storage for Resumes
 */

import java.util.Arrays;

public class ArrayStorage {
    private int numberResumes;

    Resume[] storage = new Resume[10000];

    void clear() {
        Arrays.fill(storage, 0, numberResumes, null);
        numberResumes = 0;
    }

    void save(Resume resume) {
        storage[numberResumes++] = resume;
    }

    Resume get(String uuid) {
        for (int i = 0; i < numberResumes; i++)
            if (uuid.equals(storage[i].uuid)) return storage[i];
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < numberResumes; i++)
            if (uuid.equals(storage[i].uuid)) {
                numberResumes--;
                System.arraycopy(storage, i + 1, storage, i, numberResumes - i);
                storage[numberResumes] = null;
                break;
            }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, numberResumes);
    }

    int size() {
        return numberResumes;
    }
}
