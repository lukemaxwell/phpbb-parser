package xyz.codepunk;

import java.util.Date;

public class Author {
    int id;
    String username;
    int postCount;
    String joined;

    Author(int id, String username, int postCount, String joined) {
        this.id = id;
        this.username = username;
        this.postCount = postCount;
        this.joined = joined;
    }

}
