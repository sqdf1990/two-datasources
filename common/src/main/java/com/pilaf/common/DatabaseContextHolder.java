package com.pilaf.common;

/**
 * @description:
 * @author: dufeng3
 * @create: 2019-12-23 11:33
 */
public class DatabaseContextHolder {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    /**
     * 设置当前线程的数据库名称
     * @param database
     */
    public static void setDatabase(DatabaseEnum database){
        contextHolder.set(database.getValue());
    }

    /**
     * 获取数据库名称
     * @return
     */
    public static String getDatabase(){
        return contextHolder.get();
    }

    /**
     * 清除线程上下文
     */
    public static void clearDatabase(){
        contextHolder.remove();
    }
}
