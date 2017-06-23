$(function(){
	$(".lgbtn").click(function(){
		var params = {};
		var numType = 0;
		if($("#general").attr('checked')=='checked'){
			numType = 1;
			var VC2_POST_NAME = $("#VC2_POST_NAME").val();
			params.VC2_POST_NAME = VC2_POST_NAME;
			var VC2_POST_ADDRESS = $("#VC2_POST_ADDRESS").val();
			params.VC2_POST_ADDRESS = VC2_POST_ADDRESS;
			var VC2_POST_USERPHONE = $("#VC2_POST_USERPHONE").val();
			params.VC2_POST_USERPHONE = VC2_POST_USERPHONE;
	    }else if($("#special").attr('checked')=='checked'){
	    	numType = 2;
	    	var VC2_POST_NAME = $("#VC2_POST_NAME").val();
			params.VC2_POST_NAME = VC2_POST_NAME;
			var VC2_POST_ADDRESS = $("#VC2_POST_ADDRESS").val();
			params.VC2_POST_ADDRESS = VC2_POST_ADDRESS;
			var VC2_POST_USERPHONE = $("#VC2_POST_USERPHONE").val();
			params.VC2_POST_USERPHONE = VC2_POST_USERPHONE;
			
			var VC2_TAXPAYER = $("#VC2_TAXPAYER").val();
			params.VC2_TAXPAYER = VC2_TAXPAYER;
			var VC2_TAXPAYER_NUMBER = $("#VC2_TAXPAYER_NUMBER").val();
			params.VC2_TAXPAYER_NUMBER = VC2_TAXPAYER_NUMBER;
			var VC2_TAXPAYER_ADDRESS = $("#VC2_TAXPAYER_ADDRESS").val();
			params.VC2_TAXPAYER_ADDRESS = VC2_TAXPAYER_ADDRESS;
			var VC2_TAXPAYER_BANK = $("#VC2_TAXPAYER_BANK").val();
			params.VC2_TAXPAYER_BANK = VC2_TAXPAYER_BANK;
			var VC2_TAXPAYER_ACCOUNT = $("#VC2_TAXPAYER_ACCOUNT").val();
			params.VC2_TAXPAYER_ACCOUNT = VC2_TAXPAYER_ACCOUNT;
			var VC2_TAXPAYER_PHONE = $("#VC2_TAXPAYER_PHONE").val();
			params.VC2_TAXPAYER_PHONE = VC2_TAXPAYER_PHONE;
	    }
		params.numType = numType;
		params.orderId = sessionStorage["orderId"];
		sessionStorage["invoiceInfo"] = JSON.stringify(params);
		window.location.href="issue.html";
	});
});
//复选按钮
$('.checkp').click(function(){
    if($(this).children('span').hasClass('checkps1')){
    	$('.checkp').each(function(){
    		$(this).find('input').attr('checked',false);
            $(this).children('span').addClass('checkps1');
    	});
        $(this).find('input').attr('checked',true);
        $(this).children('span').removeClass('checkps1');
    }else{
        $(this).find('input').attr('checked',false);
        $(this).children('span').addClass('checkps1');
    }
    setHTML();
});
function setHTML(){
	
	$(".special").hide();
    if($("#general").attr('checked')=='checked'){
    	$(".general").show();
    }
    if($("#special").attr('checked')=='checked'){
    	$(".special").show();
    }
}
$(".special").hide();