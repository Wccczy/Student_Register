package com.bear.web.utils;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by VIJAY-YAN on 2014/8/8.
 */
public class Common {
    /**
     * 判断对象或对象数组中每一个对象是否为空: 对象为null，字符序列长度为0，集合类、Map为empty
     *
     * @param obj
     * @return
     */
    public static boolean isNullOrEmpty(Object obj) {
        if (obj == null)
            return true;

        if (obj instanceof CharSequence)
            return ((CharSequence) obj).length() == 0;

        if (obj instanceof Collection)
            return ((Collection) obj).isEmpty();

        if (obj instanceof Map)
            return ((Map) obj).isEmpty();

        if (obj instanceof Object[]) {
            Object[] object = (Object[]) obj;
            if (object.length == 0) {
                return true;
            }
            boolean empty = true;
            for (int i = 0; i < object.length; i++) {
                if (!isNullOrEmpty(object[i])) {
                    empty = false;
                    break;
                }
            }
            return empty;
        }
        return false;
    }

    /**
     * Model转Map
     *
     * @param model
     * @return
     */
    public static Map<String, Object> modelToMap(Model model) {
        Map<String, Object> map = new HashMap<String, Object>();
        Set<Map.Entry<String, Object>> attrs = model.getAttrsEntrySet();
        for (Map.Entry<String, Object> entry : attrs) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    /**
     * Record转Map
     *
     * @param record
     * @return
     */
    public static Map<String, Object> recordToMap(Record record) {
        return record.getColumns();
    }
}
