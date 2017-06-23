var ulhtml_1 =  '<li><span class="iodatsp"><i>*</i>单位名称：</span><input type="text" id="VC2_INSURED" placeholder="北京天彩保险经纪公司"><em class="iconphoto"></em></li>' + 
					'<li><span class="iodatsp"><i>*</i>社会信用码：</span><input type="text" id="VC2_CODE" placeholder="11091843757253G1"></li>' + 
					'<li><span class="iodatsp"><i>*</i>手机号码：</span><input type="number" id="VC2_TEL" placeholder="18618479448" id="phone"></li>' + 
					'<li><span class="iodatsp">邮箱地址：</span><input type="text" id="VC2_MAIL" placeholder="taicai@ihandy.cn"></li>' ;


var ulhtml_0 =  '<li><span class="iodatsp"><i>*</i>被保险人：</span><input id="VC2_INSURED" placeholder="请输入" type="text"><em class="iconphoto"></em></li>' + 
					'<li><span class="iodatsp"><i>*</i>证件类型：</span><p class="selep"><input value="身份证" type="text"><span><select id="NUM_CARDTYPE"><option value="1">身份证</option><option value="2">驾驶证</option><option value="3">护照</option><option value="4">港澳通行证</option></select></span></p></li>' + 
					'<li><span class="iodatsp"><i>*</i>证件号码：</span><input id="VC2_CODE" placeholder="请输入" type="text"></li>' + 
					'<li><span class="iodatsp"><i>*</i>性别：</span><p class="selep"><input value="男" type="text"><span><select id="NUM_SEX"><option value="1">男</option><option value="0">女</option></select></span></p></li>' + 
					'<li><span class="iodatsp">手机号码：</span><input id="VC2_TEL" placeholder="请输入或自动带出" id="phone" type="number"></li>' ;
$(function(){
	$("#privateCar").click(function(){
		$("#businessCar").removeClass("sela");
		$("#privateCar").addClass("sela");
		$("#ulhtml").html(ulhtml_0);
	});
	$("#businessCar").click(function(){
		$("#privateCar").removeClass("sela");
		$("#businessCar").addClass("sela");
		$("#ulhtml").html(ulhtml_1);
	});
	initHtml();
	$(".lgbtnmb").click(function(){
		var carNature = "privateCar";
		if($('#businessCar').is('.sela')){
			carNature = "businessCar";
		}
		console.log(carNature);
		//封装被保人信息
		var insuredInfo = {};
		var VC2_INSURED = $("#VC2_INSURED").val();
		insuredInfo.VC2_INSURED = VC2_INSURED;
		var VC2_CODE = $("#VC2_CODE").val();
		insuredInfo.VC2_CODE = VC2_CODE;
		var VC2_TEL = $("#VC2_TEL").val();
		insuredInfo.VC2_TEL = VC2_TEL;
		if(carNature=="privateCar"){
			var NUM_CARDTYPE = $("#NUM_CARDTYPE").val();
			insuredInfo.NUM_CARDTYPE = NUM_CARDTYPE;
			var NUM_SEX = $("#NUM_SEX").val();
			insuredInfo.NUM_SEX = NUM_SEX;
		}else{
			var VC2_MAIL = $("#VC2_MAIL").val();
			insuredInfo.VC2_MAIL = VC2_MAIL;
		}
		sessionStorage["insuredInfo"] = JSON.stringify(insuredInfo);
		window.location.href="auth.html";
	});
	
	initCss();
	
	$('.citycar').click(function(){
		if($("#carCheck").attr('checked')=='checked'){
			var carInfo = stringToJson(sessionStorage["carInfo"]);
			var carNature = carInfo.carNature;
			var VC2_INSURED = carInfo.carOwner;
			var VC2_CODE = carInfo.cardNo;
			var VC2_TEL = carInfo.phone;
			if(carNature==1){
				$("#ulhtml").html(ulhtml_1);
				initCss();
				
			}else{
				$("#ulhtml").html(ulhtml_0);
				initCss();
				$("#NUM_CARDTYPE").val("1");
			}
			$("#VC2_INSURED").val(VC2_INSURED);
			$("#VC2_CODE").val(VC2_CODE);
			$("#VC2_TEL").val(VC2_TEL);
		}
	});
});
var initCss = function(){
	//css
	$('.selep span select').change(function(){
        $(this).parent('span').prevAll('input').val($(this).find("option:selected").text());
    });
	$('.checkp').click(function(){
        if($(this).children('span').hasClass('checkps1')){
            $(this).find('input').attr('checked',true);
            $(this).children('span').removeClass('checkps1');
        }else{
            $(this).find('input').attr('checked',false);
            $(this).children('span').addClass('checkps1');

        }
    });
}
var initHtml = function(){
	var carInfo = stringToJson(sessionStorage["carInfo"]);
	var carNature = carInfo.carNature;
	console.log(carNature);
	
	if(carNature==1){
		$("#ulhtml").html(ulhtml_1);
	}else{
		$("#ulhtml").html(ulhtml_0);
	}
}