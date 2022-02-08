package com.github.afarentino.getfit.core;

public class InZoneExp extends Component {

    private Integer minutes = 0;
    private Integer seconds = 0;

    @Override
    public String toString() {
        if (minutes > 0) {
            if (seconds > 0) { return minutes + " min " + seconds + " sec"; }
            return minutes + " min";
        }
        return seconds + " sec";
    }

    void parse(String text) throws ParseException {
        if (text.contains("min")) {
            int startIndex = firstDigit(text);
            if (startIndex == -1) {
                throw new ParseException("Unparseable Exp: \"" + text + "\" does not contain a digit");
            }
        }
        throw new ParseException("Invalid InZoneExp: " + text);
    }

}
