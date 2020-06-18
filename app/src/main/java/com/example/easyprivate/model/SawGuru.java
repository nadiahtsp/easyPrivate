package com.example.easyprivate.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SawGuru {

    @Expose
    @SerializedName("saw_result")
    private double sawResult;
    @Expose
    @SerializedName("pm_result")
    private double pmResult;
    @Expose
    @SerializedName("jarak_haversine")
    private double jarakHaversine;
    @Expose
    @SerializedName("id")
    private int id;

    public double getSawResult() {
        return sawResult;
    }

    public void setSawResult(double sawResult) {
        this.sawResult = sawResult;
    }

    public double getPmResult() {
        return pmResult;
    }

    public void setPmResult(double pmResult) {
        this.pmResult = pmResult;
    }

    public double getJarakHaversine() {
        return jarakHaversine;
    }

    public void setJarakHaversine(double jarakHaversine) {
        this.jarakHaversine = jarakHaversine;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
