package com.github.afarentino.getfit.core;

public class InZoneExp extends Component {
    private TimeExp delegate = new TimeExp();

    @Override
    public Type getType() { return Type.INZONE; }

    @Override
    public String toString() {
        return delegate.toString() + " in zone";
    }

    void parse(String text) throws ParseException {
        if (text.contains("in zone")) {
            delegate.parse(text);
        }
        else {
            throw new ParseException("Invalid InZoneExp: " + text);
        }
    }

}
