var lw = ('WebSocket' in window) ? '<form class="layui-form"><div class="layui-form-item"><input type="text" id="email" placeholder="邮箱" autocomplete="off" class="layui-input"></div><div class="layui-form-item"><input type="password" id="password" placeholder="密码" autocomplete="off" class="layui-input"></div><div class="layui-form-item" style="text-align:center"><button class="layui-btn" style="width:48%" lay-submit lay-filter="login">登录</button><button class="layui-btn" style="width:48%" lay-submit lay-filter="register">注册</button></div></form>':'<blockquote class="layui-elem-quote layui-quote-nm">当前浏览器 不支持websocket，建议使用Goole Chrome浏览器</blockquote>';
layui.define([ 'layer', 'form' ],
				function(exports) {
					var layer = layui.layer, form = layui.form(),$ = layui.jquery;
					var verfiy= function(username, password) {
						if ($.trim(username) == "") {
							layer.tips("请填写唯一标识符", $("#email"));// ||/^\d+\d+\d$/.test($.trim($("#username").val())||
							return false;
						}
						if (/^\d+\d+\d$/.test(username)) {
							layer.tips("唯一标识不能全为数字", $("#email"));// ||)||
							return false;
						}
						if (/(^\_)|(\__)|(\_+$)/.test(username)) {
							layer.tips("标识符不能不能出现下划线", $("#email"));// ||)||
							return false;
						}
						if (!(/^[\S]{6,12}$/.test(password))) {
							layer.tips("密码最少6位", $("#password"));// ||)||
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
								content : lw,
								closeBtn : 0,// 用户不可手动关闭
								scrollbar : false
							});
					// 输入框获取焦点等待输入
					$("#email").focus();
					form.on('submit(register)', function(data) {
						window.location.href = $("#path").val() + "/register";
						return false;
					});
					form.on('submit(login)', function(data) {
						username = $("#email").val();
						password = $("#password").val();
						if (!verfiy(username, password))
							return false;
						var xhr = $.ajax({
							url : $("#path").val() + "/user/login/",
							type : 'post',
							async : true,
							data : {
								username : username,
								password : password
							},
							success : function(response, options) {
								if (response.result) {
									window.location.href = $("#path").val()
											+ "/chat";
								} else {
									layer.tips(response.msg, $("#email"));
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
					exports('login', {}); // 注意，这里是模块输出的核心，模块名必须和use时的模块名一致
				});