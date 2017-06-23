$(function(){
	initHtml();
	$(".lgbtnmb").click(function(){
		window.location.href = "../inform/confirmInsure.html";
	});
});
initHtml = function (){
	var insurcompanyList = localStorage["insurcompanyList"];
	insurcompanyList = stringToJson(insurcompanyList);
	var companyList=new Array()
	$.each(insurcompanyList, function(i, elt) {
		companyList.push(elt.numInsurcompanyid);
	});
	
	var paramData = {};
	//保险公司
	paramData.companyid = sessionStorage["companyid"];
	//车辆信息
	paramData.carInfo = stringToJson(sessionStorage["carInfo"]);
	//险种信息
	paramData.chooseInsurTypeInfos = stringToJson(localStorage["chooseInsurTypeInfos"]);
	//被保人信息
	paramData.insuredInfo = stringToJson(sessionStorage["insuredInfo"]);
	//报价结果
	var quoteResult = stringToJson(sessionStorage["quoteResult"]);
	paramData.quoteResult = quoteResult[sessionStorage["companyid"]];
	paramData.isChoose = sessionStorage["isChoose"];
	paramData.agentId = sessionStorage.getItem('userId');
	$.ajax({
	    type : "POST",
	    url : getUrl("quote/addOrder.do"),
	    dataType : "json",
	    data : {params:JSON.stringify(paramData)},
	    success : function(data) {
	    	if(data.success == true){
	    		sessionStorage["orderId"] = data.body.orderId;
	    	}else{
	    		alert("异常错误!");
	    	}
	    }
 	}); 
}
