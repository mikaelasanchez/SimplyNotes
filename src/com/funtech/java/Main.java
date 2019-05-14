package com.funtech.java;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    // Initialises the variables for the number of notes, list of note names, user input and input scanner
    private static int noOfNotes;
    private static List<String> notes = new ArrayList<>();
    private static Scanner input = new Scanner(System.in);
    private static String choice;

    public static void main(String[] args) {
        // Gets the number of notes and names of notes, and displays them to the user
        getNotes();
        println("Welcome to SimplyNotes.");
        System.out.format("You have %d notes.", noOfNotes);
        println("");
        println("");

        // Checks if there are any notes. If there are, list them and call the menu.
        if(noOfNotes>0){
            println("Notes:");
            for (String note:notes){
                println(note!=null ? note:"-"); // If there is no note, display it as "-"
            }
            println("");
            menu();
        }else{
            // If there are no notes, ask if the user wants to add a new one.
            // Otherwise, call the menu.
            println("Would you like to add a new note?");
            println("[1] Yes");
            println("[2] No");
            println("");
            System.out.print(">> ");
            choice = input.nextLine().toLowerCase();
            println("");

            if(choice.equals("1") || choice.contains("yes")){
                addNewNote();
            }else{
                menu();
            }
        }
    }

    private static void menu(){
        // Asks the user for instructions
        println("What would you like to do?");
        println("[1] Add a new note");
        println("[2] Delete a note");
        println("[3] Read a note");
        println("[4] Sort notes");
        println("[5] Exit");
        println("");
        System.out.print(">> ");
        choice = input.nextLine().toLowerCase();
        println("");

        // If statement to handle the user's choice
        if(choice.equals("1") || choice.contains("add")){
            addNewNote();
        }else if(choice.equals("2") || choice.contains("delete")){
            deleteNote();
        }else if(choice.equals("3") || choice.contains("read")){
            readNote();
        }else if(choice.equals("4") || choice.contains("sort")){
            sortNotes();
        }else if(choice.equals("5") || choice.contains("exit")){
            System.exit(0);
        }else{
            println("Please enter a valid option.");
            println("");
            menu();
        }

    }

    private static void getNotes(){
        // Collects the information about the number of notes and their contents

        try {
            String resourcePath = Main.class.getClassLoader().getResource("\\com\\funtech\\resources\\").getPath();

            // .replace() makes sure the file is actually saved in a folder, instead of being named "...%5cnotes.txt"
            File notesFile = new File(resourcePath.replace("%5c","/")+"notes.txt");

            // Checks if the notes file exists.
            if(notesFile.isFile()) {
                Scanner fileScanner = new Scanner(notesFile);
                fileScanner.useDelimiter("--end of note--");

                List<String> notesFromTxt = new ArrayList<>();

                if(fileScanner.hasNext()){
                    notesFromTxt.add(fileScanner.next());
                }

                for(String note:notesFromTxt){
                    println(note);
                    noOfNotes+=1;
                }

            }else{
                if(notesFile.createNewFile()){
                    println("Preparing for first time use...");
                    println("");
                }
            }

            // Sets the number of

        }catch(NullPointerException e){
            println("Error: Cannot find resources folder.");
        }
        catch(IOException e){
            println("Error: Cannot find notes file.");
        }
    }

    private static void addNewNote(){
        System.out.print("Enter the note name: ");
        String newNoteName = input.nextLine();
        println("");
        System.out.print("Enter note contents:");
        String newNoteContents = input.nextLine();
        Note newNote = new Note(newNoteName, newNoteContents);
        println("");
        println("Note preview:");
        printBorder(newNote);
        println("");
        println(newNote.title);
        println(newNote.date);
        println("");
        println(newNote.contents);
        printBorder(newNote);

        println("");
        println("[Press enter to exit]");
        input.nextLine();
        menu();
    }

    private static void deleteNote(){
        println("Deleting note...");

        println("");
        println("[Press enter to exit]");
        input.nextLine();
        menu();
    }

    private static void readNote(){
        println("Reading note...");

        println("");
        println("[Press enter to exit]");
        input.nextLine();
        menu();
    }

    private static void sortNotes(){
        println("Sorting notes...");

        println("");
        println("[Press enter to exit]");
        input.nextLine();
        menu();
    }

    // Saves time from writing System.out.println()
    static void println(Object text){
        System.out.println(text);
    }
    private static void printBorder(Note note){
        for(int i=0;i<note.contents.length() || i<note.date.length();i++){System.out.print("=");}
    }
}
