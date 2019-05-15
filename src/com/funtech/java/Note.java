package com.funtech.java;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.funtech.java.Main.*;

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

    public String toString(){
        return this.title;
    }

    void save(){
        try {
            BufferedWriter noteSaver = new BufferedWriter(new FileWriter((notesFile.getPath()), true)); // true for append
            String parse = "<parse>";
            noteSaver.write(title+parse);
            noteSaver.newLine();
            noteSaver.write(date+parse);
            noteSaver.newLine();
            noteSaver.write(contents+parse);
            noteSaver.newLine();
            noteSaver.write("--end of note--");
            noteSaver.close();
        }catch(IOException e){
            println("Error: Cannot save the note.");
        }
    }

    static void delete(){

    }
}
