var numAgentId="";
var telImei="";
//var clearId;
//var time = 0;
//var type=0;
$(function(){
	/* $('.lady').show();
     $('.issuesuccess').show();
     $('#mpId').html("即将进行录屏、录像、录音<br>请点<a>“确定”</a>进入录单流程");
	 $('#btnId').click(function(){
		 $('.lady').hide();
	     $('.issuesuccess').hide();
	     getLoginUser("","onLonginRecordError");
	 });*/
	 getLoginUserNew("","");
})

function showMessage(message){
	$('.lady').show();
	$('.popbox').show();
	$('#messageId').html(message);
	  $('.popbox article .mqd').click(function () {
	      $('.lady').hide();
	      $('.popbox').hide();
	  })
}

function getLogin(){
	  /*   //打开双录时
		     function timeCountDown(){
		         time++;
		         if(time==60){
		        	 stopRecording("","",1);
		        	 time=0;
		        	 return;
		         }
		     }
//		     timeCountDown();
		     clearId =  setInterval(timeCountDown,1000);
			 document.getElementById("agencyTel").readOnly= "";*/
			 $('.lgbtn').click(function(){
					var reg = /^1[3|4|5|7|8][0-9]\d{8}$/;
					var agencyTel = $('#agencyTel').val();
					var logPwd = $('#logPwd').val();
					if(agencyTel == null || agencyTel==""){
						showMessage('请输入手机号码!');
						return ;
					}else if (!(reg.test(agencyTel))) {
						showMessage('请输入正确的手机号码!');
				        return ;
				    }else if(logPwd==null || logPwd==""){
				    	showMessage('请输入密码!');
						return;
					}else{
						 //getLocation();
						//获取手机信息
						telImei = $('#m_TelImei').val();
						if(telImei !=null && telImei !=""){
							login(agencyTel,logPwd,telImei);
						}else{
							showMessage('获取手机串号失败!');
							return;
						}
					}
				});
}

function login(agencyTel,logPwd,telImei){
	var locationX=$('#l_X').val();
	var locationY=$('#l_Y').val();
	var loginAdd =$('#l_Address').val();
	
	var jsonParam ={"agencyTel":agencyTel,"logPwd": logPwd,"telImei": telImei,"locationX": locationX,"locationY": locationY,"loginAdd": loginAdd};
	var params = JSON.stringify(jsonParam);  
	$.ajax({
	    type : "POST",
	    url : getUrl("agent/login.do?params="+params),
	    dataType : "json",
	    success : function(data) {
	    	if(data.success == true){
	    		if(data.result.success==true){
	    			numAgentId= data.result.user.numAgentId;
	    			var userId= data.result.user.vc2agentId;
	    			sessionStorage.setItem('userId',userId);
	    			sessionStorage.setItem('agentId',numAgentId);
	    			sessionStorage.setItem('telImei',telImei);
	    			sessionStorage.setItem('agencyMail',data.result.user.agencyMail);
	    			sessionStorage.setItem('userInfo',JSON.stringify(data.result.user));
	    			window.location.href=getUrl("func.html");
		    		/*var extraParam = {"numId":data.result.user.numAgentId,"flag":"1","telImei":telImei,"chrInUpload":"1","recType":"0"};
		    		var extra = JSON.stringify(extraParam); 
		    		var upURL=getUrl("agent/upLoadUrl.do");
		    		//结束双录
		    		stopRecording(extra,upURL,0);*/
		  		   
	    		}else{
	    			if(data.result.msg == 0){
	    				setTimeout(function(){showMessage("手机串号不匹配!");}, 1000);
	    			}else {
	    				setTimeout(function(){showMessage("账号无效,请联系客服!");}, 1000);
	    			}
	    		}
	    	}else{
	    		setTimeout(function(){showMessage(data.msg);}, 1000);
	    	}
	    }
 	});
}

//注册
function reg(){
	window.location.href=getUrl("ajax/content/user/regist.html");
	/*type = 1;
	stopRecording("","",1);*/
}

//找回密码
function retPwd(){
	window.location.href=getUrl("ajax/content/user/retrievePwd.html");
	/*type = 2;
	stopRecording("","",1);*/
}

//解析定位、手机信息
function  setLoginNew(result){
	var obj = eval('('+result+')');
	if(obj.code == 0){
		$('#l_X').val(obj.result.longitude);
		$('#l_Y').val(obj.result.latitude);
		$('#l_Address').val(obj.result.address);
		$('#m_TelImei').val(obj.result.uuid);
		getLogin();
	}else{
		setTimeout(function(){showMessage(obj.message);}, 1000);
	}
}

/*//解析定位信息
function setLocation(result){
	var obj = eval('('+result+')');
	$('#l_X').val(obj.result.longitude);
	$('#l_Y').val(obj.result.latitude);
	$('#l_Address').val(obj.result.address);
	$('#l_Code').val(obj.code);
	$('#l_Message').val(obj.message);
	var codeId = $('l_Code').val();
	if(codeId !=0){
		$('#l_X').val("0");
		$('#l_Y').val("0");
	}
}

//解析手机信息
function setMobileInfo(result){
	var obj = eval('('+result+')');
	$('#m_TelImei').val(obj.result.uuid);
	$('#m_Code').val(obj.code);
}

//打开双录
function setStartRecording(result){
	var obj = eval('('+result+')');
	$('#sl_Message').val(obj.message);
	$('#sl_Code').val(obj.code);
}

//结束双录
function setStopRecording(result){
	time = 0;
	var obj = eval('('+result+')');
	if(obj.code == 0){
		if(numAgentId !=""){
			//登录成功时停止调用
			clearInterval(clearId);
			 //跳转首页面功能页面
			window.location.href=getUrl("func.html");
		}else{
			getLogin();
		}
	}
	
	if(obj.code == 1){
		 $('.lady').show();
	     $('.issuesuccess').show();
	     $('#mpId').html("");
	     $('#mpId').html("录屏、录像超时 <br>请点<a>“确定”</a>重新开始双录");
		 $('#btnId').click(function(){
			 $('.lady').hide();
		     $('.issuesuccess').hide();
		     setTimeout(getLoginUser("","onLonginRecordError"),2000);
		 });
	}
	
	if(type == 1){
		window.location.href=getUrl("ajax/content/user/regist.html");
	}else if(type == 2){
		window.location.href=getUrl("ajax/content/user/retrievePwd.html");
	}
}*/

