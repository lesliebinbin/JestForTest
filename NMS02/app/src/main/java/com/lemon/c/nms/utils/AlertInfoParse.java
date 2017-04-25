package com.lemon.c.nms.utils;

import com.google.gson.Gson;
import com.lemon.c.nms.bean.AlertInfoBean;
import com.lemon.c.nms.bean.BERBean;
import com.lemon.c.nms.bean.NPABean;
import com.lemon.c.nms.bean.PortChangeBean;
import com.lemon.c.nms.bean.SNRBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by C on 2016/8/31.
 */
public class AlertInfoParse {

    public static List<AlertInfoBean> doGson(String result, int alertType){
        List<AlertInfoBean> finalList = new ArrayList<>();
        Gson gson = new Gson();
        switch (alertType){
            case 0:
                try{
                    SNRBean snr = gson.fromJson(result, SNRBean.class);
                    List<SNRBean.DataBean> snrList = snr.getData();
                    for(SNRBean.DataBean snrData : snrList) {
                        AlertInfoBean infoBean = new AlertInfoBean();
                        infoBean.setCmtsName(snrData.getCmts_name());
                        infoBean.setPortName(snrData.getP_name());
                        infoBean.setId(Integer.parseInt(snrData.getPid()));
                        infoBean.setValue(snrData.getP_snr());
                        infoBean.setLevel(Integer.parseInt(snrData.getLevel()));
                        infoBean.setTime(snrData.getTime());
                        finalList.add(infoBean);
                    }
                } catch (Exception e){

                }
                break;
            case 1:
                BERBean ber = gson.fromJson(result, BERBean.class);
                List<BERBean.DataBean> berList = ber.getData();
                for(BERBean.DataBean berData : berList){
                    AlertInfoBean infoBean = new AlertInfoBean();
                    infoBean.setCmtsName(berData.getCmts_name());
                    infoBean.setPortName(berData.getP_name());
                    infoBean.setId(Integer.parseInt(berData.getPid()));
                    infoBean.setValue(berData.getP_ucer());
                    infoBean.setLevel(Integer.parseInt(berData.getLevel()));
                    infoBean.setTime(berData.getTime());
                    finalList.add(infoBean);
                }
                break;
            case 2:
                NPABean npa = gson.fromJson(result, NPABean.class);
                List<NPABean.DataBean> npaList = npa.getData();
                for(NPABean.DataBean npaData : npaList){
                    AlertInfoBean infoBean = new AlertInfoBean();
                    infoBean.setCmtsName(npaData.getCmts_name());
                    infoBean.setPortName(npaData.getP_name());
                    infoBean.setId(Integer.parseInt(npaData.getPid()));
                    infoBean.setValue(npaData.getNpa());
                    infoBean.setLevel(Integer.parseInt(npaData.getLevel()));
                    infoBean.setTime(npaData.getTime());
                    finalList.add(infoBean);
                }
                break;
            case 3:
                //没有level
                PortChangeBean change = gson.fromJson(result, PortChangeBean.class);
                List<PortChangeBean.DataBean> changeList = change.getData();
                for(PortChangeBean.DataBean changeData : changeList){
                    AlertInfoBean infoBean = new AlertInfoBean();
                    infoBean.setCmtsName(changeData.getC_nickname());
                    infoBean.setPortName(changeData.getP_name());
                    infoBean.setId(Integer.parseInt(changeData.getPid()));
                    infoBean.setValue(changeData.getQam());
                    infoBean.setTime(changeData.getTime());
                    finalList.add(infoBean);
                }
                break;
            case 4:
                //暂时没有数据
                break;
        }
        return finalList;
    }

}
