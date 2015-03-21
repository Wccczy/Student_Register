package com.bear.web.utils;

import com.jfinal.log.Logger;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by VIJAY-YAN on 2014/8/8.
 */
public class Conditions {
    static Logger log = Logger.getLogger(Conditions.class);
    public static final String EQUAL = "EQUAL"; // 相等
    public static final String NOT_EQUAL = "NOT_EQUAL"; // 不相等
    public static final String LESS_THEN = "LESS_THEN"; // 小于
    public static final String LESS_EQUAL = "LESS_EQUAL"; // 小于等于
    public static final String GREATER_EQUAL = "GREATER_EQUAL"; // 大于等于
    public static final String GREATER_THEN = "GREATER_THEN"; // 大于
    public static final String FUZZY = "FUZZY"; // 模糊匹配 %xxx%
    public static final String FUZZY_LEFT = "FUZZY_LEFT"; // 左模糊 %xxx
    public static final String FUZZY_RIGHT = "FUZZY_RIGHT"; // 右模糊 xxx%
    public static final String NOT_EMPTY = "NOT_EMPTY"; // 不为空值的情况
    public static final String EMPTY = "EMPTY"; // 空值的情况
    // 用于接收SQL语句
    private ThreadLocal<String> sql = new ThreadLocal<String>();
    // 用于接收参数数组
    private ThreadLocal<List<Object>> paramList = new ThreadLocal<List<Object>>();
    // 用于存放设置的条件
    private ThreadLocal<Map<String, Object[]>> conditionMap = new ThreadLocal<Map<String, Object[]>>();
    // 用于存放需要排除的字段
    private ThreadLocal<Map<String, String>> excludeFieldMap = new ThreadLocal<Map<String, String>>();

    // 构造方法(表示没有设置查询类型的字段全部按照等于来处理)
    public Conditions() {
        conditionMap.set(new HashMap<String, Object[]>());
        excludeFieldMap.set(new HashMap<String, String>());
    }

    // 构造方法(设置后表示字段所有的查询方式按照设置类型来处理，除非后面针对字段的重新设置)
    public Conditions(String type) {
        Map<String, Object[]> map = new HashMap<String, Object[]>();
        map.put("GLOBALTYPE", new String[]{type});
        conditionMap.set(map);
        excludeFieldMap.set(new HashMap<String, String>());
    }

    /**
     * ************************************************************************
     * 设置字段的查询类型
     *
     * @param QueryType 查询类型
     * @param filedName 字段名称数组
     */
    public void setFiledQuery(String QueryType, String... filedName) {
        if (StringUtils.isNotBlank(QueryType) && !Common.isNullOrEmpty(filedName)) {
            Map<String, Object[]> map = conditionMap.get();
            map.put(QueryType, filedName);
            conditionMap.set(map);
        }
    }

    /**
     * ************************************************************************
     * 设置需要排除的字段
     * <p/>
     * setexcludeField<br>
     *
     * @param filedName
     * @return 返回对象
     * @Exception 异常对象
     */
    public void setExcludeField(String... filedName) {
        if (!Common.isNullOrEmpty(filedName)) {
            Map<String, String> map = excludeFieldMap.get();
            for (String str : filedName) {
                map.put(str, str);
            }
            excludeFieldMap.set(map);
        }
    }

    /**
     * ************************************************************************
     * 查询空值或者不为空值的情况 setNullFieldQuery
     *
     * @param QueryType
     * @param filedName
     * @return 返回对象
     * @Exception 异常对象
     */
    public void setNullOrNotNullFieldQuery(String QueryType, String... filedName) {
        if (StringUtils.isNotBlank(QueryType) && !Common.isNullOrEmpty(filedName)) {
            if (!NOT_EMPTY.equals(QueryType) && !EMPTY.equals(QueryType)) {
                log.error("空值或者非空查询的类型只能为：EMPTY、NOT_EMPTY");
                throw new RuntimeException("空值或者非空查询的类型只能为：EMPTY、NOT_EMPTY");
            }
            Map<String, Object[]> map = conditionMap.get();
            map.put(QueryType, filedName);
            conditionMap.set(map);
        }
    }

