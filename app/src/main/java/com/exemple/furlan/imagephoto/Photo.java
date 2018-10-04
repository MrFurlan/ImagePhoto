package com.exemple.furlan.imagephoto;

public class Photo {
    private String photoPath;
    private String photoComment;

    public Photo() {}

    public Photo(String photoPath, String photoComment) {
        this.photoPath = photoPath;
        this.photoComment = photoComment;
    }

    public String getPhotoPath() { return photoPath; }

    public void setPhotoPath(String photoPath) { this.photoPath = photoPath; }

    public String getPhotoComment() { return photoComment; }

    public void setPhotoComment(String photoComment) { this.photoComment = photoComment; }
}
