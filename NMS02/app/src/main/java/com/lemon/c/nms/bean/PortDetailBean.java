package com.lemon.c.nms.bean;

/**
 * Created by C on 2016/9/1.
 */
public class PortDetailBean {

    private int pid;
    private String  time;
    private float snr;
    private float ber;
    private float npa;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getSnr() {
        return snr;
    }

    public void setSnr(float snr) {
        this.snr = snr;
    }

    public float getBer() {
        return ber;
    }

    public void setBer(float ber) {
        this.ber = ber;
    }

    public float getNpa() {
        return npa;
    }

    public void setNpa(float npa) {
        this.npa = npa;
    }
}
