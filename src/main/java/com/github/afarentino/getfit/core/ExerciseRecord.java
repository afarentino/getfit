package com.github.afarentino.getfit.core;

import java.util.HashMap;
import java.util.Map;

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
     * Private constructor that uses the Effective Java Builder pattern
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
        public static final int NUM_COLS = 7;

        private Component start;
        private Component distance;
        private Component zoneTime;
        private Component totalTime;
        private Component avg;
        private Component max;
        private Component note; //optional can be null

        private Map<Component.Type, Component> colMap;

        // Records always start with a StartExp -- it's the only required parameter
        // you can't build a record with the header being null
        public Builder() {
            this.start = null;
            this.distance = null;
            this.zoneTime = null;
            this.totalTime = null;
            this.avg = null;
            this.max = null;
            this.note = null;

            this.colMap = new HashMap<>();
        }

        public boolean containsType(Component.Type t) {
           if (colMap.containsKey(t)) {
               return true;
            }
           return false;
        }

        public Builder start(String text) throws ParseException {
            StartExp start = (this.start == null) ? new StartExp() : (StartExp)this.start;
            start.parse(text);

            this.start = start;
            colMap.put(start.getType(), this.start);
            return this;
        }

        public Builder distance(String text) throws ParseException {
            DistanceExp distance = (this.distance == null) ? new DistanceExp() : (DistanceExp)this.distance;
            distance.parse(text);
            this.distance = distance;
            colMap.put(distance.getType(), this.distance);
            return this;   // supports method chaining
        }

        public Builder zoneTime(String text) throws ParseException {
            InZoneExp zoneTime = (this.zoneTime == null) ? new InZoneExp() : (InZoneExp)this.zoneTime;
            zoneTime.parse(text);
            this.zoneTime = zoneTime;
            colMap.put(zoneTime.getType(), this.zoneTime);
            return this;
        }

        public Builder totalTime(String text) throws ParseException {
            TimerExp totalTime = (this.totalTime == null) ? new TimerExp() : (TimerExp)this.totalTime;
            totalTime.parse(text);
            this.totalTime = totalTime;
            colMap.put(totalTime.getType(), this.totalTime);
            return this;
        }

        private void setMaxAndAvg() {
            HeartRateExp curMax = (HeartRateExp)max;
            HeartRateExp curAvg = (HeartRateExp)avg;
            curMax.isMax(curAvg);
        }

        public Builder avg(String text) throws ParseException {
            HeartRateExp avg = (this.avg == null) ? new HeartRateExp() : (HeartRateExp)this.avg;
            avg.parse(text);
            this.avg = avg;
            colMap.put(avg.getType(), this.avg);
            return this;
        }

        public Builder max(String text) throws ParseException {
            HeartRateExp max = (this.max == null) ? new HeartRateExp() : (HeartRateExp)this.max;
            max.parse(text);
            //TODO this is not going to work
            this.max = max;
            colMap.put(max.getType(), this.max);
            return this;
        }

        public Builder note(String text) throws ParseException {
            NoteExp note = (this.note == null) ? new NoteExp() : (NoteExp) this.note;
            note.parse(text);
            this.note = note;
            colMap.put(note.getType(), this.note);
            return this;
        }

        public Builder buildNext(String text) throws ParseException {

            try {
                if (start == null) {
                    return start(text);
                }
            } catch (ParseException a) {
                try {
                    if (distance == null) {
                        return distance(text);
                    }
                } catch (ParseException b) {
                    try {
                        if (zoneTime == null) {
                            return zoneTime(text);
                        }
                    } catch (ParseException c) {
                        try {
                            if (totalTime == null) {
                                return totalTime(text);
                            }
                        } catch (ParseException d) {
                            try {
                                if (avg == null) {
                                    return avg(text);
                                }
                            } catch (ParseException e) {
                                try {
                                    if (max == null) {
                                        return max(text);
                                    }
                                } catch (ParseException f) {
                                    System.out.println("Setting Note as Expression type");
                                }
                            }
                        }
                    }
                }
            }
            return note(text);
        }

        public ExerciseRecord build() {
            validate();
            return new ExerciseRecord(this);
        }

        /*
         * Print info about the Pending record to help user create better ones
         */
        private void validate() throws IllegalStateException {
            if (colMap.isEmpty()) {
                throw new IllegalStateException("Attempt to build a record with no values");
            }
        }
    }

}