package org.example.dao;

import org.example.accounts.UserDataSet;
import org.example.dbService.DBConnector;

import java.sql.*;

public class UserDAO {

    public void save(UserDataSet user) {
        String sql = "INSERT INTO users (login, pass, email) VALUES (?, ?, ?)";

        try (Connection conn = DBConnector.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, user.getLogin());
                stmt.setString(2, user.getPass());
                stmt.setString(3, user.getEmail());

                stmt.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public UserDataSet findByLogin(String login) {
        String sql = "SELECT * FROM users WHERE login = ?";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, login);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    return new UserDataSet(
                            rs.getString("login"),
                            rs.getString("pass"),
                            rs.getString("email")
                    );
                }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return null;
    }
}