$(function(){
	initHtml();
	$(".lgbtn").click(function(){
		window.location.href="insured.html";
	});
});
var initHtml = function(){
	var companyid = sessionStorage["companyid"];
	var quoteResult = sessionStorage["quoteResult"];
	var carInfo = stringToJson(sessionStorage["carInfo"]);
	quoteResult = stringToJson(quoteResult);
	quoteResult = quoteResult[companyid];
	var chooseInsurTypeInfos = localStorage["chooseInsurTypeInfos"];
	chooseInsurTypeInfos = stringToJson(chooseInsurTypeInfos);
	var detailulHTML = "";
	var bizTotal = 0;
	$.each(chooseInsurTypeInfos, function(i, elt) {
		var insurName = elt.insurName;
		var numTypeid = elt.numTypeid;
		var insurRate = quoteResult[numTypeid].toFixed(2);
		console.log(insurName+"======"+insurRate);
		if(numTypeid!=1&&numTypeid!=12){
			bizTotal += Number(insurRate);
			detailulHTML += '<li><em>'+insurRate+'</em><span>'+insurName+'</span></li>';
		}else if(numTypeid==1){
			$("#compulsory").html(insurRate);
		}else if(numTypeid==12){
			$("#vehicle").html(insurRate);
		}
	});
	if(typeof(quoteResult['1'])=="undefined"){
		$('.compulsory').hide();
	}
	$('#bizTotal').html(bizTotal.toFixed(2));
	$('#detailul').append(detailulHTML);
	$('#total').html(quoteResult['total']);
	
	$("#forceDate").html(carInfo.forceDate);
	$("#bizDate").html(carInfo.bizDate);
};