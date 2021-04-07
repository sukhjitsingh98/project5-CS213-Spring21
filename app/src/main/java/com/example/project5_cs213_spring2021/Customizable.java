package com.example.project5_cs213_spring2021;

/**
 Interface with add and remove methods.
 @author German Munguia, Sukhjit Singh
 */

public interface Customizable {

    /**
     Adds the specified object.
     @param obj the object being added
     @return true if added, false otherwise
     */
    boolean add(Object obj);

    /**
     Removes the specified object.
     @param obj the object being removed
     @return true if removed, false otherwise
     */
    boolean remove(Object obj);
}
