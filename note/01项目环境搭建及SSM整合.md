## 项目环境搭建及SSM整合

#### 1、创建父工程  export_parent

​		不需要src目录，删除

​		更改pom，加标签<packaging>pom</packaging>

![1562598753706](K:\git\repositorys\saas_export\note\images\1562598753706.png)

​		添加依赖坐标

#### 2、创建各模块(后期采用dubbo分布式架构)

1.创建 export_parent 父工程（打包方式选择 pom）

2.创建 export_web_manager 子模块（打包方式是 war 包）

3.创建 export_system_interface 子模块 (打包方式是jar包)（interface都是jar）

3.创建 export_system_service 子模块（打包方式是 war 包）（service都是war，因为是实现类）

cargo是货物模块，system是系统模块，stat是统计模块(statistic)

4.创建 export_domain 子模块（打包方式是 jar 包）

5.创建 export_dao 子模块（打包方式是 jar 包）

6.创建 export_common 子模块（打包方式是 jar 包）

![1562655235634](K:\git\repositorys\saas_export\note\images\1562655235634.png)

#### 3、配置各模块间的依赖

export_web_manager    依赖    export_system_interface 

export_system_interface 依赖     export_dao 

export_system_service  依赖   export_system_interface 

​													 export_dao                       

export_cargo_interface 依赖     export_dao 

export_cargo_service  依赖   export_cargo_interface 

​													 export_dao                       

export_dao                       依赖     export_domain 

#### 4、配置Mybatis环境

使用Mybastis_Generator逆向工程，从数据库表中生成实体类(手动添加**实现序列化结构**4个)等，

拷贝实体类，dao接口，mapper映射文件

手写BaseEntity实体类

#### 5、SSM整合配置

**Spring和Mybatis整合配置**：applicationContext-dao.xml;

​													applicationContext-service.xml;

**dubbo整合配置**：applicationContext-dubbo.xml;

​		service模块 web.xml 中监听所有配置文件

**SpringMVC整合配置**：springmvc.xml;

​		web_manager模块 web.xml 中监听所有配置文件





