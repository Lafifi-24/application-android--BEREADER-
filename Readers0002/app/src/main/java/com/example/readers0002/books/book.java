package com.example.readers0002.books;

public class book {
    private String title,subtitle,authors,description,Isbn,categories,publishedDate,publisher,thumbnailUrl,language,idgoogle;

    private int  pageCount,id;
    private double rating;

    public book(String idgoogle, String title, String subtitle, String authors, String description, String isbn, String categories, String publishedDate, String publisher, String thumbnailUrl, int pageCount,String language) {
        this.title = title;
        this.idgoogle=idgoogle;
        this.subtitle = subtitle;
        this.authors = authors;
        this.description = description;
        Isbn = isbn;
        this.categories = categories;
        this.publishedDate = publishedDate;
        this.publisher = publisher;

        this.thumbnailUrl = thumbnailUrl;

        this.pageCount = pageCount;
        this.language=language;
    }
    public book(int id,String idgoogle,double rating, String title, String subtitle, String authors, String description, String isbn, String categories, String publishedDate, String publisher, String thumbnailUrl, int pageCount,String language) {
        this.title = title;
        this.rating=rating;
        this.idgoogle=idgoogle;
        this.id=id;
        this.subtitle = subtitle;
        this.authors = authors;
        this.description = description;
        Isbn = isbn;
        this.categories = categories;
        this.publishedDate = publishedDate;
        this.publisher = publisher;

        this.thumbnailUrl = thumbnailUrl;

        this.pageCount = pageCount;
        this.language=language;
    }






    public String getAuthors() {
        return authors;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getDescription() {
        return description;
    }

    public String getIsbn() {
        return Isbn;
    }

    public String getCategories() {
        return categories;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getPublisher() {
        return publisher;
    }



    public String getThumbnailUrl() {
        return thumbnailUrl;
    }


    public String getPageCount() { return ""+pageCount;
    }

    public String getLanguage() {
        return language;
    }

    public String getIdgoogle() {
        return idgoogle;
    }

    public String getId() {
        return id+"";
    }

    public double getRating() {
        return rating;
    }
}