    /**
     * ************************************************************************
     * 传值查询
     *
     * @param QueryType  查询类型
     * @param fieldName  字段名称
     * @param filedValue 字段值
     */
    public void setValueQuery(String QueryType, String fieldName, Object filedValue) {
        if (StringUtils.isNotBlank(QueryType) && StringUtils.isNotBlank(fieldName) && !Common.isNullOrEmpty(filedValue)) {
            Object[] param = new Object[2];
            param[0] = fieldName; // 字段名
            param[1] = filedValue;// 字段值
            Map<String, Object[]> map = conditionMap.get();
            map.put(QueryType + "#" + fieldName, param);// 避免类型重复被覆盖掉就加上字段名
            conditionMap.set(map);
        }
    }

    /**
     * ************************************************************************
     * 用于生成SQL条件语句不带别名
     *
     * @param modelClass 必须继承于Model
     */
    public void modelToCondition(Model<?> modelClass) {
        modelToCondition(modelClass, null);
    }

    /**
     * ************************************************************************
     * 用于生成SQL条件语句不带别名
     *
     * @param recordClass 必须是一个Record类
     */
    public void recordToCondition(Record recordClass) {
        recordToCondition(recordClass, null);
    }

    /**
     * ************************************************************************
     * 用于生成SQL条件语句带别名
     *
     * @param modelClass 必须继承于Model
     * @param alias      别名
     */
    public void modelToCondition(Model<?> modelClass, String alias) {
        alias = StringUtils.isNotBlank(alias) ? alias + "." : "";
        if (modelClass != null) {
            // 所有的字段
            String[] fieldNames = modelClass.getAttrNames();
            // 字段名和值的map集合
            Map<String, Object> valueMap = Common.modelToMap(modelClass);
            // 构建查询条件
            buildCondition(alias, fieldNames, valueMap);
        } else {
            if (!conditionMap.get().isEmpty()) {
                buildCondition(alias, new String[]{}, new HashMap<String, Object>());
            } else {
                sql.set("");
                paramList.set(new ArrayList<Object>());
            }
        }
    }

    /**
     * ************************************************************************
     * 用于生成SQL条件语句不带别名
     *
     * @param recordClass 必须是一个Record类
     * @param alias       别名
     */
    public void recordToCondition(Record recordClass, String alias) {
        // 别名
        alias = StringUtils.isNotBlank(alias) ? alias + "." : "";
        if (recordClass != null) {
            // 所有的字段
            String[] fieldNames = recordClass.getColumnNames();
            // 字段名和值的map集合
            Map<String, Object> valueMap = Common.recordToMap(recordClass);
            // 构建查询条件
            buildCondition(alias, fieldNames, valueMap);
        } else {
            if (!conditionMap.get().isEmpty()) {
                buildCondition(alias, new String[]{}, new HashMap<String, Object>());
            } else {
                sql.set("");
                paramList.set(new ArrayList<Object>());
            }
        }
    }

