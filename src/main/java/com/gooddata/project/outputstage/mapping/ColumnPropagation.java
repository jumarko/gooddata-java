/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.gooddata.project.outputstage.mapping;

/**
 * Policy for handling of new columns in ADS
 */
public enum ColumnPropagation {

    /**
     * New LDM objects should be automatically created for new columns appearing in ADS
     */
    AUTO,

    /**
     * Annie can manually add new columns appearing in ADS to project model, they are not added automatically
     */
    MANUAL,

    /**
     * New columns are not visible for Annie; no auto-propagation
     */
    NONE
}
