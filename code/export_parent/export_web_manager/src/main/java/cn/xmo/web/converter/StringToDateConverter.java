package cn.xmo.web.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: Elves
 * @Description:
 * @Date: Created in 17:22 2019/7/9
 * @Modified By:
 */
public class StringToDateConverter implements Converter<String,Date> {

    @Override
    public Date convert(String s) {
        Date result = null;

        try{
            result = new SimpleDateFormat("yyyy-MM-dd").parse(s);
        }catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
