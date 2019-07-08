## 项目环境搭建及SSM整合

#### 1、创建父工程  export_parent

​		不需要src目录，删除

​		更改pom，加标签<packaging>pom</packaging>

![1562598753706](K:\git\repositorys\saas_export\note\images\1562598753706.png)

​		添加依赖坐标

#### 2、创建各模块

1.创建 export_parent 父工程（打包方式选择 pom）
2.创建 export_web_manager 子模块（打包方式是 war 包）
3.创建 export_service_system 子模块（打包方式是 jar 包）
4.创建 export_domain 子模块（打包方式是 jar 包）
5.创建 export_dao 子模块（打包方式是 jar 包）
6.创建 export_common 子模块（打包方式是 jar 包）

![1562599224357](K:\git\repositorys\saas_export\note\images\1562599224357.png)

#### 3、配置各模块间的依赖

export_web_manager    依赖    export_service_system 

export_service_system  依赖     export_dao 

export_dao                       依赖     export_domain 