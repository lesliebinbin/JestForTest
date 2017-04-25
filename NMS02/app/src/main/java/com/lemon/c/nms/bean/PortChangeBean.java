package com.lemon.c.nms.bean;

import java.util.List;

/**
 * Created by C on 2016/8/31.
 */
public class PortChangeBean {

    /**
     * total : 30
     * data : [{"c_nickname":"cisco_上步北_01","pid":"2425","qam":"64QAM","p_name":"Cable5/1/4-upstream3","time":"2016-08-30 11:37:50"},{"c_nickname":"cisco_上步北_01","pid":"39435","qam":"16QAM","p_name":"Cable5/1/4-upstream4","time":"2016-08-30 11:42:35"},{"c_nickname":"cisco_上步北_01","pid":"39436","qam":"16QAM","p_name":"Cable5/1/4-upstream5","time":"2016-08-30 11:42:47"},{"c_nickname":"cisco_梅林_01","pid":"4477","qam":"16QAM","p_name":"Cable6/1/0-upstream2","time":"2016-08-30 14:02:00"},{"c_nickname":"cisco_布心_02","pid":"25951","qam":"QPSK","p_name":"Cable5/0/2-upstream2","time":"2016-08-30 14:20:42"},{"c_nickname":"Cisco_国贸南_01","pid":"40929","qam":"16QAM","p_name":"Cable5/0/0-upstream4","time":"2016-08-30 15:32:45"},{"c_nickname":"cisco_百花_02","pid":"21008","qam":"64QAM","p_name":"Cable5/0/5-upstream4","time":"2016-08-30 16:23:33"},{"c_nickname":"cisco_新南油_01","pid":"49269","qam":"QPSK","p_name":"Cable5/1/4-upstream7","time":"2016-08-30 18:32:39"},{"c_nickname":"cisco_新南油_01","pid":"49268","qam":"QPSK","p_name":"Cable5/1/4-upstream6","time":"2016-08-30 18:33:01"},{"c_nickname":"Cisco_国贸南_01","pid":"18129","qam":"16QAM","p_name":"Cable5/1/3-upstream1","time":"2016-08-30 19:32:40"},{"c_nickname":"cisco_八卦岭_01","pid":"37730","qam":"16QAM","p_name":"Cable5/0/6-upstream2","time":"2016-08-30 19:42:39"},{"c_nickname":"casa_新南油_04","pid":"25425","qam":"64QAM","p_name":"Logical Upstream Channel 12/7.0/0","time":"2016-08-30 20:17:39"},{"c_nickname":"cisco_新南油_01","pid":"49269","qam":"16QAM","p_name":"Cable5/1/4-upstream7","time":"2016-08-30 20:32:51"},{"c_nickname":"cisco_新南油_01","pid":"49268","qam":"16QAM","p_name":"Cable5/1/4-upstream6","time":"2016-08-30 20:33:08"},{"c_nickname":"cisco_上步北_01","pid":"2425","qam":"16QAM","p_name":"Cable5/1/4-upstream3","time":"2016-08-30 20:37:46"},{"c_nickname":"cisco_上步北_01","pid":"39435","qam":"QPSK","p_name":"Cable5/1/4-upstream4","time":"2016-08-30 20:42:48"},{"c_nickname":"cisco_上步北_01","pid":"39436","qam":"QPSK","p_name":"Cable5/1/4-upstream5","time":"2016-08-30 20:42:58"},{"c_nickname":"cisco_上步北_01","pid":"2425","qam":"QPSK","p_name":"Cable5/1/4-upstream3","time":"2016-08-30 20:43:01"},{"c_nickname":"cisco_盐田_01","pid":"56325","qam":"QPSK","p_name":"Cable5/1/0-upstream0","time":"2016-08-30 21:09:42"},{"c_nickname":"cisco_深云村_01","pid":"48626","qam":"64QAM","p_name":"Cable6/0/3-upstream0","time":"2016-08-30 21:50:55"}]
     */

    private int total;
    /**
     * c_nickname : cisco_上步北_01
     * pid : 2425
     * qam : 64QAM
     * p_name : Cable5/1/4-upstream3
     * time : 2016-08-30 11:37:50
     */

    private List<DataBean> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String c_nickname;
        private String pid;
        private String qam;
        private String p_name;
        private String time;

        public String getC_nickname() {
            return c_nickname;
        }

        public void setC_nickname(String c_nickname) {
            this.c_nickname = c_nickname;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getQam() {
            return qam;
        }

        public void setQam(String qam) {
            this.qam = qam;
        }

        public String getP_name() {
            return p_name;
        }

        public void setP_name(String p_name) {
            this.p_name = p_name;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
