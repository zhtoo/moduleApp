package com.zht.common;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;

import androidx.core.content.FileProvider;

import com.zht.common.util.ToastUtil;

import java.io.File;

/**
 * Created by ZhangHaitao on 2018/9/3.
 * 调用系统软件打开指定文件
 */
public class OpenFileBySystem {
    //文件类型与文件后缀名的匹配表
    private static final String[][] MATCH_ARRAY = {
            //{后缀名，    文件类型}
            {".323", "text/h323"},
            {".3gp", "video/3gpp"},
            {".aaf", "application/octet-stream"},
            {".aca", "application/octet-stream"},
            {".accdb", "application/msaccess"},
            {".accdt", "application/msaccess"},
            {".acx", "application/internet-property-stream"},
            {".afm", "application/octet-stream"},
            {".ai", "application/postscript"},
            {".aifc", "audio/aiff"},
            {".aiff", "audio/aiff"},
            {".apk", "application/vnd.android.package-archive"},
            {".asf", "video/x-ms-asf"},
            {".asi", "application/octet-stream"},
            {".asm", "text/plain"},
            {".asr", "video/x-ms-asf"},
            {".asx", "video/x-ms-asf"},
            {".atom", "application/atom+xml"},
            {".au", "audio/basic"},
            {".avi", "video/x-msvideo"},
            {".bcpio", "application/x-bcpio"},
            {".bin", "application/octet-stream"},
            {".bmp", "image/bmp"},
            {".c", "text/plain"},
            {".cab", "application/octet-stream"},
            {".cat", "application/vnd.ms-pki.seccat"},
            {".cdf", "application/x-cdf"},
            {".chm", "application/octet-stream"},
            {".class", "application/octet-stream"},
            {".class", "application/x-java-applet"},
            {".clp", "application/x-msclip"},
            {".cmx", "image/x-cmx"},
            {".cnf", "text/plain"},
            {".cod", "image/cis-cod"},
            {".conf", "text/plain"},
            {".cpio", "application/x-cpio"},
            {".cpp", "text/plain"},
            {".crd", "application/x-mscardfile"},
            {".crl", "application/pkix-crl"},
            {".crt", "application/x-x509-ca-cert"},
            {".csh", "application/x-csh"},
            {".csv", "application/octet-stream"},
            {".cur", "application/octet-stream"},
            {".der", "application/x-x509-ca-cert.pfx"},
            {".dib", "image/bmp"},
            {".dir", "application/x-director"},
            {".disco", "text/xml"},
            {".dll", "application/x-msdownload"},
            {".dlm", "text/dlm"},
            {".doc", "application/msword"},
            {".docm", "application/vnd.ms-word.document.macroEnabled.12"},
            {".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {".dot", "application/msword"},
            {".dotm", "application/vnd.ms-word.template.macroEnabled.12"},
            {".dotx", "application/vnd.openxmlformats-officedocument.wordprocessingml.template"},
            {".dsp", "application/octet-stream"},
            {".dtd", "text/xml"},
            {".dvi", "application/x-dvi"},
            {".dwf", "drawing/x-dwf"},
            {".dwp", "application/octet-stream"},
            {".dxr", "application/x-director"},
            {".emz", "application/octet-stream"},
            {".eot", "application/octet-stream"},
            {".eps", "application/postscript"},
            {".etx", "text/x-setext"},
            {".evy", "application/envoy"},
            {".exe", "application/octet-stream"},
            {".fdf", "application/vnd.fdf"},
            {".fla", "application/octet-stream"},
            {".flr", "x-world/x-vrml"},
            {".flv", "flv-application/octet-stream"},
            {".gif", "image/gif"},
            {".gtar", "application/x-gtar"},
            {".gz", "application/x-gzip"},
            {".h", "text/plain"},
            {".hdf", "application/x-hdf"},
            {".hdml", "text/x-hdml"},
            {".hhk", "application/octet-stream"},
            {".hhp", "application/octet-stream"},
            {".hlp", "application/winhlp"},
            {".hqx", "application/mac-binhex40"},
            {".hta", "application/hta"},
            {".htc", "text/x-component"},
            {".htm", "text/html"},
            {".html", "text/html"},
            {".htt", "text/webviewhtml"},
            {".ico", "image/x-icon"},
            {".ics", "application/octet-stream"},
            {".ief", "image/ief"},
            {".iii", "application/x-iphone"},
            {".inf", "application/octet-stream"},
            {".ins", "application/x-internet-signup"},
            {".isp", "application/x-internet-signup"},
            {".ivf", "video/x-ivf"},
            {".jar", "application/java-archive"},
            {".java", "text/plain"},
            {".java", "application/octet-stream"},
            {".jck", "application/liquidmotion"},
            {".jcz", "application/liquidmotion"},
            {".jfif", "image/pjpeg"},
            {".jpb", "application/octet-stream"},
            {".jpe", "image/jpeg"},
            {".jpeg", "image/jpeg"},
            {".jpg", "image/jpeg"},
            {".js", "application/x-javascript"},
            {".latex", "application/x-latex"},
            {".lit", "application/x-ms-reader"},
            {".log", "text/plain"},
            {".lpk", "application/octet-stream"},
            {".lsf", "video/x-la-asf"},
            {".lsx", "video/x-la-asf"},
            {".lzh", "application/octet-stream"},
            {".m13", "application/x-msmediaview"},
            {".m14", "application/x-msmediaview"},
            {".m1v", "video/mpeg"},
            {".m3u", "audio/x-mpegurl"},
            {".m4a", "audio/mp4a-latm"},
            {".m4b", "audio/mp4a-latm"},
            {".m4p", "audio/mp4a-latm"},
            {".m4u", "video/vnd.mpegurl"},
            {".m4v", "video/x-m4v"},
            {".man", "application/x-troff-man"},
            {".map", "text/plain"},
            {".mdb", "application/x-msaccess"},
            {".mdp", "application/octet-stream"},
            {".me", "application/x-troff-me"},
            {".mht", "message/rfc822"},
            {".mhtml", "message/rfc822"},
            {".mid", "audio/mid"},
            {".midi", "audio/mid"},
            {".mix", "application/octet-stream"},
            {".mmf", "application/x-smaf"},
            {".mno", "text/xml"},
            {".mny", "application/x-msmoney"},
            {".mov", "video/quicktime"},
            {".movie", "video/x-sgi-movie"},
            {".mp2", "audio/x-mpeg"},
            {".mp2", "video/mpeg"},
            {".mp3", "audio/x-mpeg"},
            {".mp4", "video/mp4"},
            {".mpa", "video/mpeg"},
            {".mpc", "application/vnd.mpohun.certificate"},
            {".mpe", "video/mpeg"},
            {".mpeg", "video/mpeg"},
            {".mpg", "video/mpeg"},
            {".mpg4", "video/mp4"},
            {".mpga", "audio/mpeg"},
            {".mpp", "application/vnd.ms-project"},
            {".mpv2", "video/mpeg"},
            {".ms", "application/x-troff-ms"},
            {".msg", "application/vnd.ms-outlook"},
            {".mso", "application/octet-stream"},
            {".mvb", "application/x-msmediaview"},
            {".mvc", "application/x-miva-compiled"},
            {".nc", "application/x-netcdf"},
            {".nsc", "video/x-ms-asf"},
            {".nws", "message/rfc822"},
            {".oda", "application/oda"},
            {".ods", "application/oleobject"},
            {".ogg", "audio/ogg"},
            {".one", "application/onenote"},
            {".onea", "application/onenote"},
            {".onepkg", "application/onenote"},
            {".onetmp", "application/onenote"},
            {".onetoc", "application/onenote"},
            {".p10", "application/pkcs10"},
            {".p12", "application/x-pkcs12"},
            {".p7b", "application/x-pkcs7-certificates"},
            {".p7c", "application/pkcs7-mime"},
            {".p7r", "application/x-pkcs7-certreqresp"},
            {".p7s", "application/pkcs7-signature"},
            {".pbm", "image/x-portable-bitmap"},
            {".pcz", "application/octet-stream"},
            {".pdf", "application/pdf"},
            {".pfb", "application/octet-stream"},
            {".pfm", "application/octet-stream"},
            {".pgm", "image/x-portable-graymap"},
            {".pma", "application/x-perfmon"},
            {".pmc", "application/x-perfmon"},
            {".pml", "application/x-perfmon"},
            {".pmr", "application/x-perfmon"},
            {".pmw", "application/x-perfmon"},
            {".png", "image/png"},
            {".pnm", "image/x-portable-anymap"},
            {".pnz", "image/png"},
            {".pot", "application/vnd.ms-powerpoint"},
            {".potm", "application/vnd.ms-powerpoint.template.macroEnabled.12"},
            {".potx", "application/vnd.openxmlformats-officedocument.presentationml.template"},
            {".ppam", "application/vnd.ms-powerpoint.addin.macroEnabled.12"},
            {".ppm", "image/x-portable-pixmap"},
            {".pps", "application/vnd.ms-powerpoint"},
            {".ppsm", "application/vnd.ms-powerpoint.slideshow.macroEnabled.12"},
            {".ppsx", "application/vnd.openxmlformats-officedocument.presentationml.slideshow"},
            {".ppt", "application/vnd.ms-powerpoint"},
            {".pptm", "application/vnd.ms-powerpoint.presentation.macroEnabled.12"},
            {".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
            {".prf", "application/pics-rules"},
            {".prm", "application/octet-stream"},
            {".prop", "text/plain"},
            {".prx", "application/octet-stream"},
            {".ps", "application/postscript"},
            {".psd", "application/octet-stream"},
            {".psm", "application/octet-stream"},
            {".psp", "application/octet-stream"},
            {".pub", "application/x-mspublisher"},
            {".qt", "video/quicktime"},
            {".qtl", "application/x-quicktimeplayer"},
            {".qxd", "application/octet-stream"},
            {".ra", "audio/x-pn-realaudio"},
            {".ram", "audio/x-pn-realaudio"},
            {".rar", "application/x-rar-compressed"},
            {".rar", "application/octet-stream"},
            {".ras", "image/x-cmu-raster"},
            {".rc", "text/plain"},
            {".rf", "image/vnd.rn-realflash"},
            {".rgb", "image/x-rgb"},
            {".rm", "application/vnd.rn-realmedia"},
            {".rmi", "audio/mid"},
            {".rmvb", "audio/x-pn-realaudio"},
            {".rpm", "audio/x-pn-realaudio-plugin.aif"},
            {".rtf", "application/rtf"},
            {".rtx", "text/richtext"},
            {".sct", "text/scriptlet"},
            {".sea", "application/octet-stream"},
            {".setpay", "application/set-payment-initiation"},
            {".setreg", "application/set-registration-initiation"},
            {".sgml", "text/sgml"},
            {".sh", "text/plain"},
            {".sh", "application/x-sh"},
            {".shar", "application/x-shar"},
            {".sit", "application/x-stuffit"},
            {".sldm", "application/vnd.ms-powerpoint.slide.macroEnabled.12"},
            {".smd", "audio/x-smd"},
            {".smi", "application/octet-stream"},
            {".smx", "audio/x-smd"},
            {".smz", "audio/x-smd"},
            {".snd", "audio/basic"},
            {".snp", "application/octet-stream"},
            {".spc", "application/x-pkcs7-certificates"},
            {".spl", "application/futuresplash"},
            {".src", "application/x-wais-source"},
            {".ssm", "application/streamingmedia .axs"},
            {".stl", "application/vnd.ms-pki.stl"},
            {".sv4cpio", "application/x-sv4cpio"},
            {".sv4crc", "application/x-sv4crc"},
            {".swf", "application/x-shockwave-flash"},
            {".tar", "application/x-tar"},
            {".tcl", "application/x-tcl"},
            {".tex", "application/x-tex"},
            {".texi", "application/x-texinfo"},
            {".texinfo", "application/x-texinfo"},
            {".tgz", "application/x-compressed"},
            {".thmx", "application/vnd.ms-officetheme"},
            {".thn", "application/octet-stream"},
            {".tif", "image/tiff"},
            {".tiff", "image/tiff"},
            {".toc", "application/octet-stream"},
            {".tr", "application/x-troff"},
            {".trm", "application/x-msterminal"},
            {".tsv", "text/tab-separated-values"},
            {".ttf", "application/octet-stream"},
            {".txt", "text/plain"},
            {".uls", "text/iuls"},
            {".ustar", "application/x-ustar"},
            {".vbs", "text/vbscript"},
            {".vcf", "text/x-vcard"},
            {".vdx", "application/vnd.ms-visio.viewer"},
            {".vsd", "application/vnd.visio"},
            {".vss", "application/vnd.visio"},
            {".vst", "application/vnd.visio"},
            {".vsw", "application/vnd.visio"},
            {".vsx", "application/vnd.visio"},
            {".vtx", "application/vnd.visio"},
            {".wav", "audio/x-wav"},
            {".wav", "audio/wav"},
            {".wax", "audio/x-ms-wax"},
            {".wbmp", "image/vnd.wap.wbmp"},
            {".wcm", "application/vnd.ms-works"},
            {".wdb", "application/vnd.ms-works"},
            {".wks", "application/vnd.ms-works"},
            {".wm", "video/x-ms-wm"},
            {".wma", "audio/x-ms-wma"},
            {".wmd", "application/x-ms-wmd"},
            {".wmf", "application/x-msmetafile"},
            {".wml", "text/vnd.wap.wml"},
            {".wmlc", "application/vnd.wap.wmlc"},
            {".wmlsc", "application/vnd.wap.wmlscriptc"},
            {".wmp", "video/x-ms-wmp"},
            {".wmv", "audio/x-ms-wmv"},
            {".wmv", "video/x-ms-wmv"},
            {".wmx", "video/x-ms-wmx"},
            {".wps", "application/vnd.ms-works"},
            {".wri", "application/x-mswrite"},
            {".wrl", "x-world/x-vrml"},
            {".wrz", "x-world/x-vrml"},
            {".wsdl", "text/xml"},
            {".wvx", "video/x-ms-wvx"},
            {".x", "application/directx"},
            {".xaf", "x-world/x-vrml"},
            {".xbm", "image/x-xbitmap"},
            {".xla", "application/vnd.ms-excel"},
            {".xlam", "application/vnd.ms-excel.addin.macroEnabled.12"},
            {".xlc", "application/vnd.ms-excel"},
            {".xlm", "application/vnd.ms-excel"},
            {".xls", "application/vnd.ms-excel"},
            {".xlsb", "application/vnd.ms-excel.sheet.binary.macroEnabled.12"},
            {".xlsm", "application/vnd.ms-excel.sheet.macroEnabled.12"},
            {".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            {".xlt", "application/vnd.ms-excel"},
            {".xltm", "application/vnd.ms-excel.template.macroEnabled.12"},
            {".xltx", "application/vnd.openxmlformats-officedocument.spreadsheetml.template"},
            {".xlw", "application/vnd.ms-excel"},
            {".xml", "text/plain"},
            {".xml", "text/xml"},
            {".xof", "x-world/x-vrml"},
            {".xpm", "image/x-xpixmap"},
            {".xsd", "text/xml"},
            {".xsf", "text/xml"},
            {".xsl", "text/xml"},
            {".xslt", "text/xml"},
            {".xwd", "image/x-xwindowdump"},
            {".z", "application/x-compress"},
            {".zip", "application/zip"},
            {".zip", "application/x-zip-compressed"},
            {"", "*/*"}
    };


    /**
     * 根据路径打开文件
     *
     * @param context 上下文
     * @param path    文件路径
     * @param action  操作类型：
     *                ACTION_SENDTO   发送
     *                ACTION_VIEW     查看
     *                ACTION_PACKAGE_ADDED  安装apk
     *                ACTION_CAMERA_BUTTON  打开照相机
     */
    public static void openFileByPath(Context context, String path, String action) {
        openFileByPath(context, path, action, null);
    }

    public static void openFileByPath(Context context, String path, String action, String authority) {
        if (context == null || path == null) {
            return;
        }
        try {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //设置intent的Action属性
            intent.setAction(action);
            //文件的类型
            String type = "";
            for (String[] suffix : MATCH_ARRAY) {
                //判断文件的格式
                if (path.toLowerCase().contains(suffix[0].toLowerCase())) {
                    type = suffix[1];
                    break;
                }
            }
            Uri fileUri;
            //判断是否是Android N 以及更高的版本
            //设置intent的data和Type属性
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                if (TextUtils.isEmpty(authority)) {
                    authority = context.getPackageName() + ".fileProvider";
                }
                fileUri = FileProvider.getUriForFile(context, authority, new File(path));
                //临时赋予读写Uri的权限
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            } else {
                fileUri = Uri.fromFile(new File(path));
            }
            intent.putExtra(Intent.EXTRA_STREAM, fileUri);
            intent.setType(type);//设置文件类型
            context.startActivity(intent);
        } catch (Exception e) { //当系统没有携带文件打开软件，提示
            ToastUtil.showToast("无法打开该格式文件!");
            e.printStackTrace();
        }
    }

}
