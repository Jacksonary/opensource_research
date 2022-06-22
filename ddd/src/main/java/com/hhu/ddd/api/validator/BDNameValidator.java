package com.hhu.ddd.api.validator;

import org.apache.commons.lang3.StringUtils;

import com.baidu.unbiz.fluentvalidator.Result;
import com.baidu.unbiz.fluentvalidator.Validator;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;

/**
 * fluentValidator 的逻辑
 * 
 * 实现Validator接口，泛型T规范这个校验器待验证的对象的类型，继承ValidatorHandler可以避免实现一些 默认的方法，例如accept()，validate()方法第一个参数是整个校验过程的上下文，第二个参数是
 * 待验证对象
 * 
 * @author jacks
 * @date 2022/6/6
 */
public class BDNameValidator implements Validator<String> {

    /**
     * 判断在该对象上是否接受或者需要验证 如果返回true，那么则调用{@link #validate(ValidatorContext, Object)}，否则跳过该验证器
     *
     * @param context
     *            验证上下文
     * @param t
     *            待验证对象
     *
     * @return 是否接受验证
     */
    @Override
    public boolean accept(ValidatorContext context, String t) {
        return true;
    }

    /**
     * 执行验证
     * 
     * 如果发生错误内部需要调用{@link ValidatorContext#addErrorMsg(String)}方法，也即<code>context.addErrorMsg(String)
     * </code>来添加错误，该错误会被添加到结果存根{@link Result}的错误消息列表中。
     *
     * @param context
     *            验证上下文
     * @param t
     *            待验证对象
     * @return 是否验证通过
     */
    @Override
    public boolean validate(ValidatorContext context, String t) {
        if (StringUtils.isBlank(t)) {
            context.addErrorMsg(String.format("参数错误: %s", t));
            return false;
        }
        return true;
    }

    /**
     * 异常回调
     * 
     * 当执行{@link #accept(ValidatorContext, Object)}或者{@link #validate(ValidatorContext, Object)}发生异常时的如何处理
     *
     * @param e
     *            异常
     * @param context
     *            验证上下文
     * @param t
     *            待验证对象
     */
    @Override
    public void onException(Exception e, ValidatorContext context, String t) {

    }
}
