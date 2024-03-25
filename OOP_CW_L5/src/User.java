// The User class represents a user in the system.
public class User {

    // Private instance variables to store user information.
    private String userName;
    private String password;

    // Constructor for creating a User object with a specified username and password.
    public User(String userName, String password) {
        // Initialize the username and password for the User object.
        this.userName = userName;
        this.password = password;
    }

    // Getter method to retrieve the username of the user.
    public String getUserName() {
        return userName;
    }

    // Setter method to set the username of the user.
    public void setUserName(String userName) {
        this.userName = userName;
    }

    // Getter method to retrieve the password of the user.
    public String getPassword() {
        return password;
    }

    // Setter method to set the password of the user.
    public void setPassword(String password) {
        this.password = password;
    }
}
