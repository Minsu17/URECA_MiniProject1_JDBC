package movie.dto;

import java.sql.Date;

public class Review {
    private int id;
    private String username;
    private int rating;
    private String comment;
    private Date reviewDate;

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }
    
    @Override
    public String toString() {
        return "평점: " + rating + ", 댓글: " + comment + ", 작성자: " + username;
    }
}
