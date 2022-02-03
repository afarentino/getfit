package com.tnctech.getfit.core;

abstract class Component {
    abstract void parse(String x) throws ParseException;
    // TODO: Override me
    String getValue() {
        return "";
    };
    //abstract String getType();
}
