package org.greatreads.model;

import lombok.Data;

import java.util.Date;

@Data
public class Review {
    private int id;
    private short rating;
    private String comment;
    private Date publishedTimestamp;//TODO use java.sql.Date or java.time.format?
    //TODO Add hibernate
}
