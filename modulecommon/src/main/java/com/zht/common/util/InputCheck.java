package com.zht.common.util;

import android.text.TextUtils;
import android.widget.EditText;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ZhangHaitao on 2018/9/10.
 */
public class InputCheck {

    /**
     * 检查name是否是中文名
     *
     * @param name   姓名
     * @return true - 是中文名;false - 不是中文名
     */
    public static boolean checkChineseName(String name) {
        if (TextUtils.isEmpty(name)) {
            return false;
        } else if (checkChinese(name) && name.length() >= 2 && name.length() <= 6) {
            return true;
        }
        return false;
    }

    /**
     * 检测String是否全是中文
     * @param name   待检测string
     * @return true - 是中文;
     * false - 不是中文
     */
    public static boolean checkChinese(String name) {
        name = new String(name.getBytes());//用GBK编码
        String pattern = "[\u4e00-\u9fa5]+";
        Pattern p       = Pattern.compile(pattern);
        Matcher result  = p.matcher(name);
        return result.matches(); //是否含有中文字符
    }

    /**
     * 判定输入汉字
     * @param c   待检测字符
     * @return true - 是中文字符;
     * false - 不是中文字符
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern regex   = Pattern.compile("^((1[358][0-9])|(14[579])|16[6]|(17[0-3,5-8])|(19[89]))\\d{8}$");
        Matcher matcher = regex.matcher(mobiles);
        return matcher.matches();
    }

    /**
     * 验证是否是邮箱地址
     * @param email   待检测email
     * @return true - 是email;
     * false - 不是email
     */
    public static boolean isEmail(String email) {
        Pattern regex   = Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
        Matcher matcher = regex.matcher(email);// 获取邮箱然后匹对是否正确
        return matcher.matches();
    }

    /**
     * 验证密码规则，是否由6-20为数字+字母组成
     * @param pwd      待检测密码
     * @return true - 符合密码规则;
     * false - 不符合密码规则
     */
    public static boolean checkPwdRule(String pwd) {
        if (pwd != null){
            Pattern regex   = Pattern.compile("^(?![^a-zA-Z]+$)(?!\\D+$).{6,20}$");
            Matcher matcher = regex.matcher(pwd);
            return matcher.matches();
        }
        else return  false;
    }

    /**
     * 验证用户名规则，是否由4-16为数字+字母组成
     * @param name    待检测用户名
     * @return boolean
     * true - 符合用户名规则;
     * false - 不符合用户名规则
     */
    public static boolean checkUsernameRule(String name) {
        Pattern regex   = Pattern.compile("^(?![^a-zA-Z]+$)(?!\\D+$).{4,16}$");
        Matcher matcher = regex.matcher(name);
        return matcher.matches();
    }

    /**
     * 检验短信验证码是否符合规则
     * @param code      待检测验证码
     * @return true - 符合验证码规则；
     * false - 不符合验证码规则
     */
    public static boolean checkCode(String code) {
        Pattern regex   = Pattern.compile("^\\d{6}$");
        Matcher matcher = regex.matcher(code);
        return matcher.matches();
    }

    /**
     * 检验图形验证码是否符合规则
     * @param code      待检测验证码
     * @return true - 符合验证码规则；
     * false - 不符合验证码规则
     */
    public static boolean checkImgCode(String code) {
        Pattern regex   = Pattern.compile("^\\d{4}$");
        Matcher matcher = regex.matcher(code);
        return matcher.matches();
    }

    /**
     * 校验身份证是否符合规则
     * @param card
     * @return
     */
    public static boolean checkIDCard(String card) {
        Pattern idNumPattern = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9xX])");
        Matcher idNumMatcher = idNumPattern.matcher(card);
        return idNumMatcher.matches(); //是否含有中文字符
    }

    /**
     * 校验身份证后6位是否符合规范
     * @param cardAfter6
     * @return
     */
    public static boolean checkCardAfter6(String cardAfter6) {
        Pattern idNumPattern = Pattern.compile("(\\d{6}[0-9xX])");
        Matcher idNumMatcher = idNumPattern.matcher(cardAfter6);
        return idNumMatcher.matches();
    }

    /**
     * 校验输入的字符是否为数字
     * @param input
     * @return
     */
    public static boolean checkIsNumber(String input) {
        Pattern idNumPattern = Pattern.compile("[0-9]+");
        Matcher idNumMatcher = idNumPattern.matcher(input);
        return idNumMatcher.matches();
    }

    /**
     * 校验字符是否是数字 x X
     * @param input
     * @return
     */
    public static boolean checkIsNumberOrX(String input) {
        Pattern idNumPattern = Pattern.compile("[0-9xX]");
        Matcher idNumMatcher = idNumPattern.matcher(input);
        return idNumMatcher.matches();
    }

    /**
     * 验证密码规则，是否由8-16为数字+字母组成
     * @param pwd     待检测密码
     * @return true - 符合密码规则;
     * false - 不符合密码规则
     */
    public static boolean checkPayPwd(String pwd) {
        Pattern regex   = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$");
        Matcher matcher = regex.matcher(pwd);
        return matcher.matches();
    }

    /**
     * 匹配所有键盘上可见的非(字母和数字)的符号
     */
    public static boolean checkIllagleCharacter(String input) {
        Pattern regex   = Pattern.compile("((?=[\\x21-\\x7e]+)[^A-Za-z0-9])");
        Matcher matcher = regex.matcher(input);
        return matcher.matches();
    }
    /**
     * 需监听的editText list 中是否全非空
     * @return
     */
    public static boolean edListCheck(LinkedList<EditText> edList) {
        for (EditText editText : edList) {
            if (TextUtils.isEmpty(editText.getText())) {
                return false;
            }
        }
        return true;
    }


    /**
     * 校验输入的字符是否为小数
     * @param input
     * @return
     */
    public static boolean checkIsDecimal(String input) {
        Pattern idNumPattern = Pattern.compile("([0-9]+)[\\.]([0-9]{1,2})");
        Matcher idNumMatcher = idNumPattern.matcher(input);
        return idNumMatcher.matches();
    }

}
