package com.zhaogang.other.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zhaogang.other.arithmetic.tree.TreeNode;

/**
 * @author jacks
 * @date 2021/11/25
 */
@Configuration
public class MyConfig {
    @Value("${mysql.database}")
    private String name;

    @Bean(name = "treeNode")
    public TreeNode treeNode() {
        TreeNode result = new TreeNode(3);
        System.out.println(">> treeNode: " + name);
        return result;
    }
}
