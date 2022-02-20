package com.github.afarentino.getfit.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

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
                              Component calories,
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
                builder.calories,
                builder.avg,
                builder.max,
                builder.note);
    }

    public static class Builder {
        public static final int NUM_COLS = 8;

        private Component start;
        private Component distance;
        private Component zoneTime;
        private Component totalTime;
        private Component calories;
        private Component avg;
        private Component max;
        private Component note; //optional can be null

        private Map<Component.Type, Component> colMap;
        private Stack<Component.Type> parseStack;

        // Records always start with a StartExp -- it's the only required parameter
        // you can't build a record with the header being null
        public Builder() {
            this.start = null;
            this.distance = null;
            this.zoneTime = null;
            this.totalTime = null;
            this.calories = null;
            this.avg = null;
            this.max = null;
            this.note = null;

            this.colMap = new HashMap<>();
            this.parseStack = new Stack<>();
            initParseStack();
        }

        private void initParseStack() {
            if (!parseStack.isEmpty()) {
                parseStack.empty();
            }
            // Initialize the stack on creation
            //  enum Type {
            //        START,
            //        DISTANCE,
            //        NOTE,
            //        TOTALTIME,
            //        AVGHEART,
            //        MAXHEART,
            //        INZONE,
            //        CALORIES
            //    }
            if (note == null)
                this.parseStack.push(Component.Type.NOTE);
            if (calories == null)
                this.parseStack.push(Component.Type.CALORIES);
            if (max == null)
                this.parseStack.push(Component.Type.MAXHEART);
            if (avg == null)
                this.parseStack.push(Component.Type.AVGHEART);
            if (totalTime == null)
                this.parseStack.push(Component.Type.TOTALTIME);
            if (zoneTime == null)
                this.parseStack.push(Component.Type.INZONE);
            if (distance == null)
                this.parseStack.push(Component.Type.DISTANCE);
            if (start == null)
                this.parseStack.push(Component.Type.START);
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

        public Builder calories(String text) throws ParseException {
            CalorieExp cals = (this.calories == null) ? new CalorieExp() : (CalorieExp)this.calories;
            cals.parse(text);
            this.calories = cals;

            if (containsType(Component.Type.CALORIES)) {
                colMap.replace(Component.Type.CALORIES, this.calories);
            } else {
                colMap.put(cals.getType(), this.calories);
            }
            return this;
        }

        private void setMaxAndAvg(HeartRateExp next) {
            HeartRateExp curMax = (HeartRateExp)max;
            HeartRateExp curAvg = (HeartRateExp)avg;

            if (curMax == null && curAvg == null) {
                next.setMax(true);
                this.max = next;
                this.avg = null;
            }
            else if (curMax != null && curAvg == null) {
                if (!curMax.isHigher(next)) {
                    curMax.setMax(false);
                    next.setMax(true);
                    this.max = next;
                    curMax.setAvg(true);
                    this.avg = curMax;
                } else {
                    next.setAvg(true);
                    this.avg = next;
                }
            }
            else if (curMax == null && curAvg != null) {
                if (!curAvg.isHigher(next)) {
                    next.setMax(true);
                    this.max = next;
                    curAvg.setAvg(true);
                    this.avg = curAvg;
                } else {
                    curAvg.setMax(true);
                    this.max = curAvg;
                    next.setAvg(true);
                    this.avg = next;
                }
            } else {
                // Got three values to check
                if (curMax.isHigher(next)) {
                    if (!curAvg.isHigher(next)) {
                        curAvg.setAvg(false);
                        next.setAvg(true);
                        this.avg = next;
                    }
                } else {
                    curMax.setMax(false);
                    next.setMax(true);
                    this.max = next;
                    curMax.setAvg(true);
                    curAvg.setAvg(false);
                    this.avg = curMax;
                }
            }

            if (containsType(Component.Type.AVGHEART))
                colMap.replace(Component.Type.AVGHEART, this.avg);
            else
                colMap.put(Component.Type.AVGHEART, this.avg);

            if (containsType(Component.Type.MAXHEART))
                colMap.replace(Component.Type.MAXHEART, this.max);
            else
                colMap.put(Component.Type.MAXHEART, this.max);
        }

        public Builder avg(String text) throws ParseException {
            HeartRateExp avg = new HeartRateExp();
            avg.parse(text);
            setMaxAndAvg(avg);
            return this;
        }

        public Builder max(String text) throws ParseException {
            HeartRateExp max = new HeartRateExp();
            max.parse(text);
            setMaxAndAvg(max);
            return this;
        }

        public Builder note(String text) throws ParseException {
            NoteExp note = (this.note == null) ? new NoteExp() : (NoteExp) this.note;
            note.parse(text);
            this.note = note;
            colMap.put(note.getType(), this.note);
            return this;
        }

        public int retriesLeft() {
            initParseStack();
            return parseStack.size();
        }

        public Builder buildNext(String text) throws ParseException {
            // Select the next Component type to try and build
            Component.Type t = parseStack.isEmpty() ? Component.Type.NOTE : parseStack.pop();

            switch (t) {
                case START:
                    if (start == null)
                        start(text);
                    break;
                case DISTANCE:
                    if (distance == null)
                        distance(text);
                    break;
                case INZONE:
                    if (zoneTime == null)
                        zoneTime(text);
                    break;
                case TOTALTIME:
                    if (totalTime == null)
                        totalTime(text);
                    break;
                case AVGHEART:
                    if (avg == null)
                        avg(text);
                    break;
                case MAXHEART:
                    if (max == null)
                        max(text);
                    break;
                case CALORIES:
                    if (calories == null)
                        calories(text);
                    break;
                case NOTE: // always adds
                    note(text);
                    break;
            }
            return this;
        }

        public ExerciseRecord build() {
            validate();
            return new ExerciseRecord(this);
        }

        /*
         * Method used to check for errors in record values and to make any final adjustments to parsed
         * values prior to construction.
         * Records are immutable in Java so this is the final chance to set the desired values of any
         * parsed records prior to construction.
         */
        private void validate() throws IllegalStateException {
            if (colMap.isEmpty()) {
                throw new IllegalStateException("Attempt to build a record with no values");
            }

            Double zoneTime = (this.zoneTime != null) ? Double.parseDouble(this.zoneTime.getValue()) : 0.0;
            Double totalTime = (this.totalTime != null) ? Double.parseDouble(this.totalTime.getValue()) : 0.0;

            if (zoneTime > totalTime) {
                if (this.totalTime != null) {
                    //TODO: String temp = this.zone
                }
            }
            if (this.calories != null && this.max != null ) {
                Integer calories = Integer.parseInt(this.calories.getValue());
                Integer max = Integer.parseInt(this.max.getValue());

                try {
                    // Swap max with calories: Current implementation assumes Calories is always highest of these
                    if (calories < max) {
                        String newMax = this.calories.getValue();
                        calories(this.max.getValue());
                        colMap.remove(Component.Type.MAXHEART, this.max);
                        // Set new max
                        this.max = null;
                        max(newMax);
                    }
                } catch (ParseException e) {
                    throw new IllegalStateException(e);
                }
            }
        }
    }

}