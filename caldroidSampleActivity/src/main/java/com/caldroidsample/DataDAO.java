package com.caldroidsample;

import java.util.List;

/**
 * Created by Administrator on 2016/8/21.
 */
public interface DataDAO {
    public void addData(data d);
    public void delData(data d);
    public void updateData(data d);
    public List getAllData();
    public List getData(data d);
}
