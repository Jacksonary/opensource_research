package com.hhu.other.cat;

import java.io.Serializable;

import lombok.Data;

/**
 * @author jacks
 * @date 2022/3/21
 */
@Data
public class CatAlarmReq implements Serializable {
    private static final long serialVersionUID = -1641807856670511193L;
    private String msg;
}
