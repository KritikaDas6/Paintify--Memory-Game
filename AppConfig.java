package com.paintify.app;

import java.util.HashMap;

public class AppConfig{
    HashMap<String,Object> config=new HashMap<String,Object>();

    static AppConfig instance = null;

    public static final String FILL_COLOR="color.fill";
    public static final String BRUSH_SIZE="brush.size";

    public static AppConfig getInstance(){
        if (instance==null)
            instance = new AppConfig();
        return instance;
    }

    public void setConfig(String key, Object value){
        config.put(key, value);

    }

    public Object getConfig(String key){
        return config.get(key);
    }    
}
