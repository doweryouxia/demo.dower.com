/**
 * Created by chenyingyan on 2017/1/17.
 */
var codeNum="-1";
var numbers = /^1\d{10}$/;
var sends = {
    checked:1,
    send:function(){
        var val = $('#agencyTel').val().replace(/\s+/g,""); //获取输入手机号码
        if( $('.regul li a,.iodath ul li a').attr('class') == 'send1'){
            if(!numbers.test(val) || val.length ==0){
            	showMessage("手机号为空，或格式错误");
                return false;
            }else{
            	$.ajax({
            	    type : "POST",
            	    url : getUrl("user/getCode.do?params="+val),
            	    dataType : "json",
            	    success : function(data) {
            	    	//console.log(data);
            	    	if(data.success == 1){
            	    		codeNum = data.msg;
            	    	}else{
            	    		showMessage(data.msg);
            	    	}
            	    }
             	});
            }
        }
        if(numbers.test(val)){
            var time = 30;
            $('.div-phone span').remove();
            function timeCountDown(){
                if(time==0){
                    clearInterval(timer);
                    $('.regul li a,.iodath ul li a').addClass('send1').removeClass('send0').html("发送验证码");
                    sends.checked = 1;
                    return true;
                }
                $('.regul li a,.iodath ul li a').html(time+"S后再次发送");
                time--;
                return false;
                sends.checked = 0;
            }
            $('.regul li a,.iodath ul li a').addClass('send0').removeClass('send1');
            timeCountDown();
            var timer = setInterval(timeCountDown,1000);
        }
    }
}

function showMessage(message){
	$('.lady').show();
	$('.popbox').show();
	$('#messageId').html(message);
	  $('.popbox article .mqd').click(function () {
	      $('.lady').hide();
	      $('.popbox').hide();
	  })
}



$('.lgbtn').click(function(){
	var agencyTel = $('#agencyTel').val().replace(/\s+/g,"");
	var logPwd = $('#logPwd').val();
	var logPwd1 = $('#logPwd1').val();
	var codeId = $("#codeId").val();
	
	if(!numbers.test(agencyTel) || agencyTel.length ==0){
		showMessage('手机号为空或格式错误!');
		return ;
	}else if(codeId != codeNum){
		showMessage('验证码错误，请重新输入!');
		return ;
	}else if(logPwd==null || logPwd=="" || logPwd1 == null || logPwd1==""){
		showMessage('请输入密码!');
		return ;
	}else if (logPwd !=logPwd1) {
		showMessage('俩次密码不一致!');
        return ;
    }else{
    	var jsonParam ={"agencyTel":agencyTel,"logPwd": logPwd};
		var params = JSON.stringify(jsonParam);  
		$.ajax({
		    type : "POST",
		    url :getUrl("user/retrievePwd.do?params="+params),
		    dataType:"json",
		    success : function(data) {
		    	if(data.success == true){
		    		if(data.result.success == true){
		    			window.location.href = getUrl('login.html');
			    	}else{
			    		if(data.result.msg == 0){
			    			showMessage("该手机号未分配账号!");
			    		}else if(data.result.msg == 2){
			    			showMessage("对不起!该手机号已关闭!");
			    		}else if(data.result.msg == 3){
			    			showMessage("对不起!该手机号已被加入黑名单!");
			    		}else{
			    			showMessage(data.result.msg);
			    		}
			    	}
		    	}else{
		    		showMessage(data.msg);
		    	}
		    }
	 	});
    }
})
