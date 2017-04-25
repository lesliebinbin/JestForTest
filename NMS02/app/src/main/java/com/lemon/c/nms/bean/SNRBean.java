package com.lemon.c.nms.bean;

import java.util.List;

/**
 * Created by C on 2016/8/31.
 */
public class SNRBean {

    /**
     * total : 1
     * data : [{"cmts_name":"casa_怡景_01","p_name":"Logical Upstream Channel 13/5.0/0","pid":"31578","p_snr":"0","level":"1","time":"2016-08-31 09:56:37"},{"cmts_name":"casa_皇岗_02","p_name":"Logical Upstream Channel 12/2.0/0","pid":"25089","p_snr":"0","level":"1","time":"2016-08-31 09:56:37"},{"cmts_name":"casa_皇岗_02","p_name":"Logical Upstream Channel 12/2.1/0","pid":"25090","p_snr":"0","level":"1","time":"2016-08-31 09:56:37"},{"cmts_name":"casa_皇岗_02","p_name":"Logical Upstream Channel 13/3.0/0","pid":"25140","p_snr":"0","level":"1","time":"2016-08-31 09:56:37"},{"cmts_name":"casa_皇岗_02","p_name":"Logical Upstream Channel 13/3.1/0","pid":"25141","p_snr":"0","level":"1","time":"2016-08-31 09:56:37"},{"cmts_name":"casa_皇岗_02","p_name":"Logical Upstream Channel 13/3.2/0","pid":"25142","p_snr":"0","level":"1","time":"2016-08-31 09:56:37"},{"cmts_name":"cisco_南山_02","p_name":"Cable6/1/0-upstream2","pid":"51064","p_snr":"0","level":"1","time":"2016-08-31 09:56:37"},{"cmts_name":"cisco_园博园_3G60","p_name":"Cable5/0/3-upstream3","pid":"34314","p_snr":"0","level":"1","time":"2016-08-31 09:56:37"},{"cmts_name":"cisco_园博园_3G60","p_name":"Cable6/1/3-upstream3","pid":"51572","p_snr":"0","level":"1","time":"2016-08-31 09:56:37"},{"cmts_name":"cisco_国贸_01","p_name":"Cable6/1/0-upstream0","pid":"1893","p_snr":"0","level":"1","time":"2016-08-31 09:56:37"},{"cmts_name":"cisco_国贸_01","p_name":"Cable6/1/0-upstream1","pid":"1894","p_snr":"0","level":"1","time":"2016-08-31 09:56:37"},{"cmts_name":"cisco_国贸_01","p_name":"Cable6/1/0-upstream2","pid":"1895","p_snr":"0","level":"1","time":"2016-08-31 09:56:37"},{"cmts_name":"cisco_国贸_01","p_name":"Cable6/1/0-upstream3","pid":"1896","p_snr":"0","level":"1","time":"2016-08-31 09:56:37"},{"cmts_name":"cisco_国贸_01","p_name":"Cable6/1/1-upstream0","pid":"1897","p_snr":"0","level":"1","time":"2016-08-31 09:56:37"},{"cmts_name":"cisco_国贸_01","p_name":"Cable6/1/1-upstream1","pid":"1898","p_snr":"0","level":"1","time":"2016-08-31 09:56:37"},{"cmts_name":"cisco_国贸_01","p_name":"Cable6/1/1-upstream2","pid":"1899","p_snr":"0","level":"1","time":"2016-08-31 09:56:37"},{"cmts_name":"cisco_国贸_01","p_name":"Cable6/1/1-upstream3","pid":"1900","p_snr":"0","level":"1","time":"2016-08-31 09:56:37"},{"cmts_name":"cisco_国贸_01","p_name":"Cable6/1/2-upstream0","pid":"1901","p_snr":"0","level":"1","time":"2016-08-31 09:56:37"},{"cmts_name":"cisco_国贸_01","p_name":"Cable6/1/2-upstream1","pid":"1902","p_snr":"0","level":"1","time":"2016-08-31 09:56:37"},{"cmts_name":"cisco_国贸_01","p_name":"Cable6/1/2-upstream2","pid":"1903","p_snr":"0","level":"1","time":"2016-08-31 09:56:37"}]
     */

    private int total;
    /**
     * cmts_name : casa_怡景_01
     * p_name : Logical Upstream Channel 13/5.0/0
     * pid : 31578
     * p_snr : 0
     * level : 1
     * time : 2016-08-31 09:56:37
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
        private String p_snr;
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

        public String getP_snr() {
            return p_snr;
        }

        public void setP_snr(String p_snr) {
            this.p_snr = p_snr;
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
