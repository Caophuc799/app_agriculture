package vn.edu.hcmut.agricultureapp.new_feed;

/**
 * Created by vutuananh on 5/13/2018.
 */

public class Status {
    private String topic;
    private String statusId;
    private String userId;
    private String atTime;
    private String title;
    private String textContent;
    private String imageContentUrl;
    private int likeNumber;
    public Status(){}

    public Status(String topic, String statusId, String userId, String atTime, String tilte, String textContent, String imageContentUrl, int likeNumber) {
        this.topic =topic;
        this.statusId =statusId;
        this.userId = userId;
        this.atTime = atTime;
        this.title = tilte;
        this.textContent = textContent;
        this.imageContentUrl = imageContentUrl;
        this.likeNumber = likeNumber;
    }

    public void setTopic(String topic){
        this.topic =topic;
    }

    public void setStatusId(String statusId){
        this.statusId = statusId;
    }

    public void setUserId(String userId){
        this.userId= userId;
    }

    public void setAtTime(String atTime) {
        this.atTime = atTime;
    }

    public void setTitle(String title ){
        this.title = title;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public void setImageContentUrl(String imageContentUrl) {
        this.imageContentUrl = imageContentUrl;
    }

    public void setLikeNumber(int likeNumber) {
        this.likeNumber = likeNumber;
    }

    public String getTopic(){
        return topic;
    }

    public String getStatusId(){
        return statusId;
    }

    public String getUserId(){
        return userId;
    }

    public String getAtTime() {
        return atTime;
    }

    public String getTitle(){
        return title;
    }

    public String getTextContent() {
        return textContent;
    }

    public String getImageContentUrl() {
        return imageContentUrl;
    }

    public int getLikeNumber() {
        return likeNumber;
    }

}
