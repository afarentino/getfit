package com.tnctech.getfit.core;

import java.util.Date;

/**
 * When we create a record, the compiler creates bytecode and includes the following things in generated class file:
 *
 * A constructor accepting all fields.
 * The toString() method which prints the state/values of all fields in the record.
 * The equals() and hashCode() methods using an invoke dynamic based mechanism.
 * The getter methods whose names are similar to field names i.e. id(), firstName(), lastName(), email() and age().
 * The class extends java.lang.Record, which is the base class for all records. It means a record cannot extend other classes.
 * The class is marked final, which means we cannot create a subclass of it.
 * It does not have any setter method which means a record instance is designed to be immutable.
 *
 * TODO: The Record should be the simple Data types
 */
public record ExerciseRecord( Date start, // start datetime (time is nullable)
                              Float distance, // in miles
                              InZoneExp zoneTime, // contains "in zone"
                              String totalTime,
                              HeartRateExp avg,
                              HeartRateExp max,
                              String note) { }
