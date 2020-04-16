package org.example.java.code.Jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabasePool {

    private static HikariDataSource hikariDataSource;

    public HikariDataSource getHikariDataSource(){

        if(null != hikariDataSource){
            return hikariDataSource;
        }

        synchronized (DatabasePool.class){
            if(null == hikariDataSource){
                String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/school?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai ";
                String driverName = "com.mysql.cj.jdbc.Driver";
                HikariConfig hikariConfig = new HikariConfig();
                hikariConfig.setUsername("root");
                hikariConfig.setPassword("12345ysp");
                hikariConfig.setDriverClassName(driverName);
                hikariConfig.setJdbcUrl(jdbcUrl);

                hikariDataSource = new HikariDataSource(hikariConfig);

                return hikariDataSource;
            }
        }
        return null;
    }


}
