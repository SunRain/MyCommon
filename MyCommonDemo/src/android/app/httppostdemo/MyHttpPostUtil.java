package android.app.httppostdemo;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import wd.android.util.util.MyFormatter;

public class MyHttpPostUtil {
    public static int state = StringUtil.STATE_HOMENISS;

    private static final String HTTP_POST_URI_HOMENISS = "http://wap.homeinns.com/cwap/Webs/GetVersion.aspx";
    private static final String HTTP_POST_URI_HUAWEI = "http://10.142.16.105:8010/AMS/Query";

    public static String getUri() {
        if (state == StringUtil.STATE_HOMENISS) {
            return HTTP_POST_URI_HOMENISS;
        } else {
            return HTTP_POST_URI_HUAWEI;
        }
    }

    public static String getRequestStr() {
        if (state == StringUtil.STATE_HOMENISS) {
            String strResult = "<?xml version='1.0' encoding='UTF-8'?><CwapMessage xmlns='http://wap.homeinns.com/cwap'><version>1.0</version><TxtMsgMessage>";
            strResult += "<Lisence>" + 800333 + "</Lisence>";
            strResult += "</TxtMsgMessage></CwapMessage>";
            return strResult;
        } else {
            StringBuilder stringBuffer = new StringBuilder();
            stringBuffer
                    .append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><Client version=\"1.0\" entity=\"agent\"><Command Type=\"Query\">");
            // stringBuffer.append("<IMEI>" + EnvironmentInfo.getIMEI()
            // + "</IMEI>");
            // stringBuffer.append("<IMSI>" + EnvironmentInfo.getIMSI()
            // + "</IMSI>");
            stringBuffer.append("<Version>V100R002C01SPC400T</Version>");
            // <Time>2009-10-09 10:15:55</Time>
            stringBuffer.append("<Time>"
                    + MyFormatter.formatDate(System.currentTimeMillis())
                    + "</Time>");
            stringBuffer.append("</Command></Client>");
            return stringBuffer.toString();
        }
    }

    public static String getResponseStr(String uri, String requestStr) {
        try {
            /** 一.创建HTTP连接（POST或GET） */
            HttpPost httpPost = new HttpPost(uri);
            /** 二.与服务器交互：发送、接收HTTP请求 */
            // Make sure the server knows what kind of a response we will accept
            httpPost.addHeader("Accept", "text/xml");
            // Also be sure to tell the server what kind of content we are
            // sending
            httpPost.addHeader("Content-Type", "application/xml");

            StringEntity entity = new StringEntity(requestStr, "UTF-8");
            // entity.setContentType("application/xml");
            httpPost.setEntity(entity);
            HttpClient httpClient = new DefaultHttpClient();

            // 请求超时
            httpClient.getParams().setParameter(
                    CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);
            // 读取超时
            httpClient.getParams().setParameter(
                    CoreConnectionPNames.SO_TIMEOUT, 20000);
            HttpResponse response = httpClient.execute(httpPost);

			/* 若状态码为200 ok */
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /* 取出应答字符串 */
                return new String(EntityUtils.toString(response.getEntity())
                        .getBytes("ISO-8859-1"), "UTF-8");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
