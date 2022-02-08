package com.github.afarentino.getfit.core;

public class DistanceExp extends Component {
    private Float distance; // in miles

    @Override
    public String toString() {
        return distance.toString();
    }

    void parse(String text) throws ParseException {
        int startIndex = firstDigit(text);
        if (startIndex == -1) {
            throw new ParseException("Unparseable Exp: \"" + text + "\" does not contain a digit");
        }
        String distance = text.substring(startIndex);
        try {
           this.distance = Float.parseFloat(distance);
        } catch (NumberFormatException e) {
           throw new ParseException("Invalid FloatExp: " + text, e);
        }
   };
}
