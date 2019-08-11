<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="../../base.jsp" %>
<!DOCTYPE html>
<html>

<head>
    <!-- 页面meta -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>数据 - AdminLTE2定制版</title>
    <meta name="description" content="AdminLTE2定制版">
    <meta name="keywords" content="AdminLTE2定制版">
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width,initial-scale=1,maximu
    m-scale=1,user-scalable=no" name="viewport">
    <!-- 页面meta /-->
</head>
<body>
<div id="frameContent" class="content-wrapper" style="margin-left:0px;">
    <!-- 内容头部 -->
    <section class="content-header">
        <h1>
            财务管理
            <small>修改财务报表</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="all-admin-index.html"><i class="fa fa-dashboard"></i> 首页</a></li>
            <li><a href="all-order-manage-list.html">财务管理</a></li>
            <li class="active">财务报表</li>
        </ol>
    </section>
    <!-- 内容头部 /-->

    <!-- 正文区域 -->
    <form id="editForm" action="${ctx}/cargo/finance/save.do" method="post">
    <section class="content">

        <!--订单信息-->

        <div class="panel panel-default">
            <div class="panel-heading">财务报表信息</div>

                <div class="row data-type" style="margin: 0px">
                    <div class="col-md-2 title">制单人</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="制单人" name="inputBy"
                               value="${finance.inputBy}">
                    </div>

                    <div class="col-md-2 title">创建人</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="创建人" name="createBy" value="${finance.createBy}">
                    </div>
                    <div class="col-md-1 title">创建部门</div>
                    <div class="col-md-5 data">
                        <select class="form-control" onchange="document.getElementById('deptName').value=this.options[this.selectedIndex].text" name="createDempt">
                            <option value="">请选择</option>
                            <c:forEach items="${deptList}" var="item">
                                <option ${finance.createDempt == item.deptName ?'selected':''} value="${item.deptId}">${item.deptName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-2 title">创建时间</div>
                    <div class="col-md-4 data">
                        <input type="date" class="form-control" placeholder="创建时间" name="createTime" >
                    </div>
                </div>
        </div>
<!--订单信息/-->

<!--工具栏-->
<div class="box-tools text-center">
    <button type="button" onclick='document.getElementById("editForm").submit()' class="btn bg-maroon">保存</button>
    <button type="button" class="btn bg-default" onclick="history.back(-1);">返回</button>
</div>
<!--工具栏/-->

</section>
<!-- 正文区域 /-->
    <section class="content">

        <!-- .box-body -->
        <div class="box box-primary">
            <div class="box-header with-border">
                <h3 class="box-title">发票列表</h3>
            </div>

            <div class="box-body">

                <!-- 数据表格 -->
                <div class="table-box">
                    <!--数据列表-->
                    <table id="dataList" class="table table-bordered table-striped table-hover dataTable">
                        <thead>
                        <tr>
                            <td class="tableHeader"><input type="checkbox" name="selid" onclick="checkAll('id',this)"></td>
                            <td class="tableHeader">序号</td>
                            <td class="tableHeader">发票号</td>
                            <td class="tableHeader">贸易条款</td>
                            <td class="tableHeader">创建人</td>
                            <td class="tableHeader">创建部门</td>
                            <td class="tableHeader">创建时间</td>

                        </tr>
                        </thead>
                        <tbody class="tableBody" >
                        ${links }
                        <c:forEach items="${page.list}" var="o" varStatus="status">
                            <tr class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'" >
                                <td><input type="radio" name="financeId" value="${o.invoiceId}"/></td>
                                <td>${status.index+1}</td>
                                <td>${o.invoiceId}</td>
                                <td>${o.tradeTerms}</td>
                                <td>${o.createBy}</td>
                                <td>${o.createDept}</td>
                                <td>${o.createTime}</td>

                            </tr>

                        </c:forEach>
                        </tbody>
                    </table>
                    <!--数据列表/-->
                    <!--工具栏/-->
                </div>
                <!-- 数据表格 /-->
            </div>
            <!-- /.box-body -->

            <!-- .box-footer-->
            <div class="box-footer">
                <jsp:include page="../../common/page.jsp">
                    <jsp:param value="${ctx}/cargo/contractProduct/list.do?contractId=${contractId}" name="pageUrl"/>
                </jsp:include>
            </div>
            <!-- /.box-footer-->
        </div>

    </section>
    </form>
</div>
<!-- 内容区域 /-->
</body>
<script src="../../plugins/datepicker/bootstrap-datepicker.js"></script>
<script src="../../plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>
<link rel="stylesheet" href="../../css/style.css">
<script>
    $('#signingDate').datepicker({
        autoclose: true,
        format: 'yyyy-mm-dd'
    });
    $('#deliveryPeriod').datepicker({
        autoclose: true,
        format: 'yyyy-mm-dd'
    });
    $('#shipTime').datepicker({
        autoclose: true,
        format: 'yyyy-mm-dd'
    });
</script>
<script>
    function choose() {

        var startDate = $("#datepicker1").val();
        var stopDate = $("#datepicker2").val();
        alert(startDate);
        $.post("${ctx}/cargo/finance/choose.do?date1=" + startDate+"&date2="+stopDate, function () {

            alert("342423423423")
        })
    }

</script>
<%--<script>
    $('#datepicker').datepicker({
        language: "zh-CN",
        autoclose: true,
        format: 'yyyy-mm',
        startView: 'months', //开始视图层，为月视图层
        maxViewMode: 'years', //最大视图层，为年视图层
        minViewMode: 'months', //最小视图层，为月视图层
    });
</script>--%>
</html>