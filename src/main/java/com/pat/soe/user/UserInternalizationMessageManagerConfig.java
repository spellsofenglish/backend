package com.pat.soe.user;

import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;
import java.util.ResourceBundle;

public class UserInternalizationMessageManagerConfig {

    public static String getExceptionMessage(String keyMessage) {
//        ResourceBundle resourceBundle = ResourceBundle.getBundle("exceptionsMessages", LocaleContextHolder.getLocale());
        ResourceBundle resourceBundle = ResourceBundle.getBundle("exceptionsMessages", Locale.ENGLISH);
        return resourceBundle.getString(keyMessage);
    }

    public static String getMessage(String keyMessage) {
//        ResourceBundle resourceBundle = ResourceBundle.getBundle("textMessages", LocaleContextHolder.getLocale());
        ResourceBundle resourceBundle = ResourceBundle.getBundle("textMessages", Locale.ENGLISH);
        return resourceBundle.getString(keyMessage);
    }
}
