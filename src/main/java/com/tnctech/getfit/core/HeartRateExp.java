package com.tnctech.getfit.core;

public class HeartRateExp extends Component {
    private Integer rate;
    private boolean isMax = false;


    @Override
    public String toString() {
        if (rate > 100 && rate < 200) {
            if (isMax) {
                return rate.toString() + " Max Heart Rate";
            } else {
                return rate.toString() + " Average Heart Rate";
            }
        }
        return "Missing valid HeartRate " + rate;
    }

    void parse(String text) throws ParseException {
        int startIndex = firstDigit(text);
        if (startIndex == -1) {
            throw new ParseException("Unparseable Exp: \"" + text + "\" does not contain a digit");
        }
        String heartRate = text.substring(startIndex).trim();
        try {
            this.rate = Integer.parseInt(heartRate);
        } catch (NumberFormatException e) {
            throw new ParseException("Invalid HeartRateExp: " + text, e);
        }
    }
}
