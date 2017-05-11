package com.socketclient.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by admin on 2017-05-10.
 */

public class Validator {
    public static final int NOT_INPUT = 0;
    public static final int NO_VALID = -1;
    public static final int VALID = 1;

    public static int isValidEmail(String email) {
        if (isNotEmpty(email)) {
            //TODO 패턴 프로토타입. a@b.c 정도만 걸러낸다. com, net 등은 나중에 처리하자.
            Matcher result = Pattern.compile(".*@.*\\..*")
                    .matcher(email);
            if (result.find()) {
                return VALID;
            }
            return NO_VALID;
        } else return NOT_INPUT;
    }

    public static int isValidID(String id) {
        if (isNotEmpty(id)) {
            Matcher result = Pattern.compile("^[a-zA-Z0-9]+$")
                    .matcher(id);

            if (result.find()) {
                return VALID;
            }
            return NO_VALID;
        } else return NOT_INPUT;
    }

    /**
     * 비어있는 문자열인지 확인한다.
     *
     * @param text 확인할 문자열
     * @return true == 비어있지 않음. // false == 비어있음
     */
    public static boolean isNotEmpty(String text) {
        if (text == null || text.equals("")) return false;
        else return true;
    }
}
