package com.tnctech.getfit.core;

public interface ComponentFactory {
    Component createStartExp();
    Component createDistanceExp();
    Component createInZoneExp();
    Component createTimeExp();
    Component createHeartRateExp();
    Component createNoteExp();
}
