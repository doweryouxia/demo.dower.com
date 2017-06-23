/**
 * 个人中心
 */

$(function(){   
	var userInfo = JSON.parse(sessionStorage.getItem('userInfo'));
	var unPayCount = 0;
	var agentId =  1441;
	if(userInfo!=null){
		agentId = userInfo.numAgentId;
		$("#agentName").html(userInfo.agentName);
		$("#agentCode").html(userInfo.agencyCode);
	}
	$.ajax({
		type:"post",
		url:getUrl("mine/index.do"),		
		dataType:"json",
		data:{"agentId":agentId},
		success:function(data){
			//data = eval(data);
			console.log(data);	
			if(data.success){
				$("#agentName").html(data.agentInfo.agentName);
				$("#agentCode").html(data.agentInfo.agencyCode);
				unPayCount = data.unPayCount;			
				$("#unPayCount").html(data.unPayCount);				
			}
		}
	});
	
	//我的信息
	$("#myInfo").click(function(){	
		var url = getUrl("ajax/content/mine/personInfo.html");		
		location.href=url;
	});
	
	//我的订单
	$("#myOrder").click(function(){	
		var url = getUrl("ajax/content/mine/myorder.html?UN_PAY_COUNT="+unPayCount);		
		location.href=url;
	});
	
	//设置
	$("#setUp").click(function(){	
		var url = getUrl("ajax/content/mine/setup.html");		
		location.href=url;
	});

	
	//退出系统
	$("#quitBtn").click(function(){
		$.ajax({
			type:"post",
			url:getUrl("mine/logout.do"),		
			dataType:"json",			
			success:function(data){			
				console.log(data);	
				if(data.success){
					for(var i=0;i<sessionStorage.length;i++){
						var key = sessionStorage.key(i);
						sessionStorage.removeItem(key);
					}
					location.href = getUrl("login.html")			
				}else{
					alert("操作失败！");
				}
			}
		});
	});
})


