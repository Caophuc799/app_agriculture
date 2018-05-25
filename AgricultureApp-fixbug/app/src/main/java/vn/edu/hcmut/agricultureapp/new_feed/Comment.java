package vn.edu.hcmut.agricultureapp.new_feed;

/**
 * Created by vutuananh on 5/13/2018.
 */

public class Comment {
    private String userId;
    private String atTime;
    private String textContent;
    private String imageContentUrl;

    public Comment(){
    }

    public Comment(String userId, String atTime, String textContent, String imageContentUrl) {
        this.userId = userId;
        this.atTime = atTime;
        this.textContent = textContent;
        this.imageContentUrl = imageContentUrl;
    }

    public String getUserId(){
        return userId;
    }

    public String getAtTime() {
        return atTime;
    }

    public String getTextContent() {
        return textContent;
    }

    public String getImageContentUrl() {
        return imageContentUrl;
    }

    public void setAtTime(String atTime) {
        this.atTime = atTime;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public void setImageContentUrl(String imageContentUrl) {
        this.imageContentUrl = imageContentUrl;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }
}
