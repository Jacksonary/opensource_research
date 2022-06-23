package com.hhu.other.zk.curator;

import com.hhu.other.zk.curator.manager.ZKManager;
import com.hhu.other.zk.TemplateConfig;
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
