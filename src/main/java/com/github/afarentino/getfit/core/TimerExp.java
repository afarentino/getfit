package com.github.afarentino.getfit.core;

import java.math.RoundingMode;
import java.util.StringTokenizer;
import java.text.DecimalFormat;

/**
 * @see https://mkyong.com/java/how-to-round-double-float-value-to-2-decimal-points-in-java/
 */
public class TimerExp extends Component {
    private Integer minutes = 0;
    private Integer seconds = 0;
    private DistanceExp delegate = new DistanceExp();
    private String text;
    private Double decMinutes;

    private static Integer getInteger(String sec) {
       return Integer.parseInt(sec);
    }

    private void setSeconds(String val) {
        this.seconds = getInteger(val);
    }

    private void setMinutes(String val) {
        this.minutes = getInteger(val);
    }

    @Override
    String getValue() {
        return this.decMinutes.toString();
    }

    @Override
    void setValue(Object value) {
        try {
            String newText = (String) value;
            this.parse(newText);
        } catch (ParseException ex) {
            throw new IllegalStateException( ex );
        }
    }

    @Override
    public Type getType() { return Type.TOTALTIME; }

    @Override
    public String toString() {


        if (this.getType() == Type.INZONE) {
            DecimalFormat df = new DecimalFormat( "0.00");
            df.setRoundingMode(RoundingMode.UP);
            return df.format(decMinutes);
        } else {
            // Use Math.round to convert to int it will round the double up
            int minutes = (int) Math.round(decMinutes);
            return Integer.toString(minutes);
        }

    }

    void parse(String text) throws ParseException {
        try {
            delegate.parse(text);
        } catch (ParseException ex) {
            logger.debug("Text is not a DistanceExp: Continuing");
        }
        if (text.contains("am") || text.contains("AM") || text.contains("pm") || text.contains("PM")) {
            throw new ParseException("Unparseable TimerExp: \"" + text + "\" contains AM or PM");
        }
        if (delegate.toString().isEmpty() == false) {
            throw new ParseException("Invalid TimerExp " + text);
        }

        int startIndex = firstDigit(text);
        boolean hasMin = false;
        if (startIndex == -1) {
            throw new ParseException("Unparseable Exp: \"" + text + "\" does not contain a digit");
        }
        if (text.contains("min")) {
            text = text.substring(startIndex, text.indexOf("min")-1);
            hasMin = true;
        }
        int colonIndex = firstColon(text);
        if (colonIndex == -1) {
            if ((hasMin == false) && !text.contains("sec") ) {
                throw new ParseException("Invalid TimeExp: " + text);
            }
            setSeconds("0");
            setMinutes(text);
        } else {
            try {
                StringTokenizer st = new StringTokenizer(text.substring(startIndex), ":");
                setMinutes(st.nextToken());
                while (st.hasMoreTokens()) {
                    String candidate = st.nextToken();
                    int lastDigit = lastDigit(candidate);
                    setSeconds(candidate.substring(0, lastDigit));
                    break;
                }
            } catch (NumberFormatException e) {
                throw new ParseException("Invalid TimerExp " + text, e);
            }
        }
        // Wrap up parsing work here by setting state
        this.text = text;
        double secs = seconds * (1.0/60.0);
        double decMinutesVal = minutes + secs;
        this.decMinutes = decMinutesVal;
    }
}
