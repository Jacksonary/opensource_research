package com.hhu.ddd.api.validator;

import com.baidu.unbiz.fluentvalidator.Validator;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;

/**
 * fluentValidator 的逻辑
 * 
 * 实现Validator接口，泛型T规范这个校验器待验证的对象的类型，继承ValidatorHandler可以避免实现一些 默认的方法，例如accept()，validate()方法第一个参数是整个校验过程的上下文，第二个参数是
 * 待验证对象
 * 
 * @author jacks
 * @date 2022/6/6
 */
public class BDIdValidator extends ValidatorHandler<Integer> implements Validator<Integer> {
    @Override
    public boolean validate(ValidatorContext context, Integer integer) {
        if (integer < 2) {
            context.addErrorMsg(String.format("参数错误: %s", integer));
            return false;
        }
        return true;
    }
}
