package com.energicube.eno.asset.model;

import java.io.Serializable;

/**
 * 位置层级关系
 */
public class LocHierarchySet implements Serializable {

    private static final long serialVersionUID = 1L;

    private LocHierarchy locHierarchy;
    private Locations locations;

    public LocHierarchy getLocHierarchy() {
        return locHierarchy;
    }

    public void setLocHierarchy(LocHierarchy locHierarchy) {
        this.locHierarchy = locHierarchy;
    }

    public Locations getLocations() {
        return locations;
    }

    public void setLocations(Locations locations) {
        this.locations = locations;
    }

}
