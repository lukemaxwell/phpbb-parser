package xyz.codepunk.phpbbparser.common;

import java.util.HashMap;

public class BaseParser {

    /**
     * Return hashmap of URL parameters.
     * @param urlStr the URL
     * @return HashMap containing parameters and their values
     */
    public static HashMap<String, String> parseUrlParams(String urlStr) {
        final String queryStr = urlStr.split("\\?")[1];
        final String[] paramsList = queryStr.split("\\&");
        final HashMap<String, String> params = new HashMap<String, String>();
        for(String paramString : paramsList) {
            final String[] tokens = paramString.split("=");
            final String key = tokens[0];
            String val = "";
            for(int i=1; i < tokens.length; i++) {
                val += tokens[i];
            }
            params.put(key, val);
        }
        return params;
    }

    /**
     * Return URL parameter value for `key`
     * @param urlStr the URL
     * @param key the parameter to return
     * @return the parameter value as a String
     */
    public static String parseUrlParam(String urlStr, String key) {
        final HashMap<String, String> params = parseUrlParams(urlStr);
        return params.get(key);
    }
}
