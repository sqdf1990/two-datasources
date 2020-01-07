package com.pilaf.common;

/**
 * @description:
 * @author: pilaf
 * @create: 2019-12-23 11:34
 */
public enum DatabaseEnum {
    /**
     * db1数据库
     */
    db1("db1"),
    /**
     * db2数据库
     */
    db2("db2");

    private String value;

    DatabaseEnum(String value){
        this.value=value;
    }

    public String getValue(){
        return value;
    }
}
