package lzw.app.com.myutils.utils;


import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/4/11.
 */

public class BankUtil {

    public static String makePostHTML(String apiURl,Map<String,String> formData){
        String html = "<!DOCTYPE HTML><html><body><form id='sbform' action='%s' method='post'>%s</form><script type='text/javascript'>document.getElementById('sbform').submit();</script></body></html>";
        List<String> list = new ArrayList<>(formData.size());
        String input = "<input type='hidden' name='%s' value='%s'/>";
        for(Map.Entry<String,String> entry:formData.entrySet()){
            list.add(String.format(input,entry.getKey(),entry.getValue()));
        }
        return String.format(html, apiURl, StringUtils.join(list, "\n"));
    }

    private static Map<String, String> obj2Map(Object obj) {

        Map<String, String> map = new HashMap<String, String>();
        // System.out.println(obj.getClass());
        // 获取f对象对应类中的所有属性域
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0, len = fields.length; i < len; i++) {
            String varName = fields[i].getName();
            varName = varName.toLowerCase();//将key置为小写，默认为对象的属性
            try {
                // 获取原来的访问控制权限
                boolean accessFlag = fields[i].isAccessible();
                // 修改访问控制权限
                fields[i].setAccessible(true);
                // 获取在对象f中属性fields[i]对应的对象中的变量
                Object o = fields[i].get(obj);
                if (o != null)
                    map.put(varName, o.toString());
                // System.out.println("传入的对象中包含一个如下的变量：" + varName + " = " + o);
                // 恢复访问控制权限
                fields[i].setAccessible(accessFlag);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }
        }
        return map;
    }
}
