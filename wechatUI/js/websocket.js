var websocket = null;

window.ws = {
	init: function(param) {
		initWebSocket(param);
	}
}

function initWebSocket(param) {
	if (window.WebSocket) {
		websocket = new WebSocket("ws://172.23.154.233:8084/");

		websocket.onopen = function() {
			//heart() 			// 开始发送心跳
			//closeConn() 		// 定时器关闭连接
			param.onopen()
		}

		websocket.onclose = function() {
			//clearInterval(heartTimeout);
			//reConn(param);
			param.onclose();
		}

		websocket.onmessage = function(resp) {
			var data = resp.data
			// if (data == "heart") {
			// 	console.info(data)
				// clearTimeout(timeout); // 清除定时器
				// closeConn()
				// return;
			// }
			param.onmessage(data)
		}
		
	} else {
		alert("不支持websocket");
	}
}

var heartTimeout = null
function heart() {
	heartTimeout = setInterval(function() {
		var msg = {
			"type": 2,
			"id": "heart"
		}
		sendObj(msg);
	}, 5000)
}

var timeout = null;
function closeConn() {
	timeout = setTimeout(function() {
		websocket.close()
	}, 10000);
}

function reConn(param) {
	// 断开后每5秒重连一次
	setTimeout(function() {
		initWebSocket(param)
	}, 5000)
} 

// 发送对象
function sendObj(obj){
	sendStr(JSON.stringify(obj));
}

// 发送字符串
function sendStr(str){
	websocket.send(str);
}
