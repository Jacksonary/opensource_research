package com.hhu.kafka.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jacks
 * @date 2022/2/28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NameMsgDTO implements Serializable {
    private static final long serialVersionUID = -5484155279172117035L;

    private Integer id;
    private String name;
}
