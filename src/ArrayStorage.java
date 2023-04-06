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

    private int findResume(String uuid) {
        for (int i = 0; i < numberResumes; i++)
            if (uuid.equals(storage[i].uuid)) return i;
        return -1;
    }

    void save(Resume resume) {
        if (numberResumes == 10000 || findResume(resume.uuid) >= 0) return;
        storage[numberResumes++] = resume;
    }

    Resume get(String uuid) {
        int idx = findResume(uuid);
        return (idx >= 0 ? storage[idx] : null);
    }

    void delete(String uuid) {
        int idx = findResume(uuid);
        if (idx < 0) return;

        numberResumes--;
        System.arraycopy(storage, idx + 1, storage, idx, numberResumes - idx);
        storage[numberResumes] = null;
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
