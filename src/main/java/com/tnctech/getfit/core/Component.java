package com.tnctech.getfit.core;

public abstract class Component {
    abstract void parse(String x) throws ParseException;
    // Probably not needed if toString will be used but here just in case...
    String getValue() {
        return this.toString();
    }

    //abstract String getType();
}
