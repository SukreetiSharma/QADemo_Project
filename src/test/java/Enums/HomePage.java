package Enums;

public enum HomePage {
    BOOKSTORE("Book Store Application");

    private String name;
    private HomePage(String name) {
        this.name = name;
    }

    public String getResourcesName() {
        return name;
    }
}
