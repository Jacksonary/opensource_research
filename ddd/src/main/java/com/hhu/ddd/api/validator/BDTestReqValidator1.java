package com.hhu.ddd.api.validator;

import org.apache.commons.lang3.StringUtils;

import com.baidu.unbiz.fluentvalidator.Validator;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.hhu.ddd.api.model.BDTestReq;

/**
 * @author jacks
 * @date 2022/6/6
 */
public class BDTestReqValidator1 implements Validator<BDTestReq> {
    @Override
    public boolean accept(ValidatorContext context, BDTestReq bdTestReq) {
        return true;
    }

    @Override
    public boolean validate(ValidatorContext context, BDTestReq bdTestReq) {
        if (StringUtils.isBlank(bdTestReq.getName())) {
            String errorMessage = String.format("校验器1 参数错误: %s", bdTestReq.getName());
            context.addErrorMsg(errorMessage);
            throw new IllegalArgumentException(errorMessage);
            // return false;
        }
        return true;
    }

    @Override
    public void onException(Exception e, ValidatorContext context, BDTestReq bdTestReq) {

    }
}
