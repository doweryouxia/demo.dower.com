$(function(){
	//获取险种信息
	$.ajax({
	    type : "POST",
	    url : getUrl("quote/getInsurTypeList.do"),
	    dataType : "json",
	    async: false,
	    success : function(data) {
	    	if(data.success == true){
	    		setInsurs(data);
	    	}else{
	    		alert("异常错误!");
	    	}
	    }
 	});
	//获取所有保险公司
	$.ajax({
		type : "POST",
	    url : getUrl("quote/getCompanyList.do"),
	    dataType : "json",
	    async: false,
	    success : function(data) {
	    	if(data.success == true){
	    		setCompany(data);
	    	}else{
	    		alert("异常错误!");
	    	}
	    }
 	});
	
	$("#quote").click(function(){
		var carInfo = stringToJson(sessionStorage["carInfo"]);
		carInfo.forceDate = $("#forceDate").val();
		carInfo.bizDate = $("#bizDate").val();
		sessionStorage["carInfo"] = JSON.stringify(carInfo);
		var chooseInsurTypeInfos = new Array();
		var companyList = new Array();
		var isChooseForce = 0;
		var isChooseBiz = 0;
		//较强险
		if($("#forceInsurType").attr("checked")){
			isChooseForce = 1;
			var insurType = {};
			insurType.numTypeid = 1;
			insurType.numTypeRate = 0;
			insurType.insurName = "交强险";
			chooseInsurTypeInfos.push(insurType);
			var insurType = {};
			insurType.numTypeid = 12;
			insurType.numTypeRate = 0;
			insurType.insurName = "车船税";
			chooseInsurTypeInfos.push(insurType);
		}
		//商业险
		
		$("#bizUl li").each(function(){
			if($(this).find(".seleInsurtype").attr("checked")){
				isChooseBiz = 2;
				var numTypeRate = $(this).find(".numTypeRate").val();
				var insurType = {};
				insurType.numTypeid = $(this).find(".numTypeid").val();
				if(typeof(numTypeRate)=="undefined"){
					insurType.numTypeRate = 0;
				}else{
					insurType.numTypeRate = numTypeRate;
				}
				var insurName = $(this).find('.instit').html();
				insurType.insurName = insurName;
				chooseInsurTypeInfos.push(insurType);
			}
		});
		$("#bujimianUl .bluep").each(function(){
			var insurType = {};
			insurType.numTypeid = $(this).attr("id");
			insurType.numTypeRate = 0;
			insurType.insurName = $(this).html();
			chooseInsurTypeInfos.push(insurType);
		});
		sessionStorage["isChoose"] = isChooseForce + isChooseBiz;
		localStorage["chooseInsurTypeInfos"] = JSON.stringify(chooseInsurTypeInfos);
		
		$(".company span").each(function(){
			companyList.push($(this).attr("id"));
		});
		getQuotes();
	});
	
	var carInfo = sessionStorage["carInfo"];
	carInfo = stringToJson(carInfo);
	if(carInfo.newCar=='1'){
		$("#xubaoInfo").hide();
	}
	
});

