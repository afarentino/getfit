package com.github.afarentino.getfit.core;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @see: https://www.netjstech.com/2017/10/how-to-format-time-in-am-pm-format-java-program.html
 */
public class StartExp extends Component {
    private Date start;
    private String text;
    private boolean timeMissing;

    // Format to use for Date
    private SimpleDateFormat df;


    /*
     * @param text
     * @param d
     * @throws java.text.ParseException
     */
    private void setStart(String text, boolean timeMissing) throws java.text.ParseException {
        this.start = df.parse(text);
        this.text = text;
        this.timeMissing = timeMissing;
    }

    /**
     * Use this method to
     * add a time programmatically to the formatted DateTime value
     * @param t
     */
    @Override
    public void setValue(Object t) {
        try {
            String newText= (this.text != null) ? this.text + ' ' + (String) t : (String) t;
            this.parse(newText);
        } catch (ParseException ex) {
            throw new IllegalStateException( ex );
        }
    }

    @Override
    public Type getType() { return Type.START; }

    @Override
    public String toString() { return (start != null) ? df.format(start) : ""; }

    void parse(String text) throws ParseException {
        int startIndex = firstDigit(text);

        if (startIndex == -1) {
            throw new ParseException("Unparseable Exp: \"" + text + "\" does not contain a digit");
        }

        String start = text.substring(startIndex).toUpperCase();  // normalize input
        try {
            if ( start.contains("AM") || start.contains("PM") ) {
                df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
                setStart(start.replaceAll("-", " "), false);  // parse a date time
            }
            else {
                df = new SimpleDateFormat("MM/dd/yyyy");
                setStart(start, true);  // Attempt to parse using only a Date pattern
            }
        } catch (java.text.ParseException e) {
            throw new ParseException(text + " is not a StartExp", e);
        }
    }
}
