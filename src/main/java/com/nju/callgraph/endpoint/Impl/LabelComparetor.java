package com.nju.callgraph.endpoint.Impl;

import com.nju.callgraph.pojo.Label;
import com.nju.callgraph.pojo.LabelExample;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class LabelComparetor implements Comparator<Label> {
    @Override
    public int compare(Label o1, Label o2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date1 = null;
        Date date2 = null;
        try {
            date1 = sdf.parse(o1.getCreatetime());
            date2 = sdf.parse(o2.getCreatetime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(date1.before(date2)){
            return 1;
        }else if(date1.equals(date2)){
            return 0;
        }else{
            return -1;
        }
    }
}
