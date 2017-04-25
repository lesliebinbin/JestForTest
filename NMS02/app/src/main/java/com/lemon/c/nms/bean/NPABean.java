package com.lemon.c.nms.bean;

import java.util.List;

/**
 * Created by C on 2016/8/31.
 */
public class NPABean {

    /**
     * total : 87
     * data : [{"cmts_name":"10_XX_OLT-1","p_name":"docsCableUpstream 39/1/0/1","pid":"90460","npa":"48","level":"1","time":"2016-08-31"},{"cmts_name":"10_XX_OLT-1","p_name":"docsCableUpstream 29/1/0/4","pid":"90423","npa":"48","level":"1","time":"2016-08-31"},{"cmts_name":"10_XX_OLT-1","p_name":"docsCableUpstream 22/1/0/1","pid":"90392","npa":"38","level":"1","time":"2016-08-31"},{"cmts_name":"10_XX_OLT-1","p_name":"docsCableUpstream 14/1/0/2","pid":"90361","npa":"56","level":"1","time":"2016-08-31"},{"cmts_name":"10_XX_OLT-1","p_name":"docsCableUpstream 39/1/0/2","pid":"90461","npa":"35","level":"1","time":"2016-08-31"},{"cmts_name":"10_XX_OLT-1","p_name":"docsCableUpstream 34/1/0/1","pid":"90440","npa":"35","level":"1","time":"2016-08-31"},{"cmts_name":"10_XX_OLT-1","p_name":"docsCableUpstream 29/1/0/2","pid":"90421","npa":"31","level":"1","time":"2016-08-31"},{"cmts_name":"10_XX_OLT-1","p_name":"docsCableUpstream 14/1/0/3","pid":"90362","npa":"35","level":"1","time":"2016-08-31"},{"cmts_name":"16_FX_OLT-1","p_name":"docsCableUpstream 35/1/0/1","pid":"100902","npa":"54","level":"1","time":"2016-08-31"},{"cmts_name":"16_FX_OLT-1","p_name":"docsCableUpstream 19/1/0/3","pid":"100840","npa":"51","level":"1","time":"2016-08-31"},{"cmts_name":"16_FX_OLT-1","p_name":"docsCableUpstream 36/1/0/1","pid":"100906","npa":"57","level":"1","time":"2016-08-31"},{"cmts_name":"16_FX_OLT-1","p_name":"docsCableUpstream 19/1/0/4","pid":"100841","npa":"42","level":"1","time":"2016-08-31"},{"cmts_name":"27_CT_OLT-1","p_name":"docsCableUpstream 28/1/0/2","pid":"91320","npa":"54","level":"1","time":"2016-08-31"},{"cmts_name":"27_CT_OLT-1","p_name":"docsCableUpstream 30/1/0/3","pid":"91329","npa":"38","level":"1","time":"2016-08-31"},{"cmts_name":"27_CT_OLT-1","p_name":"docsCableUpstream 28/1/0/3","pid":"91321","npa":"54","level":"1","time":"2016-08-31"},{"cmts_name":"27_CT_OLT-1","p_name":"docsCableUpstream 30/1/0/4","pid":"91330","npa":"41","level":"1","time":"2016-08-31"},{"cmts_name":"27_CT_OLT-1","p_name":"docsCableUpstream 28/1/0/4","pid":"91322","npa":"32","level":"1","time":"2016-08-31"},{"cmts_name":"casa_彩田村_01","p_name":"Logical Upstream Channel 13/1.0/0","pid":"11253","npa":"46","level":"1","time":"2016-08-31"},{"cmts_name":"casa_彩田村_01","p_name":"Logical Upstream Channel 12/8.0/0","pid":"11218","npa":"54","level":"1","time":"2016-08-31"},{"cmts_name":"casa_怡景_01","p_name":"Logical Upstream Channel 11/15.1/0","pid":"31513","npa":"57","level":"1","time":"2016-08-31"}]
     */

    private int total;
    /**
     * cmts_name : 10_XX_OLT-1
     * p_name : docsCableUpstream 39/1/0/1
     * pid : 90460
     * npa : 48
     * level : 1
     * time : 2016-08-31
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
        private String npa;
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

        public String getNpa() {
            return npa;
        }

        public void setNpa(String npa) {
            this.npa = npa;
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
