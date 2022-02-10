package com.github.afarentino.getfit.core;

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
    public Type getType() { return Type.START; }

    @Override
    public String toString() {
        return start.toString();
    }

    void parse(String text) throws ParseException {
        int startIndex = firstDigit(text);

        if (startIndex == -1) {
            throw new ParseException("Unparseable Exp: \"" + text + "\" does not contain a digit");
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
