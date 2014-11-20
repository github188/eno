package com.energicube.eno.monitor.model;

import java.util.List;

public class OkcMenuSet implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private OkcMenu okcMenu;
    private List<OkcMenu> okcMenus;

    public OkcMenu getOkcMenu() {
        return okcMenu;
    }

    public void setOkcMenu(OkcMenu okcMenu) {
        this.okcMenu = okcMenu;
    }

    public List<OkcMenu> getOkcMenus() {
        return okcMenus;
    }

    public void setOkcMenus(List<OkcMenu> okcMenus) {
        this.okcMenus = okcMenus;
    }

}
