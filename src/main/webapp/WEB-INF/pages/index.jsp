<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%
    String staticHost = "/html";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>宝宝的记录工具</title>
    <link type="image/x-icon" rel="shortcut icon" href="<%=staticHost %>/images/pikaqiu.jpeg">
    <link rel="stylesheet" href="<%=staticHost %>/public/angular/angular-growl.min.css" />
    <link rel="stylesheet" href="<%=staticHost %>/public/jquery/jquery-ui.css" />
    <link rel="stylesheet" href="<%=staticHost %>/public/bootstrap/bootstrap.min.css" />
    <link rel="stylesheet" href="<%=staticHost %>/public/bootstrap/bootstrap-theme.css" />
    <link rel="stylesheet" href="<%=staticHost %>/public/Font-Awesome/css/font-awesome.min.css" />
    <link rel="stylesheet" href="<%=staticHost %>/public/angular/datetimepicker/css/datetimepicker.css" />
    <link rel="stylesheet" href="<%=staticHost %>/stylesheet/style.css" />
</head>
<body>
<nav class="nav navbar-fixed-top">
    <ul class="list-inline">
        <li class="home">
            <a href="#">
                <i class="fa fa-home"></i>
            </a>
        </li>
        <li>
            <a href="" ui-sref="addAccount">
                <i class="fa fa-pencil"></i>
                <span>录入密码</span>
            </a>
        </li>
        <li>
            <a href="" ui-sref="accountList">
                <i class="fa fa-list"></i>
                <span>查看密码</span>
            </a>
        </li>
        <li>
            <a href="" ui-sref="addRemindDate">
                <i class="fa fa-pencil"></i>
                <span>录入时间</span>
            </a>
        </li>
        <li>
            <a href="" ui-sref="remindDateList">
                <i class="fa fa-list"></i>
                <span>查看时间</span>
            </a>
        </li>
    </ul>
</nav>
<div class="container">
    <div growl></div>
    <div ui-view></div>
</div>
<nav class="nav navbar-fixed-bottom text-center">
    <span>©2018 大师兄</span>
</nav>
<!--requireJs-->
<script src="<%=staticHost %>/public/requireJs/require.js" data-main="<%=staticHost %>/app.js" defer="defer"></script>
</body>
</html>