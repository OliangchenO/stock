package com.liachen.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum UserType {
    TOTAL("/paihang.shtml"),MONTH,WEEK,DAILY,SUCCESS,FOLLOW,GRADE;
    /**
     * 查询地址
     */
    private String url;

    public static UserType getByType(String type) {
        if (StringUtils.isBlank(type)) {
            return null;
        }
        for (UserType userType : UserType.values()) {
            if (type.equalsIgnoreCase(userType.name())) {
                return userType;
            }
        }
        return null;
    }
}