    /**
     * ************************************************************************
     * 构建条件语句
     *
     * @param alias      别名
     * @param fieldNames 所有查询的字段名称
     * @param valueMap   所有的值的map
     */
    private void buildCondition(String alias, String[] fieldNames, Map<String, Object> valueMap) {
        try {
            // 构建条件前先清空变量
            sql.set("");
            paramList.set(new ArrayList<Object>());
            // 用于存放参数列表
            ArrayList<Object> paramArrayList = new ArrayList<Object>();
            StringBuilder sb = new StringBuilder();
            // 所有的字段名称
            Map<String, String> usedFieldMap = new HashMap<String, String>();
            if (!conditionMap.get().isEmpty()) {
                for (Map.Entry<String, Object[]> map : conditionMap.get().entrySet()) {
                    String queryType = map.getKey();
                    Object[] array = map.getValue();
                    if (queryType.indexOf("#") > 0) {// 传值查询
                        String fieldQueryType = queryType.split("#")[0];
                        String fieldName = array[0] != null ? array[0].toString() : "";
                        Object fieldValue = array[1];
                        // 将设置过的字段保存到数组中
                        usedFieldMap.put(fieldName, fieldName);
                        // 构建SQL语句
                        buildSQL(sb, fieldQueryType, fieldName, fieldValue, alias, paramArrayList);
                    } else {// 字段查询
                        for (Object field : array) {
                            String filedName = field != null ? field.toString() : "";
                            if (!excludeFieldMap.get().containsKey(filedName)) {
                                Object fieldValue = valueMap.get(filedName);
                                // 将设置过的字段保存到数组中
                                usedFieldMap.put(filedName, filedName);
                                // 构建查询语句
                                buildSQL(sb, queryType, filedName, fieldValue, alias, paramArrayList);
                            }
                        }
                    }
                }
            }
            // 对没有设置条件的字段进行查询类型设置
            String queryType = EQUAL;
            if (conditionMap.get().containsKey("GLOBALTYPE")) {
                String[] typeArray = (String[]) conditionMap.get().get("QueryType");
                queryType = typeArray[0];
            }
            // 对未使用过的字段进行build
            for (String field : fieldNames) {
                if (!usedFieldMap.containsKey(field)) {
                    Object fieldValue = valueMap.get(field);
                    // 构建查询语句
                    buildSQL(sb, queryType, field, fieldValue, alias, paramArrayList);
                }
            }
            // 合并传入的参数到参数对象中
            sql.set(sb.toString());
            paramList.set(paramArrayList);
            conditionMap.set(new HashMap<String, Object[]>());// 清空本次的条件map
            excludeFieldMap.set(new HashMap<String, String>());// 清空本次的排除字段
        } catch (Exception e) {
            log.error("Conditions构建SQL语句出现错误,请仔细检查!");
            e.printStackTrace();
        }
    }

    /**
     * ************************************************************************
     * 构建SQL语句
     *
     * @param sb         用于拼接SQL语句
     * @param queryType  查询类型
     * @param fieldName  字段名称
     * @param fieldValue 字段值
     * @param alias      别名
     * @return
     */
    private void buildSQL(StringBuilder sb, String queryType, String fieldName, Object fieldValue, String alias, ArrayList<Object> params) {
        // 非空的时候进行设置
        if (!Common.isNullOrEmpty(fieldValue) && !Common.isNullOrEmpty(fieldName)) {
            if (EQUAL.equals(queryType)) {
                sb.append(" and " + alias + fieldName + " = ? ");
                params.add(fieldValue);
            } else if (NOT_EQUAL.equals(queryType)) {
                sb.append(" and " + alias + fieldName + " <> ? ");
                params.add(fieldValue);
            } else if (LESS_THEN.equals(queryType)) {
                sb.append(" and " + alias + fieldName + " < ? ");
                params.add(fieldValue);
            } else if (LESS_EQUAL.equals(queryType)) {
                sb.append(" and " + alias + fieldName + " <= ? ");
                params.add(fieldValue);
            } else if (GREATER_THEN.equals(queryType)) {
                sb.append(" and " + alias + fieldName + " > ? ");
                params.add(fieldValue);
            } else if (GREATER_EQUAL.equals(queryType)) {
                sb.append(" and " + alias + fieldName + " >= ? ");
                params.add(fieldValue);
            } else if (FUZZY.equals(queryType)) {
                sb.append(" and " + alias + fieldName + " like ? ");
                params.add("%" + fieldValue + "%");
            } else if (FUZZY_LEFT.equals(queryType)) {
                sb.append(" and " + alias + fieldName + " like ? ");
                params.add("%" + fieldValue);
            } else if (FUZZY_RIGHT.equals(queryType)) {
                sb.append(" and " + alias + fieldName + " like ? ");
                params.add(fieldValue + "%");
            }
        } else {
            if (EMPTY.equals(queryType)) {
                sb.append(" and " + alias + fieldName + " is null ");
            } else if (NOT_EMPTY.equals(queryType)) {
                sb.append(" and " + alias + fieldName + " is not null ");
            }
        }
    }

    public String getSql() {
        return sql.get();
    }

    public List<Object> getParamList() {
        return paramList.get();
    }
}
