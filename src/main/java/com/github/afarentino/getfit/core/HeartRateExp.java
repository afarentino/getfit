package com.github.afarentino.getfit.core;

public class HeartRateExp extends Component {
    private Integer rate;
    private boolean isMax = false;
    private boolean isAvg = false;

    public void setMax(boolean value) {
        this.isMax = value;
    }

    public void setAvg(boolean value) {
        this.isAvg = true;
    }

    /**
     * Compare this heart rate to another
     * @param other
     * @return true if this rate is > false otherwise
     */
    public boolean isHigher(HeartRateExp other) {
        if (other == null) {
            return true;
        }
        if (this.rate > other.rate) {
            if (rate < 100 || rate > 200) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public Type getType() {
        if (isMax == true) {
            return Type.MAXHEART;
        }
        return Type.AVGHEART; // it's a potential avg heart rate
    }

    @Override
    public String toString() {
        if (rate > 100 && rate < 200) {
            return rate.toString();
        }
        return "HeartRate out of range " + rate;
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
