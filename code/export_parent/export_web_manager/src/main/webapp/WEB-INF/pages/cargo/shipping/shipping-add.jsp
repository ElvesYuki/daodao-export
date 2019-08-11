<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="../../base.jsp"%>
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
    <meta content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no" name="viewport">
    <!-- 页面meta /-->
</head>
<script>
    function changeShipId() {
        var id = getPackingCheckId();
        $("#ShipId").val(id);
    }

    function getPackingCheckId() {
        // language=JQuery-CSS
        var size = $("input:checkbox:checked").length;
        if(size == 1) {
            return $("#PackId").val();
        }else {
            var a= $('input[type=checkbox]:checked').val();
            $("#input").val(a);
            alert("每次只能勾选一个装箱单")
        }
    }
</script>
<body>
<div id="frameContent" class="content-wrapper" style="margin-left:0px;">
    <!-- 内容头部 -->
    <section class="content-header">
        <h1>
            委托单管理
            <small>委托单管理详情</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="all-admin-index.html"><i class="fa fa-dashboard"></i> 首页</a></li>
            <li><a href="all-order-manage-list.html">委托单管理</a></li>
            <li class="active">委托单管理详情</li>
        </ol>
    </section>
    <!-- 内容头部 /-->

    <!-- 正文区域 -->
    <section class="content">

        <!--订单信息-->
        <div class="panel panel-default">
            <div class="panel-heading">新增委托单</div>
            <form id="editForm" action="${ctx}/cargo/shipping/edit.do" method="post">
                <input type="text" id="ShipId" name="shippingOrderId" value="${shipping.shippingOrderId}">
                <div class="row data-type" style="margin: 0px">
                    <div class="col-md-2 title">海运/空运</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="海运/空运" name="orderType" value="${shipping.orderType}">
                    </div>

                    <div class="col-md-2 title">货主</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="货主" name="shipper" value="${shipping.shipper}">
                    </div>

                    <div class="col-md-2 title">装期</div>
                    <div class="col-md-4 data">
                        <div class="input-group date">
                            <div class="input-group-addon">
                                <i class="fa fa-calendar"></i>
                            </div>
                            <input type="text" placeholder="装期"  name="loadingDate" class="form-control pull-right"
                                   value="<fmt:formatDate value="${shipping.loadingDate}" pattern="yyyy-MM-dd"/>" id="loadingDate">
                        </div>
                    </div>
                    <%--<div class="col-md-2 title">状态</div>--%>
                    <%--<div class="col-md-4 data">--%>
                    <%--<input type="text" class="form-control" placeholder="状态" name="state" value="${packingList.state}">--%>
                    <%--</div>--%>
                    <div class="col-md-2 title">装运港</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="装运港" name="portOfLoading" value="${shipping.portOfLoading}">
                    </div>

                    <div class="col-md-2 title">转船港</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="转船港" name="portOfTrans" value="${shipping.portOfTrans}">
                    </div>

                    <div class="col-md-2 title">卸货港</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="卸货港" name="portOfDiscarge" value="${shipping.portOfDiscarge}">
                    </div>

                    <div class="col-md-2 title">是否转船</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="是否转船" name="isTrans" value="${shipping.isTrans}">
                    </div>
                    <div class="col-md-2 title">扼要说明</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="扼要说明" name="remark" value="${shipping.remark}">
                    </div>

                    <div class="col-md-2 title">效期</div>
                    <div class="col-md-4 data">
                        <div class="input-group date">
                            <div class="input-group-addon">
                                <i class="fa fa-calendar"></i>
                            </div>
                            <input type="text" placeholder="效期"  name="limitDate" class="form-control pull-right"
                                   value="<fmt:formatDate value="${shipping.limitDate}" pattern="yyyy-MM-dd"/>" id="limitDate">
                        </div>
                    </div>

                    <div class="col-md-2 title">运费说明</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="运费说明" name="freight" value="${packingList.freight}">
                    </div>
                    <div class="col-md-2 title">复核人</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="复核人" name="checkBy" value="${packingList.checkBy}">
                    </div>

                    <div class="col-md-2 title">创建人</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="创建人" name="createBy" value="${packingList.createBy}">
                    </div>
                    <div class="col-md-2 title">创建部门</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="创建部门" name="createDept" value="${packingList.createDept}">
                    </div>

                    <div class="col-md-2 title">创建日期</div>
                    <div class="col-md-4 data">
                        <div class="input-group date">
                            <div class="input-group-addon">
                                <i class="fa fa-calendar"></i>
                            </div>
                            <input type="text" placeholder="创建日期"  name="createTime" class="form-control pull-right"
                                   value="<fmt:formatDate value="${shipping.createTime}" pattern="yyyy-MM-dd"/>" id="createTime">
                        </div>
                    </div>
                </div>
            </form>
        </div>

        <!-- .box-body -->
        <div class="box box-primary">
            <div class="box-header with-border">
                <h3 class="box-title">装箱单列表</h3>
            </div>

            <div class="box-body">

                <!-- 数据表格 -->
                <div class="table-box">
                    <!--数据列表-->
                    <table id="dataList" class="table table-bordered table-striped table-hover dataTable">
                        <thead>
                        <tr>
                            <th class="" style="padding-right:0px;">

                            </th>
                            <th class="sorting">装箱单号</th>
                            <th class="sorting">报运单</th>
                            <th class="sorting">卖方</th>
                            <th class="sorting">买方</th>
                            <th class="sorting">创建人</th>
                            <th class="sorting">发票号</th>
                            <th class="sorting">发票日期</th>
                            <th class="sorting">唛头</th>
                            <th class="sorting">描述</th>
                            <th class="sorting">状态</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${pack.list}" var="o" varStatus="status">
                            <tr>
                                <td><input type="checkbox" id="PackId" name="PackId" value="${o.packingListId}" onclick="changeShipId()"/></td>

                                <td>${o.packingListId}</td>
                                <td>
                                        ${o.exportNos}
                                </td>
                                <td>${o.seller }</td>
                                <td>${o.buyer }</td>
                                <td>${o.createBy }</td>
                                <td>${o.invoiceNo}</td>
                                <td><fmt:formatDate value="${o.invoiceDate}" pattern="yyyy-MM-dd"/></td>
                                <td>${o.marks}</td>
                                <td>${o.descriptions}</td>
                                <td><c:if test="${o.state==0}">草稿</c:if>
                                    <c:if test="${o.state==1}"><font color="green">已上报</font></c:if>
                                    <c:if test="${o.state==2}"><font color="red">已报运</font></c:if>
                                    <c:if test="${o.state==3}"><font color="red">已装箱</font></c:if>
                                    <c:if test="${o.state==4}"><font color="red">已委托</font></c:if>
                                    <c:if test="${o.state==5}"><font color="red">发票</font></c:if>
                                    <c:if test="${o.state==6}"><font color="red">财务统计</font></c:if>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <!--数据列表/-->
                    <!--工具栏/-->
                </div>
                <!-- 数据表格 /-->
            </div>
        </div>
        <!-- /.box-body -->

        <!--工具栏-->
        <div class="box-tools text-center">
            <button type="button" onclick='document.getElementById("editForm").submit()' class="btn bg-maroon">保存</button>
            <button type="button" class="btn bg-default" onclick="history.back(-1);">返回</button>
        </div>
        <!--工具栏/-->

    </section>
    <!-- 正文区域 /-->


</div>
<!-- 内容区域 /-->
</body>
<script src="../../../../plugins/datepicker/bootstrap-datepicker.js"></script>
<script src="../../../../plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>
<link rel="stylesheet" href="../../../../css/style.css">
<script>
    $('#loadingDate').datepicker({
        autoclose: true,
        format: 'yyyy-mm-dd'
    });
    $('#createTime').datepicker({
        autoclose: true,
        format: 'yyyy-mm-dd'
    });
    $('#limitDate').datepicker({
        autoclose: true,
        format: 'yyyy-mm-dd'
    });
</script>
</html>