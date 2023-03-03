package com.slamacraft.hnuc.common.core.constant;


/**
 * @author lhd
 * @date 2020/12/30
 */
public enum UserAdminTypeEnum {
    /**
     * 管理员
     */
    ADMIN(1),
    /**
     * 用户
     */
    USER(0),
    ;

    private Integer value;

    public Integer value() {
        return value;
    }

    public Integer getValue() {
        return value;
    }


    UserAdminTypeEnum(Integer value) {
        this.value = value;
    }

    public static UserAdminTypeEnum instance(Integer value) {
        UserAdminTypeEnum[] enums = values();
        for (UserAdminTypeEnum statusEnum : enums) {
            if (statusEnum.getValue().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }
}
