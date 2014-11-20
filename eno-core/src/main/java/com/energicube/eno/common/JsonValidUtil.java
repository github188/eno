package com.energicube.eno.common;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * Json字符串格式校验
 */
public class JsonValidUtil {

    public static boolean isValidJSON(final String json) {
        boolean valid = false;
        try {
            final JsonParser parser = new ObjectMapper().getJsonFactory()
                    .createJsonParser(json);
            while (parser.nextToken() != null) {
            }
            valid = true;
        } catch (JsonParseException jpe) {
            jpe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return valid;
    }


    public static void main(String[] args) {

        String json = "{\"id\":\"1\"}";
        String json1 = "{\"id\":\"1\"}";
        String josn2 = "{\"msgid\":\"%s\",\"msgtype\":\"%s\",\"syscode\":\"%s\",\"timestamp\":\"%s\",\"checktype\":\"\",\"responsetime\":\"%s\"}";
        boolean ret = isValidJSON(json);
        boolean ret1 = isValidJSON(json1);
        boolean ret2 = isValidJSON(josn2);

        System.out.println("json:" + ret);
        System.out.println("json1:" + ret1);
        System.out.println("json2:" + ret2);
    }

}
