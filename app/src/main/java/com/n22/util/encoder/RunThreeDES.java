package com.n22.util.encoder;

public class RunThreeDES {

    public static String decode(String encoded) {
        try {
            return ThreeDES.decode(encoded, Config.ORG_VALIDATE_CODE);
        } catch (Exception e) {
            return "解密失败";
        }
    }
}
