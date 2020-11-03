package anichan.bookmark;

import java.util.ArrayList;

/**
 * Represents a note for a bookmark entry.
 */
public class Note {

    public ArrayList<String> notes;

    public Note() {
        this.notes = new ArrayList<>();
    }

    public void addNote(String note) {
        this.notes.add(note);
    }

    public String getNote(int noteIndex) {
        return notes.get(noteIndex);
    }

    public int getSize() {
        return notes.size();
    }

    public String removeNote(int noteIndex) {
        String note = notes.get(noteIndex);
        this.notes.remove(noteIndex);
        return note;
    }

}
