package org.example.accounts;

import org.example.Connectivity;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AccountService {

    private static AccountService singleton;
    public static AccountService getInstance() {
        if (singleton == null)
            singleton = new AccountService();
        return singleton;
    }

    private final Map<String, User> sessionIdToProfile;
    private final Connectivity connectivity;

    private AccountService() {
        this.connectivity = new Connectivity();
        this.sessionIdToProfile = new HashMap<>();
    }

    public void addNewUser(User user) throws IOException {
        try {
            String query = String.format("INSERT INTO `users` (`login`, `password`, `email`) VALUES ('%s', '%s', '%s');",
                    user.getLogin(), user.getPassword(), user.getEmail());
            connectivity.getStatement().executeUpdate(query);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUserByLogin(String login) {
        String query = String.format("SELECT * FROM users WHERE login='%s'", login);
        try {
            ResultSet rs = connectivity.getStatement().executeQuery(query);
            rs.next();
            User user = new User(rs.getString(2),
                    rs.getString(3),
                    rs.getString(4));
            return user;
        } catch (SQLException e) {
            return null;
        }
    }

    public User getUserBySessionId(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }

    public void addSession(String sessionId, User user) {
        sessionIdToProfile.put(sessionId, user);
    }

    public void deleteSession(String sessionId) {
        sessionIdToProfile.remove(sessionId);
    }
}