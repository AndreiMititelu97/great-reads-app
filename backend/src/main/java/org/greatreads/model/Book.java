package org.greatreads.model;

import lombok.Data;
import org.greatreads.util.Genre;

import java.util.Date;

@Data
public class Book {
    private int id;
    private String title;
    private String description;
    private Genre genre;
    private long isbn;
    private Date publishDate;//TODO use java.sql.Date?
    private boolean isApproved;
    private String pdfLink;
    //TODO Add hibernate
}
