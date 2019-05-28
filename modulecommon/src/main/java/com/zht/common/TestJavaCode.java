package com.zht.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

public class TestJavaCode {

    public static void main(String[] args) {

//        dowloadFile();



//        int year = 5;
        double r = 0.033;
        int money = 22000;
        double totalMoney = 170000;
        for (int i = 0; i < 8; i++) {
            System.out.print("第" + "\t" + (i / 12 + 1) + "年：");
            System.out.print("\t" + (i % 12 + 1) + "个月：");
            int interest = (int) (100 * totalMoney * r * 30 / 365 );
            double interestD = interest / 100D;
            System.out.print("\t利息：" + interestD);
            totalMoney += (money /*+ interestD*/ - 6000);
            DecimalFormat fnum = new DecimalFormat("##0.00");
            String dd = fnum.format(((int) totalMoney*100 / 10000)/100D);
            System.out.print("\t 剩余总额：" + dd + "W\n");
        }
       /* for (int i = 0; i < year; i++) {
            System.out.print("第");
            System.out.print("\t" + (i % 12 + 1) + "年：");
            int interest = (int) (100 * totalMoney * r);
            double interestD = interest / 100D;
            System.out.print("\t利息：" + interestD);
            totalMoney += (money * 12 + interestD - 6000 * 12);
            System.out.print("\t 剩余总额：" + ((int) totalMoney / 10000) + "W\n");
        }*/

    }

