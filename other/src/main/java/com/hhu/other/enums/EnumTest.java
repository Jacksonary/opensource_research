package com.hhu.other.enums;

/**
 * @author jacks
 * @date 2022/2/9
 */
public class EnumTest {
    public static void main(String[] args) {
        StatusEnum statusEnum = BaseCodeEnum.parseByCode(StatusEnum.class, (byte)-1);
        System.out.println(statusEnum);

        TypeEnum typeEnum = BaseCodeEnum.parseByCode(TypeEnum.class, (byte)1);
        System.out.println(typeEnum);

        PhoneEnum phoneEnum = BaseNameEnum.parseByName(PhoneEnum.class, "pixel");
        PhoneEnum phoneEnum2 = BaseCodeEnum.parseByCode(PhoneEnum.class, (byte)1);
        System.out.println(phoneEnum);
        System.out.println(phoneEnum2);
    }
}
