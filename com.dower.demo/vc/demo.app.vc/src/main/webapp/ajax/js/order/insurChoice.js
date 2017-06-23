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
	//获取所有保险公NA0977司
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

	//chooseInsurTypeInfos
   /* var chooseInsurTypeInfos = localStorage["chooseInsurTypeInfos"];
    if(chooseInsurTypeInfos){
        chooseInsurTypeInfos = stringToJson(chooseInsurTypeInfos);
        //清空页面险种选择项
        selectInsurTypeRange('-1',0,0);
        $.each(chooseInsurTypeInfos,function (n,value) {
        	var numTypeRate = value.numTypeRate;
        	if(numTypeRate=='0'){
                numTypeRate = '投保'
			}
            selectInsurTypeRange(value.vc2insrekey,numTypeRate,0);
        });
        //显示时间
        var carInfo = stringToJson(sessionStorage["carInfo"]);
        $("#appDate").val(carInfo.forceDate);
        $("#appDateTime").val(carInfo.bizDate);
	}else{
        //显示虚报信息
        showRenewalInfo();
	}*/
    showRenewalInfo();


	$(".confirmbtn").click(function(){
		if(!$("#appDate").val()){
			alert("请选择交强险开始时间！");
			return;
		}
		var carInfo = stringToJson(sessionStorage["carInfo"]);
		carInfo.forceDate = $("#appDate").val();
		carInfo.bizDate = $("#appDateTime").val();
		sessionStorage["carInfo"] = JSON.stringify(carInfo);
		var chooseInsurTypeInfos = new Array();
		var quotePrice = {};
		var isChooseForce = 1;
		var isChooseBiz = 0;
		//较强险
		/*{
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
		}*/
		//商业险
		
		$("#bizUl li").each(function(){
			console.log("==="+$(this).find(".seleInsurtype").val()+"======"+$(this).find(".seleInsurtype").attr("vc2insrekey"));
			var vc2insrekey = $(this).find(".seleInsurtype").attr("vc2insrekey");
			var insurtypeVal = $(this).find(".seleInsurtype").val();
			if(insurtypeVal=="投保"){
				insurtypeVal = 1;
				isChooseBiz = 2;
				var numTypeRate = $(this).find(".numTypeRate").val();
				var insurType = {};
				insurType.numTypeid = $(this).find(".seleInsurtype").attr("numinsurid");
				insurType.numTypeRate = 0;
				var insurName = $(this).find('.salaslisp').html();
				insurType.insurName = insurName;
				insurType.vc2insrekey = vc2insrekey;
				chooseInsurTypeInfos.push(insurType);
			}else if(insurtypeVal=="不投保"){
				insurtypeVal = 0;
			}else if(insurtypeVal.indexOf("国产玻璃")>-1){
				isChooseBiz = 2;
				var numTypeRate = $(this).find(".numTypeRate").val();
				var insurType = {};
				insurType.numTypeid = $(this).find(".seleInsurtype").attr("numinsurid");
				insurType.numTypeRate = insurtypeVal;
				var insurName = $(this).find('.salaslisp').html();
				insurType.insurName = insurName;
				insurType.vc2insrekey = vc2insrekey;
				chooseInsurTypeInfos.push(insurType);
				insurtypeVal = 1;
			}else if(insurtypeVal.indexOf("进口玻璃")>-1){
				isChooseBiz = 2;
				var numTypeRate = $(this).find(".numTypeRate").val();
				var insurType = {};
				insurType.numTypeid = $(this).find(".seleInsurtype").attr("numinsurid");
				insurType.numTypeRate = insurtypeVal;
				var insurName = $(this).find('.salaslisp').html();
				insurType.insurName = insurName;
				insurType.vc2insrekey = vc2insrekey;
				chooseInsurTypeInfos.push(insurType);
				insurtypeVal = 2;
			}else if(insurtypeVal.indexOf("万")>-1){
				isChooseBiz = 2;
				var numTypeRate = $(this).find(".numTypeRate").val();
				var insurType = {};
				insurType.numTypeid = $(this).find(".seleInsurtype").attr("numinsurid");
				insurType.numTypeRate = insurtypeVal;
				var insurName = $(this).find('.salaslisp').html();
				insurType.insurName = insurName;
				insurType.vc2insrekey = vc2insrekey;
				chooseInsurTypeInfos.push(insurType);
				insurtypeVal = insurtypeVal.substring(0,insurtypeVal.length-1)+"0000";
				insurtypeVal = Number(insurtypeVal);
			}else{
				isChooseBiz = 2;
				var numTypeRate = $(this).find(".numTypeRate").val();
				var insurType = {};
				insurType.numTypeid = $(this).find(".seleInsurtype").attr("numinsurid");
				insurType.numTypeRate = insurtypeVal;
				var insurName = $(this).find('.salaslisp').html();
				insurType.insurName = insurName;
				insurType.vc2insrekey = vc2insrekey;
				chooseInsurTypeInfos.push(insurType);
				insurtypeVal = Number(insurtypeVal);
			}
			quotePrice[vc2insrekey] = insurtypeVal+"";
		});
		console.log(quotePrice);
		$("#bizUl li .bjmp").each(function(){
			var vc2insrekey = $(this).attr("vc2insrekey");
			var insurtypeVal = 0;
			if(!$(this).hasClass('bjmp1')){
				insurtypeVal = 1;
				var insurType = {};
				insurType.numTypeid = $(this).attr("numbujimid");
				insurType.numTypeRate = 0;
				insurType.insurName = $(this).attr("vc2insurname");
				insurType.vc2insrekey = $(this).attr("vc2insrekey");
				chooseInsurTypeInfos.push(insurType);
			}
			quotePrice[vc2insrekey] = insurtypeVal+"";
		});
		
		if(isChooseBiz==2){
			if(!$("#appDateTime").val()){
				alert("请选择商业险开始时间！");
				return;
			}
		}
		console.log(chooseInsurTypeInfos);
		sessionStorage["isChoose"] = isChooseForce + isChooseBiz;
		localStorage["chooseInsurTypeInfos"] = JSON.stringify(chooseInsurTypeInfos);
		localStorage["quotePrice"] = JSON.stringify(quotePrice);
		//将报价时间信息放到车辆信息中
		var carInfo = JSON.parse(sessionStorage.getItem("carInfo"));
		carInfo.forceDate = $("#appDate").val();
		carInfo.bizDate = $("#appDateTime").val();
		sessionStorage["carInfo"] = JSON.stringify(carInfo);
		getQuotes();
	});
	
	var carInfo = sessionStorage["carInfo"];
	carInfo = stringToJson(carInfo);
	if(carInfo.newCar=='1'){
		$("#xubaoInfo").hide();
	}
	
});

