package com.zht.common.util;

/**
 * Created by ZhangHaitao on 2018/9/4.
 */
public class JsonFormat {

/**
 * 对输入字符串，追个字符的遍历
 1、获取当前字符。
 2、如果当前字符是前方括号、前花括号做如下处理：
 （1）如果前面还有字符，并且字符为“：”，打印：换行和缩进字符字符串。
 （2）打印：当前字符。
 （3）前方括号、前花括号，的后面必须换行。打印：换行。
 （4）每出现一次前方括号、前花括号；缩进次数增加一次。打印：新行缩进。
 （5）进行下一次循环。
 3、如果当前字符是后方括号、后花括号做如下处理：
 （1）后方括号、后花括号，的前面必须换行。打印：换行。
 （2）每出现一次后方括号、后花括号；缩进次数减少一次。打印：缩进。
 （3）打印：当前字符。
 （4）如果当前字符后面还有字符，并且字符不为“，”，打印：换行。
 （5）继续下一次循环。
 4、如果当前字符是逗号。逗号后面换行，并缩进，不改变缩进次数。
 5、打印：当前字符。
 */

    /**
     * 解析键值对
     * return json对象  json数组  json数据
     */
    public void analyzeJson(String json) {

        for (int i = 0; i < json.length(); i++) {
            char mChar = json.charAt(i);
            //json对象开始
            if(mChar=='{'){

            }else if(mChar=='['){

            }
            else if(mChar=='\"'){

            }else if(mChar==':'){

            }else if(mChar==','){

            }

        }

    }


    /**
     * 解析json对象
     */
    public void analyzeJsonObject(String json) {

    }

    /**
     * 解析json数组
     */
    public void analyzeJsonArrsy(String json) {

    }

    /**
     * 解析键值对
     * return json对象  json数组  json数据
     */
    public void analyzeJsondata(String json) {

    }


}
