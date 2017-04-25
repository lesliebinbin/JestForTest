package com.lemon.c.nms.bean;

import java.util.List;

/**
 * Created by C on 2016/8/31.
 */
public class BERBean {

    /**
     * total : 502
     * data : [{"cmts_name":"10_XX_OLT-1","p_name":"docsCableUpstream 3/1/0/2","pid":"90317","p_ucer":"0.000134948350","level":"1","time":"2016-08-31 10:04:07"},{"cmts_name":"10_XX_OLT-1","p_name":"docsCableUpstream 9/1/0/1","pid":"90340","p_ucer":"0.000565739290","level":"1","time":"2016-08-31 10:04:07"},{"cmts_name":"10_XX_OLT-1","p_name":"docsCableUpstream 10/1/0/2","pid":"90345","p_ucer":"0.000357927696","level":"1","time":"2016-08-31 10:04:07"},{"cmts_name":"10_XX_OLT-1","p_name":"docsCableUpstream 10/1/0/3","pid":"90346","p_ucer":"0.000251284073","level":"1","time":"2016-08-31 10:04:07"},{"cmts_name":"10_XX_OLT-1","p_name":"docsCableUpstream 12/1/0/2","pid":"90353","p_ucer":"0.000109576642","level":"1","time":"2016-08-31 10:04:07"},{"cmts_name":"10_XX_OLT-1","p_name":"docsCableUpstream 12/1/0/3","pid":"90354","p_ucer":"0.000177318565","level":"1","time":"2016-08-31 10:04:07"},{"cmts_name":"10_XX_OLT-1","p_name":"docsCableUpstream 12/1/0/4","pid":"90355","p_ucer":"0.000120692268","level":"1","time":"2016-08-31 10:04:07"},{"cmts_name":"10_XX_OLT-1","p_name":"docsCableUpstream 14/1/0/1","pid":"90360","p_ucer":"0.000118914249","level":"1","time":"2016-08-31 10:04:07"},{"cmts_name":"10_XX_OLT-1","p_name":"docsCableUpstream 14/1/0/2","pid":"90361","p_ucer":"0.000135694034","level":"1","time":"2016-08-31 10:04:07"},{"cmts_name":"10_XX_OLT-1","p_name":"docsCableUpstream 14/1/0/3","pid":"90362","p_ucer":"0.000866831571","level":"1","time":"2016-08-31 10:04:07"},{"cmts_name":"10_XX_OLT-1","p_name":"docsCableUpstream 14/1/0/4","pid":"90363","p_ucer":"0.000748365768","level":"1","time":"2016-08-31 10:04:07"},{"cmts_name":"10_XX_OLT-1","p_name":"docsCableUpstream 15/1/0/1","pid":"90364","p_ucer":"0.017627015710","level":"1","time":"2016-08-31 10:04:07"},{"cmts_name":"10_XX_OLT-1","p_name":"docsCableUpstream 15/1/0/2","pid":"90365","p_ucer":"0.001003454439","level":"1","time":"2016-08-31 10:04:07"},{"cmts_name":"10_XX_OLT-1","p_name":"docsCableUpstream 15/1/0/3","pid":"90366","p_ucer":"0.001585520920","level":"1","time":"2016-08-31 10:04:07"},{"cmts_name":"10_XX_OLT-1","p_name":"docsCableUpstream 15/1/0/4","pid":"90367","p_ucer":"0.000146676728","level":"1","time":"2016-08-31 10:04:07"},{"cmts_name":"10_XX_OLT-1","p_name":"docsCableUpstream 17/1/0/4","pid":"90375","p_ucer":"0.000116628275","level":"1","time":"2016-08-31 10:04:07"},{"cmts_name":"10_XX_OLT-1","p_name":"docsCableUpstream 21/1/0/1","pid":"90388","p_ucer":"0.000746826001","level":"1","time":"2016-08-31 10:04:07"},{"cmts_name":"10_XX_OLT-1","p_name":"docsCableUpstream 21/1/0/2","pid":"90389","p_ucer":"0.000312587508","level":"1","time":"2016-08-31 10:04:07"},{"cmts_name":"10_XX_OLT-1","p_name":"docsCableUpstream 22/1/0/1","pid":"90392","p_ucer":"0.001171981916","level":"1","time":"2016-08-31 10:04:07"},{"cmts_name":"10_XX_OLT-1","p_name":"docsCableUpstream 25/1/0/4","pid":"90407","p_ucer":"0.000246852636","level":"1","time":"2016-08-31 10:04:07"}]
     */

    private int total;
    /**
     * cmts_name : 10_XX_OLT-1
     * p_name : docsCableUpstream 3/1/0/2
     * pid : 90317
     * p_ucer : 0.000134948350
     * level : 1
     * time : 2016-08-31 10:04:07
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
        private String cmts_name;
        private String p_name;
        private String pid;
        private String p_ucer;
        private String level;
        private String time;

        public String getCmts_name() {
            return cmts_name;
        }

        public void setCmts_name(String cmts_name) {
            this.cmts_name = cmts_name;
        }

        public String getP_name() {
            return p_name;
        }

        public void setP_name(String p_name) {
            this.p_name = p_name;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getP_ucer() {
            return p_ucer;
        }

        public void setP_ucer(String p_ucer) {
            this.p_ucer = p_ucer;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}

