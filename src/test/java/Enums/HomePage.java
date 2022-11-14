package Enums;

public enum HomePage {
    ELEMENT("Elements"),
    FORM("Forms"),
    ALERTS("Alerts, Frame & Windows"),
    WIDGETS("Widgets"),
    INTERACTIONS("Interactions"),
    BOOKSTORE("Book Store Application");

    private String name;
    private HomePage(String name) {
        this.name = name;
    }

    public String getResourcesName() {
        return name;
    }
}
