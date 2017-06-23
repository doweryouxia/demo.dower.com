/**
 * 设置
 */
var orderDetail = JSON.parse(sessionStorage.getItem("orderDetail"));
var numOrderId = orderDetail.orderId;
$(function(){	
	var total = orderDetail.total;
	$("#carRate").text((orderDetail.carRate).toFixed(2));
	$("#jqxRate").text((orderDetail.jqxRate).toFixed(2));
	$("#syxRate").text((orderDetail.syxRate).toFixed(2));
	$(".payselp3 i").html("￥"+total.toFixed(2));	
	var ciNo = orderDetail.ciPolicyNo;
	var biNo = orderDetail.biPolicyNo;
	var policyNo = "";
	if(biNo==null||biNo=="null"){
		if(ciNo!="null"){
			policyNo = ciNo;
		}		
	}else{
		policyNo = biNo;
	}
	$(".payselp4 i").html(policyNo);
});

/**
 * 支付方式
 * @param payType
 */
function orderPayType(payType){
	var jsonParams = {"numOrderId":numOrderId,"payStatus":2,"payType":payType};
	
}