function showRenewalInfo() {
    var renewalInfo = sessionStorage["renewalInfo"];
    if(!renewalInfo){
    	return false;
	}
    renewalInfo = stringToJson(renewalInfo);
    //报价开始时间
	var carInfo = renewalInfo.carInfo;
	if(!carInfo){
		return false;
	}
    $('#appDate').val(carInfo.nextTrafficBeginDate);
    $('#appDateTime').val(carInfo.nextBizBeginDate);
    //上年险种信息
    var quotePrice = renewalInfo.quotePrice;
    if(quotePrice)
    	//清空页面险种选择项
        selectInsurTypeRange('-1',0,0);
		$.each(quotePrice,function (name,value) {
			if(value!='0'){
				selectInsurTypeRange(name,value,0);
			}
		});

}

function getQuotes(){
	//模拟车辆信息

	var carInfo = sessionStorage["carInfo"];
	console.log("carInfo==="+carInfo);
	carInfo = stringToJson(carInfo);
	//模拟投被保人信息
	var userInfo = {
	    "holderCredentialsType":"0",
	    "holderCredentialsNo":"",
	    "holderPhone":"",
	    "holderName":""
	}
	//模拟报价信息
	var quotePrice = {
        "damagelosscoverage":"1",
        "damagelossexemptdeductiblespecialclause":"1",
        "thirdpartyliabilitycoverage":"500000",
        "thirdpartyliabilityexemptdeductiblespecialclause":"1",
        "theftcoverage":"0",
        "theftcoverageexemptdeductiblespecialclause":"0",
        "incardriverliabilitycoverage":"0",
        "incarpassengerliabilitycoverage":"0",
        "incarpersonliabilityexemptdeductiblespecialclause":"0",
        "glassbrokencoverage":"0",
        "carbodypaintcoverage":"0",
        "carBodyPaintCoverageDeductibleSpecialClause":"0",
        "selfignitecoverage":"0",
        "selfIgniteCoverageDeductibleSpecialClause":"0",
        "waterCoverage":"0",
        "waterCoverageDeductibleSpecialClause":"0",
        "escapeCoverage":"0"
    }
	var quotePrice = localStorage["quotePrice"]; 
	quotePrice = stringToJson(quotePrice);
	var paramData = {};
	//添加车辆信息
	$.extend(paramData, carInfo);
	//添加被保人信息
	//$.extend(paramData, userInfo);
	//添加险种信息
	$.extend(paramData,quotePrice);
	var userId = sessionStorage.getItem("userId");
	if(userId==null){
		userId = "e42c56c58e4b2e92f33f0df4035a5310";
	}
	paramData.userId = userId;
	paramData.insuranceCompany = "-1";
	
	paramData.isNewVehicle = "0";//‘isNewVehicle’:0,       // 0：旧车，1：新车
	paramData.carUsedType = "1";
	paramData.fuelType = "1";
	var isChoose = sessionStorage["isChoose"];
	if(isChoose==3){
		paramData.trafficTax = "1";// ‘trafficTax’:0,          //报价险种类型(0：单商业 ，1:交强车船+商业险，2:单交强) 
	}else{
		paramData.trafficTax = "2";
	}
	var startingDateOfVehicleTrafficAccident = $("#appDate").val();
	var startingDateOfBusiness = $("#appDateTime").val();
	paramData.startingDateOfVehicleTrafficAccident = startingDateOfVehicleTrafficAccident;
	paramData.startingDateOfBusiness = startingDateOfBusiness;
	paramData.isSingle = "0";
	//-------------------------------------------------分割线---------------------------------------
	var flag = 0;
	$.ajax({
	    type : "POST",
	    async: false,
	    url : getUrl("quote/getQuoteResult.do"),
	    dataType : "json",
	    data : {params:JSON.stringify(paramData)},
	    success : function(data) {
	    	console.log("上传报价结果==========="+JSON.stringify(data));
	    	if(data.errorCode == "A0000000"){
	    		flag++ ;
	    		//sessionStorage["quoteResult"] = JSON.stringify(data.body);
	    		window.location.href="premiumRate.html";
	    		//setQuoteResult();
	    	}else{
	    		alert("异常错误!");
	    	}
	    }
 	});
	paramData.frameNo = paramData.vinNo;
	paramData.enrollDate = paramData.registerDate;
	paramData.carModel = paramData.brandModle;
	paramData.owner = paramData.ownerName;
	paramData.mobile = "15555555555";//车主手机号
	paramData.idcard = paramData.ownerCredentialsNo;
	paramData.cityName = "上海"; 
	
	paramData.spName = "1,3";
	paramData.insuranceOfVehicleTrafficAccident = true;
	$.ajax({
	    type : "POST",
	    async: false,
	    url : getUrl("quote/saveIntention.do"),
	    dataType : "json",
	    data : {params:JSON.stringify(paramData)},
	    success : function(data) {
	    	console.log("插入意向单结果==========="+JSON.stringify(data));
	    	if(data.errorCode == "A0000000"&&flag>0){
	    		var intentionId = data.responseBody.intentionId;
	    		sessionStorage["intentionId"] = intentionId;
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
//	var companyHTML = "";
//	$.each(insurcompanyList, function(i, elt) {
//		companyHTML += '<li><em id="company_quote_'+elt.numInsurcompanyid+'"></em><span id="company_'+elt.numInsurcompanyid+'">'+elt.vc2Insurcompanyshortname+'</span></li>';
//	});
//	$("#companyList").append(companyHTML);
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
			
			
			insurTypeHTML += '<li style="display: inline-block;"><span class="salaslisp">'+elt.vc2Insurname+'</span>'; 
			if(elt.numBujimId!=null){
				$.each(insurTypeList, function(i, elt2) {
					if(elt.numBujimId==elt2.numInsurid){
						if(elt.numIsdefault==1){
							insurTypeHTML += '<a href="###" class="bjmp" vc2Insurname="'+elt2.vc2Insurname+'" vc2InsreKey="'+elt2.vc2InsreKey+'" numBujimId="'+elt.numBujimId+'">不计免赔</a>';
						}else{
							insurTypeHTML += '<a href="###" class="bjmp bjmp1" disabled="true" vc2Insurname="'+elt2.vc2Insurname+'"  vc2InsreKey="'+elt2.vc2InsreKey+'" numBujimId="'+elt.numBujimId+'">不计免赔</a>';
						}
					}
				})	
			}
			if(elt.numIsdefault==1){
				
				//是否有额度选项
				if(elt.numHasrate==1){
					$.each(insurrateList, function(i, rate) {
						if(rate.numInsurid==elt.numInsurid&&rate.numInsurrateid==elt.numDefaultrate){
							insurTypeHTML += '<p class="selep"><input type="text" class="seleInsurtype" numInsurid="'+elt.numInsurid+'"  vc2InsreKey="'+elt.vc2InsreKey+'" value="'+rate.vc2Insurratecontent+'"><span><select id="'+elt.numInsurid+'" class=""><option selected="selected">不投保</option>';
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
				}else{
					insurTypeHTML += '<p class="selep"><input type="text" class="seleInsurtype" numInsurid="'+elt.numInsurid+'" vc2InsreKey="'+elt.vc2InsreKey+'" value="投保"><span><select id="'+elt.numInsurid+'" class=""><option >不投保</option>';
					insurTypeHTML += '<option selected="selected">投保</option>';
				}
				
			}else{
				insurTypeHTML += '<p class="selep"><input type="text" class="seleInsurtype" numInsurid="'+elt.numInsurid+'" vc2InsreKey="'+elt.vc2InsreKey+'" value="不投保"><span><select id="'+elt.numInsurid+'" class=""><option selected="selected">不投保</option>';
				//是否有额度选项
				if(elt.numHasrate==1){
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
				}else{
					insurTypeHTML += '<option>投保</option>';
				}
				
			}
			
			
			
			insurTypeHTML += '</select></span></p>' + 
			'</li>';
		}
	});
	
	$("#bizUl").append(insurTypeHTML);

    $('.insdiv3 dd p').click(function(){
        if($(this).hasClass('bluep')){
           $(this).removeClass('bluep');
        }else{
           $(this).addClass('bluep');
        }
    });
    
    $(".bjmp").click(function(){
    	if($(this).next().children('span').children('select').val()=="不投保"){
    		return;
    	}
    	if($(this).hasClass('bjmp1')){
            $(this).removeClass('bjmp1');
         }else{
            $(this).addClass('bjmp1');
         }
    });
    
    $('.selep span select').change(function(){
        $(this).parent('span').prevAll('input').val($(this).find("option:selected").text());
        //选择不投保是取消不计免选择
        if($(this).val()=="不投保"){
        	$(this).parent('span').parent('p').prevAll('a').addClass('bjmp1');
        }else{
        	$(this).parent('span').parent('p').prevAll('a').removeClass('bjmp1');
        }
    });
    
}

//动态设置险种信息
function selectInsurTypeRange(key, value, type){
	//全部设置为未选中
	if(key=='-1'){
        $("#bizUl li").each(function () {
            $(this).find(".bjmp").addClass('bjmp1');
            $(this).find(".seleInsurtype").val('不投保');
            $(this).find("select").val('不投保');
        });
	}else{
        if(type == 0){
            if(key == 'glassBrokenCoverage'){//玻璃险
                value ==  1 ? value = "国产玻璃" : value = "进口玻璃" ;
            }
            if(value >= 10000){
                value = (value/10000) + "万";
            }
            if(value == 1){
            	value = '投保';
			}
			//车损险报价没有保额
			if(key=='damageLossCoverage'){
                value = '投保';
			}
        }

        if(value=='0'){
            return false;
        }

        if(key.indexOf("DeductibleSpecialClause")>-1){
            $("#bizUl li").each(function () {
                //console.log("==="+$(this).find(".seleInsurtype").val()+"======"+$(this).find(".seleInsurtype").attr("vc2insrekey"));
                var vc2insrekey = $(this).find(".bjmp").attr("vc2insrekey");
                if (key == vc2insrekey) {
                    $(this).find(".bjmp").removeClass('bjmp1');
                }
            });
        }else {
            $("#bizUl li").each(function () {
                //console.log("==="+$(this).find(".seleInsurtype").val()+"======"+$(this).find(".seleInsurtype").attr("vc2insrekey"));
                var vc2insrekey = $(this).find(".seleInsurtype").attr("vc2insrekey");
                var insurtypeVal = $(this).find(".seleInsurtype").val();
                if (key == vc2insrekey) {
                    $(this).find(".seleInsurtype").val(value);
                    $(this).find("select").val(value);
                }
            });
        }
	}

            // $(this).children(".selep").children(".chooseInsurTypeRange").val(chooseInsurTypeRange);
            // $(this).children(".selep").children(".chooseInsurTypeRangeId").val(chooseInsurTypeRangeId);


}