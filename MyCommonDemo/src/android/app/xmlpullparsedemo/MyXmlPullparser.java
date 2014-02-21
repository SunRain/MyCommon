package android.app.xmlpullparsedemo;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class MyXmlPullparser {
    static final String KEY_RESULT = "Result";
    static final String KEY_RESPONSE = "Response";
    static final String RESPONSE_TYPE = "Type";
    static final String RESPONSE_TYPE_VALUE = "Query";

    static String getRequestStr() {
        StringBuilder stringBuffer = new StringBuilder();
        stringBuffer
                .append("<?xml version='1.0' encoding=\"UTF-8\"?><Client version=\"1.0\" entity=\"agent\"><Command Type=\"Query\"/>");
        stringBuffer.append("<IMEI></IMEI>");
        stringBuffer.append("<IMSI></IMSI>");
        stringBuffer.append("<Version></Version>");
        // <Time>2009-10-09 10:15:55</Time>
        stringBuffer.append("<Time></Time>");
        stringBuffer.append("</Client>");
        return stringBuffer.toString();
    }

    static Map<String, String> parse(String str) throws Exception {
        Map<String, String> responseValue = new HashMap<String, String>();
        /**
         * android给我们提供了xml 用来得到xmlpull解析器
         */
        XmlPullParser xmlPullParser = Xml.newPullParser();
        /**
         * 将输入流传入 设定编码方式
         */
        // xmlpull.setInput(inputStream, "utf-8");
        xmlPullParser.setInput(new StringReader(str));
        /**
         * pull读到xml后 返回数字 读取到xml的声明返回数字0 START_DOCUMENT; 读取到xml的结束返回数字1
         * END_DOCUMENT ; 读取到xml的开始标签返回数字2 START_TAG 读取到xml的结束标签返回数字3 END_TAG
         * 读取到xml的文本返回数字4 TEXT
         */
        int eventCode = xmlPullParser.getEventType();
        /**
         * 只要这个事件返回的不是1 我们就一直读取xml文件
         */
        while (eventCode != XmlPullParser.END_DOCUMENT) {
            if (eventCode == XmlPullParser.START_TAG) {
                process(responseValue, xmlPullParser);
            }
            eventCode = xmlPullParser.next();// 没有结束xml文件就推到下个进行解析
        }
        return responseValue;
    }

    private static void process(Map<String, String> responseValue,
                                XmlPullParser xmlPullParser) throws XmlPullParserException,
            IOException {
        if (KEY_RESPONSE.equals(xmlPullParser.getName())) {
            String type_value = xmlPullParser.getAttributeValue(
                    xmlPullParser.getNamespace(), RESPONSE_TYPE);
            responseValue.put(KEY_RESPONSE, type_value);
        } else if (KEY_RESULT.equals(xmlPullParser.getName())) {
            responseValue.put(KEY_RESULT, xmlPullParser.nextText());
        }
    }
}
