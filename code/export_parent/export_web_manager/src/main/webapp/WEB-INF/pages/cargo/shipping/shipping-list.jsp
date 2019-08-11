<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../base.jsp"%>
<!DOCTYPE html>
<html>

<head>
    <!-- 页面meta -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>数据 - AdminLTE2定制版</title>
    <meta name="description" content="委托单">
    <meta name="keywords" content="委托单">
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no" name="viewport">
</head>
<script>
    function deleteById() {
        var id = getCheckId();
        if(id) {
            if(confirm("你确认要删除此条记录吗？")) {
                location.href="${ctx}/cargo/shipping/delete.do?id="+id;
            }
        }else{
            alert("请勾选待处理的记录，且每次只能勾选一个")
        }
    }

    function roleList() {
        var id = getCheckId()
        if(id) {
            location.href="${ctx}/cargo/shipping/toUpdate.do?id="+id;
        }else{
            alert("请勾选待处理的记录，且每次只能勾选一个")
        }
    }
    function submit() {
        var id = getCheckId()
        if(id) {
            location.href="${ctx}/cargo/shipping/submit.do?id="+id;
        }else{
            alert("请勾选待处理的记录，且每次只能勾选一个")
        }
    }

    function cancel() {
        var id = getCheckId()
        if(id) {
            location.href="${ctx}/cargo/shipping/cancel.do?id="+id;
        }else{
            alert("请勾选待处理的记录，且每次只能勾选一个")
        }
    }
</script>
<body>
<div id="frameContent" class="content-wrapper" style="margin-left:0px;">
<section class="content-header">
    <h1>
        系统管理
        <small>用户管理</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="all-admin-index.html"><i class="fa fa-dashboard"></i> 首页</a></li>
    </ol>
</section>
<section class="content">
    <div class="box box-primary">
        <div class="box-header with-border">
            <h3 class="box-title">委托列表</h3>
        </div>
        <div class="box-body">
            <div class="table-box">
                <div class="pull-left">
                    <div class="form-group form-inline">
                        <div class="btn-group">
                            <button type="button" class="btn btn-default" title="新建" onclick='location.href="${ctx}/cargo/shipping/toAdd.do"'><i class="fa fa-file-o"></i> 新建</button>
                            <button type="button" class="btn btn-default" title="删除" onclick='deleteById()'><i class="fa fa-trash-o"></i> 删除</button>
                            <button type="button" class="btn btn-default" title="刷新" onclick="window.location.reload();"><i class="fa fa-refresh"></i> 刷新</button>
                            <button type="button" class="btn btn-default" title="提交" onclick="submit()"><i class="fa fa-retweet"></i> 提交</button>
                            <button type="button" class="btn btn-default" title="取消" onclick="cancel()"><i class="fa fa-remove"></i> 取消</button>
                        </div>
                    </div>
                </div>
                <div class="box-tools pull-right">
                    <div class="has-feedback">
                        <input type="text" class="form-control input-sm" placeholder="搜索">
                        <span class="glyphicon glyphicon-search form-control-feedback"></span>
                    </div>
                </div>
                <table id="dataList" class="table table-bordered table-striped table-hover dataTable">
                    <thead>
                    <tr>
                        <th class="" style="padding-right:0px;">

                        </th>
                        <th class="sorting">海运/空运</th>
                        <th class="sorting">货主</th>
                        <%--<th class="sorting">提单抬头</th>--%>
                        <%--<th class="sorting">正本通知人</th>--%>
                        <%--<th class="sorting">信用证</th>--%>
                        <th class="sorting">装运港</th>
                        <th class="sorting">转船港</th>
                        <th class="sorting">卸货港</th>
                        <%--<th class="sorting">装期</th>--%>
                        <%--<th class="sorting">效期</th>--%>
                        <%--<th class="sorting">是否分批</th>--%>
                        <%--<th class="sorting">是否转船</th>--%>
                        <th class="sorting">份数</th>
                        <th class="text-center">扼要说明</th>
                        <th class="text-center">运输要求</th>
                        <%--<th class="text-center">运费说明</th>--%>
                        <th class="sorting">复核人</th>
                        <th class="sorting">状态</th>
                        <th class="sorting">创建人</th>
                        <th class="sorting">创建部门</th>
                        <th class="sorting">操作</th>
                        <%--<th class="sorting">创建日期</th>--%>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${page.list}" var="item">
                    <tr>
                        <td><input name="id" value="${item.shippingOrderId}" type="checkbox"></td>

                        <td>${item.orderType}</td>
                        <td>${item.shipper}</td>
                        <%--<td>${item.consignee}</td>--%>
                        <%--<td>${item.notifyParty}</td>--%>
                        <%--<td>${item.lcNo}</td>--%>
                        <td>${item.portOfLoading}</td>
                        <td>${item.portOfTrans}</td>
                        <td>${item.portOfDiscarge}</td>
                        <%--<td>${item.loadingDate}</td>--%>
                        <%--<td>${item.limitDate}</td>--%>
                        <%--<td>${item.isBatch}</td>--%>
                        <%--<td>${item.isTrans}</td>--%>
                        <td>${item.copyNum}</td>
                        <td>${item.remark}</td>
                        <td>${item.specialCondition}</td>
                        <%--<td>${item.freight}</td>--%>
                        <td>${item.checkBy}</td>
                        <td><c:if test="${item.state==0}">草稿</c:if>
                            <c:if test="${item.state==1}"><font color="green">已上报</font></c:if></td>
                        <td>${item.createBy}</td>
                        <td>${item.createDept}</td>
                        <%--<td>${item.createTime}</td>--%>
                        <th class="text-center">
                            <button type="button" class="btn bg-olive btn-xs" onclick='location.href="${ctx}/cargo/shipping/toUpdate.do?id=${item.shippingOrderId}"'>编辑</button>
                        </th>
                    </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="box-footer">
            <jsp:include page="../../common/page.jsp">
                <jsp:param value="${ctx}/system/user/list.do" name="pageUrl"/>
            </jsp:include>
        </div>
    </div>

</section>
</div>
</body>

</html>