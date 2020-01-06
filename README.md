演示双数据源的使用方式
数据库MySQL
框架：Spring Boot+MyBatis/MyBatis-plus


两个数据库：db1、db2
db1中有一张表student，db2中有一张表teacher<br/>

在自己本地数据库中建好db1、db2数据库，分别在两个库中执行建表语句，然后运行`com.pilaf.mybatis.service`包中的测试方法，观察数据库中的数据