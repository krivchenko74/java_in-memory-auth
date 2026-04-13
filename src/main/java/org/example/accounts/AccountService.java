package org.example.accounts;

import java.util.HashMap;
import java.util.Map;

public class AccountService {
    private static final AccountService instance = new AccountService();
    public static AccountService getInstance() {
        return instance;
    }
    private final Map<String, UserProfile> loginToProfile;

    public AccountService() {
        loginToProfile = new HashMap<>();
    }

    public void addNewUser(UserProfile user) {
        loginToProfile.put(user.getLogin(), user);
    }

    public UserProfile getUserByLogin(String login) {
        return loginToProfile.get(login);
    }
}
