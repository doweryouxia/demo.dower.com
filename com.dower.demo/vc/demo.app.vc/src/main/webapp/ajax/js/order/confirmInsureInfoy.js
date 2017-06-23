var insurcompanyList = [{'numInsurcompanyid':1,'vc2Insurcompanyshortname':'太平洋'},{'numInsurcompanyid':3,'vc2Insurcompanyshortname':'人保'}];
$(function(){
	//被保人信息
	var insuredInfo = sessionStorage["insuredInfo"];
	
	insuredInfo = stringToJson(insuredInfo);
	$("#vc2Insured").html(insuredInfo.VC2_INSURED);
	$('#vc2Tel').html(insuredInfo.VC2_TEL);
	$('#vc2Code').html(insuredInfo.VC2_CODE);
	//报价信息
	var chooseInsurTypeInfos = localStorage["chooseInsurTypeInfos"];
	chooseInsurTypeInfos = stringToJson(chooseInsurTypeInfos);
	var insurcompanyList = localStorage["insurcompanyList"];
	insurcompanyList = stringToJson(insurcompanyList)
	var quoteResult = sessionStorage["quoteResult"];
	quoteResult = stringToJson(quoteResult);
	var componyId = sessionStorage["componyId"];
	var carInfo = sessionStorage["carInfo"];
	carInfo = stringToJson(carInfo);
	
	var htmlStr = "";
	var bizTotalPrice = stringToJson(quoteResult[componyId]).bizTotalPrice;
	var trafficTotalPrice = stringToJson(quoteResult[componyId]).trafficTotalPrice;
	var taxTotalPrice = stringToJson(quoteResult[componyId]).taxTotalPrice;
	var total = Number(bizTotalPrice)+Number(trafficTotalPrice)+Number(taxTotalPrice);
	//时间
    var forceDate = carInfo.forceDate;
    var forceDate2=new Date(forceDate);
    forceDate2.setFullYear(forceDate2.getFullYear()+1);
    forceDate2.setDate(forceDate2.getDate()-1);
    forceDate2 = getStringFormatDate(forceDate2);
    //forceDate2=forceDate2.toLocaleString();
    //forceDate2 = forceDate2.replace(/(\d{4}).(\d{1,2}).(\d{1,2}).+/mg, '$1-$2-$3');
    var bizDate = carInfo.bizDate;
    var bizDate2=new Date(bizDate);
    bizDate2.setFullYear(bizDate2.getFullYear()+1);
    bizDate2.setDate(bizDate2.getDate()-1);
    bizDate2 = getStringFormatDate(bizDate2);
    //bizDate2=bizDate2.toLocaleString();
    //bizDate2 = bizDate2.replace(/(\d{4}).(\d{1,2}).(\d{1,2}).+/mg, '$1-$2-$3');
	$.each(insurcompanyList,function(i,v){
		if(v.numInsurcompanyid==componyId){
			htmlStr += '<h3 class="confirm"><a href="###" id="updateInsur"><i></i>修改</a>'+v.vc2Insurcompanyshortname+'</h3>'+ 
			'<ul class="isiul">' + 
			'<li class="isili1"><em class="isiem1">￥'+total.toFixed(2)+'</em><span class="isisp1">保费合计</span><span class="isisp2"></span></li>' +
			'<li class="isili1"><em class="isiem1 isiem2">￥'+taxTotalPrice.toFixed(2)+'</em><span class="isisp1">车船税</span><span class="isisp2">同意交税</span></li>' + 
			'<li class="isili1"><em class="isiem1 isiem2">￥'+trafficTotalPrice.toFixed(2)+'</em><span class="isisp1">交强险</span><span class="isisp2">'+carInfo.forceDate+'至'+forceDate2+'</span></li>' + 
			'<li class="isili1"><em class="isiem1 isiem2">￥'+bizTotalPrice.toFixed(2)+'</em><span class="isisp1">商业险</span><span class="isisp2">'+carInfo.bizDate+'至'+bizDate2+'</span></li>' ;
			var buJiMianNum = 0;
			var buJiMianStr = "";
			$.each(chooseInsurTypeInfos,function(iInsur,vInsur){
				var insurName = vInsur.insurName;
				if(insurName.indexOf("不计免")==-1){
					htmlStr += '<li><em class="isiem1">￥'+stringToJson(quoteResult[componyId])[vInsur.vc2insrekey]["expense"].toFixed(2)+'</em><span class="isisp1">'+vInsur.insurName+'</span><span class="isisp2">'+(vInsur.numTypeRate==0?"投保":vInsur.numTypeRate)+'</span></li>';
				}else{
					buJiMianStr += "、" + insurName.substring(3,insurName.length);
					buJiMianNum += stringToJson(quoteResult[componyId])[vInsur.vc2insrekey]["expense"];
					//console.log(insurName.substring(3,insurName.length));
				}
			});
			htmlStr += '<li><p>不计免赔（'+buJiMianStr.substring(1,buJiMianStr.length)+'）</p><em class="isiem1">￥'+buJiMianNum.toFixed(2)+'</em><span class="isisp1">&nbsp;</span><span class="isisp2"></span></li>' + 
			'</ul>' ;
		}
	});
	$("#issueinfo").append(htmlStr);
	//carinfo
	var carInfo = sessionStorage["carInfo"];
	carInfo = stringToJson(carInfo);
	
	var ifNewCar = carInfo.newCar;
	
	if(ifNewCar==1){
		var carinfoHtml = '' + 
		'<p><span class="isspanl">车主姓名：</span><span class="isspanr">'+carInfo.ownerName+'</span></p>' + 
		'<p><span class="isspanl">车主身份证号：</span><span class="isspanr">'+carInfo.ownerCredentialsNo+'</span></p>' + 
		'<p><span class="isspanl">车辆识别代码：</span><i class="isspanr">'+carInfo.vinNo+'</i></p>' + 
		'<p><span class="isspanl">车辆品牌：</span><i class="isspanr">'+carInfo.brandModle+'</i></p>' + 
		'<p><span class="isspanl">发动机号：</span><span class="isspanr">'+carInfo.engineNo+'</span></p>' + 
		'<p><span class="isspanl">车辆型号：</span><i class="isspanr">'+carInfo.brand+'</i></p>' + 
		/*'<p><span class="isspanl">车座：</span><span class="isspanr" >'+carInfo.vc2Seat+'座</span></p>' + */
		'<p><span class="isspanl">购车日期：</span><span class="isspanr" id="datRegDate">'+carInfo.registerDate+'</span></p>' ;
	}else{
		var carinfoHtml = '<p><span class="isspanl">车牌号：</span><span class="isspanr">'+carInfo.licenseNo+'</span></p>' + 
		'<p><span class="isspanl">行驶证车主信息：</span><span class="isspanr">'+carInfo.ownerName+'</span></p>' + 
		'<p><span class="isspanl">车主身份证号：</span><span class="isspanr">'+carInfo.ownerCredentialsNo+'</span></p>' + 
		'<p><span class="isspanl">车辆识别代码：</span><i class="isspanr">'+carInfo.vinNo+'</i></p>' + 
		'<p><span class="isspanl">品牌型号：</span><i class="isspanr">'+carInfo.brandModle+'</i></p>' + 
		'<p><span class="isspanl">发动机号：</span><span class="isspanr">'+carInfo.engineNo+'</span></p>' + 
		'<p><span class="isspanl">车型：</span><i class="isspanr">'+carInfo.brand+'</i></p>' + 
		/*'<p><span class="isspanl">车座：</span><span class="isspanr" >'+carInfo.vc2Seat+'座</span></p>' +*/ 
		'<p><span class="isspanl">注册日期：</span><span class="isspanr" id="datRegDate">'+carInfo.registerDate+'</span></p>' ;
	}
	

	
	$("#carInfo").append(carinfoHtml);
	
	
	$('.confirmbtn').click(function(){
		var insurcompanyList = localStorage["insurcompanyList"];
		insurcompanyList = stringToJson(insurcompanyList);
		var companyList=new Array()
		$.each(insurcompanyList, function(i, elt) {
			companyList.push(elt.numInsurcompanyid);
		});
		
		var paramData = {};
		//保险公司
		//paramData.companyid = sessionStorage["componyId"];
		//车辆信息
		//paramData.carInfo = stringToJson(sessionStorage["carInfo"]);
		//险种信息
		//paramData.chooseInsurTypeInfos = stringToJson(localStorage["chooseInsurTypeInfos"]);
		//被保人信息
		//paramData.insuredInfo = stringToJson(sessionStorage["insuredInfo"]);
		//报价结果
		var bizTotalPrice = stringToJson(quoteResult[componyId]).bizTotalPrice;
		var trafficTotalPrice = stringToJson(quoteResult[componyId]).trafficTotalPrice;
		var taxTotalPrice = stringToJson(quoteResult[componyId]).taxTotalPrice;
		var total = Number(bizTotalPrice)+Number(trafficTotalPrice)+Number(taxTotalPrice);
		
		
		var carInfo = sessionStorage["carInfo"];
		carInfo = stringToJson(carInfo);
		paramData.salesManPhone="15555551646";   //代理人手机号
		paramData.actualPayAmount=total;   //实收价格
		paramData.enrolldate=carInfo.registerDate;//车辆注册日期
		var userId = sessionStorage.getItem("userId");
		if(userId==null){
			userId = "e42c56c58e4b2e92f33f0df4035a5310";
		}
		paramData.userid=userId;   //
		paramData.detailedcompareid=sessionStorage["intentionId"]+"_"+sessionStorage["componyId"];   //意向单id_保险公司id
		paramData.insurancecompany = sessionStorage["componyId"];  //保险公司id
		paramData.ownerName=carInfo.ownerName;   //
		paramData.certificateno=carInfo.ownerCredentialsNo; 
		paramData.owerPhone="13564105569"
		paramData.address="1111";
		paramData.remark="1111";
		paramData.payType=2;
		paramData.deliveryDate="2017-07-07"; //配送日期
		paramData.certificateType=carInfo.ownerCredentialsType;
		paramData.newCar=0;
		//被保人信息
		var insuredInfo = sessionStorage["insuredInfo"];
		insuredInfo = stringToJson(insuredInfo);
		$("#vc2Insured").html(insuredInfo.VC2_INSURED);
		$('#vc2Tel').html(insuredInfo.VC2_TEL);
		$('#vc2Code').html(insuredInfo.VC2_CODE);
		paramData.insuredPersonName=insuredInfo.VC2_INSURED;
		paramData.insuredPersonIdCardNo=insuredInfo.VC2_CODE;
		paramData.insuredPersonPhone=insuredInfo.VC2_TEL;
		paramData.insuredPersonCertificateType="1";
		paramData.insuredPersonProvince="1";
		paramData.insuredPersonCity="1";
		paramData.insuredPersonLocation="1";
		//投保人信息
		paramData.insuranceApplicantName=insuredInfo.VC2_INSURED;
		paramData.insuranceApplicantIdCardNo=insuredInfo.VC2_CODE;
		paramData.insuranceApplicantPhone=insuredInfo.VC2_TEL;
		paramData.insuranceApplicantCertificateType="1";
		//受益人
		paramData.rightsName=insuredInfo.VC2_INSURED;
		paramData.rightsIdCardNo=insuredInfo.VC2_CODE;
		paramData.rightsPhone=insuredInfo.VC2_TEL;
		paramData.rightsCertificateType="1";
		//邮寄信息
		paramData.deliveryAddressee="1";
		paramData.deliveryContact="1";
		paramData.deliveryProvince="1";
		paramData.deliveryCity="1";
		paramData.deliveryLocation="1";
		paramData.maintainChannel="0";
		paramData.maintainRate="0.5";
		paramData.maintainFee="1";
		
		$.ajax({
		    type : "POST",
		    url : getUrl("quote/addOrder.do"),
		    dataType : "json",
		    data : {params:JSON.stringify(paramData)},
		    success : function(data) {
		    	//alert(JSON.stringify(data))
		    	
		    	if(data.errorCode == "A0000000"){
		    		sessionStorage["orderId"] = data.responseBody.orderId;
		    		
		    		var checkResult = {      
		    			       biPolicyNo:'qwerqwer',
		    			       ciPolicyNo:'12341234',
		    			       status:3,  // 0 核保中 1核保失败 2核保成功 3 转人工 
		    			       result:0
		    			    }
		    		sessionStorage["checkResult"] = JSON.stringify(checkResult);
		    		//commitCheck();
		    		window.location.href="../inform/confirmInsure.html";
		    	}else{
		    	}
		    }
	 	}); 
	});
	
	$("#updateCarInfo").click(function(){
		if(confirm("确定修改信息？"))
			window.location.href="../record/carInfo.html";
	});
	
	$("#updateInsur").click(function(){
		if(confirm("确定修改信息？"))
			window.location.href="../order/insurChoice.html";
	});
	
	$("#updateInsureInfo").click(function(){
		if(confirm("确定修改信息？"))
			window.location.href="../record/insureInfo.html";
	});
	
	
})

function commitCheck() {
	var paramData = {};
	paramData.userId="9767070dcb704d92ba40359020a44d6e"; 
	paramData.orderId = "2722";
	$.ajax({
	    type : "POST",
	    url : getUrl("quote/commitCheck.do"),
	    dataType : "json",
	    data : {params:JSON.stringify(paramData)},
	    success : function(data) {
	    	console.log(JSON.stringify(data));
	    	if(data.errorCode=='A0000000'){
	    		getCheckResult();
	    	}else{
	    	}
	    }
 	});
}
	
function getCheckResult(){
	var paramData = {};
	paramData.orderId = "2722";
	$.ajax({
	    type : "POST",
	    url : getUrl("quote/getCheckResult.do"),
	    dataType : "json",
	    data : {params:JSON.stringify(paramData)},
	    success : function(data) {
	    	alert(JSON.stringify(data))
	    	if(data.success == true){
	    		/*sessionStorage["orderId"] = data.body.orderId;
	    		//alert("订单插入成功！");
	    		window.location.href="../inform/confirmInsure.html";*/
	    	}else{
	    	}
	    }
 	});
}