var quoteNum = 50;
//保险公司id
var companyIdArray = ['1','3'];
//保险公司
var insurcompanyList = [{'numInsurcompanyid':1,'vc2Insurcompanyshortname':'太平洋'},{'numInsurcompanyid':3,'vc2Insurcompanyshortname':'人保'}];
$(function(){
	
	localStorage["insurcompanyList"] = JSON.stringify(insurcompanyList);
	var num = companyIdArray.length;//最多四个
    switch (num){
        case 1:
            $.when(getPriceResult(companyIdArray[0]));
            break;
        case 2:
            $.when(getPriceResult(companyIdArray[0]));
            setTimeout(function(){getPriceResult(companyIdArray[1])}, 500)
            break;
    }
	
	
	$("#next").click(function(){
		var obj = document.getElementsByName("insurId");
		var flag = 0;
		$.each(obj,function(i,v){
			if($(v).attr('checked')){
				flag = 1;
				sessionStorage["componyId"] = $(v).val()
			}
		});
		if(flag==0){
			alert("选则报价的保险公司！");
			return;
		}
		window.location.href="../record/insureInfo.html";
	});
	
	$("#updateInsur").click(function(){
		window.location.href="../order/insurChoice.html";
	});
	


});

function getPriceResult(insuranceCompany){
	var paramData = {};
	var userId = sessionStorage.getItem("userId");
	if(userId==null){
		userId = "e42c56c58e4b2e92f33f0df4035a5310";
	}
	paramData.userId = userId;
	var carInfo = sessionStorage["carInfo"];
	carInfo = stringToJson(carInfo);
	paramData.licenseNo = carInfo.licenseNo;
	var intentionId = sessionStorage["intentionId"];
	paramData.carInsuranceId = intentionId+"_"+insuranceCompany;
	$.ajax({
	    type : "POST",
	    url : getUrl("quote/getPriceResult.do"),
	    dataType : "json",
	    data : {params:JSON.stringify(paramData)},
	    success : function(data) {
//	    	console.log("获取报价结果==========="+JSON.stringify(data));
//	    	alert("获取报价结果==========="+JSON.stringify(data));
	    	if(data.errorCode == "A0000000"){
	    		var quoteResult = sessionStorage["quoteResult"];
	    		if(quoteResult){
	    			quoteResult = stringToJson(quoteResult);
	    		}else{
	    			quoteResult = {};
	    		}
	    		quoteResult[insuranceCompany] = JSON.stringify(data.responseBody.priceInfo);
	    		sessionStorage["quoteResult"] = JSON.stringify(quoteResult);
                bijiaSort();
	    	}else{
	    		if(quoteNum>0){
	    			quoteNum--;
	    			getPriceResult(insuranceCompany);
	    		}else{
	    			//alert("系统异常！");
	    		}
	    	}
	    }
 	});
}

function showPage2(){
	var chooseInsurTypeInfos = localStorage["chooseInsurTypeInfos"];
	chooseInsurTypeInfos = stringToJson(chooseInsurTypeInfos);

	var quoteResult = sessionStorage["quoteResult"];
	quoteResult = stringToJson(quoteResult);
	var resultArray = sessionStorage["componyShowArray"]
	resultArray = stringToJson(resultArray);
	var htmlStr = "";
	$.each(resultArray,function(index,value){
		$.each(insurcompanyList,function(i,v){
			if(v.numInsurcompanyid==value){
				
				var bizTotalPrice = stringToJson(quoteResult[value]).bizTotalPrice;
				var trafficTotalPrice = stringToJson(quoteResult[value]).trafficTotalPrice;
				var taxTotalPrice = stringToJson(quoteResult[value]).taxTotalPrice;
				var total = Number(bizTotalPrice)+Number(trafficTotalPrice)+Number(taxTotalPrice);
				
				htmlStr += '<li class=""><div class="ratelidiv1"><p class="ratep"><span>￥'+total.toFixed(2)+'</span><em>'+(total*0.25).toFixed(2)+'积分</em></p><p class="checkp"><span class="checkps1">&nbsp;</span>'+
				'<input type="checkbox" value="'+value+'" name="insurId"></p><span class="rateft">'+v.vc2Insurcompanyshortname+'</span></div>' + 
				'<div class="ratelidiv">';
				var buJiMianNum = 0;
				var buJiMianStr = "";
				htmlStr += '<p class="cent"><span>交强险</span><span>投保</span><span>'+trafficTotalPrice.toFixed(2)+'</span></p>';
				htmlStr += '<p class="cent"><span>车船税</span><span>投保</span><span>'+taxTotalPrice.toFixed(2)+'</span></p>';
				$.each(chooseInsurTypeInfos,function(iInsur,vInsur){
					var insurName = vInsur.insurName;
					if(insurName.indexOf("不计免")==-1){
						htmlStr += '<p class="cent"><span>'+vInsur.insurName+'</span><span>'+(vInsur.numTypeRate==0?"投保":vInsur.numTypeRate)+'</span><span>'+stringToJson(quoteResult[value])[vInsur.vc2insrekey]["expense"].toFixed(2)+'</span></p>';
					}else{
						buJiMianStr += "、" + insurName.substring(3,insurName.length);
						buJiMianNum += stringToJson(quoteResult[value])[vInsur.vc2insrekey]["expense"];
						//console.log(insurName.substring(3,insurName.length)+"======="+JSON.stringify(stringToJson(quoteResult[value])[vInsur.vc2insrekey]));
					}
				});
				htmlStr += '<p class="ratep1 cent"><span class="ratesp">不计免赔（'+buJiMianStr.substring(1,buJiMianStr.length)+'）</span><span></span><span>￥'+buJiMianNum.toFixed(2)+'</span></p>' + 
				'</div></li>';
			}
		});
	});
	//$("#ulBaojia").append(htmlStr);
    $("#ulBaojia").html(htmlStr);
    loadingCss();
}

