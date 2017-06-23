/**
 * 重置密码
 */
var codeNum="";
var sends = {
    checked:1,
    send:function(){
        var numbers = /^1\d{10}$/;
        var val = $('#agencyTel').val().replace(/\s+/g,""); //获取输入手机号码
        if(!numbers.test(val) || val.length ==0){
        	showMessage("手机号为空，或格式错误");
            return false;
        }        
        if(numbers.test(val)){
            var time = 30;            
            $.ajax({
        	    type : "POST",
        	    url : getUrl("agent/getCode.do?params="+val),
        	    dataType : "json",
        	    success : function(data) {
        	    	//console.log(data);
        	    	if(data.success == 1){
        	    		codeNum = data.msg;
        	    		$('.div-phone span').remove();
        	            function timeCountDown(){
        	                if(time==0){
        	                    clearInterval(timer);
        	                    $('.regul li a,.iodath ul li a').addClass('send1').removeClass('send0').html("发送验证码");
        	                    sends.checked = 1;
        	                    codeNum="";
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
        	    	}else{
        	    		showMessage(data.msg);
        	    	}
        	    },
        	    error:function(){
        	    	showMessage("发送短信失败！");
        	    }
         	});
        }
    }
}

var reg = /^1[3|4|5|7|8][0-9]\d{8}$/;

$('#retPwdBtn').click(function() {
	var agencyTel = $('#agencyTel').val();
	var yzmCode = $("#yzmCode").val();
	var pwd = $("#pwd").val();
	var rePwd = $("#rePwd").val();
	if (agencyTel == null || agencyTel == "") {
		showMessage('请输入手机号码!');
		return;
	}
	if (!(reg.test(agencyTel))) {
		showMessage('请输入正确的手机号码!');
		return;
	}
	if(yzmCode==""){
		showMessage('请输入验证码!');
		return;
	}
	if(codeNum!=yzmCode){
		showMessage('验证码输入有误!');
		return;
	}
	if (pwd == null || pwd == "") {
		showMessage('请输入密码!');
		return;
	}
	if (pwd != rePwd) {
		showMessage("两次输入密码不一致！");
		return;
	}
	setTimeout(resetPwd(pwd,rePwd), 2 * 1000);
});
/**
 * 重置密码
 */
function resetPwd(pwd,rePwd) {	
	var agentId = sessionStorage.getItem('agentId');
	if(agentId==null){
		agentId=1421;
	}
	var params = {"agentId":agentId,"logPwd":pwd,"rePwd": rePwd};
	$.ajax({
		type : "post",
		url : getUrl("mine/resetPwd.do"),
		dataType : "text",
		data : params,
		success : function(data) {
			if (data == 0) {
				//showMessage("修改成功,请重新登录！");
				location.href = getUrl("login.html")
			} else if (data == 2) {
				showMessage("两次输入密码不一致！");
			} else {
				showMessage("修改失败！")
			}
		}
	});
}

function showMessage(message) {
	$('.lady').show();
	$('.popbox').show();
	$('#messageId').html(message);
	$('.popbox article .mqd').click(function() {
		$('.lady').hide();
		$('.popbox').hide();
	})
}