package com.jonathan;

public class User implements Runnable{
    private final WebPage webPage;
    private final String userName;

    public User(WebPage webPage, String userName) {
        this.webPage = webPage;
        this.userName = userName;
    }

    public void run() {
        try {
            webPage.connect(userName);
            webPage.disconnect(userName);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
