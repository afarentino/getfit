package com.tnctech.getfit.core;

import java.util.Date;

/**
 * When we create a record, the compiler creates bytecode and includes the
 * following things in generated class file:
 *
 * A constructor accepting all fields.
 * The toString() method which prints the state/values of all fields in the record.
 * The equals() and hashCode() methods using an invoke dynamic based mechanism.
 * The getter methods whose names are similar to field names i.e. id(),
 * firstName(), lastName(), email() and age().
 *
 * The class extends java.lang.Record, which is the base class for all records.
 * It means a record cannot extend other classes.
 * The class is marked final, which means we cannot create a subclass of it.
 *
 * It does not have any setter method which means a record instance is designed to be immutable.
 *
 */
public record ExerciseRecord( Component start, // start datetime (time is nullable)
                              Component distance, // in miles
                              Component zoneTime, // contains "in zone"
                              Component totalTime,
                              Component avg,
                              Component max,
                              Component note) {

    /**
     * Private constructor using Effective Java Builder pattern
     * @see: https://blogs.oracle.com/javamagazine/post/exploring-joshua-blochs-builder-design-pattern-in-java
     * @param builder
     */
    private ExerciseRecord(Builder builder) {
        this(builder.start,
                builder.distance,
                builder.zoneTime,
                builder.totalTime,
                builder.avg,
                builder.max,
                builder.note);
    }

    public static class Builder {
        private Component start;
        private Component distance;
        private Component zoneTime;
        private Component totalTime;
        public Component avg;
        public Component max;
        public Component note; //optional can be null

        // Records always start with a StartExp -- it's the only required parameter
        // you can't build a record with the header being null
        public Builder() {
            this.start = null;
            this.distance = null;
            this.zoneTime = null;
            this.avg = null;
            this.max = null;
            this.note = null;
        }

        public Builder start(StartExp start, String text) throws ParseException {
            this.start = start;
            return this;
        }

        public Builder distance(DistanceExp distance, String text) throws ParseException {
            validate(distance, text);
            this.distance = distance;
            return this;   // supports method chaining
        }

        public Builder zoneTime(InZoneExp zoneTime, String text) throws ParseException {
            validate(zoneTime, text);
            this.zoneTime = zoneTime;
            return this;
        }

        public Builder totalTime(TimeExp totalTime, String text) throws ParseException {
            validate(totalTime, text);
            this.totalTime = totalTime;
            return this;
        }

        public Builder avg(HeartRateExp avg, String text) throws ParseException {
            validate(avg, text);
            this.avg = avg;
            return this;
        }

        public ExerciseRecord build() {
            return new ExerciseRecord(this);
        }

        private void validate(Component c, String text) throws ParseException {
            if (c != null) {
                c.parse(text);
            }
        }

    }

}