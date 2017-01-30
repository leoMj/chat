//tj:好友列表切换时保存上一次的对象
var tj, icon, path = $("#path").val(), uid = $("#uid").val(), websocket, tid;
//关闭WebSocket连接
function closeWebSocket() {
	websocket.close();
}
function h(data, lr, url) {
	var html = '<li><p class="time"><span>'
			+ (lr > 0 ? "刚刚" : data.formatTime)
			+ '</span></p ><div class="main ' + (lr > 0 ? "self" : "")
			+ '"><img class="avatar" width="30" height="30" src="'
			+ (lr > 0 ? $("#gravatar").val() : url)
			+ '"><div class="text">' + (lr > 0 ? data : data.content)
			+ '</div></div></li>'
	$(".m-message ul").append(html);
	document.getElementById("msgEnd") .scrollIntoView();
}
function cache(s, url) {// from database pre
	$.ajax({
		url : path + "/chat/caches/" + s,
		type : 'get',
		async : true,
		success : function(data) {
			if(typeof(data)=="string"){
				websocket.close();
				window.location.reload();
			}
			if (data != null && data.length > 0) {
				for (var i = 0; i < data.length; i++) {
					if (data[i].sender == uid)
						h(data[i].content, 1, url);
					else
						h(data[i], 0, url);
				}
			}
		}
	});
}
//单击好友发送消息
function f(obj){
	$(".m-message ul").html("");
	// 好友列表状态切换
	tid = $(obj).attr("dataid");
	if (tj != null) {
		$(tj).attr("class", "");
	} else {
		$("#content").removeAttr("disabled");
	}
	tj = obj;
	// receive:有消息但是未接收，active：鼠标选中状态
	$(obj).attr("class", "active");
	// 修改消息输入框状态，可写状态
	$("#targetNick").text($(obj).attr("datanick"));
	// 消息输入框获取焦点
	$("#content").focus();
	// 获取选中好友的交互消息
	cache(tid + "-" + uid, $(obj).attr("dataurl"));
	sessionStorage.setItem("recent", "");
}
function list(o) {// 获取所有用户
	if (icon != o) {
		$(icon).css("color", "white");
		$(o).css("color", "#5FB878");
		icon = o;
		var html = sessionStorage.getItem($(o).attr("sessionItem"));
		if (null != html && html != "") {
			$(".m-list ul").html(html);
		} else {
			var nl = "";
			$.ajax({url : $(o).attr("ac"),
				type : 'get',
				async : true,
				success : function(data) {
					if(typeof(data)=="string"){
						websocket.close();
						window.location.reload();
					}
					if (data != null && data.length > 0&&typeof(data)=="object") {
						for (var i = 0; i < data.length; i++) {
							nl += '<li onclick="javascript:f(this)" id="' + data[i].Id
									+ '" dataid="' + data[i].Id + '" dataurl="'
									+ data[i].url + '" datanick="'+ data[i].nickName
									+ '"><img class="avatar" width="30" '
									+ 'height="30" alt="头像" src="'
									+ data[i].url + '"><p class="name">'
									+ data[i].nickName + '</p ></li>';
						}
						sessionStorage.setItem($(o).attr("sessionItem"), nl);
					}
					$(".m-list ul").html(nl);
				}
			});
		}
	}
}
// 消息内容特殊字符过滤
function s(msg) {
	return msg.replace(/[\t]/g, "").replace(/[ ]/g, "&nbsp;").replace(/</g,
			"&lt;").replace(/>/g, "&gt;").replace(/[\r\n]/g, "<br/>");// 去掉空格
}
function logout() {
	closeWebSocket();
	window.location.href = path + "/user/logout";
}
// 发送消息
function send() {
	if (tid == null || tid == "")
		return;
	var msg = s($("#content").val());
	websocket.send(JSON.stringify(tid + ',' + msg));
	h(msg, 1);
	$("#content").val("");
}
$(document).ready(function() {
	// 将消息显示在网页上
			// 判断当前浏览器是否支持WebSocket
			if ('WebSocket' in window) {
				websocket = new WebSocket(
						"ws://"+window.location.host+path+"/chatSocket/" + uid);
				// 连接成功建立后才进行
				$(".m-list ul li").on('click', function() {
					f(this);
				});
				$(".m-icon").on('click', function() {
					list(this);
				});
				
				// 设置默认
				icon = $(".m-icon")[0];
				// 消息输入框键盘响应事件，enter键发送消息，ctrl+enter键为消息换行
				$("#content").keydown(function(event) {
					if (event.ctrlKey && event.keyCode == 13) {
						$("#content").val($("#content").val() + "\r\n");
						// 返回false是为了阻止浏览器的默认键盘执行动作，如果不加会在消息发送的时候换行
						return false;
					} else if (event.keyCode == 13) {// 13等于回车键(Enter)键值,ctrlKey
					// 发送消息
						send();
						return false;// 阻止js默认时间（防止enter键后又出现换行）
					}
				});
			} else {
				h('当前浏览器 不支持websocket，建议使用Goole Chrome浏览器', 0);
			}
			// 连接发生错误的回调方法
			websocket.onerror = function() {
				// h("WebSocket连接发生错误", 0);
				window.location.href = path + "/user/logout";
			};

			// 连接成功建立的回调方法
			websocket.onopen = function() {
				// setMessageInnerHTML("WebSocket连接成功");
			}

			// 接收到消息的回调方法
			websocket.onmessage = function(event) {
				var obj = JSON.parse(event.data);
				if (obj.sender != tid) {
					$("#" + obj.sender).attr("class", "receive");
				} else {
					h(obj, 0, $("#" + tid).attr("dataurl"));
				}
			}

			// 连接关闭的回调方法
			websocket.onclose = function() {
				// h("WebSocket连接关闭", 0);
				//window.location.href = path + "/user/logout";
			}

			// 监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
			window.onbeforeunload = function() {
				websocket.close();
			}
		});