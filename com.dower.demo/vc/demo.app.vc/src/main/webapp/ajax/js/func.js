

$(function(){
	$("#carInfoBtnId").click(function(){	
		var userId = sessionStorage.getItem('agentId');
		$.ajax({
		    type : "POST",
		    url : getUrl("agent/getAgentDetail.do?params="+userId),
		    dataType : "json",
		    success : function(data) {
		    	if(data.success == true){
		    		if(data.result.success==true){
		    			window.location.href=getUrl("ajax/content/record/carInfo.html");	
		    		}else{
		    			$('.lady').show();
		    			$('.popbox').show();
		    			//提示完善信息
		    			$(".qd").click(function(){
		    				window.location.href=getUrl("ajax/content/user/salemanInfo.html");
		    			});
		    		}
		    	}else{
		    		console.log("异常错误！");			    		
		    	}
		    }
	 	});
	});
	
})
