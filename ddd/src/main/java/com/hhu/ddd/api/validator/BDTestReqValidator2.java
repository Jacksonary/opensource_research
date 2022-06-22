package com.hhu.ddd.api.validator;

import com.baidu.unbiz.fluentvalidator.Validator;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.hhu.ddd.api.model.BDTestReq;

/**
 * @author jacks
 * @date 2022/6/6
 */
public class BDTestReqValidator2 implements Validator<BDTestReq> {
    @Override
    public boolean accept(ValidatorContext context, BDTestReq bdTestReq) {
        return true;
    }

    @Override
    public boolean validate(ValidatorContext context, BDTestReq bdTestReq) {
        if (bdTestReq.getId() < 2) {
            context.addErrorMsg(String.format("校验器2 参数错误: %s", bdTestReq.getId()));
            return false;
        }
        return true;
    }

    @Override
    public void onException(Exception e, ValidatorContext context, BDTestReq bdTestReq) {

    }
}
