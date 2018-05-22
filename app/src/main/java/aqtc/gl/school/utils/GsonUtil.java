package aqtc.gl.school.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.List;
/**
 * @author gl
 * @date 2018/5/22
 * @desc Gson工具
 */
public class GsonUtil {
    public static <T> T jsonToBean(String json, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(json, clazz);
    }

    public static <T> List<T> jsonArrayToList(String jsonData,
                                              Class<T> type) {
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonArray array = parser.parse(jsonData).getAsJsonArray();

        ArrayList<T> lcs = new ArrayList<T>();

        for (JsonElement obj : array) {
            T cse = gson.fromJson(obj, type);
            lcs.add(cse);
        }
        return lcs;
    }

    // 将Json数组解析成相应的映射对象列表
    public static <T> List<T> parseJsonArrayToList(String jsonData,
                                                   Class<T> type) {
        ArrayList<T> result = new ArrayList<>();
        if (TextUtils.isEmpty(jsonData)) {
            jsonData = "[]";
        }
        try {
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonArray jArray = parser.parse(jsonData).getAsJsonArray();
            for (JsonElement obj : jArray) {
                T cse = gson.fromJson(obj, type);
                result.add(cse);
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return result;
    }

    //将列表数据转成json
    public static <T> String convertListToJson(List<T> datalist) {
        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(datalist);
        return strJson;
    }
}
