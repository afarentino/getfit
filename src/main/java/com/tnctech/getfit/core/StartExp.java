package com.tnctech.getfit.core;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class StartExp extends Component {
    private Date start;
    private DateFormat df = DateFormat.getDateInstance();
    private SimpleDateFormat sd = new SimpleDateFormat("MM/dd/yyyy");

    private void setStart(String text, DateFormat d) throws java.text.ParseException {
        this.start = d.parse(text);
    }

    @Override
    public String toString() {
        return start.toString();
    }

    /*
     * Helper method that scans the given string for the index of the first digit
     * @return the index or -1 if one is not found
     */
    private int firstDigit(String text) {
        int index = -1;
        char[] ch = text.toCharArray();

        for (int i=0; i < text.length(); i++) {
            if (Character.isDigit(ch[i])) {
                index = i;
                break;
            }
        }
        return index;
    }
    void parse(String text) throws ParseException {
        int startIndex = firstDigit(text);

        if (startIndex == -1) {
            throw new ParseException("Unparseable StartExp: \"" + text + "\" does not contain a digit");
        }

        String start = text.substring(startIndex);
        try {
            setStart(start, df);
        } catch (java.text.ParseException ex) {
            try {
               setStart(start, sd);
            } catch (java.text.ParseException e) {
                throw new ParseException(text + " is not a StartExp", e);
            }
        }
    }
}
