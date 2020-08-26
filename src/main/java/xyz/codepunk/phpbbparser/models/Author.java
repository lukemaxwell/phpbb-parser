package xyz.codepunk.phpbbparser.models;


import java.util.HashMap;

public class Author {
    public int id;
    public String username;
    public int postCount;
    public String joined;

    public Author(int id, String username, int postCount, String joined) {
        this.id = id;
        this.username = username;
        this.postCount = postCount;
        this.joined = joined;
    }

}
