package com.github.afarentino.getfit.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public abstract class Component {
    // If new metrics are needed add to this enum
    enum Type {
        START,
        DISTANCE,
        NOTE,
        TOTALTIME,
        AVGHEART,
        MAXHEART,
        INZONE,
        CALORIES
    }

    /**
     * @see: https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/regex/Pattern.html
     */
    public static final String TIME_PATTERN = "(1[012]|[1-9]):[0-5][0-9](\s+)(?i)(am|pm)";

    // Logger to be used by any concrete subclasses in this hierarchy
    protected static final Logger logger = LoggerFactory.getLogger(Component.class);

    /**
     * Find the first matching occurrence of a regex pattern in a string
     * @return the extracted match if found or null if no match is found.
     */
    public static String firstIn(String s, String pattern) {
        if (s == null || s.isEmpty()) {
            return null;
        }
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(s);
        if (!m.matches()) {
            return null;
        }
        return m.group(0);  // match found return it!
    }

    /**
     * Returns the first colon present in a String of text
     * @param text
     * @return the index of the first colon or -1
     */
    public static int firstColon(String text) {
        int index = -1;
        char[] ch = text.toCharArray();

        for (int i=0; i < text.length(); i++) {
            if (ch[i] == ':') {
                index = i;
                break;
            }
        }
        return index;
    }

    public static int lastDigit(String text) {
        int index = -1;
        char[] ch = text.toCharArray();

        for(int i=0; i < text.length(); i++) {
            if (!Character.isDigit(ch[i])) {
                index++;
                break;
            }
            index = i;
        }
        return index;
    }

    /**
     * Helper method that scans the given string for the index of the first digit
     * @return the index or -1 if one is not found
     */
    public static int firstDigit(String text) {
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

    /**
     * Attempt to parse the provided string into a component
     * @param x - the string to parse (usually a full line of text)
     * @throws ParseException if String cannot be Parsed
     */
    abstract void parse(String x) throws ParseException;

    /**
     * Get the type of the component
     * @return String representing the TYPE.
     */
    Type getType() { return Type.NOTE; }

    /**
     * Return a String representation of this component
     * Children should Override this if they want to return
     * a value other than toString()
     *
     * TODO: See if we can return Object here instead of String
     */
    String getValue() {
        return this.toString();
    }

    /**
     * Optional setter for concrete subtypes to implement
     * if they want to support dynamically changing there parsed
     * value programmatically
     */
    void setValue(Object value) { throw new RuntimeException("Not implemented"); }

}
