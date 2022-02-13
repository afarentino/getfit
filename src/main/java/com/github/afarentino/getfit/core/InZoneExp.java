package com.github.afarentino.getfit.core;

public class InZoneExp extends Component {
    private TimerExp delegate = new TimerExp();

    @Override
    public Type getType() { return Type.INZONE; }

    @Override
    public String toString() {
        return delegate.toString();
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
