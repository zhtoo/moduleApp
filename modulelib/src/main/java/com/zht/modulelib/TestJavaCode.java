package com.zht.modulelib;

import com.zht.modulelib.util.RemoveJsonNullObjectUtil;

public class TestJavaCode {

    public static void main(String[] args) {

        String json = "{\"code\":\"\"," +
                "\"data\":" +
                    "{\"dto\":" +
                        "{\"askNum\":\"\",\"collectNum\":2,\"corpVipMoney\":0,\"corpVipMoneyStr\":\"0.00\",\"courseNum\":0,\"fundsIncome\":0,\"fundsIncomeStr\":\"0.00\",\"funsNum\":0,\"learningPointNum\":0,\"learningPointNumStr\":\"0.00\",\"myAppleFunds\":0,\"myAppleFundsStr\":\"0.00\"}," +
                    "\"isCorpUser\":true," +
                    "\"messageNum\":0," +
                    "\"userInfo\":" +
                        "{\"active\":0,\"activeValue\":42,\"avatar\":\"upload/avatar/2018/08/27/468_photo.jpg\",\"avatarOriginalFile\":\"upload/avatar/2018/08/27/468_photo_original.jpg\",\"birthday\":1534953600000,\"corpHasBuy\":false,\"corpId\":0,\"corpStatus\":0,\"createtime\":1534867200000,\"deptId\":0,\"education\":\"UNKNOW\",\"email\":\"\",\"fansNum\":0,\"fundsMoney\":0,\"id\":468,\"isAdmin\":0,\"isBoss\":0,\"isCorpVip\":false,\"isListenCourse\":0,\"isdeleted\":0,\"listenNum\":0,\"nickname\":\"\",\"originalAvatar\":\"upload/avatar/2018/08/27/468_original.jpg\",\"password\":\"e10adc3949ba59abbe56e057f20f883e\",\"phone\":\"13517194559\",\"profession\":\"\",\"realname\":\"test1你打击报复 范德萨发你史蒂夫按时递交\",\"registerType\":\"CROP_ADD\",\"reportNum\":0,\"safePhone\":\"135 **** 4559\",\"sex\":\"UNKNOW\",\"showName\":\"test1你打击报复 范德萨发你史蒂夫按时递交\",\"showSimpleName\":\"135****4559\",\"status\":0,\"themeNum\":0,\"userIdentity\":\"USER_STUDENT\",\"userType\":\"CORP_USER\",\"username\":\"13517194559\"}}," +
                "\"msg\":\"\"}";


        String json1 = RemoveJsonNullObjectUtil.removeNullObject(json);
        System.out.print(json1);
    }




}
