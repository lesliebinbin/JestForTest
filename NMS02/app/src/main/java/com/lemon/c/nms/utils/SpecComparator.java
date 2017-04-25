package com.lemon.c.nms.utils;

import java.lang.reflect.Method;
import java.util.Comparator;

/**
 * Created by C on 2016/9/6.
 */
public class SpecComparator implements Comparator {

    private String methodName;

    public SpecComparator() {
    }

    public SpecComparator(String methodName) {
        this.methodName = methodName;
    }

    @Override
    public int compare(Object o, Object t1) {
        int val = -1;
        try {
            if (methodName == null) {
                val = _compare(o,t1);
            } else {
                val = _compare(getValue(o, methodName), getValue(t1, methodName));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return val;
    }


    public int _compare(Object object1, Object object2) throws Exception {
        if (object1 == null || object2 == null) {
            return object1 == null ? -1 : 1;
        }
        Class c1 = object1.getClass();
        if (object1 instanceof java.lang.Comparable) {
            Method getMethod = object1.getClass().getMethod("compareTo", new Class[] { c1 });
            return (Integer) getMethod.invoke(object1, new Object[] { object2 });
        }
        return -1;
    }


    public Object getValue(Object bean, String methodName) throws Exception {
        Method getMethod = bean.getClass().getMethod(methodName, null);
        return getMethod.invoke(bean, null);
    }


}
