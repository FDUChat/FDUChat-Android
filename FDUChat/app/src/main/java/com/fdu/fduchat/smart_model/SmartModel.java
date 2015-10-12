package com.fdu.fduchat.smart_model;

import com.fdu.fduchat.smart_compoent.SmartComponent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

public class SmartModel {
    private class Callback {
        public Object component;
        public Method handler;
        public Callback(Object component, Method handler) {
            this.component = component;
            this.handler = handler;
        }
    }
    private HashMap<String, ArrayList<Callback> > entries = new HashMap<String, ArrayList<Callback>>();
    public void bindComponent(SmartComponent component, String handler, String field) {
        if (!entries.containsKey(field)) {
            entries.put(field, new ArrayList<Callback>());
        }
        try {
            Method m = component.getClass().getMethod(handler, Object.class);
            entries.get(field).add(new Callback(component, m));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
    public void set(String field, Object content) {
        try {
            Method m = this.getClass().getMethod("set" + upperCase(field), content.getClass());
            m.invoke(this, content);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (entries.containsKey(field)) {
            for (Callback c : entries.get(field)) {
                try {
                    c.handler.invoke(c.component, content);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private String upperCase(String field) {
        char[] tmp = field.toCharArray();
        tmp[0] -= 32;
        return String.valueOf(tmp);
    }

}
