package no.kristiania.pgr200.core;

public class Talk {

    private String title;
	private String description;
    private String topic;
    private int talkId;

    public Talk() {

    }

    public Talk(String title, String description, String topic) {
        this.title = title;
        this.description = description;
        this.topic = topic;
    }

	public void setTitle(String title) {
    	this.title = title;
    }
    
    public void setDescription(String description) {
    	this.description = description;
    }
    
    public void setTopic(String topic) {
    	this.topic = topic;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getTopic() {
        return topic;
    }

    public int getId() {
        return talkId;
    }

    public void setId(int talkid) {
        this.talkId = talkid;
    }

    @Override
    public String toString() {
        return "Talk: \nTitle: " + title + "\n" +
                "Description: " + description + "\n" +
                "Topic: " + topic + "\n" +
                "Talk ID: " + talkId;
    }
}
