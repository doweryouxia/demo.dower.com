
$(function(){ 
	var realityPrice = getQueryString("realityPrice");
	$("#realityPrice").html("￥"+realityPrice);
})


function goPay(){
	var realityPrice=$("#realityPrice").html();
	var policyNo = getQueryString("policyNo");
	window.location.href = getUrl("ajax/content/pay/payment.html?realityPrice="+realityPrice+"&policyNo="+policyNo);
}


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

