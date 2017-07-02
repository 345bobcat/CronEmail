# cronEmail
SpringMVC整合quartz，datatables，velocity邮件模板，通过动态配置数据源和设置sql和velocity模板，定时发送邮件。

##使用框架：
   前端整体框架使用bootstrap，静态模板设计使用kindeditor，动态模板设计以及sql编辑使用
codeMirror；<br>
   后台使用springmvc，mybatis，tk.Mybatis，翻页使用pagehelper<br>
   定时任务使用quartz的api进行封装实现，邮件模板的输出使用velocity做为模板<br>
   数据连接池使用的是druid连接池
   
##运行方式：
   1、git下载代码到本地环境（本地环境配置idea，jdk1.8，tomcat，mysql，maven）<br>
   2、导入sql文本（web/src/main/resources/sql/cron_email.sql，含data）<br>
   3、修改根目录pom.xml中的properties参数，如邮件参数（qq邮箱需要使用申请的授权码，具体百度），
  数据库参数<br>
   4、运行工程，页面使用见下面的截图

##遗留问题以及建议：
   1、弹出层可以在不同浏览器会有兼容性问题，但不影响功能，目前代码中bootstrap的modal样式采用全局，
  如果出现问题可以自行修改<br>
   2、数据源的测试，如果是数据库连接有问题会很久弹出连接失败，因为数据库连接池有重试机制，可以自行
  修改druid参数或者改用其他连接池（c3p0，boneCp）<br>
   3、数据库有部分字段是目前没有使用的，主要目的是方便以后扩展，如template_config中部分字段保留了
  几个字段，目前系统没有实现sql的测试和模板的测试，以后模板测试可以通过生成文件的方式进行测试。同时，
  也可把邮件模板的维护扩展为代码生成工具<br>
   4、数据库目前只支持mysql，可以自己扩展代码实现<br>
   5、系统没有做前端js的字段校验<br>
   6、可以在velocity模板生成时传入常用参数（当前日期，时间，操作人）方便邮件使用，需要修改代码<br>
   7、邮件的收件人，抄送人等，已经sql内容都需要采用英文分号隔开

##页面操作

数据源操作，记得加上utf-8，不然有可能引起乱码问题
![image](https://github.com/345bobcat/CronEmail/blob/master/readmeImg/dbsource.png)

静态模板
![image](https://github.com/345bobcat/CronEmail/blob/master/readmeImg/staticTpl.png)

动态模板，模板对查询的参数使用List<List<Object>>封装，参数依次是table1，table2...
![image](https://github.com/345bobcat/CronEmail/blob/master/readmeImg/dynamicTpl.png)

邮件配置
![image](https://github.com/345bobcat/CronEmail/blob/master/readmeImg/emailConf.png)

定时任务
![image](https://github.com/345bobcat/CronEmail/blob/master/readmeImg/cron.png)

测试截图：
![image](https://github.com/345bobcat/CronEmail/blob/master/readmeImg/staticEmail.png)
![image](https://github.com/345bobcat/CronEmail/blob/master/readmeImg/dynamicEmail.png)

