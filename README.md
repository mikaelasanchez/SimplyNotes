# SimplyNotes

by <a href="mailto:mikaelaysanchez@gmail.com">Mikaela Sanchez</a>

## Features
This is a simple note taking console application for Funtech's interview process.<br>
The application has the following features:
1) Add notes with a title (date and time automatically recorded)
2) Remove notes
3) List and view available notes
4) Sort notes alphabetically

## Instructions
In order to use this application, you must:
1) Compile the .java files by typing `javac Main.java Note.java` to your console
2) Run the application by typing `java -classpath /path/ com.funtech.java.Main` to your console. <br>
e.g. `java -classpath /d/Users/Name/SimplyNotes/src/ com.funtech.java.Main `

You can clone the github repository for this project by typing the following in your console:
`git clone https://github.com/mikaelasanchez/SimplyNotes.git`


## FAQ<br>
**Q:** Are the note titles case-sensitive?<br>
**A:** No, the program recognises the note as long as you type the full title.

**Q:** Can you give notes the same names?<br>
**A:** Yes, but keep in mind the newer notes will be listed after older notes.

**Q:** Bash is giving me the error `Error: Could not find or load main class com.funtech.java.Main` <br>
**A:** Make sure the class files are in the directories: `/com/funtech/java/`
