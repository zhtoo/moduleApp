package com.zht.common.util;

import android.util.Log;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * @Date 2023/3/21 18:04
 * @Author zhanghaitao
 * @Description
 */
public class FileDownloader {

    private static final String TAG ="FileDownloader";

    public interface Callback {

        void onSuccess(File file);

        void onFailed(int code, String message);

        default void onProgress(long writeLength, long totalLength) {

        }
    }

    public static void downloadFile(String downloadUrl, String saveFilePath, Callback callback) {
        HttpURLConnection urlConn = null;
        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        try {
            URL url = new URL(downloadUrl);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setConnectTimeout(5 * 1000);
            urlConn.setReadTimeout(5 * 1000);
            urlConn.addRequestProperty("Connection", "Keep-Alive");
            urlConn.connect();
            int responseCode = urlConn.getResponseCode();
            String contentType = urlConn.getHeaderField("Content-Type");
            Log.e(TAG,contentType);
            String suffix = mContentTypes.get(contentType);
            if (suffix == null) {
                suffix = "";
            }
            Log.e(TAG,suffix);
            if (responseCode == 200) {
                File descFile = new File(saveFilePath + suffix);
                fileOutputStream = new FileOutputStream(descFile);
                byte[] buffer = new byte[8 * 1024];
                int len;
                long writeLength = 0;
                long totalLength = urlConn.getContentLength();
                inputStream = urlConn.getInputStream();
                while ((len = inputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, len);
                    writeLength += len;
                    if (callback != null) {
                        callback.onProgress(writeLength, totalLength);
                    }
                }
                if (callback != null) {
                    callback.onSuccess(descFile);
                }
            } else {
                //文件下载失败
                if (callback != null) {
                    callback.onFailed(responseCode, "下载失败");
                }
            }
        } catch (Exception e) {
            if (callback != null) {
                callback.onFailed(0, e.getMessage());
            }
            e.printStackTrace();
        } finally {
            if (urlConn != null) {
                urlConn.disconnect();
            }
            safeClose(fileOutputStream);
            safeClose(inputStream);
        }
    }

