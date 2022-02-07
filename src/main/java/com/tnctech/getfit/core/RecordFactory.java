package com.tnctech.getfit.core;

import java.util.List;

/**
 * @see - https://dev.to/jackcmeyer/what-s-new-in-java-15-record-44bl
 */
public final class RecordFactory {

    private List<String> pendingParts;

    /**
     * Create a record factory designed to create reccords for the specified list of Parts
     * @param parts
     */
    public RecordFactory(List<String> parts) {
        this.pendingParts = parts;
    }

}
