package cn.cangling.docker.composer.client.js;

import com.google.gwt.i18n.client.DateTimeFormat;

import java.util.Date;

public class DateTimes {
    public static  String formatDateTime(Date date)
    {
        DateTimeFormat customFormat = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss");
        return customFormat.format(date);
    }

}
