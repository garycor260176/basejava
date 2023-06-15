package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.Execute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("DELETE FROM resume")) {
                        ps.execute();
                        return null;
                    }
                }
        );
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.Execute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement(
                            "INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                        ps.setString(1, resume.getUuid());
                        ps.setString(2, resume.getFullName());
                        ps.execute();
                    }
                    return null;
                }
        );
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.Execute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement(
                            "UPDATE resume SET full_name = ? WHERE uuid =?")) {
                        String uuid = resume.getUuid();
                        ps.setString(1, resume.getFullName());
                        ps.setString(2, uuid);
                        executeUpdate(ps, resume.getUuid());
                    }
                    return null;
                }
        );
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.Execute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement(
                            "SELECT * FROM resume WHERE resume.uuid =?")) {
                        ps.setString(1, uuid);
                        ResultSet rs = ps.executeQuery();
                        if (!rs.next()) {
                            throw new NotExistStorageException(uuid);
                        }
                        return new Resume(uuid, rs.getString("full_name"));
                    }
                }
        );
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.Execute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement(
                            "DELETE FROM resume WHERE resume.uuid =?")) {
                        ps.setString(1, uuid);
                        ps.execute();
                        executeUpdate(ps, uuid);
                    }
                    return null;
                }
        );
    }

    @Override
    public List<Resume> getAllSorted() {
        Map<String, Resume> map = new LinkedHashMap<>();
        return sqlHelper.Execute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement(
                            "SELECT * FROM resume r ORDER BY full_name, uuid")) {
                        ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                            Resume resume = new Resume(rs.getString("uuid"), rs.getString("full_name"));
                            map.put(resume.getUuid(), resume);
                        }
                    }
                    return new ArrayList<>(map.values());
                }
        );
    }

    @Override
    public int size() {
        return sqlHelper.Execute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("SELECT count(*) FROM resume")) {
                        ResultSet rs = ps.executeQuery();
                        return rs.next() ? rs.getInt(1) : 0;
                    }
                }
        );
    }

    private void executeUpdate(PreparedStatement ps, String uuid) throws SQLException {
        if (ps.executeUpdate() == 0) {
            throw new NotExistStorageException(uuid);
        }
    }
}

