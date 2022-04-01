window.ip={
	server_ip: "http://172.23.154.233:8888"
}

window.url={
	register_url: ip.server_ip + "/user/register",
	login_url: ip.server_ip + "/user/login",
	upload_url: ip.server_ip + "/user/upload",
	search_url: ip.server_ip + "/user/search",
	check_friend_url: ip.server_ip + "/user/check",
	apply_url: ip.server_ip + "/apply/add" ,
	get_applies_url: ip.server_ip + "/apply/applies" ,
	process_applies_url: ip.server_ip + "/apply/update",
	friend_list_url: ip.server_ip + "/api/friends",
	find_user_url: ip.server_ip + "/user/findUserById"
}

window.util={
	ajax:function(param){
		plus.nativeUI.showWaiting();
		mui.ajax(param.url,{
			data:param.data,
			dataType:'json',//服务器返回json格式数据
			type:'post',//HTTP请求类型	              
			success:function(data){
				plus.nativeUI.closeWaiting();
				param.success(data);
			},
			error:function(xhr,type,errorThrown){
				//异常处理
				plus.nativeUI.closeWaiting();
				plus.nativeUI.toast("服务端出现异常");
			}
		});
	},
	setUser:function(user){
		plus.storage.setItem("login_user", JSON.stringify(user));
	},
	getUser:function(){
		return JSON.parse(plus.storage.getItem("login_user"));
	},
	setFriendList:function(fList){
		plus.storage.setItem("friend_list", JSON.stringify(fList));
	},
	getFriendList:function(){
		return JSON.parse(plus.storage.getItem("friend_list"));
	}
}