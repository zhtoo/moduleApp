package com.zht.modulelib;

/**
 * Created by ZhangHaitao on 2018/9/11.
 */
public enum UserEducationEnum {

    /**
     * 未知
     */
    UNKNOW(0, "未知"),

    /**
     * 小学
     */
    PRIMARY(1, "小学"),

    /**
     * 初中
     */
    MIDDLE(2, "初中"),

    /**
     * 高中
     */
    HIGH(3, "高中"),

    /**
     * 中专
     */
    SECONDARY(4, "中专"),

    /**
     * 大专
     */
    JUNIOR(5, "大专"),

    /**
     * 本科
     */
    COLLEGE(6, "本科"),

    /**
     * 硕士
     */
    MASTER(7, "硕士"),

    /**
     * 博士
     */
    DOCTOR(8, "博士"),

    /**
     * 博士后
     */
    POSTDOCTOR(9, "博士后");

    private int value;
    private String nameValue;

    private UserEducationEnum(int value, String nameValue) {
        this.setValue(value);
        this.setNameValue(nameValue);
    }

    /**
     * 根据值得到是否类型
     *
     * @param value
     * @return 如果没有得到对应的是否类型，返回null
     */
    public static UserEducationEnum get(int value) {
        for (UserEducationEnum type : UserEducationEnum.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        return null;
    }

    /**
     * @return Returns the value.
     */
    public int getValue() {
        return value;
    }

    /**
     * @param value
     *            The value to set.
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * @return Returns the nameValue.
     */
    public String getNameValue() {
        return nameValue;
    }

    /**
     * @param nameValue
     *            The nameValue to set.
     */
    public void setNameValue(String nameValue) {
        this.nameValue = nameValue;
    }

}
