public class Comment {

    private String time;
    private String content;
    private String author;

    public Comment(String time, String content, String author) {
        this.time = time;
        this.content = content;
        this.author = author;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        String string = "Posted by: %s at %s\n%s\n\n";
        return String.format(string, author, time, content);
    }
}
