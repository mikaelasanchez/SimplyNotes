package com.funtech.java;

//import statements
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 *  Note taking console application for the Funtech Interview process
 *
 * @author Mikaela Sanchez
 * @version 1.0
 *
 */

public class Main {
    // Initialises the variables for the number of notes, list of notes, user input and input scanner
    private static int noOfNotes;
    private static List<Note> notes = new ArrayList<>();
    private static Scanner input = new Scanner(System.in);
    private static String choice;
    static List<String> notesFromTxt;
    static File notesFile;


    public static void main(String[] args) {

        // Sets the file where note data will be stored
        try {
            String resourcePath = Main.class.getClassLoader().getResource("\\com\\funtech\\resources\\").getPath();
            notesFile = new File(resourcePath.replace("%5c","/")+"notes.txt");
        }catch(NullPointerException e) {
            println("Error: Cannot find resources folder.");
            println("Creating resources folder...");

            try{
                String resourcePath = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
                File resourceFolder = new File(resourcePath+"/com/funtech/resources/");
                println((resourceFolder.mkdirs())?"Success!":"Could not create folder.");
            }catch(Exception e2){
                println("Error: Unable to create resources folder.");
            }finally{
                String resourcePath = Main.class.getClassLoader().getResource("\\com\\funtech\\resources\\").getPath();
                notesFile = new File(resourcePath.replace("%5c","/")+"notes.txt");
            }
        }

        // Gets the number of notes and names of notes, and displays them to the user
        getNotes();
        println("Welcome to SimplyNotes.");
        System.out.format("You have %d notes.", noOfNotes);
        println("");
        println("");

        // Checks if there are any notes. If there are, list them and call the menu.
        if(noOfNotes>0){
            println("Notes:");
            for (Note note:notes){
                println(note);
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

        noOfNotes = 0; // Sets number to 0 to recount number of notes
        notes.clear();

        try {
            // Checks if the notes file exists.
            if(notesFile.isFile()) {

                // If it does exist, read the file and separate each note
                Scanner fileScanner = new Scanner(notesFile);

                notesFromTxt = new ArrayList<>();

                // Add every note to the notesFromTxt arraylist and add 1 to number of notes
                while(fileScanner.hasNext()){
                    fileScanner.useDelimiter("--end of note--");
                    notesFromTxt.add(noOfNotes, fileScanner.next());
                    noOfNotes++;
                }

                // Parse each individual note and turn the data into a Note object
                for(String note:notesFromTxt){
                    String parseString = "<parse>";
                    Pattern parsePattern = Pattern.compile(parseString);
                    String[] noteData = parsePattern.split(note);

                    String title = noteData[0];
                    String date = noteData[1];
                    String content = noteData[2];

                    // Adds information about imported note into new note object
                    Note newNote = new Note(title, content);
                    newNote.setDate(date);
                    notes.add(newNote);
                }

            }else{
                // Creates a notes file if successful, otherwise informs you there's an error
                if(notesFile.createNewFile()){
                    println("Preparing for first time use...");
                    println("");
                }else{
                    println("Error creating a notes file. Please contact the author of the application.");
                }
            }
        }
        catch(IOException e){
            println("Error: Cannot find notes file.");
        }
    }

    private static void addNewNote(){
        // Asks user for note name and contents and displays a preview

        System.out.print("Enter the note name: ");
        String newNoteName = input.nextLine();
        println("");
        System.out.print("Enter note contents: ");
        String newNoteContents = input.nextLine();
        Note newNote = new Note(newNoteName, newNoteContents);
        println("");
        println("Note preview: ");
        printPreview(newNote);

        // Asks if user wants to save or delete their note
        // Save: saves to array
        // Delete: deletes note reference
        println("");
        println("[1] Save or [2] Delete?");
        println("");
        System.out.print(">> ");

        choice = input.nextLine().toLowerCase();

        if(choice.equals("1") || choice.contains("save")){
            println("The note has been saved.");
            notes.add(newNote);
            newNote.save();
            noOfNotes++;
        }else if(choice.equals("2") || choice.contains("delete")){
            println("The note has been deleted.");
            newNote = null;
        }else{
            println("Invalid choice. Cancelling...");
        }

        println("[Press enter to return to menu]");
        input.nextLine();
        menu();
    }

    @SuppressWarnings("Duplicates")
    private static void deleteNote(){
        println("Which note would you like to delete?");
        println("");

        // Create an array to gather note names
        List<String> noteTitles = new ArrayList<>();

        // List available notes for choosing and ask user for choice
        if(noOfNotes>0){
            println("Notes:");
            for (int i=0;i<noOfNotes;i++){
                // Gather note name
                noteTitles.add(notes.get(i).toString().toLowerCase());
                // Display note
                System.out.print("["+(i+1)+"] ");
                println(notes.get(i));
            }

            println("");
            System.out.print(">> ");

            choice = input.nextLine().toLowerCase();
            int choiceAsInt = 0;
            boolean choiceIsInt;

            // Try to parse the choice as an int. It successful, set choiceIsInt true
            try{
                choiceAsInt = Integer.parseInt(choice);
                choiceIsInt = true;
            }catch(NumberFormatException e){
                choiceIsInt = false;
            }

            // Delete note depending on whether the choice is a number or a title
            // If statement makes sure user chooses within the correct range
            // If not, it will cancel the deletion.

            if(choiceIsInt&&choiceAsInt>0&&choiceAsInt<=noOfNotes) {
                    Note selectedNote = notes.get(choiceAsInt-1);

                // Asks for confirmation that the user wants to delete the note
                println("");
                println("[1] Delete or [2] Cancel?");
                println("");
                System.out.print(">> ");

                choice = input.nextLine().toLowerCase();

                if(choice.equals("1") || choice.contains("delete")){
                    println("The note has been deleted.");
                    notes.remove(selectedNote);
                    selectedNote.delete();
                    noOfNotes--;
                }else if(choice.equals("2") || choice.contains("cancel")){
                    println("Cancelling...");
                }else{
                    println("Invalid choice. Cancelling...");
                }

            }else if(noteTitles.contains(choice)){
                Note selectedNote = notes.get(noteTitles.indexOf(choice));

                // Asks for confirmation that the user wants to delete the note
                println("");
                println("[1] Delete or [2] Cancel?");
                println("");
                System.out.print(">> ");

                choice = input.nextLine().toLowerCase();

                if(choice.equals("1") || choice.contains("delete")){
                    println("The note has been deleted.");
                    notes.remove(selectedNote);
                    selectedNote.delete();
                    noOfNotes--;
                }else if(choice.equals("2") || choice.contains("cancel")){
                    println("Cancelling...");
                }else{
                    println("Invalid choice. Cancelling...");
                }
            }else{
                println("Invalid choice. Cancelling...");
            }
            // Clears array to save memory
            noteTitles.clear();
        }else{
            println("You have no notes.");
        }

        println("");
        println("[Press enter to return to menu]");
        input.nextLine();
        menu();
    }

    @SuppressWarnings("Duplicates")
    private static void readNote(){
        println("Which note would you like to open?");
        println("");

        // Create an array to gather note names
        List<String> noteTitles = new ArrayList<>();

        // List available notes for choosing and ask user for choice
        if(noOfNotes>0){
            println("Notes:");
            for (int i=0;i<noOfNotes;i++){
                // Gather note name
                noteTitles.add(notes.get(i).toString().toLowerCase());
                // Display note
                System.out.print("["+(i+1)+"] ");
                println(notes.get(i));
            }
            println("");
            System.out.print(">> ");

            choice = input.nextLine().toLowerCase();
            int choiceAsInt = 0;
            boolean choiceIsInt;

            // Try to parse the choice as an int. It successful, set choiceIsInt true
            try{
                choiceAsInt = Integer.parseInt(choice);
                choiceIsInt = true;
            }catch(NumberFormatException e){
                choiceIsInt = false;
            }

            // Preview note depending on whether the choice is a number or a title
            // Ternary operator makes sure user chooses within the correct range
            // If not, it will display the first note

            if(choiceIsInt) {
                Note selectedNote = notes.get((choiceAsInt>0&&choiceAsInt<=noOfNotes) ? choiceAsInt-1:0);
                printPreview(selectedNote);
            }else if(noteTitles.contains(choice)){
                Note selectedNote = notes.get(noteTitles.indexOf(choice));
                printPreview(selectedNote);
            }else{
                println("Invalid choice. Cancelling...");
            }
            // Clears array to save memory
            noteTitles.clear();
        }else{
            println("You have no notes.");
        }
        println("");
        println("[Press enter to return to menu]");
        input.nextLine();
        menu();
    }

    private static void sortNotes(){
        getNotes();
        println("Notes:");

        // Lists notes
        if(noOfNotes>0){
            for(Note note:notes){
                println(note);
            }
            println("");

            // Sort notes. Add ellipsis for dramatic effect.
            Collections.sort(notes);
            System.out.print("Sorting notes");

            try{
                Thread.sleep(1000);System.out.print(".");
                Thread.sleep(1000);System.out.print(".");
                Thread.sleep(1000);println(".");
            }catch(InterruptedException e){
                println("...");
            }
            println("");

            // Prints sorted note list
            for(Note note:notes){
                println(note);
            }
        }else{
            println("You have no notes.");
        }

        println("");
        println("[Press enter to return to menu]");
        input.nextLine();
        menu();
    }

    // Saves time from writing System.out.println()
    static void println(Object text){
        System.out.println(text);
    }

    private static void printPreview(Note note) {
        printBorder(note);
        println("");
        println(note.getTitle());
        println(note.getDate());
        println("");
        println(note.getContents());
        printBorder(note);
    }

    /**
     * Prints a border according to size of the note
     *
     * @param note The note the border size will be based on
     */
    private static void printBorder(Note note){
        for(int i=0;i<note.getContents().length() || i<note.getDate().length();i++){System.out.print("=");}
    }
}
