package com.bunary.vocab.util;

import java.text.Normalizer;

public class StringUtil {
    public static String removeVietnameseAccent(String str) {
        if (str == null)
            return null;
        // Chuyển về dạng Unicode chuẩn (NFD)
        String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
        // Loại bỏ các ký tự dấu
        temp = temp.replaceAll("\\p{M}", "");
        // Loại bỏ ký tự không phải chữ số hoặc chữ cái
        temp = temp.replaceAll("[^a-zA-Z0-9]", "");
        return temp;
    }
}
