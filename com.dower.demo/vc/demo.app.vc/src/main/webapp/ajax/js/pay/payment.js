
$(function(){ 
	
	var agencyMail=sessionStorage.getItem('agencyMail');
	$("#agencyMailId").html(agencyMail);	
	var orderId = sessionStorage.getItem('orderId');
	if(orderId==null){
		orderId = getQueryString("NUM_ORDER_ID");
	}
	$("#detailBtn").click(function (){	
		location.href = getUrl("ajax/content/mine/detail.html?NUM_ORDER_ID="+orderId);
	});
	
//	var policyNo = getQueryString("policyNo");
//	$("#bCodeId").html(policyNo);
	
	/*sessionStorage.removeItem('payParam');
	sessionStorage.removeItem('telImei');
	sessionStorage.removeItem('objDate');
	sessionStorage.removeItem('signImagePath')*/;
//	$(".pmbtn").click(function(){
//		window.location.href = getUrl("ajax/content/quote/invoice.html");
//	})
})


/**
 * 获取url参数
 * @param name
 * @returns
 */
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return r[2]; return null;
}


