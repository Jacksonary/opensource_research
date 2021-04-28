package com.zhaogang.other.zk.curator;

import com.zhaogang.other.zk.TemplateConfig;
import com.zhaogang.other.zk.curator.manager.ZKManager;
import java.util.List;

/**
 * @author weiguo.liu
 * @date 2021/3/17
 * @description
 */
public class Main {
    public static void main(String[] args) {
        ZKManager zkManager = new ZKManager();
        List<TemplateConfig> data = zkManager.getData();
        System.out.println(data);
    }
}
