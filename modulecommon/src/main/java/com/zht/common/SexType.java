package com.zht.common;

/**
 * Created by ZhangHaitao on 2018/9/11.
 */
public enum SexType {

    /**
     * 未知
     */
    UNKNOW {
        @Override
        public int getValue() {
            return 0;
        }

        @Override
        public String getNameValue() {
            return UNKONW_NAME;
        }
    },
    /**
     * 男
     */
    MAN {
        @Override
        public int getValue() {
            return 1;
        }

        @Override
        public String getNameValue() {
            return MAN_NAME;
        }
    },
    /**
     * 女
     */
    WOMAN {
        @Override
        public int getValue() {
            return 2;
        }

        @Override
        public String getNameValue() {
            return WOMAN_NAME;
        }
    };

    private static final String MAN_NAME = "男";
    private static final String WOMAN_NAME = "女";
    private static final String UNKONW_NAME = "未知";

    /**
     * 得到类型的整数值
     *
     * @return
     */
    public abstract int getValue();

    /**
     * 得到类型的名称
     *
     * @return
     */
    public abstract String getNameValue();

    /**
     * 根据值得到性别类型
     *
     * @param value
     * @return 如果没有得到对应的性别类型，返回null
     */
    public static SexType get(int value) {
        for (SexType type : SexType.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }

        return null;
    }

    public static int getValue(String name) {
        for (SexType type : SexType.values()) {
            if (type.getNameValue().equals(name)) {
                return type.getValue();
            }
        }

        return 0;
    }

    /**
     * 根据名称得到类型
     * @return 如果没有得到对应的类型，返回null
     */
    public static SexType getTypeByName(String name) {
        for (SexType type : SexType.values()) {
            if (type.getNameValue().equals(name)) {
                return type;
            }
        }
        return null;
    }


}
