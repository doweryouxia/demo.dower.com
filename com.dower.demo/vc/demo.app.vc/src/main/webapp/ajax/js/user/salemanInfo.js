var agentId = sessionStorage.getItem('agentId');
if(agentId==null){
	agentId =1442;
}
$(function(){	
	//身份证正面
	$("#frontBtn").click(function(){
		var extraParam = {"agentId":agentId,"idcardType":"1","imgType":"1"};
		var extra = JSON.stringify(extraParam);
		scanIDCard(1,extra);
	});	
	//身份证反面
	$("#backBtn").click(function(){	
		var extraParam = {"agentId":agentId,"idcardType":"1","imgType":"2"};
		var extra = JSON.stringify(extraParam);
		scanIDCard(2,extra);
	});	
	
	//代理人信息确认
	$("#confirmBtn").click(function(){
		confirmAgentInfo();
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
			$("#vc2TrueName").val(obj.result.name);
			$("#vc2IdcardNum").val(obj.result.code); 
			$("#vc2Nation").val(obj.result.nation);
			$("#vc2Sex").val(obj.result.gender);
			$("#vc2Address").val(obj.result.address);
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
 * 代理人信息确认
 */
function confirmAgentInfo(){	
	var param = {};
	var vc2FrontPath = $("#vc2FrontPath").val();
	/*if(vc2FrontPath==""){
		showMessage("请上传身份证正面！");
		return;
	}*/
	var vc2BackPath = $("#vc2BackPath").val();
	/*if(vc2BackPath==""){
		showMessage("请上传身份证反面！");
		return;
	}*/
	var vc2TrueName = $("#vc2TrueName").val();
	if(vc2TrueName==""){
		showMessage("请输入姓名！");
		return;
	}
	var reg=/^[\u2E80-\u9FFF]+$/;//Unicode编码中的汉字范围
	if(!reg.test(vc2TrueName)){
		showMessage("只能输入汉字");
		return;
	}
	var vc2IdcardNum = $("#vc2IdcardNum").val();
	if(vc2IdcardNum==""){
		showMessage("请输入身份证号！");
		return;
	}
	if(!IdentityCodeValid(vc2IdcardNum)){
		showMessage("请输入正确的身份证号！");
		return;
	}
	var vc2Nation = $("#vc2Nation").val();
	var vc2Sex = $("#vc2Sex").val();
	var vc2Address = $("#vc2Address").val();
	
	param.agentId = agentId;
	param.vc2TrueName = vc2TrueName;
	param.vc2IdcardNum = vc2IdcardNum;
	if(vc2Nation){
		param.vc2Nation = vc2Nation;
	}
	if(vc2Sex){
		param.vc2Sex = vc2Sex;
	}
	if(vc2Address){
		param.vc2Address = vc2Address;
	}
	param.vc2FrontPath = vc2FrontPath;
	param.vc2BackPath = vc2BackPath;
	
	$.ajax({
		type:"post",
		url:getUrl("agentDetail/confirmAgentDetail.do"),		
		dataType:"json",
		data:param,
		success:function(data){
			data = eval(data);				
			//console.log(data);			
			if(data.success){			
				location.href = getUrl("ajax/content/record/carInfo.html");				
			}
		}
	});
}

/**
 * 校验身份证号
 * @param card
 * @returns {Boolean}
 */
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