    private static void safeClose(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {

            }
        }
    }

    public static final HashMap<String, String> mContentTypes = new HashMap<String, String>();

    static {
        mContentTypes.put("application/x-001", ".001");
        mContentTypes.put("application/x-301", ".301");
        mContentTypes.put("text/h323", ".323");
        mContentTypes.put("application/x-906", ".906");
        mContentTypes.put("drawing/907", ".907");
        mContentTypes.put("application/x-a11", ".a11");
        mContentTypes.put("audio/x-mei-aac", ".acp");
        mContentTypes.put("application/postscript", ".ai");
//        mContentTypes.put("audio/aiff", ".aif");
//        mContentTypes.put("audio/aiff", ".aifc");
        mContentTypes.put("audio/aiff", ".aiff");
        mContentTypes.put("application/x-anv", ".anv");
        mContentTypes.put("text/asa", ".asa");
        mContentTypes.put("video/x-ms-asf", ".asf");
        mContentTypes.put("text/asp", ".asp");
//        mContentTypes.put("video/x-ms-asf", ".asx");
        mContentTypes.put("audio/basic", ".au");
        mContentTypes.put("video/avi", ".avi");
        mContentTypes.put("application/vnd.adobe.workflow", ".awf");
//        mContentTypes.put("text/xml", ".biz");
        mContentTypes.put("application/x-bmp", ".bmp");
        mContentTypes.put("application/x-bot", ".bot");
        mContentTypes.put("application/x-c4t", ".c4t");
        mContentTypes.put("application/x-c90", ".c90");
        mContentTypes.put("application/x-cals", ".cal");
        mContentTypes.put("application/s-pki.seccat", ".cat");
        mContentTypes.put("application/x-netcdf", ".cdf");
        mContentTypes.put("application/x-cdr", ".cdr");
        mContentTypes.put("application/x-cel", ".cel");
//        mContentTypes.put("application/x-x509-ca-cert", ".cer");
//        mContentTypes.put("application/x-g4", ".cg4");
        mContentTypes.put("application/x-cgm", ".cgm");
        mContentTypes.put("application/x-cit", ".cit");
//        mContentTypes.put("java/*", ".class");
//        mContentTypes.put("text/xml", ".cml");
        mContentTypes.put("application/x-cmp", ".cmp");
        mContentTypes.put("application/x-cmx", ".cmx");
        mContentTypes.put("application/x-cot", ".cot");
        mContentTypes.put("application/pkix-crl", ".crl");
//        mContentTypes.put("application/x-x509-ca-cert", ".crt");
        mContentTypes.put("application/x-csi", ".csi");
        mContentTypes.put("text/css", ".css");
        mContentTypes.put("application/x-cut", ".cut");
        mContentTypes.put("application/x-dbf", ".dbf");
        mContentTypes.put("application/x-dbm", ".dbm");
        mContentTypes.put("application/x-dbx", ".dbx");
//        mContentTypes.put("text/xml", ".dcd");
        mContentTypes.put("application/x-dcx", ".dcx");
//        mContentTypes.put("application/x-x509-ca-cert", ".der");
        mContentTypes.put("application/x-dgn", ".dgn");
        mContentTypes.put("application/x-dib", ".dib");
//        mContentTypes.put("application/x-msdownload", ".dll");
        mContentTypes.put("application/msword", ".doc");
//        mContentTypes.put("application/msword", ".dot");
        mContentTypes.put("application/x-drw", ".drw");
//        mContentTypes.put("text/xml", ".dtd");
        mContentTypes.put("Model/vnd.dwf", ".dwf");
        mContentTypes.put("application/x-dwf", ".dwf");
        mContentTypes.put("application/x-dwg", ".dwg");
        mContentTypes.put("application/x-dxb", ".dxb");
        mContentTypes.put("application/x-dxf", ".dxf");
        mContentTypes.put("application/vnd.adobe.edn", ".edn");
        mContentTypes.put("application/x-emf", ".emf");
//        mContentTypes.put("message/rfc822", ".eml");
//        mContentTypes.put("text/xml", ".ent");
        mContentTypes.put("application/x-epi", ".epi");
//        mContentTypes.put("application/x-ps", ".eps");
//        mContentTypes.put("application/postscript", ".eps");
        mContentTypes.put("application/x-ebx", ".etd");
        mContentTypes.put("application/x-msdownload", ".exe");
        mContentTypes.put("image/fax", ".fax");
        mContentTypes.put("application/vnd.fdf", ".fdf");
        mContentTypes.put("application/fractals", ".fif");
//        mContentTypes.put("text/xml", ".fo");
        mContentTypes.put("application/x-frm", ".frm");
//        mContentTypes.put("application/x-g4", ".g4");
        mContentTypes.put("application/x-gbr", ".gbr");
        mContentTypes.put("application/x-gcd", ".gcd");
        mContentTypes.put("image/gif", ".gif");
        mContentTypes.put("application/x-gl2", ".gl2");
        mContentTypes.put("application/x-gp4", ".gp4");
        mContentTypes.put("application/x-hgl", ".hgl");
        mContentTypes.put("application/x-hmr", ".hmr");
        mContentTypes.put("application/x-hpgl", ".hpg");
        mContentTypes.put("application/x-hpl", ".hpl");
        mContentTypes.put("application/mac-binhex40", ".hqx");
        mContentTypes.put("application/x-hrf", ".hrf");
        mContentTypes.put("application/hta", ".hta");
        mContentTypes.put("text/x-component", ".htc");
//        mContentTypes.put("text/html", ".htm");
        mContentTypes.put("text/html", ".html");
        mContentTypes.put("text/webviewhtml", ".htt");
//        mContentTypes.put("text/html", ".htx");
        mContentTypes.put("application/x-icb", ".icb");
        mContentTypes.put("image/x-icon", ".ico");
        mContentTypes.put("application/x-ico", ".ico");
        mContentTypes.put("application/x-iff", ".iff");
//        mContentTypes.put("application/x-g4", ".ig4");
        mContentTypes.put("application/x-igs", ".igs");
        mContentTypes.put("application/x-iphone", ".iii");
        mContentTypes.put("application/x-img", ".img");
//        mContentTypes.put("application/x-internet-signup", ".ins");
//        mContentTypes.put("application/x-internet-signup", ".isp");
        mContentTypes.put("video/x-ivf", ".IVF");
//        mContentTypes.put("java/*", ".java");
//        mContentTypes.put("image/jpeg", ".jfif");
//        mContentTypes.put("image/jpeg", ".jpe");
        mContentTypes.put("application/x-jpe", ".jpe");
        mContentTypes.put("image/jpeg", ".jpeg");
//        mContentTypes.put("image/jpeg", ".jpg");
        mContentTypes.put("application/x-jpg", ".jpg");
        mContentTypes.put("application/x-javascript", ".js");
//        mContentTypes.put("text/html", ".jsp");
        mContentTypes.put("audio/x-liquid-file", ".la1");
        mContentTypes.put("application/x-laplayer-reg", ".lar");
        mContentTypes.put("application/x-latex", ".latex");
        mContentTypes.put("audio/x-liquid-secure", ".lavs");
        mContentTypes.put("application/x-lbm", ".lbm");
        mContentTypes.put("audio/x-la-lms", ".lmsff");
//        mContentTypes.put("application/x-javascript", ".ls");
        mContentTypes.put("application/x-ltr", ".ltr");
//        mContentTypes.put("video/x-mpeg", ".m1v");
//        mContentTypes.put("video/x-mpeg", ".m2v");
        mContentTypes.put("audio/mpegurl", ".m3u");
        mContentTypes.put("video/mpeg4", ".m4e");
        mContentTypes.put("application/x-mac", ".mac");
        mContentTypes.put("application/x-troff-man", ".man");
//        mContentTypes.put("text/xml", ".math");
        mContentTypes.put("application/msaccess", ".mdb");
        mContentTypes.put("application/x-mdb", ".mdb");
//        mContentTypes.put("application/x-shockwave-flash", ".mfp");
//        mContentTypes.put("message/rfc822", ".mht");
//        mContentTypes.put("message/rfc822", ".mhtml");
        mContentTypes.put("application/x-mi", ".mi");
        mContentTypes.put("audio/mid", ".mid");
//        mContentTypes.put("audio/mid", ".midi");
        mContentTypes.put("application/x-mil", ".mil");
//        mContentTypes.put("text/xml", ".mml");
        mContentTypes.put("audio/x-musicnet-download", ".mnd");
        mContentTypes.put("audio/x-musicnet-stream", ".mns");
//        mContentTypes.put("application/x-javascript", ".mocha");
        mContentTypes.put("video/x-sgi-movie", ".movie");
        mContentTypes.put("audio/mp1", ".mp1");
        mContentTypes.put("audio/mp2", ".mp2");
//        mContentTypes.put("video/mpeg", ".mp2v");
        mContentTypes.put("audio/mp3", ".mp3");
        mContentTypes.put("video/mp4", ".mp4");
        mContentTypes.put("video/x-mpg", ".mpa");
        mContentTypes.put("application/-project", ".mpd");
        mContentTypes.put("video/x-mpeg", ".mpe");
        mContentTypes.put("video/mpg", ".mpeg");
//        mContentTypes.put("video/mpg", ".mpg");
        mContentTypes.put("audio/rn-mpeg", ".mpga");
//        mContentTypes.put("application/-project", ".mpp");
//        mContentTypes.put("video/x-mpeg", ".mps");
//        mContentTypes.put("application/-project", ".mpt");
//        mContentTypes.put("video/mpg", ".mpv");
        mContentTypes.put("video/mpeg", ".mpv2");
        mContentTypes.put("application/s-project", ".mpw");
//        mContentTypes.put("application/-project", ".mpx");
//        mContentTypes.put("text/xml", ".mtx");
        mContentTypes.put("application/x-mmxp", ".mxp");
        mContentTypes.put("image/pnetvue", ".net");
        mContentTypes.put("application/x-nrf", ".nrf");
//        mContentTypes.put("message/rfc822", ".nws");
        mContentTypes.put("text/x-ms-odc", ".odc");
        mContentTypes.put("application/x-out", ".out");
        mContentTypes.put("application/pkcs10", ".p10");
//        mContentTypes.put("application/x-pkcs12", ".p12");
//        mContentTypes.put("application/x-pkcs7-certificates", ".p7b");
//        mContentTypes.put("application/pkcs7-mime", ".p7c");
        mContentTypes.put("application/pkcs7-mime", ".p7m");
        mContentTypes.put("application/x-pkcs7-certreqresp", ".p7r");
        mContentTypes.put("application/pkcs7-signature", ".p7s");
        mContentTypes.put("application/x-pc5", ".pc5");
        mContentTypes.put("application/x-pci", ".pci");
        mContentTypes.put("application/x-pcl", ".pcl");
        mContentTypes.put("application/x-pcx", ".pcx");
        mContentTypes.put("application/pdf", ".pdf");
        mContentTypes.put("application/vnd.adobe.pdx", ".pdx");
        mContentTypes.put("application/x-pkcs12", ".pfx");
        mContentTypes.put("application/x-pgl", ".pgl");
        mContentTypes.put("application/x-pic", ".pic");
        mContentTypes.put("application-pki.pko", ".pko");
        mContentTypes.put("application/x-perl", ".pl");
//        mContentTypes.put("text/html", ".plg");
//        mContentTypes.put("audio/scpls", ".pls");
        mContentTypes.put("application/x-plt", ".plt");
        mContentTypes.put("image/png", ".png");
        mContentTypes.put("application/x-png", ".png");
//        mContentTypes.put("applications-powerpoint", ".pot");
        mContentTypes.put("application/vs-powerpoint", ".ppa");
        mContentTypes.put("application/x-ppm", ".ppm");
        mContentTypes.put("application-powerpoint", ".pps");
        mContentTypes.put("applications-powerpoint", ".ppt");
        mContentTypes.put("application/x-ppt", ".ppt");
        mContentTypes.put("application/x-pr", ".pr");
        mContentTypes.put("application/pics-rules", ".prf");
        mContentTypes.put("application/x-prn", ".prn");
        mContentTypes.put("application/x-prt", ".prt");
//        mContentTypes.put("application/x-ps", ".ps");
//        mContentTypes.put("application/postscript", ".ps");
        mContentTypes.put("application/x-ptn", ".ptn");
        mContentTypes.put("application/powerpoint", ".pwz");
        mContentTypes.put("text/vnd.rn-realtext3d", ".r3t");
        mContentTypes.put("audio/vnd.rn-realaudio", ".ra");
        mContentTypes.put("audio/x-pn-realaudio", ".ram");
        mContentTypes.put("application/x-ras", ".ras");
        mContentTypes.put("application/rat-file", ".rat");
//        mContentTypes.put("text/xml", ".rdf");
        mContentTypes.put("application/vnd.rn-recording", ".rec");
        mContentTypes.put("application/x-red", ".red");
        mContentTypes.put("application/x-rgb", ".rgb");
        mContentTypes.put("application/vnd.rn-realsystem-rjs", ".rjs");
        mContentTypes.put("application/vnd.rn-realsystem-rjt", ".rjt");
        mContentTypes.put("application/x-rlc", ".rlc");
        mContentTypes.put("application/x-rle", ".rle");
        mContentTypes.put("application/vnd.rn-realmedia", ".rm");
        mContentTypes.put("application/vnd.adobe.rmf", ".rmf");
//        mContentTypes.put("audio/mid", ".rmi");
        mContentTypes.put("application/vnd.rn-realsystem-rmj", ".rmj");
//        mContentTypes.put("audio/x-pn-realaudio", ".rmm");
        mContentTypes.put("application/vnd.rn-rn_music_package", ".rmp");
        mContentTypes.put("application/vnd.rn-realmedia-secure", ".rms");
        mContentTypes.put("application/vnd.rn-realmedia-vbr", ".rmvb");
        mContentTypes.put("application/vnd.rn-realsystem-rmx", ".rmx");
        mContentTypes.put("application/vnd.rn-realplayer", ".rnx");
        mContentTypes.put("image/vnd.rn-realpix", ".rp");
        mContentTypes.put("audio/x-pn-realaudio-plugin", ".rpm");
        mContentTypes.put("application/vnd.rn-rsml", ".rsml");
        mContentTypes.put("text/vnd.rn-realtext", ".rt");
//        mContentTypes.put("application/msword", ".rtf");
        mContentTypes.put("application/x-rtf", ".rtf");
        mContentTypes.put("video/vnd.rn-realvideo", ".rv");
        mContentTypes.put("application/x-sam", ".sam");
        mContentTypes.put("application/x-sat", ".sat");
        mContentTypes.put("application/sdp", ".sdp");
        mContentTypes.put("application/x-sdw", ".sdw");
        mContentTypes.put("application/x-stuffit", ".sit");
        mContentTypes.put("application/x-slb", ".slb");
        mContentTypes.put("application/x-sld", ".sld");
        mContentTypes.put("drawing/x-slk", ".slk");
//        mContentTypes.put("application/smil", ".smi");
        mContentTypes.put("application/smil", ".smil");
        mContentTypes.put("application/x-smk", ".smk");
//        mContentTypes.put("audio/basic", ".snd");
//        mContentTypes.put("text/plain", ".sol");
//        mContentTypes.put("text/plain", ".sor");
        mContentTypes.put("application/x-pkcs7-certificates", ".spc");
        mContentTypes.put("application/futuresplash", ".spl");
//        mContentTypes.put("text/xml", ".spp");
        mContentTypes.put("application/streamingmedia", ".ssm");
        mContentTypes.put("application-pki.certstore", ".sst");
        mContentTypes.put("application/-pki.stl", ".stl");
//        mContentTypes.put("text/html", ".stm");
        mContentTypes.put("application/x-sty", ".sty");
//        mContentTypes.put("text/xml", ".svg");
//        mContentTypes.put("application/x-shockwave-flash", ".swf");
        mContentTypes.put("application/x-tdf", ".tdf");
        mContentTypes.put("application/x-tg4", ".tg4");
        mContentTypes.put("application/x-tga", ".tga");
//        mContentTypes.put("image/tiff", ".tif");
        mContentTypes.put("application/x-tif", ".tif");
        mContentTypes.put("image/tiff", ".tiff");
//        mContentTypes.put("text/xml", ".tld");
        mContentTypes.put("drawing/x-top", ".top");
        mContentTypes.put("application/x-bittorrent", ".torrent");
//        mContentTypes.put("text/xml", ".tsd");
        mContentTypes.put("text/plain", ".txt");
        mContentTypes.put("application/x-icq", ".uin");
        mContentTypes.put("text/iuls", ".uls");
        mContentTypes.put("text/x-vcard", ".vcf");
        mContentTypes.put("application/x-vda", ".vda");
//        mContentTypes.put("application/vnd.visio", ".vdx");
//        mContentTypes.put("text/xml", ".vml");
        mContentTypes.put("application/x-vpeg005", ".vpg");
        mContentTypes.put("application/vnd.visio", ".vsd");
        mContentTypes.put("application/x-vsd", ".vsd");
//        mContentTypes.put("application/vnd.visio", ".vss");
//        mContentTypes.put("application/vnd.visio", ".vst");
        mContentTypes.put("application/x-vst", ".vst");
//        mContentTypes.put("application/vnd.visio", ".vsw");
//        mContentTypes.put("application/vnd.visio", ".vsx");
//        mContentTypes.put("application/vnd.visio", ".vtx");
//        mContentTypes.put("text/xml", ".vxml");
        mContentTypes.put("audio/wav", ".wav");
        mContentTypes.put("audio/x-ms-wax", ".wax");
        mContentTypes.put("application/x-wb1", ".wb1");
        mContentTypes.put("application/x-wb2", ".wb2");
        mContentTypes.put("application/x-wb3", ".wb3");
        mContentTypes.put("image/vnd.wap.wbmp", ".wbmp");
//        mContentTypes.put("application/msword", ".wiz");
        mContentTypes.put("application/x-wk3", ".wk3");
        mContentTypes.put("application/x-wk4", ".wk4");
        mContentTypes.put("application/x-wkq", ".wkq");
        mContentTypes.put("application/x-wks", ".wks");
        mContentTypes.put("video/x-ms-wm", ".wm");
        mContentTypes.put("audio/x-ms-wma", ".wma");
        mContentTypes.put("application/x-ms-wmd", ".wmd");
        mContentTypes.put("application/x-wmf", ".wmf");
        mContentTypes.put("text/vnd.wap.wml", ".wml");
        mContentTypes.put("video/x-ms-wmv", ".wmv");
        mContentTypes.put("video/x-ms-wmx", ".wmx");
        mContentTypes.put("application/x-ms-wmz", ".wmz");
        mContentTypes.put("application/x-wp6", ".wp6");
        mContentTypes.put("application/x-wpd", ".wpd");
        mContentTypes.put("application/x-wpg", ".wpg");
        mContentTypes.put("application/-wpl", ".wpl");
        mContentTypes.put("application/x-wq1", ".wq1");
        mContentTypes.put("application/x-wr1", ".wr1");
        mContentTypes.put("application/x-wri", ".wri");
        mContentTypes.put("application/x-wrk", ".wrk");
        mContentTypes.put("application/x-ws", ".ws");
//        mContentTypes.put("application/x-ws", ".ws2");
        mContentTypes.put("text/scriptlet", ".wsc");
//        mContentTypes.put("text/xml", ".wsdl");
        mContentTypes.put("video/x-ms-wvx", ".wvx");
        mContentTypes.put("application/vnd.adobe.xdp", ".xdp");
//        mContentTypes.put("text/xml", ".xdr");
        mContentTypes.put("application/vnd.adobe.xfd", ".xfd");
        mContentTypes.put("application/vnd.adobe.xfdf", ".xfdf");
//        mContentTypes.put("text/html", ".xhtml");
        mContentTypes.put("application/-excel", ".xls");
        mContentTypes.put("application/x-xls", ".xls");
        mContentTypes.put("application/x-xlw", ".xlw");
        mContentTypes.put("text/xml", ".xml");
        mContentTypes.put("audio/scpls", ".xpl");
//        mContentTypes.put("text/xml", ".xq");
//        mContentTypes.put("text/xml", ".xql");
//        mContentTypes.put("text/xml", ".xquery");
//        mContentTypes.put("text/xml", ".xsd");
//        mContentTypes.put("text/xml", ".xsl");
//        mContentTypes.put("text/xml", ".xslt");
        mContentTypes.put("application/x-xwd", ".xwd");
        mContentTypes.put("application/x-x_b", ".x_b");
        mContentTypes.put("application/x-x_t", ".x_t");
    }
}