function getQuotes(){
	var insurcompanyList = localStorage["insurcompanyList"];
	insurcompanyList = stringToJson(insurcompanyList);
	var companyList=new Array()
	$.each(insurcompanyList, function(i, elt) {
		companyList.push(elt.numInsurcompanyid);
	});
	
	var paramData = {};
	paramData.companyList = companyList;
	paramData.carInfo = stringToJson(sessionStorage["carInfo"]);
	paramData.chooseInsurTypeInfos = stringToJson(localStorage["chooseInsurTypeInfos"]);
	$.ajax({
	    type : "POST",
	    url : getUrl("quote/getQuoteResult.do"),
	    dataType : "json",
	    data : {params:JSON.stringify(paramData)},
	    success : function(data) {
	    	if(data.success == true){
	    		sessionStorage["quoteResult"] = JSON.stringify(data.body);
	    		setQuoteResult();
	    	}else{
	    		alert("异常错误!");
	    	}
	    }
 	});
}
//显示报价结果
function setQuoteResult(){
	var quoteResult = stringToJson(sessionStorage["quoteResult"]);
	var insurcompanyList = localStorage["insurcompanyList"];
	insurcompanyList = stringToJson(insurcompanyList);
	$.each(insurcompanyList, function(i, elt) {
		var companyId = elt.numInsurcompanyid;
		var quoteValue = quoteResult[companyId]["total"]
		$("#company_quote_"+elt.numInsurcompanyid).html(quoteValue);
	});
	
	//进入报价详情
	$(".company li").click(function(){
		var companyid = $(this).find("span").attr("id");
		companyid = companyid.substr(companyid.length-1);
		sessionStorage["companyid"] = companyid;
		window.location.href="quoteDetail.html";
	});
}
//显示保险公司列表 <li><em>87600.00</em><span id="company_1">中国人保财产保险公司</span></li>
function setCompany(data){
	var insurcompanyList = data.body.insurcompanyList;
	localStorage["insurcompanyList"] = JSON.stringify(insurcompanyList);
	var companyHTML = "";
	$.each(insurcompanyList, function(i, elt) {
		companyHTML += '<li><em id="company_quote_'+elt.numInsurcompanyid+'"></em><span id="company_'+elt.numInsurcompanyid+'">'+elt.vc2Insurcompanyshortname+'</span></li>';
	});
	$("#companyList").append(companyHTML);
}
//显示险种信息
function setInsurs(data){
	var insurTypeList = data.body.insurTypeList;
	var insurrateList = data.body.insurrateList;
	var insurTypeHTML = "";
	var insurBujimianHTML = "";
	$.each(insurTypeList, function(i, elt) {
		if(elt.numInsurtype==2){
//			console.log(JSON.stringify(elt));
//			console.log(JSON.stringify(elt.numInsurid+"======"+elt.vc2Insurname));
			
			insurTypeHTML += '<li><div class="insdiv">'; 
			console.log(elt.numIsdefault);
			if(elt.numIsdefault==1){
				insurTypeHTML += '<p class="checkp"><span class=""></span><input class="seleInsurtype" type="checkbox" checked="checked"></p>';
			}else{
				insurTypeHTML += '<p class="checkp"><span class="checkps1"></span><input class="seleInsurtype" type="checkbox"></p>';
			}
			
			insurTypeHTML += '<input type="hidden" value="'+elt.numInsurid+'" class="numTypeid"><span class="instit itf1">'+elt.vc2Insurname+'</span>' + 
			'<span class="insh"></span></div>' ;
			
			if(elt.numHasrate==1){
				insurTypeHTML += '<div class="insdiv2">' + 
				'<span class="insd2sp">2500.00</span>'; 
				$.each(insurrateList, function(i, rate) {
					if(rate.numInsurid==elt.numInsurid&&rate.numInsurrateid==elt.numDefaultrate){
						insurTypeHTML +='<span class="insd2sp1">保额：</span><p class="insd1p insd2p selep"><input type="text" value="'+rate.vc2Insurratecontent+'"><span><select class="numTypeRate">';
					}
				});
				$.each(insurrateList, function(i, rate) {
					if(rate.numInsurid==elt.numInsurid){
						//console.log(rate.numInsurrateid+"=========="+elt.numDefaultrate);
						if(rate.numInsurrateid==elt.numDefaultrate){
							insurTypeHTML += '<option selected="selected">'+rate.vc2Insurratecontent+'</option>';
						}else{
							insurTypeHTML += '<option>'+rate.vc2Insurratecontent+'</option>';
						}
					}
				});
				insurTypeHTML += '</select></span></p>' + 
				'</div>'; 
			}
			
		}
	});
	
	$("#bizUl").append(insurTypeHTML);
    //<p class="checkp" id="notlose"><span class=""></span><input type="checkbox" checked="checked"></p>
    insurBujimianHTML+='<li><div class="insdiv"><em class="insm insmf1">650.00</em>' + 
    '' + 
    '<span class="instit itf1">不记免赔险</span>' + 
    '<span class="insh"></span></div>' + 
    '<dl class="insdiv3">' + 
    '<dd>' ;
    $.each(insurTypeList, function(i, elt) {
    	if(elt.numInsurtype==3){
    		insurBujimianHTML += '<p class="bluep" id="'+elt.numInsurid+'">'+elt.vc2Insurname+'</p>';
    	}
    });
    insurBujimianHTML+='</dd>' + 
    '<dt>详情</dt>' + 
    '</dl>' + 
    '</li>';
   
    
    $("#bujimianUl").append(insurBujimianHTML);
	
	//复选按钮
    $('.checkp').click(function(){
        if($(this).children('span').hasClass('checkps1')){
            $(this).find('input').attr('checked',true);
            $(this).children('span').removeClass('checkps1');
        }else{
            $(this).find('input').attr('checked',false);
            $(this).children('span').addClass('checkps1');

        }
    });
    $('.insdiv3 dd p').click(function(){
        if($(this).hasClass('bluep')){
           $(this).removeClass('bluep');
        }else{
           $(this).addClass('bluep');
        }
    });
    $('.selep span select').change(function(){
        $(this).parent('span').prevAll('input').val($(this).find("option:selected").text());
    });
    
}