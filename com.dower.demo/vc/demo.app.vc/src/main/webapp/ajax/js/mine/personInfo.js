/**
 * 个人信息
 */
var agentId = sessionStorage.getItem("agentId");
if(agentId==null){
	agentId =  1441;
}
$(function(){   

	/*var userInfo = JSON.parse(sessionStorage.getItem('userInfo'));
	if(userInfo!=null){		
		$("#agentName").html(userInfo.agentName);
		$("#agentCode").html(userInfo.agencyCode);
		var imgPath = userInfo.imgPath;
		if(imgPath==null){
			var headImg = sessionStorage.getItem("headImg");
			if(headImg!=null){
				$("#headImgPath").attr("src","data:image/png;base64,"+headImg);	
			}
		}else{
			$("#headImgPath").attr("src",imgUrl+imgPath);
		}	
	}*/
	$.ajax({
		type:"post",
		url:getUrl("mine/personInfo.do"),		
		dataType:"json",
		data:{"agentId":agentId},
		success:function(data){
			data = eval(data);
			var headImg = sessionStorage.getItem("headImg");			
			//console.log(data);	
			if(data.status){				
				$("#agentName").html(data.agentInfo.agentName);
				$("#agentCode").html(data.agentInfo.agencyCode);			
			}			 
			if(headImg==null || headImg=="" || headImg==undefined){
				var imgPath = sessionStorage.getItem("imgPath");
				if(imgPath==null || imgPath==undefined || imgPath=='undefined'){
					imgPath = data.imgPath;
					sessionStorage.setItem("imgPath",imgPath);
				}
				$("#headImgPath").attr("src","data:image/png;base64,"+imgPath);
			}else{
				$("#headImgPath").attr("src","data:image/png;base64,"+headImg);				
			}			
		}
	});
	
	$("#headImgPath").click(function(){
		$(".list").show();		
		setTimeout(function(){			
			$(".slide").show();
			$(".slide").slideDown();
		},300);	
				
	});
	
	//相册
	$("#albumBtn").click(function(){		
		changeImage("",0);
	});	
	//拍照
	$("#photoBtn").click(function(){		
		changeImage("",1);
	});	
	//点击遮罩层
	$(".list").click(function(){		
		$(".slide").slideUp();	
		setTimeout(function(){			
			$(".list").hide();
		},300);									
	});
	//取消
	$("#cancelBtn").click(function(){		
		$(".slide").slideUp();	
		setTimeout(function(){			
			$(".list").hide();
		},300);									
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
	    	closeMsg();
	    	if(data.success == true){
	    		//alertMsg("上传成功!")
	    		$("#headImgPath").attr("src","data:image/png;base64,"+sessionStorage.getItem('headImg'));
	    		//location.href=getUrl("ajax/content/mine/personInfo.html");		
	    	}
	    },
	    error:function(){
	    	
	    }
 	});
}

function alertMsg(msg){
	$(".prom").html(msg);
	$(".prom").show();
	$(".lady").show();
}
function closeMsg(){
	$(".prom").html("");
	$(".prom").hide();
	$(".lady").hide();
}
