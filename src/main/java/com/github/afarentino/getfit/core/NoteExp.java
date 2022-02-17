package com.github.afarentino.getfit.core;

public class NoteExp extends Component {
    private String note = "";

    @Override
    public String toString() { return note.toString(); }

    public void parse(String text) throws ParseException {
        if (note.isEmpty() == false) {
            note = note + " " + text;   // allows us to parse multi-line notes as one record
        } else {
            note = text;
        }
        note = note.trim(); // any leading and trailing whitespace.
    }
}
