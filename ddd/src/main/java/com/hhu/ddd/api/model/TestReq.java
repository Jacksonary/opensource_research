package com.hhu.ddd.api.model;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.hhu.ddd.api.validator.IsCustomStr;

import lombok.Data;

/**
 * 支持的校验
 * 
 * @Null 对象，为空 Bean Validation 1.0
 * @NotNull 对象，不为空
 * @AssertTrue 布尔，为True
 * @AssertFalse 布尔，为False
 * @Min(value) 数字，最小为value
 * @Max(value) 数字，最大为value
 * @DecimalMin(value) 数字，最小为value
 * @DecimalMax(value) 数字，最大为value
 * @Size(max, min) min<=value<=max
 * @Digits (integer, fraction) 数字，某个范围内
 * @Past 日期，过去的日期
 * @Future 日期，将来的日期
 * @Pattern(value) 字符串，正则校验
 * @Email 字符串，邮箱类型 Bean Validation 2.0
 * @NotEmpty 集合，不为空
 * @NotBlank 字符串，不为空字符串
 * @Positive 数字，正数
 * @PositiveOrZero 数字，正数或0
 * @Negative 数字，负数
 * @NegativeOrZero 数字，负数或0
 * @PastOrPresent 过去或者现在
 * @FutureOrPresent 将来或者现在
 * 
 * @author jacks
 * @date 2022/6/6
 */
@Data
public class TestReq {
    @Min(value = 0, message = "id 不能小于 0")
    private Long id;
    @Size(min = 2, max = 5, message = "姓名只能 2-5 个字符")
    @NotBlank(message = "姓名不能为空")
    private String name;
    @NotEmpty(message = "兴趣为空")
    private List<String> likes;
    @AssertFalse(message = "未成年人禁止注册")
    private Boolean child;
    @Min(value = 18, message = "小于18岁禁止注册")
    @Max(value = 65, message = "大于65禁止注册")
    private Integer age;
    @Past(message = "日期非法，必须是过去日期(不包含当天)")
    // @Future(message = "日期非法，必须是未来日期(不包含当天)")
    private LocalDate birthDay;
    @IsCustomStr(message = "自定义字串校验失败")
    private String customStr;
}
