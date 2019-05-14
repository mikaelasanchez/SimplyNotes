package com.funtech.java;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

class Note {
    String title;
    String date;
    String contents;

    Note(String title, String contents){
        this.title = title;
        this.contents = contents;

        /*
        Formats the date to dd/mm/yyyy and time to hh:mm
        e.g. 11/05/2019 18:03
        Then assigns this string value to the date attribute
        */
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();
        this.date = dateFormat.format(date);
    }

    static void delete(){

    }

    static void read(){

    }
}
