var E3MALL = {
	checkLogin : function() {
		var _ticket = $.cookie("COOKIES_TOKEN");
		if (!_ticket) {
			return;
		}
		$
				.ajax({
					url : "http://localhost:8088/user/name/" + _ticket,
					dataType : "jsonp",
					type : "GET",
					success : function(d) {
						if (d.status == 200) {
							var username = d.data.username;
							var html = username
									+ "，欢迎来到宜立方购物网！<a  href='javascript:void(0)' onclick='loginOut(\""
									+ _ticket
									+ "\")' class='link-logout'>[退出]</a>";
							$("#loginbar").html(html);
						}
					}
				});
	}
}

$(function() {
	// 查看是否已经登录，如果已经登录查询登录信息
	E3MALL.checkLogin();
});

function loginOut(_ticket) {

	$.ajax({
		type : "GET",
		dataType : "jsonp",

		success : function(c) {
			if (c.status == 200) {

				location.reload();
			}
		},
		url : "http://localhost:8088/remove/user/" + _ticket
	})

	// $.ajax({
	// type : "POST",
	// dataType : "jsonp",
	// data : {
	// "token" : _ticket
	// },
	// success : function(c) {
	// if (c.status == 200) {
	//
	// location.reload();
	// }
	// },
	// url : "http://localhost:8088/remove/user"
	// })

}