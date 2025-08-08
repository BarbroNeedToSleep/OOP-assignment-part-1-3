package se.lexicon.dao.impl;

import se.lexicon.dao.AppUserDAO;
import se.lexicon.model.AppUser;

import java.util.ArrayList;
import java.util.List;

public class AppUserDAOCollection implements AppUserDAO {

    private List<AppUser> appUsers = new ArrayList<>();



    @Override
    public AppUser persist(AppUser appUser) {

        if (appUser == null) {
            throw new IllegalArgumentException("App user cannot be null");
        }

        // Check for duplicate username
        if (findByUsername(appUser.getUserName()) != null) {
            System.out.println("Username already exists: " + appUser.getUserName());
            return null;
        }

        appUsers.add(appUser);
        System.out.println("App user saved: " + appUser);
        return appUser;
    }

    @Override
    public AppUser findByUsername(String userName) {

        for (AppUser user : appUsers) {
            if (user.getUserName().equals(userName)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<AppUser> findAll() {
        return new ArrayList<>(appUsers);
    }

    @Override
    public AppUser remove(AppUser appUser) {
        if (appUser == null) {
            throw new IllegalArgumentException("App user cannot be null");
        }

        boolean removed = appUsers.remove(appUser);
        if (removed) {
            System.out.println("App user removed: " + appUser);
            return appUser;
        } else {
            System.out.println("App user not found: " + appUser);
            return null;
        }
    }


}
