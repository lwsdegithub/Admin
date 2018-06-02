package com.ywh.admin.helper.GsonBean;

import com.ywh.admin.bean.Goods;
import com.ywh.admin.bean.User;

/**
 * Created by 李维升 on 2018/5/14.
 */

public class GoodsDetails {
    public Goods goods;
    public User user;

    public GoodsDetails(Goods goods, User use) {
        super();
        this.goods = goods;
        this.user = user;
    }
}
