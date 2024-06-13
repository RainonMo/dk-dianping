# dkdp
打卡点评后端

## 项目简介

打卡点评,类似大众点评，用户可以打卡店铺，发送打卡笔记，对打卡的店铺进行点评，可以关注用户，查看打卡笔记。商家可以入住，展示店铺，添加优惠券。

## 技术栈

- Java
- springboot
- mybatis-plus
- mysql
- springfox-swagger2
- lombok
- redisson
- hutool

## 功能模块

### 登录模块
1.发送验证码

2.登录

3.登出

4.查询当前用户

5.根据id查询信息

6.根据id查询用户

### 博客模块
1.添加

2.博客推荐

3.根据id查询博客

4.根据id查询top5的点赞用户

5.根据用户id查询博主笔记

6.查看关注的博主笔记

### 关注模块

### 商家模块

### 商家分类模块

## 快速上手

1. 克隆本仓库到本地计算机。


2. 使用IDE（如IntelliJ IDEA）打开项目。


3. 执行 `src/resources/db/dkdp.sql` 中的数据库语句，自动创建库表


4. 修改 `application.yml` 的数据库配置为你自己的：

```yml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/my_db
    username: root
    password: 123456
  redis:
      host: 127.0.0.1
      port: 6379
      password: 6EhSiGpsmSMRyZieglUImkTr-eoNRNBgRk397mVyu66MHYuZDsepCeZ8A-MHdLBQwQQVQiHBufZbPa
```

4. 运行应用程序：在IDE中运行`DkDianPingApplication.java`文件。


5. 访问应用程序：在浏览器中输入 `http://localhost:8082/doc.html` 即可打开接口文档，不需要写前端就能在线调试接口了。
