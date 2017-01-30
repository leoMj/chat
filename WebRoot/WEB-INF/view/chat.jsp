<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${path }/css/chat.css" rel="stylesheet">
<link rel="stylesheet" href="${path }/plugin/layui/css/layui.css">
<script src="${path }/plugin/jquery-3.1.1.min.js"></script>
<title>web-chat</title>
</head>
<body>
	<div id="chat">
		<div class="sidebar">
			<div class="m-card">
				<header> <img class="avatar" width="40" height="40"
					alt="Coffce" src="${u.url}">
				<p class="name">${u.nickName}</p>
				</header>
				<footer> <input class="search" placeholder="search user..."></footer>
				<div class="m-group">
					<table style="width: 100%">
						<thead>
							<tr>
								<td align="center"><i ac="${path}/user/recentCaches/"
									sessionItem="recent" class="layui-icon m-icon"
									style="font-size: 15px; color: #5FB878;">&#xe606;</i></td>
								<td align="center"><i ac="${path}/user/friends/"
									sessionItem="friend" class="layui-icon m-icon"
									style="font-size: 15px; color: white;">&#xe613;</i></td>
								<td align="center"><i ac="${path}/user/users/"
									sessionItem="user" class="layui-icon m-icon"
									style="font-size: 15px; color: white;">&#xe613;</i></td>
							</tr>
						</thead>
					</table>
				</div>
			</div>
			<!--v-component-->
			<div class="m-list">
				<ul>
					<!--v-for-start-->
					<c:forEach items="${recentInteractive }" var="interactiver">
						<li id="${interactiver.Id }"
							dataid="${interactiver.Id }"
							dataurl="${interactiver.url }"
							datanick="${interactiver.nickName }"><img
							class="avatar" width="30" height="30" alt="示例介绍"
							src="${interactiver.url}">
							<p class="name">${interactiver.nickName }</p></li>
					</c:forEach>
					<!--v-for-end-->
				</ul>
			</div>
			<!--v-component-->
		</div>
		<div class="main">
			<div style="padding: 15px 15px; text-align: center;">
				<span id="targetNick"></span> <span
					style="position: absolute; right: 15px; top: 15px; font-size: 0; line-height: initial;">
					<a
					style="cursor: pointer; osition: relative; width: 16px; height: 16px; margin-left: 10px; font-size: 12px;"
					href="javascript:logout();"><i class="layui-icon">&#x1006;</i></a>
				</span>
			</div>
			<div class="m-message">
				<ul>
					<!--v-for-start-->
					<li><p class="time">
							<span>2017-1-20</span>
						</p>
						<div class="main">
							<img class="avatar" width="30" height="30" src="${u.url }">
							<div class="text">
								项目简介：<br />1、这是一个基于jfinal+layui+webSocket构建的简单chat示例，用户信息保存mysql数据库，聊天记录保存在ehcache缓存中。<br />2、用户头像使用WordPress头像服务器，配置头像请email登录到http://www.gravatar.com/avatar/进行配置
							</div>
						</div></li>
					<!--v-for-end-->
				</ul>
				<div id="msgEnd" style="height:0px; overflow:hidden"></div>
			</div>
			<!--v-component-->
			<div class="m-text">
				<textarea id="content" disabled="disabled"
					placeholder=" Enter发送|Ctrl+Enter换行 "></textarea>
			</div>
			<!--v-component-->
		</div>
	</div>
	<input type="hidden" id="uid" value="${u.Id }" />
	<input type="hidden" id="path" value="${path }" />
	<input type="hidden" id="gravatar" value="${u.url }" />
	<script src="${path }/js/chat.js"></script>
</body>
</html>