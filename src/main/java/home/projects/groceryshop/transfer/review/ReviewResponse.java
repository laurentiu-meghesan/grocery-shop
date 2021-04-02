package home.projects.groceryshop.transfer.review;

public class ReviewResponse {

    private long id;
    private String content;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ReviewResponse{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }
}
