package com.zht.modulelib;

import java.lang.reflect.Field;

public class TestJavaCode {

    public static void main(String[] args) {

        User user = new User();

        user.setSex(SexType.MAN);
        user.setEducation(UserEducationEnum.COLLEGE);

        System.out.println(user.getSex());


        // 反射获取字段, name成员变量
        try {
//            Class clazz = Class.forName("com.zht.modulelib.User");
//            Class clazz = User.class;
            Class clazz = user.getClass();

            Field field = clazz.getDeclaredField("sex");
            field.setAccessible(true);
       //   field.set(user,"1");

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(user.getSex());



    }

}
