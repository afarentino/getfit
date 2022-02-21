package com.github.afarentino.getfit.core;

public class InZoneExp extends TimerExp {
    //private TimerExp delegate = new TimerExp();

    @Override
    public Type getType() {
        return super.getType();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    void parse(String text) throws ParseException {
        // Make sure this is a TimerExp
        super.parse(text);
        this.setType(Component.Type.INZONE);
    }

}
