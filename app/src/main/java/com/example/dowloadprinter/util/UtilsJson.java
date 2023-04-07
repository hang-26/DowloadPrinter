package com.example.dowloadprinter.util;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UtilsJson {
    public static String getJsonObjectByKey(String strJs, String key) {
        String value = null;

        if (strJs != null && key != null) {
            int indexKey = strJs.indexOf(key);
            int indexCurlyOpen = -1;
            int indexCurlyClose = -1;

            if (indexKey >= 0) {
                char[] arrStrJs = strJs.toCharArray();

                /** find indexCurlyOpen*/
                for (int i = indexKey; i < arrStrJs.length; i++) {
                    if (arrStrJs[i] == '{') {
                        indexCurlyOpen = i;
                        break;
                    }
                }
                /** find indexCurlyOpen*/
                int countCurlyOpen = 1;
                for (int i = indexCurlyOpen + 1; i < arrStrJs.length; i++) {
                    if (arrStrJs[i] == '{') {
                        countCurlyOpen++;
                    } else if (arrStrJs[i] == '}') {
                        countCurlyOpen--;
                    }
                    if (countCurlyOpen == 0) {
                        indexCurlyClose = i;
                        break;
                    }
                }
            }

            if (indexCurlyOpen >= 0 && indexCurlyClose >= 0
                    && indexCurlyClose > indexCurlyOpen) {
                value = strJs.substring(indexCurlyOpen, indexCurlyClose + 1);
            }
        }
        return value;
    }

    public static String getJsonArrayByKey(String strJs, String key) {
        String value = "[]";
        if (strJs != null && key != null) {

            int indexKey = strJs.indexOf(key);
            int indexCurlyOpen = -1;
            int indexCurlyClose = -1;

            if (indexKey >= 0) {
                char[] arrStrJs = strJs.toCharArray();

                /** find indexCurlyOpen*/
                for (int i = indexKey; i < arrStrJs.length; i++) {
                    if (arrStrJs[i] == '[') {
                        indexCurlyOpen = i;
                        break;
                    }
                }
                /** find indexCurlyOpen*/
                int countCurlyOpen = 1;
                for (int i = indexCurlyOpen + 1; i < arrStrJs.length; i++) {
                    if (arrStrJs[i] == '[') {
                        countCurlyOpen++;
                    } else if (arrStrJs[i] == ']') {
                        countCurlyOpen--;
                    }
                    if (countCurlyOpen == 0) {
                        indexCurlyClose = i;
                        break;
                    }
                }
            }

            if (indexCurlyOpen >= 0 && indexCurlyClose >= 0
                    && indexCurlyClose > indexCurlyOpen) {
                value = strJs.substring(indexCurlyOpen, indexCurlyClose + 1);
            }
        }
        return value;
    }

    public static String getStringInJsonByKey(String strJs, String key) {
        String value = "";
        if (strJs != null && key != null) {

            int indexKey = strJs.indexOf(key);
            int indexCurlyOpen = -1;
            int indexCurlyClose = -1;

            if (indexKey >= 0) {
                char[] arrStrJs = strJs.toCharArray();

                /** find indexCurlyOpen
                 *
                 *   "key":"..."
                 *   -----
                 *    key.length()  to remove '"' in "key"
                 * */
                for (int i = indexKey + key.length() + 2; i < arrStrJs.length; i++) {
                    if (arrStrJs[i] == '"') {
                        indexCurlyOpen = i;
                        break;
                    }
                }
                /** find indexCurlyOpen*/
                for (int i = indexCurlyOpen + 1; i < arrStrJs.length; i++) {
                    if (arrStrJs[i] == '"') {
                        indexCurlyClose = i;
                        break;
                    }
                }
            }

            if (indexCurlyOpen >= 0 && indexCurlyClose >= 0
                    && indexCurlyClose > indexCurlyOpen) {
                value = strJs.substring(indexCurlyOpen + 1, indexCurlyClose);
            }
        }
        return value;
    }

    public static int getIntInJsonByKey(String strJs, String key) {
        int value = -1;
        if (strJs != null && key != null) {

            int indexKey = strJs.indexOf(key);
            int indexCurlyOpen = -1;
            int indexCurlyClose = -1;

            if (indexKey >= 0) {
                char[] arrStrJs = strJs.toCharArray();

                /** find indexCurly :*/
                for (int i = indexKey; i < arrStrJs.length; i++) {
                    if (arrStrJs[i] == ':') {
                        indexCurlyOpen = i;
                        break;
                    }
                }
                /** find indexCurlyOpen*/
                for (int i = indexCurlyOpen + 1; i < arrStrJs.length; i++) {
                    if (arrStrJs[i] == ',') {
                        indexCurlyClose = i;
                        break;
                    }
                }
            }

            if (indexCurlyOpen >= 0 && indexCurlyClose >= 0
                    && indexCurlyClose > indexCurlyOpen) {
                String strValue = strJs.substring(indexCurlyOpen + 1, indexCurlyClose).trim();
                try {
                    value = Integer.valueOf(strValue);
                } catch (Exception e) {

                }
            }
        }

        return value;

    }
    public static String  KEY_OF_OBJECT = "key_of_json";

    public static List<String> getListJsonObjectChild(String strJs) {
        if (strJs == null) {
            strJs = "{}";
        }
        List<String> arrList = new ArrayList<>();
        try {
            JSONObject jsonParent = new JSONObject(strJs);
            jsonParent.keys().forEachRemaining((it)->{
                try {
                    arrList.add(jsonParent.optJSONObject(it).putOpt(KEY_OF_OBJECT,it).toString());
                } catch (JSONException e) {

                }
            });
        } catch (JSONException e) {

        }

        return arrList;
    }
}
