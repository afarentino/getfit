package com.github.afarentino.getfit.core;

public class InZoneExp extends TimerExp {
    //private TimerExp delegate = new TimerExp();

    @Override
    public Type getType() {
        return Type.INZONE;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    void parse(String text) throws ParseException {
        // Make sure this is a TimerExp
        if (text.contains("in zone")) {
            super.parse(text);
        }
        else {
            throw new ParseException("Invalid InZoneExp: " + text);
        }
    }

}
