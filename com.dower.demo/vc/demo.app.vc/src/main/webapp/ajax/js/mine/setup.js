/**
 * 设置
 */
$(function(){
	
	//重置密码
	$("#retPwd").click(function(){	
		var url = getUrl("ajax/content/mine/restPsw.html");		
		location.href=url;
	});
	//关于我们
	$("#aboutus").click(function(){	
		var url = getUrl("ajax/content/mine/about.html");		
		location.href=url;
	});
	//重置密码
	$("#retPwdBtn").click(function(){		
		resetPwd();
	});
	//相册
	$("#albumBtn").click(function(){		
		changeImage("",0);
	});	
	//拍照
	$("#photoBtn").click(function(){		
		changeImage("",1);
	});	
	
});



/**
 * 设置头像
 * @param result
 */
function setHeadImg(result){
	var obj = eval('('+result+')');
	if(obj.code == 0){
		$(".slide").slideUp(1000);	
		$(".list").hide();		
		var imagePath = obj.result.imgStr;
		 sessionStorage.setItem('headImg',imagePath);		
		 var agentId = sessionStorage.getItem('agentId');
		 var jsonParam ={"imagePath":encodeURIComponent(imagePath),"agentId":agentId};
		 var params = JSON.stringify(jsonParam);  
		 $.ajax({
		    type : "POST",
		    url : getUrl("mine/selectHeadImg.do?params="+params),
		    dataType : "json",
		    beforeSend:function(){alertMsg("正在上传...")},
		    success : function(data) {
		    	if(data.success == true){		    		
		    		uploadHeadImg(data.agentId,data.imgPath);
		    		//location.href=getUrl("ajax/content/mine/personInfo.html");		    		
		    	}else{
		    		alertMsg("异常错误!");
		    	}
		    }
	 	});
	 }
}

/**
 * 头像上传服务器
 */
function uploadHeadImg(agentId,imgPath){	
	var jsonParam ={"imgPath":imgPath,"agentId":agentId};	
	var params = JSON.stringify(jsonParam);  
	$.ajax({
	    type : "POST",
	    url : getUrl("mine/uploadHeadImg.do?params="+params),
	    dataType : "json",	
	    beforeSend:function(){},
	    success : function(data) {
	    	if(data.success == true){
	    		alertMsg("上传成功!")
	    		location.href=getUrl("ajax/content/mine/personInfo.html");		
	    	}else{
	    		alertMsg("上传失败!");
	    	}
	    }
 	});
}

function alertMsg(msg){
	$(".prom").html(msg);
	$(".prom").show();
	$(".lady").show();
}

