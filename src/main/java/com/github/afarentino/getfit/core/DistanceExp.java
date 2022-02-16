package com.github.afarentino.getfit.core;

public class DistanceExp extends Component {
    private Float distance; // in miles

    @Override
    public Type getType() { return Type.DISTANCE; }

    @Override
    public String toString() {
        if (distance != null) {
            return distance.toString();
        }
        return "";
    }

    void parse(String text) throws ParseException {
        int startIndex = firstDigit(text);
        if ( startIndex == -1 ) {
            throw new ParseException("Unparseable Exp: \"" + text + "\" does not contain a digit");
        }

        // Special case: check if firstDigit is a decimal digit
        if (startIndex > 0 && text.charAt(startIndex-1) == '.') {
            startIndex = startIndex - 1;
        }

        String distance = text.substring(startIndex);
        try {
           Float temp = Float.parseFloat(distance);
           if (temp > 100) { throw new ParseException("Invalid DistanceExp " + temp + " is greater than 100 miles"); }
           this.distance = temp;
        } catch (NumberFormatException e) {
           throw new ParseException("Invalid DistanceExp: " + text, e);
        }
   };
}
