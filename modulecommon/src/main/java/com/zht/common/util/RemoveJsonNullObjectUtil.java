package com.zht.common.util;

import java.util.Comparator;
import java.util.TreeMap;

/**
 * 作者：zhanghaitao on 2018/2/6 17:58
 * 邮箱：820159571@qq.com
 *
 * @describe:移除json数据中的空对象。 已经测试通过。
 * 解决的问题：后台数值（int、long...）类型的数据有可能给你传 "" 。
 */

public class RemoveJsonNullObjectUtil {

    /**
     * json
     * "" : ""
     * 特殊字符： " : ,{} []  0-9
     * 1、 "xxxx" : 2123 ,
     * 2、 "xxxx" : "",
     * 3、 "xxxx" : {},
     * 4、 "xxxx" : [],
     * {}
     * 对象之间的分割以,隔开
     * 过滤其中的空值对象
     *
     * @param json
     * @return
     */
    public static String removeNullObject(String json) {

        if (json == null || json.length() == 0) return "";

        StringBuffer SB = new StringBuffer(json);
        //Map必须是可排序的，而且是降序排列。
        //因为从头开始删除会导致记录的Position发生改变。
        TreeMap<Integer, Integer> positionMap = new TreeMap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });

        //是否能被删除
        boolean couldDelete = false;
        //开始位子
        int startPosition = 1;
        //记录对象开始位子
        int quotationMark = 0;

        for (int i = 0; i < json.length(); i++) {
            char compareChar = json.charAt(i);
            //若果json的value是对象或者数组或者是结束标志，重置判断条件。
            if (compareChar == '{' || compareChar == '[') {
                quotationMark = 0;
                couldDelete = false;
            } else if (compareChar == '\"') {//是不是引号，用来判断是否是对象
                //对象的开头是否记录
                if (quotationMark > 0) {//已经记录过开头
                    quotationMark++;
                    if (quotationMark == 4 && couldDelete && i > 0) {
                        char preChar = json.charAt(i - 1);
                        char nextChar = json.charAt(i + 1);
                        if (preChar == '\"') {
                            if (nextChar == ',') {
                                positionMap.put(startPosition, i + 2);
                            } else {
                                positionMap.put(startPosition, i + 1);
                                couldDelete = false;
                            }
                        }
                    }
                } else {//没有记录开头
                    //将开始位子的值设置成当前位子
                    startPosition = i;
                    quotationMark = 1;
                }
            } else if (compareChar == ':') {
                if (quotationMark == 2) {
                    couldDelete = true;
                } else {
                    quotationMark = 0;
                    couldDelete = false;
                }
            } else if (compareChar >= '0' && compareChar <= '9') {
                if (quotationMark == 2 && couldDelete) {
                    quotationMark = 0;
                    couldDelete = false;
                }
            } else if (compareChar == ',') {
                quotationMark = 0;
            }
        }
        //循环删除SB中的空对象
        for (Integer start : positionMap.keySet()) {
            Integer end = positionMap.get(start);
            SB.delete(start, end);
        }
        return SB.toString();
    }

}