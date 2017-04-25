package com.lemon.c.nms.bean;

import java.util.List;

/**
 * Created by C on 2016/9/5.
 */
public class SpectrumBean {

    /**
     * total : 1
     * data : [{"port_id":"560305","err_paper_id":"20160904683017","company_name":"天威公司","sr_name":"园博园","fs_code":"YBY14J","district":"财富广场小区_YBY14J_侨城分部","create_time":"2016-09-04 14:02:19","remain":"4620","alarm_name":"严重噪声干扰","time":"2016-09-04 15:20:40","time1":"-","fixed":"待签收","depart_name":"NPA-华侨城片区"}]
     */

    private int total;
    /**
     * port_id : 560305
     * err_paper_id : 20160904683017
     * company_name : 天威公司
     * sr_name : 园博园
     * fs_code : YBY14J
     * district : 财富广场小区_YBY14J_侨城分部
     * create_time : 2016-09-04 14:02:19
     * remain : 4620
     * alarm_name : 严重噪声干扰
     * time : 2016-09-04 15:20:40
     * time1 : -
     * fixed : 待签收
     * depart_name : NPA-华侨城片区
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
        private String port_id;
        private String err_paper_id;
        private String company_name;
        private String sr_name;
        private String fs_code;
        private String district;
        private String create_time;
        private String remain;
        private String alarm_name;
        private String time;
        private String time1;
        private String fixed;
        private String depart_name;

        public String getPort_id() {
            return port_id;
        }

        public void setPort_id(String port_id) {
            this.port_id = port_id;
        }

        public String getErr_paper_id() {
            return err_paper_id;
        }

        public void setErr_paper_id(String err_paper_id) {
            this.err_paper_id = err_paper_id;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public String getSr_name() {
            return sr_name;
        }

        public void setSr_name(String sr_name) {
            this.sr_name = sr_name;
        }

        public String getFs_code() {
            return fs_code;
        }

        public void setFs_code(String fs_code) {
            this.fs_code = fs_code;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getRemain() {
            return remain;
        }

        public void setRemain(String remain) {
            this.remain = remain;
        }

        public String getAlarm_name() {
            return alarm_name;
        }

        public void setAlarm_name(String alarm_name) {
            this.alarm_name = alarm_name;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTime1() {
            return time1;
        }

        public void setTime1(String time1) {
            this.time1 = time1;
        }

        public String getFixed() {
            return fixed;
        }

        public void setFixed(String fixed) {
            this.fixed = fixed;
        }

        public String getDepart_name() {
            return depart_name;
        }

        public void setDepart_name(String depart_name) {
            this.depart_name = depart_name;
        }
    }
}
