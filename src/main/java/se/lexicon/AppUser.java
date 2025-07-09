package se.lexicon;

public class AppUser {

    private String userName;
    private String password;
    private AppRole role;


    public AppUser(String userName, String password, AppRole role) {
       setUserName(userName);
       setPassword(password);
       setRole(role);
    }

    //Getters

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public AppRole getRole() {
        return role;
    }

    // Setters


   private void setUserName(String userName) {

        if (userName == null|| userName.trim().isEmpty()){
            throw new IllegalArgumentException("User name cannot be null or empty");
        }
        this.userName = userName;
    }

    private void setPassword(String password) {

        if (password == null|| password.trim().isEmpty()){
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        this.password = password;
    }

    private void setRole(AppRole role) {

        if (role == null){
            throw new IllegalArgumentException("Role cannot be null");
        }
        this.role = role;
    }

    @Override
    public String toString() {
        return "AppUser{" + "userName='" + userName + ", role=" + role + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Same memory
        if (obj == null || getClass() != obj.getClass()) return false;

        AppUser other = (AppUser) obj;

        return userName.equals(other.userName) && role == other.role;
    }

    @Override
    public int hashCode() {
        int result = userName != null ? userName.hashCode() : 0;
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }
}
