package com.energicube.eno.fssm.model;


import com.energicube.eno.alarm.model.UcAlarmactive;
import com.energicube.eno.monitor.model.UcTaginfoExt;

public class UcAlarmactiveSet {

    private UcTaginfoExt ucTaginfoExt;
    private UcAlarmactive ucAlarmactive;

    public UcTaginfoExt getUcTaginfoExt() {
        return ucTaginfoExt;
    }

    public void setUcTaginfoExt(UcTaginfoExt ucTaginfoExt) {
        this.ucTaginfoExt = ucTaginfoExt;
    }

    public UcAlarmactive getUcAlarmactive() {
        return ucAlarmactive;
    }

    public void setUcAlarmactive(UcAlarmactive ucAlarmactive) {
        this.ucAlarmactive = ucAlarmactive;
    }

}
