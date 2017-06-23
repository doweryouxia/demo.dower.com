var realityPrice = 0;
var policyNo="";
$(function(){ 
	var payParam = sessionStorage.getItem('payParam');
	var objData = eval('('+payParam+')');
	realityPrice = objData.sySalefee+objData.jqSalefee+objData.cSalefee;
	policyNo = objData.policyNo;
	var divHtml='<p class="typep"><em>提交订单完成，请尽快完成付款！</em><span>保单为您保留至今日23：59分，请及时付款。</span></p>'
		+'<p class="typep1"><span>商业险：</span><em>'+objData.sySalefee+'</em><br><span>交强险：</span><em>'+objData.jqSalefee+'</em><br><span>车船税：</span><em>'+objData.cSalefee+'</em></p>'
		+'<p class="typep2"><span class="bf1">保费金额：</span><em class="bf2">￥'+realityPrice+'</em><br><span class="tbd">投保单编号：'+policyNo+'</span></p>';
	$('.paytype').html(divHtml);
})

function pay(n){
	if(n==0){
		window.location.href = getUrl("ajax/content/pay/payment.html?realityPrice="+realityPrice+"&policyNo="+policyNo);
	}
	if(n==1){
		window.location.href = getUrl('ajax/content/pay/pay.html?typeId='+n+'&realityPrice='+realityPrice+"&policyNo="+policyNo);
	}
}

