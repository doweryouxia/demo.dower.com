$(function(){
	var params = sessionStorage["invoiceInfo"];
	$.ajax({
	    type : "POST",
	    url : getUrl("quote/addInvoiceInfo.do"),
	    dataType : "json",
	    data : {params:params},
	    success : function(data) {
	    	if(data.success == true){
	    		{
	    			
	    			var insuredInfo = stringToJson(sessionStorage["insuredInfo"]);
	    			var carInfo = stringToJson(sessionStorage["carInfo"]);
	    			var quoteResult = stringToJson(sessionStorage["quoteResult"]);
	    			var baoDanNo = "--";
	    			var VC2_POST_NAME = insuredInfo.VC2_INSURED;
	    			var carNo = carInfo.carNo;
	    			var bizDate = carInfo.bizDate;
	    			var forceDate = carInfo.forceDate;
	    			var total = quoteResult[sessionStorage["companyid"]].total;
	    			
	    			$("#baoDanNo").html(baoDanNo);
	    			$("#VC2_POST_NAME").html(VC2_POST_NAME);
	    			$("#carNo").html(carNo);
	    			$("#bizDate").html(bizDate);
	    			$("#forceDate").html(forceDate);
	    			$("#total").html(total);
	    		}
	    	}else{
	    		alert("异常错误!");
	    	}
	    }
 	});
	  $('#btnId').click(function(){
		  window.location.href=getUrl("func.html");
	    });
	
	$('.lady').height($(document).height());
    $('.lgbtn').click(function(){
        $('.lady').show();
        $('.issuesuccess').show();
    });
    $('.issuesuccess header a').click(function(){
        $('.lady').hide();
        $('.issuesuccess').hide();
    })
})
