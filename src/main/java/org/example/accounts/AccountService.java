package org.example.accounts;

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

    private final Map<String, User> loginToProfile;
    private final Map<String, User> sessionIdToProfile;

    private AccountService() {
        this.loginToProfile = new HashMap<>();
        this.sessionIdToProfile = new HashMap<>();
    }

    public void InitUsers() throws IOException {
        File users = new File("D:\\Java_Work\\FileManager\\users.txt");
        BufferedReader reader = new BufferedReader(new FileReader(users));

        String line = reader.readLine();
        while (line != null) {
            String[] parts = line.split(" ");
            User user = loginToProfile.get(parts[0]);
            if (user == null) {
                loginToProfile.put(parts[0], new User(parts[0], parts[2], parts[1]));
            }
            line = reader.readLine();
        }
        reader.close();
    }

    private void WriteUser(User user) throws IOException {
        File users = new File("D:\\Java_Work\\FileManager\\users.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(users, true));

        String line = "\n" + user.getLogin() + " " + user.getEmail() + " " + user.getPassword();
        writer.append(line);
        writer.close();
    }

    public void addNewUser(User user) throws IOException {
        loginToProfile.put(user.getLogin(), user);
        WriteUser(user);
    }

    public User getUserByLogin(String login) {
        return loginToProfile.get(login);
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