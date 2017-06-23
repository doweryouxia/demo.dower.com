	var agentId = sessionStorage.getItem('userId');
	if(agentId==null){
		agentId =1;
	}
$(function(){   	
	//数据回显
	var insuredInfo = sessionStorage["insuredInfo"];
	if(insuredInfo){
		insuredInfo = stringToJson(insuredInfo);
		$("#vc2Insured").val(insuredInfo.VC2_INSURED);
		$("#vc2Code").val(insuredInfo.VC2_CODE);
		$("#vc2Tel").val(insuredInfo.VC2_TEL);
		$("#vc2Mail").val(insuredInfo.VC2_MAIL);
		var cardFront = sessionStorage.getItem('idCardFrontPath');
		$("#idCardFront").attr("src","data:image/png;base64,"+cardFront);
		$("#vc2FrontPath").val(cardFront);
		var cardBack = sessionStorage.getItem('idCardBackPath');
		$("#idCardBack").attr("src","data:image/png;base64,"+cardBack);
		$("#vc2BackPath").val(cardBack);
	}else{
		//默认显示车主姓名
		var carInfo = sessionStorage["carInfo"];
		carInfo = stringToJson(carInfo);
		$("#vc2Insured").val(carInfo.carOwner);
		$("#vc2Code").val(carInfo.cardNo);
	}
	
	//身份证正面
	$("#frontBtn").click(function(){	
		var vc2Code = $("#vc2Code").val();
		var extraParam = {"agentId":agentId,"idcardType":"2","imgType":"1","vc2Code":vc2Code};
		var extra = JSON.stringify(extraParam);
		scanIDCard(1,extra);
	});	
	//身份证反面
	$("#backBtn").click(function(){	
		var vc2Code = $("#vc2Code").val();
		var extraParam = {"agentId":agentId,"idcardType":"2","imgType":"2","vc2Code":vc2Code};
		var extra = JSON.stringify(extraParam);
		scanIDCard(2,extra);
	});	
	
	//被保险人信息确认
	$("#confirmBtn").click(function(){
		confirmInsuredInfo();
	});
})

/**
 * 身份证
 * @param result
 */
function setScanIDCard(result){		
	var obj = eval('('+result+')');
	if(obj.code == 0){			
		var imagePath = obj.result.imgStr;
		var idtype = obj.result.type;	

		if(idtype==1){
			sessionStorage.setItem('idCardFrontPath',imagePath);
			$("#idCardFront").attr("src","data:image/png;base64,"+imagePath);
			if(obj.result!=null){
				$("#vc2Insured").val(obj.result.name);
				$("#vc2Code").val(obj.result.code);
			}			 			
			$("#vc2FrontPath").val(imagePath);
		}else{
			sessionStorage.setItem('idCardBackPath',imagePath);
			$("#idCardBack").attr("src","data:image/png;base64,"+imagePath);
			$("#vc2BackPath").val(imagePath);
		}
		//setTimeout(function(){alert(idtype+"-"+obj.result.name);}, 1000);				
	 }
}

/**
 * 被保险人信息确认
 */
function confirmInsuredInfo(){	
	var param = {};
	
	var vc2FrontPath = $("#vc2FrontPath").val();	
	if(vc2FrontPath==""){
		showMessage("请上传身份证正面！");
		//return;
	}
	var vc2BackPath = $("#vc2BackPath").val();
	if(vc2BackPath==""){
		showMessage("请上传身份证反面！");
		//return;
	}
	var vc2Insured = $("#vc2Insured").val();
	if(vc2Insured==""){
		showMessage("请输入姓名！");
		return;
	}
	var reg= /^[\u2E80-\u9FFF]+$/;
	if (!reg.test(vc2Insured)) //判断是否符合正则表达式
	{
		showMessage("姓名只能输入汉字！");
		return;
	}
	var vc2Code = $("#vc2Code").val();
	if(vc2Code==""){
		showMessage("请输入身份证号！");
		return;
	}
	if(!IdentityCodeValid(vc2Code)){
		showMessage("请输入正确的身份证号！");
		return;
	}
	var vc2Tel = $("#vc2Tel").val();
	if(vc2Tel==""){
		showMessage("请输入手机号码！");
		return;
	}
	if(!checkMobile(vc2Tel)){
		showMessage("手机号码格式输入错误！");
		return;
	}
	var vc2Mail = $("#vc2Mail").val();	
	if(vc2Mail==""){
		showMessage("请输入邮箱！");
		return;
	}
	if(!checkEmail(vc2Mail)){
		showMessage("邮箱格式输入错误！");
		return;
	}
	param.agentId = agentId;
	param.VC2_INSURED = vc2Insured;
	param.VC2_CODE = vc2Code;
	param.numType = 0;
	param.numCardtype = 1;
	param.VC2_TEL = vc2Tel;
	param.VC2_MAIL = vc2Mail;
	param.vc2FrontPath = vc2FrontPath;
	param.vc2BackPath = vc2BackPath;	
	
	$.ajax({
		type:"post",
		url:getUrl("insuredInfo/confirmInsuredInfo.do"),		
		dataType:"json",
		data:param,
		success:function(data){
			data = eval(data);	
			if(data.success){
				param.vc2FrontPath = data.vc2FrontPath;
				param.vc2BackPath = data.vc2BackPath;
				console.log(JSON.stringify(param));
			}
		}
	});
	sessionStorage.setItem("insuredInfo",JSON.stringify(param));
	window.location.href="../order/confirmInsureInfoy.html";
	
}

function isCardNo(card){  
   // 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X  
   var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;  
   if(reg.test(card) === false){  
       return  false;  
   }
   return true;
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
