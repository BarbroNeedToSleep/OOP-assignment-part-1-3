package se.lexicon.model;

import java.util.HashMap;
import java.util.Map;

public class AppUser {

    private static final Map<String, AppUser> instances = new HashMap<>();

    private final String userName;
    private final String password;
    private final AppRole role;


    private AppUser(String userName, String password, AppRole role) {
        if (userName == null || userName.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }

        this.userName = userName;
        this.password = password;
        this.role = role;
    }

    public static boolean hasUser(String userName) {
        return instances.containsKey(userName);
    }

    public static AppUser getInstance(String userName, String password, AppRole role) {
        if (instances.containsKey(userName)) {
            throw new IllegalStateException("AppUser with username '" + userName + "' already exists.");
        }

        AppUser user = new AppUser(userName, password, role);
        instances.put(userName, user);
        System.out.println("Created new AppUser: " + userName);
        return user;

    }




    // Getters

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public AppRole getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "AppUser{" + "userName='" + userName + "', role=" + role + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        AppUser other = (AppUser) obj;
        return userName.equals(other.userName);
    }

    @Override
    public int hashCode() {
        return userName.hashCode();
    }

    //Testing utility
    public static void clearInstances() {
        instances.clear();
    }
}
