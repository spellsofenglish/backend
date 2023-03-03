package com.pat.soe.information;

public class InformationInternalizationMessageManagerConfig {

    public static String getExceptionMessage(String keyMessage) {
//        ResourceBundle resourceBundle = ResourceBundle.getBundle("exceptionsMessages", LocaleContextHolder.getLocale());
//        return resourceBundle.getString(keyMessage);
        return "OOOps...";
    }

    public static String getMessage(String keyMessage) {
//        ResourceBundle resourceBundle = ResourceBundle.getBundle("textMessages", LocaleContextHolder.getLocale());
//        return resourceBundle.getString(keyMessage);
        return "All right)))";
    }
}
