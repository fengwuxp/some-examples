package com.oak.member.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.Formatter;

import java.text.MessageFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * 用于进制转换
 * @author wxup
 */
public class HexFormatter implements Formatter<Long> {


//    private static final String SOURCES_TEXT = "Ab0Cd1Ef2Gh3Ij4Kl5Mn6Oq7Qr8St9UvWxYzaBcDeFgHiJkLmNoPqRsTuVwXyZ";
    private static final String SOURCES_TEXT = "H19J0ZLAGUPFWOY7DVB8RNCEX642QMT3SI5K";


    private String sourceText = SOURCES_TEXT;

    private char[] chars = SOURCES_TEXT.toCharArray();

    private int length = 5;

    private int scale = 36;

    /**
     * 偏移量
     */
    private int offset = 95427;

    /**
     * 填充字符
     */
    private String fillCharacter = "0";

    private String fillTextPattern = MessageFormat.format("^{0}*", this.fillCharacter);

    public HexFormatter() {
    }

    public HexFormatter(String sourceText) {
        this.sourceText = sourceText;
        this.chars = sourceText.toCharArray();
    }

    public HexFormatter(String sourceText, int length, int offset) {
        this(sourceText);
        this.length = length;
        this.offset = offset;
    }

    public HexFormatter(String sourceText, int length, int scale, int offset) {
        this(sourceText, length, offset);
        this.length = length;
        this.scale = scale;
    }

    @Override
    public Long parse(String text, Locale locale) throws ParseException {
        if (text.length() > this.length) {
            throw new ParseException("无效的内容:" + text, -1);
        }
        // 清除填充字符
        text = text.replace(this.fillTextPattern, "");
        long value = 0;
        char tempChar;
        int tempCharValue;
        for (int i = 0; i < text.length(); i++) {
            //获取字符
            tempChar = text.charAt(i);
            //单字符值
            tempCharValue = sourceText.indexOf(tempChar);
            //单字符值在进制规则下表示的值
            value += (long) (tempCharValue * Math.pow(this.scale, text.length() - i - 1));
        }
        return value - this.offset;
    }

    @Override
    public String print(Long num, Locale locale) {
        StringBuilder sb = new StringBuilder();
        int remainder;
        num += this.offset;
        int scale = this.scale;
        char[] chars = this.chars;
        while (num > scale - 1) {
            //对 scale 进行求余，然后将余数追加至 sb 中，由于是从末位开始追加的，因此最后需要反转字符串
            remainder = Long.valueOf(num % scale).intValue();
            sb.append(chars[remainder]);
            //除以进制数，获取下一个末尾数
            num = num / scale;
        }
        sb.append(chars[num.intValue()]);
        String value = sb.reverse().toString();
        return StringUtils.leftPad(value, this.length, this.fillCharacter);
    }
}
