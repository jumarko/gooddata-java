package com.gooddata.project.outputstage;

import java.util.EnumSet;

/**
 * Enumerates supported data types
 */
public enum DataTypeName {

    INTEGER, FLOAT, NUMERIC, VARCHAR, DATE, TIMESTAMP;

    public boolean hasLength() {
        return EnumSet.of(NUMERIC, VARCHAR).contains(this);
    }

    public boolean hasDecimals() {
        return this == NUMERIC;
    }

}
