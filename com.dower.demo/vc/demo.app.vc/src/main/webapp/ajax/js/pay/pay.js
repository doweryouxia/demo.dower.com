var realityPrice = 0;
var policyNo="";
$(function(){
	var orderId = sessionStorage.getItem('orderId');
	$.ajax({
		type:"post",
		url:getUrl("policy/getPolicyById.do?params="+orderId),		
		dataType:"json",
		success:function(data){	
			if(data.success == true){
				 var total = 0;
				 var cSalefee=0;
				 var jSalefee=0;
				 var saleFee = 0;
				 $.each(data.result, function(k, value) {
					 if(value.numInsurId == 1){
						 if(!value.numPremium== 0){
							 jSalefee = value.numPremium ;
						 }
					 }
					 if(value.numInsurId == 12){
						 if(!value.numPremium== 0){
						 cSalefee = value.numPremium ;
						 }
					 }
					 total = value.numBIPremium + value.numCIPremium;
					 saleFee = value.numBIPremium;
					 
				 });
				 
				 var divHtml='<div class="payseldiv1"><p class="payselp1">订单提交成功</p><p class="payselp2">交费后保单生效，请在我的中查询订单，并在当日24点前完成支付，否则订单将无法保留。</p></div>'
						+'<div class="payseldiv2"><p>车船税：<i>'+(cSalefee).toFixed(2)+'</i></p><p>交强险：<i>'+(jSalefee).toFixed(2)+'</i></p><p>商业险：<i>'+(saleFee).toFixed(2)+'</i></p> </div>'
						+'<div class="payseldiv3"> <p  class="payselp3">保费金额：<i>￥'+total.toFixed(2)+'</i></p><p  class="payselp4">投保单编号：<i>'+policyNo+'</i></p> </div>';
         
				 $('.payselat').html(divHtml);
			}else{
				showMessage("请求异常",0);
			}
		}
	})
	
//	var payParam = sessionStorage.getItem('payParam');
//	var objData = eval('('+payParam+')');
//	realityPrice = objData.total;
//	policyNo = objData.policyNo;
	
})

    
function showMessage(message,type){
	if(type==1){
		$('#messageId').html("交费后保单生效，请在我的中查询订单，并在当日24点前完成支付，否则订单将无法保留。");
		$('.cent').html('<a href="###" class="qd">确定离开</a><a href="###" class="qx">继续支付</a>');
		 $('.lady').height($(document).height());
	        $('.popbox article .qx').click(function () {
	            $('.lady').hide();
	            $('.popbox').hide();
	        });
	        $('.header .return').click(function() {
	            $('.lady').show();
	            $('.popbox').show();
	        })
	}else{
		$('.lady').show();
		$('.popbox').show();
		$('#messageId').html(message);
		$('.cent').html('<a href="###" class="mqd">确定</a>');
		  $('.popbox article .mqd').click(function () {
		      $('.lady').hide();
		      $('.popbox').hide();
		 })
	}
}
function wxPay(n){
	if(n==0){
		//微信支付
		var orderId = sessionStorage.getItem('orderId');
		 location.href=getUrl("ajax/content/pay/payment.html");
	/*	$.ajax({
			type:"post",
			url:getUrl("wx/pay.do?policyNo="+orderId),		
			dataType:"json",
			success:function(data){	
				if(data.success){
					var result = eval('('+data.result+')');
					console.log(result);
					if(result.errorCode == "100000"){
						goWxPay(result);
					}else{
						showMessage(result.errorMsg,0);
					}
				}else{
					showMessage(result.errorMsg,0);
				}
			}
		})*/			
	}
}


function onBridgeReady(result){
	showMessage("onBridgeReady==>appid="+result.appid,0);
    WeixinJSBridge.invoke('getBrandWCPayRequest', {
           "appId" : result.appid,//公众号名称，由商户传入     
            "timeStamp":result.return_msg, //时间戳，自1970年以来的秒数     
            "nonceStr" : result.nonce_str, //随机串     
            "package" : result.prepay_id, //订单id    
            "signType" : "MD5", //微信签名方式    
            "paySign" : result.sign //微信签名 
       },function(res){
    	   alert(res.err_msg);  // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。 
    	   if(res.err_msg == "get_brand_wcpay_request:ok" ) {
    		   location.href=getUrl("ajax/content/pay/payment.html");
    		   //修改订单状态
//    			var jsonParams = {"numOrderId":orderId,"payStatus":2,"payType":n};
//    			var params = JSON.stringify(jsonParams);
//    			 $.ajax({
//    				type:"post",
//    				url:getUrl("mine/payOrder.do?params="+params),	
//    				dataType:"json",
//    				success:function(data){
//    					data = eval(data);
//    					if(data.success){			
//    						location.href=getUrl("ajax/content/pay/payment.html");
//    					}
//    				}
//    			});	
    	   }     
       }
   ); 
 }
function goWxPay(result){  
	showMessage("appid="+result.appid,0);
	if (typeof WeixinJSBridge == "undefined"){
	   if( document.addEventListener ){
	         document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
	     }else if (document.attachEvent){
	         document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
	        document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
	    }
	 }else{
	   onBridgeReady(result);
	 }
}


function goHistory(){
	showMessage("",1);
	$(".qd").click(function(){
		window.location.href=getUrl("func.html");
	});
}


