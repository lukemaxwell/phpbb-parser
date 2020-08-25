package xyz.codepunk;

import java.util.Date;

public class Post {
    Author author;
    String date;
    String content;

    Post(Author author, String date, String content) {
        this.author = author;
        this.date = date;
        this.content = content;
    }
}
