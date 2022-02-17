package com.github.afarentino.getfit.core;

public class CalorieExp extends Component {
    private static final int CALORIE_THRESHOLD = 168;  // TODO: Remove these in a future release
    private static final int CALORIE_MAX = 2000;
    HeartRateExp delegate = new HeartRateExp();

    private Integer calories;

    @Override
    void setValue(Object t) {
        this.calories = (Integer)t;
    }

    @Override
    public Type getType() { return Type.CALORIES; }

    @Override
    public String toString() {
        return (calories != null) ? calories.toString() : "";
    }

    void parse(String text) throws ParseException {
        try {
            delegate.parse(text);
        } catch (ParseException ex) {
            System.out.println("Text is not a HeartRateExp...");
        }
        int startIndex = firstDigit(text);
        if (startIndex == -1) {
            throw new ParseException("Unparseable Exp: \"" + text + "\" does not contain a digit");
        }

        String calsBurned = text.substring(startIndex).trim();
        try {
            int temp = Integer.parseInt(calsBurned);
            if (temp > CALORIE_MAX) {
                throw new ParseException(temp + " calories burned is above configured threshold of 2000");
            }
            this.calories = Integer.parseInt(calsBurned);
        } catch (NumberFormatException e) {
            throw new ParseException("Invalid CalorieExp: " + text, e);
        }
    }
}
