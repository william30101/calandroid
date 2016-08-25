package com.caldroidsample;

import java.util.Date;

/**
 * Created by Administrator on 2016/8/20.
 */
public class data {

    public String date;
    public String title;
    public String content;

    public data(String d, String t, String c)
    {
        date = d;
        title = t;
        content = c;
    }
    public data(String d)
    {
        date = d;
    }
}
