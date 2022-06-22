package com.hhu.ddd.api.controller;

import java.util.Arrays;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSON;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.Result;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;
import com.baidu.unbiz.fluentvalidator.ValidatorChain;
import com.hhu.ddd.api.model.BDTestReq;
import com.hhu.ddd.api.model.TestReq;
import com.hhu.ddd.api.validator.BDIdValidator;
import com.hhu.ddd.api.validator.BDNameValidator;
import com.hhu.ddd.api.validator.BDTestReqValidator1;
import com.hhu.ddd.api.validator.BDTestReqValidator2;

/**
 * @author jacks
 * @date 2022/6/6
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/timestamp")
    public String test() {
        return String.valueOf(System.currentTimeMillis());
    }

    @PostMapping("/validator")
    public String testValidator(@Valid @RequestBody TestReq testReq) {
        System.out.println(JSON.toJSONString(testReq));
        return "ok";
    }

    @PostMapping("/fluent_validator")
    public String testFluentValidator(@Valid @RequestBody BDTestReq testReq) {
        System.out.println(JSON.toJSONString(testReq));

        // 进行验证:
        // 通过FluentValidator.checkAll()获取了一个FluentValidator实例，紧接着调用了failFast()表示有错了立即返回，它的反义词是failOver，然后，一连串on()操作表示在Car的3个属性上依次使用3个校验器进行校验（这个过程叫做applying
        // constraints），截止到此，真正的校验还并没有做，这就是所谓的“惰性求值（Lazy valuation）”，有点像Java8 Stream
        // API中的filter()、map()方法，直到doValidate()验证才真正执行了，最后我们需要收殓出来一个结果供caller获取打印，直接使用默认提供的静态方法toSimple()来做一个回调函数传入result()方法，最终返回Result类
        Result ret = FluentValidator.checkAll().failOver().on(testReq.getId(), new BDIdValidator())
            .on(testReq.getName(), new BDNameValidator()).doValidate().result(ResultCollectors.toSimple());

        System.out.println("fluent validator result: " + JSON.toJSONString(ret));

        return "ok";
    }

    @PostMapping("/fluent_validator_chain")
    public String testFluentValidatorChain(@Valid @RequestBody BDTestReq testReq) {
        System.out.println(JSON.toJSONString(testReq));

        // 直接构造一个校验链
        ValidatorChain chain = new ValidatorChain();
        chain.setValidators(Arrays.asList(new BDTestReqValidator1(), new BDTestReqValidator2()));

        Result ret =
            FluentValidator.checkAll().failOver().on(testReq, chain).doValidate().result(ResultCollectors.toSimple());

        System.out.println("fluent validator result: " + JSON.toJSONString(ret));

        return "ok";
    }
}
