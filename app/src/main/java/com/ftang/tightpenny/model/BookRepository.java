package com.ftang.tightpenny.model;

import java.util.Iterator;

public class BookRepository {

    public void fetch() {
        Book book = new Book("Title here", "2nd edition");
        book.save();

        Iterator<Book> books = Book.findAll(Book.class);
    }
}
