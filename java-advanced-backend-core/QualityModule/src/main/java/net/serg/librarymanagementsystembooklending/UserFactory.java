package net.serg.librarymanagementsystembooklending;

public class UserFactory {
    public static User createUser(String id, String name) {
        return new User(id, name);
    }
}