layui.define([ 'layer', 'form' ],function(exports) {
var rw = ('WebSocket' in window) ? '<form class="layui-form"><div class="layui-form-item"><input type="text" id="email" placeholder="邮箱" autocomplete="off" class="layui-input"></div><div class="layui-form-item"><input type="password" id="password" placeholder="密码" autocomplete="off" class="layui-input"></div><div class="layui-form-item"><input type="password" id="repetition" placeholder="重复密码" autocomplete="off" class="layui-input"></div><div class="layui-form-item"><input type="text" id="nickName" placeholder="昵称" autocomplete="off" class="layui-input"></div><div class="layui-form-item" style="text-align:center"><button class="layui-btn" style="width:48%" lay-submit lay-filter="register">注册</button><button class="layui-btn" lay-submit lay-filter="login" style="width:48%">登录</button></div></form>':'<blockquote class="layui-elem-quote layui-quote-nm">当前浏览器 不支持websocket，建议使用Goole Chrome浏览器</blockquote>';
var layer = layui.layer, form = layui.form(),$ = layui.jquery;
var verfiy = function(email, password, repetition, nickName) {
	if ($.trim(email) == "") {
		layer.tips("请填写登录邮箱", $("#email"));// ||/^\d+\d+\d$/.test($.trim($("#username").val())||
		return false;
	}
	if (!(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/.test(email))) {
		layer.tips("邮箱格式不正确", $("#email"));// ||)||
		return false;
	}
	if (/(^\_)|(\__)|(\_+$)/.test(email)) {
		layer.tips("标识符不能不能出现下划线", $("#email"));// ||)||
		return false;
	}
	if (!(/^[\S]{6,12}$/.test(password)) || !(/^[\S]{6,12}$/.test(repetition))) {
		layer.tips("密码最少6位", $("#password"));// ||)||
		return false;
	}
	if (password != repetition) {
		layer.tips("密码不一致", $("#password"));
		return false;
	}
	if ($.trim(nickName) == "") {
		layer.tips("请填写昵称", $("#nickName"));// ||/^\d+\d+\d$/.test($.trim($("#username").val())||
		return false;
	}
	return true;
}
var loginwin = layer.open({
			type : 0,
			btn : 0,
			btnAlign : 'c',
		title : '<i class="layui-icon" style="font-size: 30px; color: #009688;">&#xe612;</i> ',
		area : [ '360px' ],
		content : rw,
		closeBtn : 0,// 用户不可手动关闭
		scrollbar : false
	});
// 输入框获取焦点等待输入
$("#email").focus();
form.on('submit(login)', function(data) {
window.location.href = $("#path").val() + "/user/register";
	return false;
});
form.on('submit(register)',function(data) {
		var email = $("#email").val(), 
		password = $("#password").val(),
		repetition = $("#repetition").val(),
		nickName = $("#nickName").val();
		if (!verfiy(email, password, repetition,nickName))
			return false;
		var xhr = $.ajax({
			url : $("#path").val() + "/user/register/",
			type : 'post',
			async : true,
			data : {
				email : email,
				password : password,
				repetition : repetition,
				nickName : nickName
			},
			success : function(response, options) {
				if (response.result) {
					window.location.href = $("#path").val()+ "/chat";
				} else {
					layer.tips(response.msg,$("#email"));
				}
			},
			beforeSend : function() {
				// loginwin.type=3;
				// loginwin.content='<i
				// class="layui-icon">&#xe63e;</i>';
			},
			error : function(data) {
				layer.msg(data.msg, {
					icon : 6
				});
			}
		});
		return false; // 阻止表单跳转。如果需要表单跳转，去掉这段即可。
		});
exports('register', {}); // 注意，这里是模块输出的核心，模块名必须和use时的模块名一致
});