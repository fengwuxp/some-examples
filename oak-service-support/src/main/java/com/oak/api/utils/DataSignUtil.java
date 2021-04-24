package com.oak.api.utils;

import javax.persistence.Column;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 数据签名工具
 *
 * @author wxup
 */
public final class DataSignUtil {

    private DataSignUtil() {
    }



    public static String buildSign(Object entity, String key, String... excluded) throws Exception {

        List<String[]> fieldList = getField(entity, excluded);

        String[][] data = new String[fieldList.size()][2];

        for (int i = 0; i < fieldList.size(); i++) {
            data[i][0] = fieldList.get(i)[0];
            data[i][1] = fieldList.get(i)[1];
        }

        if (key == null || key.isEmpty()) {
            key = entity.getClass().getName();
        }

        return buildSign(data, key);
    }

    public static String buildSign(String[][] data, String key) throws Exception {

        StringBuilder sb = new StringBuilder();

        int i = 0;
        int length = data.length;
        for (String[] row : data) {
            String name = row[0];
            String value = row[1];

            // 拼接时，不包括最后一个&字符
            if (i++ == length - 1) {
                sb.append(name).append("=").append(value);
            } else {
                sb.append(name).append("=").append(value).append("&");
            }
        }

        sb.append(i == 0 ? "" : "&").append(key);

        return md5(sb.toString());
    }

    public static boolean checkSign(Object entity, String key, String sign, String... excluded) throws Exception {
        String s = buildSign(entity, key, excluded);
        return sign != null && sign.equalsIgnoreCase(s);
    }

    public static boolean checkSign(String[][] data, String key, String sign) throws Exception {
        String s = buildSign(data, key);
        return sign != null && sign.equalsIgnoreCase(s);
    }

    private static List<String[/*0-fieldName,1-fieldValue*/]> getField(Object entity, String... excluded) throws Exception {

        List<String[]> fieldList = new ArrayList<>();

        List<String> fieldNames = new ArrayList<>();

        List<String> excludedFiled = new ArrayList<>();
        if (excluded != null) {
            excludedFiled = Arrays.asList(excluded);
        }

        // 获取对象中所有的Field
        Field[] fields = entity.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                if (Boolean.FALSE.equals(column.nullable()) && !excludedFiled.contains(field.getName())) {
                    fieldNames.add(field.getName());
                }
            }
        }

        // 字典排序
        java.util.Collections.sort(fieldNames);

        for (String fieldName : fieldNames) {
            fieldList.add(new String[]{fieldName, getFieldValue(fieldName, entity)});
        }

        return fieldList;
    }

    private static String getFieldValue(String field, Object obj)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        if (field == null || obj == null) {
            return null;
        }

        Class clzss = obj.getClass();

        if (field.startsWith("is") && !field.matches("is\\d+")) {
            field = field.substring(2, field.length());
        }

        String methodName = "get" + field.substring(0, 1).toUpperCase() + field.substring(1, field.length());
        Method method = clzss.getMethod(methodName);
        Object value = method.invoke(obj);

        if (value instanceof Date) {
            return new BigDecimal(((Date) value).getTime())
                    .divide(new BigDecimal(1000), 0, BigDecimal.ROUND_HALF_UP).toString()
                    + "000";
        }

        return value != null ? value.toString() : "";
    }

    private static String md5(String str) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes(StandardCharsets.UTF_8));
            byte s[] = md.digest();

            for (int i = 0; i < s.length; i++) {
                result += Integer.toHexString
                        ((0x000000ff & s[i]) | 0xffffff00).substring(6);
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }

}
