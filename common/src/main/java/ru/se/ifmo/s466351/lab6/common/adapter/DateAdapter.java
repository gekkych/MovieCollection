package ru.se.ifmo.s466351.lab6.common.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateAdapter extends XmlAdapter<String, Date> {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    @Override
    public Date unmarshal(String s) throws Exception {
        return dateFormat.parse(s);
    }

    @Override
    public String marshal(Date date) throws Exception {
        return dateFormat.format(date);
    }
}
