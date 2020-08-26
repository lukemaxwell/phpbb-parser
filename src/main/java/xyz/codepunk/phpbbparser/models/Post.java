package xyz.codepunk.phpbbparser.models;


public class Post {
    public Author author;
    public String date;
    public String content;

    public Post(Author author, String date, String content) {
        this.author = author;
        this.date = date;
        this.content = content;
    }

}
