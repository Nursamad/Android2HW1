package kg.geektech.TaskApp36.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Task implements Serializable {




    @PrimaryKey(autoGenerate = true)
    private long id;
    private String text;
    private long createdAt;


    private String docId;
//    private String imageUrl;

    public Task() {
    }

    public Task(String text, long createdAt) {
        this.text = text;
        this.createdAt = createdAt;
    }
//
//    public String getImageUrl() {
//        return imageUrl;
//    }

    public void setDocId(String docId) {
        this.docId = docId;
    }
//
//    public void setImageUrl(String imageUrl) {
//        this.imageUrl = imageUrl;
//    }

    public String getDocId() {
        return docId;
    }
//
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
