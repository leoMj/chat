<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>web Chat Version 1.0</title>
<link rel="stylesheet" href="${path }/plugin/layui/css/layui.css">
<script src="${path }/plugin/layui/layui.js"></script>
<style>.layui-layer-content{padding-bottom: 10px;}</style>
</head>
<body>
<input type="hidden" id="path" value="${path }" />
<script>
layui.config({
base : '${path}/js/' //你的模块目录
}).use('register'); //加载入口
</script>
</body>
</html>