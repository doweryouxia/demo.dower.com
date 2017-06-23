//var imgUrl = "http://124.127.240.51:8099/";
var imgUrl = "http://localhost:8080/";
function getUrl(url){
	return "http://localhost:8080/tcep.app.web/"+url;
	//return "http://124.127.240.51:8099/tcep.chexian.app/"+url;
}

/**
 * 底部菜单
 */
$(function(){
	//首页
	$("#indexBtn").click(function(){	
		var url = getUrl("func.html");		
		location.href=url;
	});
	
	//客户
	$("#customerBtn").click(function(){	
		
	});
	
	//发现
	$("#findBtn").click(function(){	
		
	});
	
	//我的
	$("#mineBtn").click(function(){	
		var url = getUrl("ajax/content/mine/person.html");
		location.href=url;
	});
})