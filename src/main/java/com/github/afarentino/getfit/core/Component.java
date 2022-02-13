package com.github.afarentino.getfit.core;

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
     * General-purpose sliding window text search pattern -- useful for finding the smallest
     * occurrence of a substring pattern in a string
     */
    public static int findIn(String s, String pattern) {
        throw new RuntimeException("Not implemented yet!");
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

}
