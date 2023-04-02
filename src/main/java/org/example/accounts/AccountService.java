package org.example.accounts;

import org.example.orm.UserDAO;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class AccountService {

    private static AccountService singleton;
    public static AccountService getInstance() {
        if (singleton == null)
            singleton = new AccountService();
        return singleton;
    }

    private static UserDAO dao = new UserDAO();
    private final Map<String, User> sessionIdToProfile;

    private AccountService() {
        this.sessionIdToProfile = new HashMap<>();
    }

    public void addNewUser(User user) throws IOException {
        dao.add(user);
    }

    public User getUserByLogin(String login) {
        return dao.getByLogin(login);
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