package com.urise.webapp.sql;

import com.urise.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void runSql(String sql) {
        runSql(sql, PreparedStatement::execute);
    }

    public interface SqlStatement<T> {
        T runSql(PreparedStatement st) throws SQLException;
    }

    public <T> T runSql(String sql, SqlStatement<T> statement) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            return statement.runSql(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public <T> T Execute(SqlExecutor<T> executor) {
        try (Connection conn = connectionFactory.getConnection()) {
            try {
                T res = executor.execute(conn);
                return res;
            } catch (SQLException e) {
                throw ExceptionUtil.convertException(e);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
