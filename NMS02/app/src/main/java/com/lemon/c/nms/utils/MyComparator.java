package com.lemon.c.nms.utils;

import com.lemon.c.nms.bean.AlertInfoBean;

import java.util.Comparator;

/**
 * Created by C on 2016/8/30.
 */
public class MyComparator implements Comparator{

    private int type;

    public MyComparator(int type){
        this.type = type;
    }
    @Override
    public int compare(Object o, Object t1) {
        AlertInfoBean a = (AlertInfoBean) o;
        AlertInfoBean b = (AlertInfoBean) t1;
        if(type == 0){
            return  Double.valueOf(a.getValue()).compareTo(Double.valueOf(b.getValue()));
        } else {
            return  Double.valueOf(b.getValue()).compareTo(Double.valueOf(a.getValue()));
        }
    }

}
