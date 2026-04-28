package org.example.accounts;

import org.example.dao.UserDAO;

public class AccountService {
    private static final AccountService instance = new AccountService();
    public static AccountService getInstance() {
        return instance;
    }

    private final UserDAO userDao = new UserDAO();

    public void addNewUser(UserDataSet user) {
        userDao.save(user);
    }

    public UserDataSet getUserByLogin(String login) {
        return userDao.findByLogin(login);
    }
}