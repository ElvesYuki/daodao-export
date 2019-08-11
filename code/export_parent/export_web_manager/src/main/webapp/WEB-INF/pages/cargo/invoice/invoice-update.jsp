<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
    function changeId() {
        var id = getShippingCheckId()
        $ ("#shipId").val(id)
    }

    function getShippingCheckId() {
        var size = $("input:radio:checked").length;
        if(size!=1) {
            return ;
        }else {
            return $('input[type=radio]:checked').val();
        }

    }
    function submit00(){
        var id = getShippingCheckId()
        if(id) {
            document.getElementById("editForm").submit()
        }else{
            alert("请勾选待处理的记录，且每次只能勾选一个")
        }


    }
</script>
<body>
<div id="frameContent" class="content-wrapper" style="margin-left:0px;">
    <!-- 内容头部 -->
    <section class="content-header">
        <h1>
            货运管理
            <small>发票管理详情</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="all-admin-index.html"><i class="fa fa-dashboard"></i> 首页</a></li>
            <li><a href="all-order-manage-list.html">货运管理</a></li>
            <li class="active">发票管理详情</li>
        </ol>
    </section>
    <!-- 内容头部 /-->

    <!-- 正文区域 -->
    <section class="content">

        <!--订单信息-->
        <div class="panel panel-default">
            <div class="panel-heading">新增发票</div>
            <%--

            --%>
            <form id="editForm" action="${ctx}/cargo/invoice/update.do" method="post" enctype="multipart/form-data">
                <input type="text" name="invoiceId" value="${invoice.invoiceId}">
                <div class="row data-type" style="margin: 0px">
                    <%--<div class="col-md-2 title">发票号</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control"  id="shipId" placeholder="发票号" name="invoiceId" >
                    </div>--%>

                    <div class="col-md-2 title">blno</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="blno" name="blNo" value="${invoice.blNo}">
                    </div>

                    <div class="col-md-2 title">合同号</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="合同号" name="scNo" value="${invoice.scNo}">
                    </div>

                    <div class="col-md-2 title">trade_term</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="tradeTerms" name="tradeTerms" value="${invoice.tradeTerms}">
                    </div>

                    <div class="col-md-2 title">状态</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="状态" name="state" value="${invoice.state}">
                    </div>

                    <div class="col-md-2 title">创建人</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="创建人" name="createBy" value="${invoice.createBy}">
                    </div>

                    <div class="col-md-2 title">创建部门</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="创建部门" name="createDept" value="${invoice.createDept}">
                    </div>

                    <div class="col-md-2 title">创建时间</div>
                    <div class="col-md-4 data">
                        <div class="input-group date">
                            <div class="input-group-addon">
                                <i class="fa fa-calendar"></i>
                            </div>
                            <input type="text" placeholder="创建时间"  name="signingDate" class="form-control pull-right"
                                   value="<fmt:formatDate value="${invoice.createTime}" pattern="yyyy-MM-dd"/>" id="signingDate">
                        </div>
                    </div>
                </div>
            </form>
        </div>
        <!--订单信息/-->

        <!--工具栏-->
        <div class="box-tools text-center">
            <button type="button" onclick="submit00()" <%--onclick='document.getElementById("editForm").submit()'--%> class="btn bg-maroon">保存</button>
            <button type="button" class="btn bg-default" onclick="history.back(-1);">返回</button>
        </div>
        <!--工具栏/-->

    </section>
    <!-- 正文区域 /-->

    <section class="content">

        <!-- .box-body -->
        <div class="box box-primary">
            <div class="box-header with-border">
                <h3 class="box-title">委托单列表</h3>
            </div>

            <div class="box-body">

                <!-- 数据表格 -->
                <div class="table-box">
                    <!--数据列表-->
                    <table id="dataList" class="table table-bordered table-striped table-hover dataTable">
                        <thead>
                        <tr>
                            <td class="tableHeader"><input type="radio" name="selid" ></td>
                            <th class="sorting">海运/空运</th>
                            <th class="sorting">货主</th>
                            <th class="sorting">装运港</th>
                            <th class="sorting">转船港</th>
                            <th class="sorting">卸货港</th>
                            <th class="sorting">份数</th>
                            <th class="text-center">扼要说明</th>
                            <th class="text-center">运输要求</th>
                            <th class="sorting">复核人</th>
                            <th class="sorting">创建人</th>
                            <th class="sorting">创建部门</th>
                        </tr>
                        </thead>
                        <tbody class="tableBody" >
                        ${links }


                        <c:forEach items="${ship.list}" var="o" varStatus="status">
                            <tr class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'" >
                                <td><input type="radio" id="shippingOrder" name="id"
                                           value="${o.shippingOrderId}" onclick="changeId()"/></td>
                                <td>${o.orderType}</td>
                                <td>${o.shipper}</td>
                                <td>${o.portOfLoading}</td>
                                <td>${o.portOfTrans}</td>
                                <td>${o.portOfDiscarge}</td>
                                <td>${o.copyNum}</td>
                                <td>${o.remark}</td>
                                <td>${o.specialCondition}</td>
                                <td>${o.checkBy}</td>
                                <td>${o.createBy}</td>
                                <td>${o.createDept}</td>
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
                    <jsp:param value="${ctx}/cargo/invoice/list.do?invoiceId=${invoiceId}" name="pageUrl"/>
                </jsp:include>
            </div>
            <!-- /.box-footer-->
        </div>

    </section>

</div>
<!-- 内容区域 /-->
</body>

</html>