    private static void dowloadFile() {
        try {
            URL url = new URL("https://codeload.github.com/zhtoo/zhangLib/zip/master");
            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5 * 1000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/octet-stream");
            connection.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
            connection.setRequestProperty("Charset", "UTF-8");
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            byte[] data = new byte[1024];
            File file = new File("C:/Users/user/Desktop" + "/zhangLib.zip");
            FileOutputStream fileOut = new FileOutputStream(file);
            int length = 0;
            int totalLength = 0;
            int progress = 0;
            int available =connection.getContentLength();
            while ((length = inputStream.read(data)) != -1) {
                fileOut.write(data);
                totalLength += length;
                System.out.println(totalLength);
            }
            inputStream.close();
            fileOut.close();
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String get(long args) {


        long G = args/(1024*1024*1024) ;
        long M = args%(1024*1024*1024)/(1024*1024)  ;
        long kb = args%(1024*1024*1024)%(1024*1024)/1024 ;

        return G+"G"+M+"M"+ kb+"kb";
    }


//    public static void main(String[] args) {
////        System.out.println("");
////        String[][] MATCH_ARRAY = {
////                //{后缀名，    文件类型}
////                {".3gp", "video/3gpp"},
////                {".apk", "application/vnd.android.package-archive"},
////                {".c", "text/plain"},
////                {".class", "application/octet-stream"},
////                {".conf", "text/plain"},
////                {".cpp", "text/plain"},
////                {".exe", "application/octet-stream"},
////                {".gtar", "application/x-gtar"},
////                {".java", "text/plain"},
////                {".log", "text/plain"},
////                {".m4a", "audio/mp4a-latm"},
////                {".m4b", "audio/mp4a-latm"},
////                {".m4p", "audio/mp4a-latm"},
////                {".m4u", "video/vnd.mpegurl"},
////                {".m4v", "video/x-m4v"},
////                {".mov", "video/quicktime"},
////                {".mp2", "audio/x-mpeg"},
////                {".mp3", "audio/x-mpeg"},
////                {".mp4", "video/mp4"},
////                {".mpc", "application/vnd.mpohun.certificate"},
////                {".mpg", "video/mpeg"},
////                {".mpg4", "video/mp4"},
////                {".mpga", "audio/mpeg"},
////                {".msg", "application/vnd.ms-outlook"},
////                {".ogg", "audio/ogg"},
////                {".pps", "application/vnd.ms-powerpoint"},
////                {".ppt", "application/vnd.ms-powerpoint"},
////                {".prop", "text/plain"},
////                {".rar", "application/x-rar-compressed"},
////                {".rc", "text/plain"},
////                {".rmvb", "audio/x-pn-realaudio"},
////                {".sh", "text/plain"},
////                {".tar", "application/x-tar"},
////                {".wav", "audio/x-wav"},
////                {".wmv", "audio/x-ms-wmv"},
////                {".xml", "text/plain"},
////                {".zip", "application/zip"},
//////                {"", "*/*"},
////                {".asx", "video/x-ms-asf"},
////                {".xml", "text/xml"},
////                {".tsv", "text/tab-separated-values"},
////                {".ra", "audio/x-pn-realaudio"},
////                {".sv4crc", "application/x-sv4crc"},
////                {".spc", "application/x-pkcs7-certificates"},
////                {".pmc", "application/x-perfmon"},
////                {".lit", "application/x-ms-reader"},
////                {".crd", "application/x-mscardfile"},
////                {".isp", "application/x-internet-signup"},
////                {".wmlsc", "application/vnd.wap.wmlscriptc"},
////                {".vst", "application/vnd.visio"},
////                {".xlam", "application/vnd.ms-excel.addin.macroEnabled.12"},
////                {".ttf", "application/octet-stream"},
////                {".pfm", "application/octet-stream"},
////                {".csv", "application/octet-stream"},
////                {".aaf", "application/octet-stream"},
////                {".one", "application/onenote"},
////                {".hta", "application/hta"},
////                {".atom", "application/atom+xml"},
////                {".323", "text/h323"},
////                {".mhtml", "message/rfc822"},
////                {".midi", "audio/mid"},
////                {".p7r", "application/x-pkcs7-certreqresp"},
////                {".mny", "application/x-msmoney"},
////                {".clp", "application/x-msclip"},
////                {".vsd", "application/vnd.visio"},
////                {".lpk", "application/octet-stream"},
////                {".bin", "application/octet-stream"},
////                {".onetoc", "application/onenote"},
////                {".x", "application/directx"},
////                {".wvx", "video/x-ms-wvx"},
////                {".vcf", "text/x-vcard"},
////                {".htc", "text/x-component"},
////                {".htt", "text/webviewhtml"},
////                {".h", "text/plain"},
////                {".mht", "message/rfc822"},
////                {".mid", "audio/mid"},
////                {".p7b", "application/x-pkcs7-certificates"},
////                {".gz", "application/x-gzip"},
////                {".dvi", "application/x-dvi"},
////                {".cpio", "application/x-cpio"},
////                {".vdx", "application/vnd.ms-visio.viewer"},
////                {".sldm", "application/vnd.ms-powerpoint.slide.macroEnabled.12"},
////                {".xlm", "application/vnd.ms-excel"},
////                {".fdf", "application/vnd.fdf"},
////                {".setreg", "application/set-registration-initiation"},
////                {".eps", "application/postscript"},
////                {".p7s", "application/pkcs7-signature"},
////                {".toc", "application/octet-stream"},
////                {".mdp", "application/octet-stream"},
////                {".ics", "application/octet-stream"},
////                {".chm", "application/octet-stream"},
////                {".asi", "application/octet-stream"},
////                {".afm", "application/octet-stream"},
////                {".evy", "application/envoy"},
////                {".wmp", "video/x-ms-wmp"},
////                {".qt", "video/quicktime"},
////                {".mpv2", "video/mpeg"},
////                {".xslt", "text/xml"},
////                {".etx", "text/x-setext"},
////                {".cod", "image/cis-cod"},
////                {".snd", "audio/basic"},
////                {".au", "audio/basic"},
////                {".man", "application/x-troff-man"},
////                {".qtl", "application/x-quicktimeplayer"},
////                {".pmw", "application/x-perfmon"},
////                {".class", "application/x-java-applet"},
////                {".iii", "application/x-iphone"},
////                {".csh", "application/x-csh"},
////                {".z", "application/x-compress"},
////                {".vtx", "application/vnd.visio"},
////                {".vsw", "application/vnd.visio"},
////                {".wps", "application/vnd.ms-works"},
////                {".potx", "application/vnd.openxmlformats-officedocument.presentationml.template"},
////                {".ps", "application/postscript"},
////                {".p7c", "application/pkcs7-mime"},
////                {".thn", "application/octet-stream"},
////                {".mso", "application/octet-stream"},
////                {".dot", "application/msword"},
////                {".doc", "application/msword"},
////                {".sgml", "text/sgml"},
////                {".nws", "message/rfc822"},
////                {".pbm", "image/x-portable-bitmap"},
////                {".ief", "image/ief"},
////                {".wav", "audio/wav"},
////                {".texi", "application/x-texinfo"},
////                {".mvb", "application/x-msmediaview"},
////                {".hdf", "application/x-hdf"},
////                {".vsx", "application/vnd.visio"},
////                {".dotm", "application/vnd.ms-word.template.macroEnabled.12"},
////                {".docm", "application/vnd.ms-word.document.macroEnabled.12"},
////                {".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
////                {".psm", "application/octet-stream"},
////                {".java", "application/octet-stream"},
////                {".eot", "application/octet-stream"},
////                {".jar", "application/java-archive"},
////                {".mpeg", "video/mpeg"},
////                {".xsf", "text/xml"},
////                {".map", "text/plain"},
////                {".uls", "text/iuls"},
////                {".rf", "image/vnd.rn-realflash"},
////                {".m3u", "audio/x-mpegurl"},
////                {".wma", "audio/x-ms-wma"},
////                {".aifc", "audio/aiff"},
////                {".mdb", "application/x-msaccess"},
////                {".mvc", "application/x-miva-compiled"},
////                {".stl", "application/vnd.ms-pki.stl"},
////                {".ppsx", "application/vnd.openxmlformats-officedocument.presentationml.slideshow"},
////                {".xlsb", "application/vnd.ms-excel.sheet.binary.macroEnabled.12"},
////                {".setpay", "application/set-payment-initiation"},
////                {".prm", "application/octet-stream"},
////                {".mix", "application/octet-stream"},
////                {".lzh", "application/octet-stream"},
////                {".hhk", "application/octet-stream"},
////                {".onepkg", "application/onenote"},
////                {".xaf", "x-world/x-vrml"},
////                {".flr", "x-world/x-vrml"},
////                {".ivf", "video/x-ivf"},
////                {".cnf", "text/plain"},
////                {".asm", "text/plain"},
////                {".tiff", "image/tiff"},
////                {".wax", "audio/x-ms-wax"},
////                {".ms", "application/x-troff-ms"},
////                {".tcl", "application/x-tcl"},
////                {".shar", "application/x-shar"},
////                {".sh", "application/x-sh"},
////                {".nc", "application/x-netcdf"},
////                {".hlp", "application/winhlp"},
////                {".oda", "application/oda"},
////                {".pfb", "application/octet-stream"},
////                {".fla", "application/octet-stream"},
////                {".wm", "video/x-ms-wm"},
////                {".rgb", "image/x-rgb"},
////                {".ppm", "image/x-portable-pixmap"},
////                {".ram", "audio/x-pn-realaudio"},
////                {".sit", "application/x-stuffit"},
////                {".dir", "application/x-director"},
////                {".mpp", "application/vnd.ms-project"},
////                {".xla", "application/vnd.ms-excel"},
////                {".ssm", "application/streamingmedia .axs", "application/olescript"},
////                {".ods", "application/oleobject"},
////                {".psp", "application/octet-stream"},
////                {".jpb", "application/octet-stream"},
////                {".wrz", "x-world/x-vrml"},
////                {".m1v", "video/mpeg"},
////                {".mno", "text/xml"},
////                {".cmx", "image/x-cmx"},
////                {".jpeg", "image/jpeg"},
////                {".dib", "image/bmp"},
////                {".rmi", "audio/mid"},
////                {".aiff", "audio/aiff"},
////                {".wmd", "application/x-ms-wmd"},
////                {".wri", "application/x-mswrite"},
////                {".pub", "application/x-mspublisher"},
////                {".ins", "application/x-internet-signup"},
////                {".wks", "application/vnd.ms-works"},
////                {".xls", "application/vnd.ms-excel"},
////                {".ai", "application/postscript"},
////                {".crl", "application/pkix-crl"},
////                {".qxd", "application/octet-stream"},
////                {".dwp", "application/octet-stream"},
////                {".xof", "x-world/x-vrml"},
////                {".wmv", "video/x-ms-wmv"},
////                {".nsc", "video/x-ms-asf"},
////                {".mpa", "video/mpeg"},
////                {".pnm", "image/x-portable-anymap"},
////                {".rpm", "audio/x-pn-realaudio-plugin.aif", "audio/x-aiff"},
////                {".me", "application/x-troff-me"},
////                {".pml", "application/x-perfmon"},
////                {".trm", "application/x-msterminal"},
////                {".m13", "application/x-msmediaview"},
////                {".js", "application/x-javascript"},
////                {".dxr", "application/x-director"},
////                {".potm", "application/vnd.ms-powerpoint.template.macroEnabled.12"},
////                {".xltx", "application/vnd.openxmlformats-officedocument.spreadsheetml.template"},
////                {".xlt", "application/vnd.ms-excel"},
////                {".xlc", "application/vnd.ms-excel"},
////                {".p10", "application/pkcs10"},
////                {".smi", "application/octet-stream"},
////                {".sea", "application/octet-stream"},
////                {".hqx", "application/mac-binhex40"},
////                {".spl", "application/futuresplash"},
////                {".movie", "video/x-sgi-movie"},
////                {".lsf", "video/x-la-asf"},
////                {".txt", "text/plain"},
////                {".jfif", "image/pjpeg"},
////                {".jpe", "image/jpeg"},
////                {".zip", "application/x-zip-compressed"},
////                {".wmf", "application/x-msmetafile"},
////                {".m14", "application/x-msmediaview"},
////                {".latex", "application/x-latex"},
////                {".wcm", "application/vnd.ms-works"},
////                {".pptm", "application/vnd.ms-powerpoint.presentation.macroEnabled.12"},
////                {".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
////                {".hhp", "application/octet-stream"},
////                {".aca", "application/octet-stream"},
////                {".accdb", "application/msaccess"},
////                {".jcz", "application/liquidmotion"},
////                {".wrl", "x-world/x-vrml"},
////                {".wmx", "video/x-ms-wmx"},
////                {".asr", "video/x-ms-asf"},
////                {".lsx", "video/x-la-asf"},
////                {".xsl", "text/xml"},
////                {".html", "text/html"},
////                {".tif", "image/tiff"},
////                {".der", "application/x-x509-ca-cert.pfx", "application/x-pkcs12"},
////                {".p12", "application/x-pkcs12"},
////                {".ppsm", "application/vnd.ms-powerpoint.slideshow.macroEnabled.12"},
////                {".cur", "application/octet-stream"},
////                {".accdt", "application/msaccess"},
////                {".hdml", "text/x-hdml"},
////                {".htm", "text/html"},
////                {".xbm", "image/x-xbitmap"},
////                {".jpg", "image/jpeg"},
////                {".texinfo", "application/x-texinfo"},
////                {".ppam", "application/vnd.ms-powerpoint.addin.macroEnabled.12"},
////                {".xlw", "application/vnd.ms-excel"},
////                {".rm", "application/vnd.rn-realmedia"},
////                {".pdf", "application/pdf"},
////                {".rar", "application/octet-stream"},
////                {".psd", "application/octet-stream"},
////                {".inf", "application/octet-stream"},
////                {".emz", "application/octet-stream"},
////                {".dsp", "application/octet-stream"},
////                {".onea", "application/onenote"},
////                {".jck", "application/liquidmotion"},
////                {".mpe", "video/mpeg"},
////                {".mp2", "video/mpeg"},
////                {".sct", "text/scriptlet"},
////                {".ras", "image/x-cmu-raster"},
////                {".swf", "application/x-shockwave-flash"},
////                {".flv", "flv-application/octet-stream"},
////                {".xpm", "image/x-xpixmap"},
////                {".ico", "image/x-icon"},
////                {".gif", "image/gif"},
////                {".dwf", "drawing/x-dwf"},
////                {".src", "application/x-wais-source"},
////                {".tr", "application/x-troff"},
////                {".pmr", "application/x-perfmon"},
////                {".pma", "application/x-perfmon"},
////                {".dll", "application/x-msdownload"},
////                {".bcpio", "application/x-bcpio"},
////                {".wmlc", "application/vnd.wap.wmlc"},
////                {".wdb", "application/vnd.ms-works"},
////                {".dotx", "application/vnd.openxmlformats-officedocument.wordprocessingml.template"},
////                {".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
////                {".pot", "application/vnd.ms-powerpoint"},
////                {".xltm", "application/vnd.ms-excel.template.macroEnabled.12"},
////                {".rtf", "application/rtf"},
////                {".prf", "application/pics-rules"},
////                {".snp", "application/octet-stream"},
////                {".cab", "application/octet-stream"},
////                {".avi", "video/x-msvideo"},
////                {".asf", "video/x-ms-asf"},
////                {".dtd", "text/xml"},
////                {".wml", "text/vnd.wap.wml"},
////                {".vbs", "text/vbscript"},
////                {".rtx", "text/richtext"},
////                {".dlm", "text/dlm"},
////                {".xwd", "image/x-xwindowdump"},
////                {".pgm", "image/x-portable-graymap"},
////                {".bmp", "image/bmp"},
////                {".crt", "application/x-x509-ca-cert"},
////                {".ustar", "application/x-ustar"},
////                {".tex", "application/x-tex"},
////                {".sv4cpio", "application/x-sv4cpio"},
////                {".tgz", "application/x-compressed"},
////                {".cdf", "application/x-cdf"},
////                {".vss", "application/vnd.visio"},
////                {".cat", "application/vnd.ms-pki.seccat"},
////                {".thmx", "application/vnd.ms-officetheme"},
////                {".xlsm", "application/vnd.ms-excel.sheet.macroEnabled.12"},
////                {".prx", "application/octet-stream"},
////                {".pcz", "application/octet-stream"},
////                {".onetmp", "application/onenote"},
////                {".acx", "application/internet-property-stream"},
////                {".wsdl", "text/xml"},
////                {".disco", "text/xml"},
////                {".xsd", "text/xml"},
////                {".wbmp", "image/vnd.wap.wbmp"},
////                {".png", "image/png"},
////                {".pnz", "image/png"},
////                {".smd", "audio/x-smd"},
////                {".smz", "audio/x-smd"},
////                {".smx", "audio/x-smd"},
////                {".mmf", "application/x-smaf"}
////        };
////
////
////        for (int i = 0; i < MATCH_ARRAY.length; i++) {
////            for (int j = 0; j < MATCH_ARRAY.length; j++) {
////                if (i != j
////                        &&
////                        MATCH_ARRAY[i][0].equals(MATCH_ARRAY[j][0])
////                        &&
////                        MATCH_ARRAY[i][1].equals(MATCH_ARRAY[j][1])
////                        ) {
////                    System.out.println("{\"" +
////                            MATCH_ARRAY[j][0] +
////                            "\", \"" +
////                            MATCH_ARRAY[j][1] +
////                            "\"}"
////                    );
////                    MATCH_ARRAY[j][0] = "0" + j;
////                    MATCH_ARRAY[j][1] = "1" + j;
////                }
////            }
////        }
////
////
////        for (int i = 0; i < MATCH_ARRAY.length - 1; i++) {
////            for (int j = 0; j < MATCH_ARRAY.length - 1 - i; j++) {
////                sort(MATCH_ARRAY, j, 1);
////            }
////        }
////
////        for (int i = 0; i < MATCH_ARRAY.length; i++) {
////            System.out.print("{\"" +
////                    MATCH_ARRAY[i][0] +
////                    "\", \"" +
////                    MATCH_ARRAY[i][1] +
////                    "\"},\n");
////        }
//
//        String remind = "昵称%02d不能超过%02d个字！";
//        String format = String.format(remind, 300);
//        System.out.println(format);
//
//    }


    //排序
    public static void sort(String[][] MATCH_ARRAY, int position, int index) {
        try {
            char tempChar1 = MATCH_ARRAY[position][0].charAt(index);
            char tempChar2 = MATCH_ARRAY[position + 1][0].charAt(index);
            if (tempChar1 > tempChar2) {        // 相邻元素两两对比
                String temp = MATCH_ARRAY[position][0];   // 元素交换
                MATCH_ARRAY[position][0] = MATCH_ARRAY[position + 1][0];
                MATCH_ARRAY[position + 1][0] = temp;

                String temp1 = MATCH_ARRAY[position][1];
                MATCH_ARRAY[position][1] = MATCH_ARRAY[position + 1][1];
                MATCH_ARRAY[position + 1][1] = temp1;
            } else if (tempChar1 == tempChar2) {
                sort(MATCH_ARRAY, position, index + 1);
            }
        } catch (Exception e) {
            if (MATCH_ARRAY[position][0].length() > MATCH_ARRAY[position + 1][0].length()) {
                String temp = MATCH_ARRAY[position][0];   // 元素交换
                MATCH_ARRAY[position][0] = MATCH_ARRAY[position + 1][0];
                MATCH_ARRAY[position + 1][0] = temp;

                String temp1 = MATCH_ARRAY[position][1];
                MATCH_ARRAY[position][1] = MATCH_ARRAY[position + 1][1];
                MATCH_ARRAY[position + 1][1] = temp1;
            }
        }


    }


}
