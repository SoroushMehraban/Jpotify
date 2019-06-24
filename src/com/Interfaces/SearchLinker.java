package com.Interfaces;

/**
 * This interface link center part of center panel with search box.
 *
 * @author Soroush Mehraban
 * @version 1.0
 */
public interface SearchLinker {
    /**
     * This method searches in every songs in library and show song panels that it found it.
     *
     * @param searchingMessage given text to search.
     */
    void doSearch(String searchingMessage);
}
