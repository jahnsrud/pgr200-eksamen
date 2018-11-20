package no.kristiania.pgr200.core;

public class Conference {

    private String title;
    private String description;

    public Conference(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Conference() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

