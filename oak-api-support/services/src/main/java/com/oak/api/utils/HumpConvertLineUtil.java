package com.oak.api.utils;

/**
 * @Classname HumpConvert
 * @Description 驼峰字符串转换
 * @Date 2020/5/16 10:16
 * @Created by 44487
 */
public class HumpConvertLineUtil {

    //转变的依赖字符
    public static final char UNDERLINE = '_';

    /**
     * 将驼峰转换成"_"(userId:USER_ID)
     *
     * @param param
     * @return
     */
    public static String camelToUnderline(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 将"_"转成驼峰(user_id:userId)
     *
     * @param param
     * @return
     */
    public static String underlineToCamel(String param) {
        param = param.toLowerCase();
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == UNDERLINE) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(camelToUnderline("userNameAll"));
        System.out.println(underlineToCamel("USER_NAME_ALL"));
    }
}
