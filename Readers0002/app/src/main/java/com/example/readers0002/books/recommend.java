package com.example.readers0002.books;

public class recommend {
    private String username,comment,date;
    private book book1,book2;
    public recommend(String username,String comment,String date,book book1,book book2){

        this.username=username;
        this.comment=comment;
        this.date=date;
        this.book1=book1;
        this.book2=book2;
    }
    public String getUsername() {
        return username;
    }

    public String getComment() {
        return comment;
    }

    public String getDate() {
        return date;
    }

    public book getBook1() {
        return book1;
    }

    public book getBook2() {
        return book2;
    }
}
