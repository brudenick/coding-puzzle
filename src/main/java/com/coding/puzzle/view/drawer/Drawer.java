package com.coding.puzzle.view.drawer;

import java.util.List;

/**
 * Renders the strings received.
 */
public interface Drawer {

    /**
     * Displays the given message.
     * @param message
     */
    void displayMessage(String message);

    /**
     * Displays the given options.
     * @param options
     */
    void displayOptions(List<String> options);
}
