server:
    port: 9090

spring:
    datasource:
        name: test
        driver-class-name: com.mysql.jdbc.Driver
        url: jdbc:mysql://localhost:3306/EAP5?characterEncoding=utf8&useSSL=false
        username: root
        password: root

mybatis:
    mapper-locations: classpath:Mapper/*.xml
    type-aliases-package: ${classPath}.Entity