function showPage(){
	var chooseInsurTypeInfos = localStorage["chooseInsurTypeInfos"];
	chooseInsurTypeInfos = stringToJson(chooseInsurTypeInfos);

	var quoteResult = sessionStorage["quoteResult"];
	quoteResult = stringToJson(quoteResult);
	var resultArray = sessionStorage["componyShowArray"]
	resultArray = stringToJson(resultArray);
	var htmlStr = "";
	$.each(resultArray,function(index,value){
		$.each(insurcompanyList,function(i,v){
			if(v.numInsurcompanyid==value){
				htmlStr += '<li class=""><div class="ratelidiv1"><p class="ratep"><span>￥'+quoteResult[value].total.toFixed(2)+'</span><em>'+(quoteResult[value].total*0.25).toFixed(2)+'积分</em></p><p class="checkp"><span class="checkps1">&nbsp;</span>'+
				'<input type="checkbox" value="'+value+'" name="insurId"></p><span class="rateft">'+v.vc2Insurcompanyshortname+'</span></div>' + 
				'<div class="ratelidiv">';
				var buJiMianNum = 0;
				var buJiMianStr = "";
				$.each(chooseInsurTypeInfos,function(iInsur,vInsur){
					var insurName = vInsur.insurName;
					if(insurName.indexOf("不计免")==-1){
						htmlStr += '<p class="cent"><span>'+vInsur.insurName+'</span><span>'+(vInsur.numTypeRate==0?"投保":vInsur.numTypeRate)+'</span><span>'+quoteResult[value][vInsur.numTypeid].toFixed(2)+'</span></p>';
					}else{
						buJiMianStr += "、" + insurName.substring(3,insurName.length);
						buJiMianNum += quoteResult[value][vInsur.numTypeid];
						console.log(insurName.substring(3,insurName.length));
					}
				});
				htmlStr += '<p class="ratep1 cent"><span class="ratesp">不计免赔（'+buJiMianStr.substring(1,buJiMianStr.length)+'）</span><span></span><span>￥'+buJiMianNum.toFixed(2)+'</span></p>' + 
				'</div></li>';
			}
		});
	});
	$("#ulBaojia").append(htmlStr);
}

function bijiaSort(){
	//报价结果
	var quoteResult = sessionStorage["quoteResult"];
	quoteResult = stringToJson(quoteResult);
	//[4578, 4143, 4475, 4018, 3678, 4571, 3955]
	var quoteResultArray = new Array();
	
	var quoteMap = {};
	for(var key in quoteResult){
		console.log(key+"========"+quoteResult[key]);
		var bizTotalPrice = stringToJson(quoteResult[key]).bizTotalPrice;
		var trafficTotalPrice = stringToJson(quoteResult[key]).trafficTotalPrice;
		var taxTotalPrice = stringToJson(quoteResult[key]).taxTotalPrice;
		var v = Number(bizTotalPrice)+Number(trafficTotalPrice)+Number(taxTotalPrice);
		quoteMap[key] = v;
		quoteResultArray.push(v);
	}
	console.log(quoteResultArray);
	quoteResultArray.sort();
	console.log(quoteResultArray);
	var resultArray = new Array();
	for(var i=0;i<quoteResultArray.length;i++){
		if(i<5){
			for(var key in quoteMap){
		    	if(quoteMap[key]==quoteResultArray[i]){
		    		resultArray.push(key);
		    	}
		    }
		}
	}
	console.log(resultArray);
	sessionStorage["componyShowArray"] = JSON.stringify(resultArray);

    showPage2();
}

function loadingCss() {
    $('.rateul li').each(function(index) {
        $(this).children('div.ratelidiv1').click(function() {
			/* if($(this).children('span').hasClass('checkps1')){*/
            $('.rateul li .ratelidiv1 p.checkp').find('input').attr('checked',false);
            $('.rateul li .ratelidiv1 p.checkp').children('span').addClass('checkps1');
            $('.rateul li').removeClass('ratelisel')
            $('.rateul li .ratelidiv1').nextAll('.ratelidiv').slideUp();
            $(this).children('p.checkp').find('input').attr('checked',true);
            $(this).children('p.checkp').children('span').removeClass('checkps1');
            $(this).children('p.checkp').parents('li').addClass('ratelisel')
            $(this).children('p.checkp').parent('.ratelidiv1').nextAll('.ratelidiv').slideDown();

        });
		/*$(this).children('.ratelidiv').children('.shq').click(function() {
		 $(this).parent('.ratelidiv').prevAll('.ratelidiv1').children('p.checkp').children('input').attr('checked',false);
		 $(this).parent('.ratelidiv').prevAll('.ratelidiv1').children('p.checkp').children('span').addClass('checkps1');
		 $(this).parents('li').removeClass('ratelisel')
		 $(this).parent('.ratelidiv').slideUp();
		 });*/
        $(this).children('.ratelidiv').children('.shq1').click(function(){
            if($(this).children('em').html()=='查看详情'){
                $(this).prevAll('.ratexq').slideDown();
                $(this).children('img').attr('src','images/icon53.png');
                $(this).removeClass('shq1').addClass('shq');
                $(this).children('em').html('收起');}else{
                $(this).prevAll('.ratexq').slideUp('slow');
                $(this).children('img').attr('src','images/icon54.png');
                $(this).removeClass('shq').addClass('shq1');
                $(this).children('em').html('查看详情');
            }
        })
    })
}