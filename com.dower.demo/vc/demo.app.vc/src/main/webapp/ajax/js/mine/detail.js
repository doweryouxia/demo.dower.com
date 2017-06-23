/**
 * 订单详情
 */
$(function(){ 
	var orderDetail = JSON.parse(sessionStorage.getItem("orderDetail"));
	//console.log(orderDetail);
	var orderId = orderDetail.orderId;
	//是否解付
	var isDebits = orderDetail.isDebits;
	//是否出单
	var isSuing = orderDetail.isSuing;
	
	var payStatus = "";
	if(isDebits==0 || isDebits==null){
		payStatus=7;
	}else if(isDebits==1&&(isSuing==""||isSuing==null)){
		payStatus=8;
	}
	if(isSuing==1){
		payStatus=9;
	}
	$.ajax({
		type:"post",
		url:getUrl("mine/getOrderDetail.do?orderId="+orderId),		
		dataType:"json",
		success:function(data){				
			if(data.success){		
				//var result = eval('('+data.responseBody+')');
				var result = data.responseBody;
				
				console.log(result);
				var chrStatus = "";
				if(payStatus==7){
					chrStatus = "未支付";					
				}else if(payStatus==8){
					chrStatus = "已支付";
				}else if(payStatus==9){
					chrStatus = "完成";
				}
				
				$("#payStatus").html(chrStatus);
				$("#orderNo").html(orderDetail.orderNo);	
				$("#insurCompany").html(getInsureCompanyName(result.carInsurance.spName,false));
				
				//总计保费
				var total = result.quotation.total;
				
				//车船税保费
				var carRate = result.quotation.vehicleVesselUsageTax;
				//交强险保费
				var jqxRate = result.quotation.insuranceOfVehicleTrafficAccident;				
				//交强险起保日期
				var jqxDate = result.carInsurance.startingDateOfVehicleTrafficAccident;
				
				if(jqxDate!=""){					
					var bizDate2=new Date(jqxDate); 
					bizDate2.setFullYear(bizDate2.getFullYear()+1); 
					bizDate2.setDate(bizDate2.getDate()-1); 
					//截止日期
					var endDate = new Date(bizDate2).Format("yyyy-MM-dd");
					jqxDate=jqxDate+" 至  "+endDate;
				}
				$(".orderatsp2").html("￥"+total.toFixed(2));
				$("#jqxMoney").text("￥"+(jqxRate+carRate).toFixed(2));
				$("#jqxDate").text(jqxDate);
				
				var syxArticle = "";				 
				//商业险起保日期
				var syxDate = result.carInsurance.startingDateOfBusiness;
				var syxRate = "0";
				if(syxDate!=""){
					var bizDate2=new Date(syxDate); 
					bizDate2.setFullYear(bizDate2.getFullYear()+1); 
					bizDate2.setDate(bizDate2.getDate()-1); 
					//截止日期
					var endDate = new Date(bizDate2).Format("yyyy-MM-dd");					
					//syxDate = syxDate+" 至  "+endDate
					syxRate = (parseFloat(total)-parseFloat(carRate)-parseFloat(jqxRate));
					
					syxArticle +="<p class=\"orderat2p1\"><span class=\"orderat2sp2\">￥"+(syxRate).toFixed(2)+"</span><span class=\"orderat2sp1\">商业险</span></p>";
					syxArticle +="<p class=\"orderat2p2\">"+syxDate+" 至  "+endDate+"</p>";
					syxArticle +="<p class=\"orderat2p3\"><a href=\"javascript:void(0)\" class=\"xqa\" onclick=\"showXQA()\"><i></i>查看详情</a></p>";
					$("#sxyArticle").html(syxArticle);
					$("#sxyArticle").show();
				}
				
				orderDetail.total=total;
				orderDetail.carRate=carRate;
				orderDetail.jqxRate=jqxRate;
				orderDetail.syxRate=syxRate;
				
				var insur = "";
				
				//是否选择车损险
				if(result.carInsurance.damageLossCoverage==true){
					insur+="<article class=\"orderat3\">";
					insur+="<span class=\"orderat3sp1\">车辆损失险</span>";
					insur+="<p><em>保额：</em><span>"+"0.00"+"</span></p><p><em class=\"orderat3em1\">保费：</em><span>"+(result.quotation.damageLossCoverage).toFixed(2)+"</span></p>";
					//insur+="<p><em>绝对赔付额：</em><span>0.00</span></p><p><em class=\"orderat3em1\">是否投保不计免：</em><span>是</span></p>";
					insur+="</article>";
				}
				//是否选择三者险，未选=0，选择=险种的保额
				if(result.carInsurance.thirdPartyLiabilityCoverage!=0){
					insur+="<article class=\"orderat3\">";
					insur+="<span class=\"orderat3sp1\">第三方责任险</span>";
					insur+="<p><em>保额：</em><span>"+"0.00"+"</span></p><p><em class=\"orderat3em1\">保费：</em><span>"+(result.quotation.thirdPartyLiabilityCoverage).toFixed(2)+"</span></p>";
					//insur+="<p><em>绝对赔付额：</em><span>0.00</span></p><p><em class=\"orderat3em1\">是否投保不计免：</em><span>是</span></p>";
					insur+="</article>";
				}
				//是否选择司机险，未选择=0，已选择=选择保险额度
				if(result.carInsurance.inCarDriverLiabilityCoverage!=0){
					insur+="<article class=\"orderat3\">";
					insur+="<span class=\"orderat3sp1\">司机座位险</span>";
					insur+="<p><em>保额：</em><span>"+"0.00"+"</span></p><p><em class=\"orderat3em1\">保费：</em><span>"+(result.quotation.inCarDriverLiabilityCoverage).toFixed(2)+"</span></p>";
					//insur+="<p><em>绝对赔付额：</em><span>0.00</span></p><p><em class=\"orderat3em1\">是否投保不计免：</em><span>是</span></p>";
					insur+="</article>";
				}
				
				//是否选择乘客险，未选择=0，已选择=选择保险额度
				if(result.carInsurance.inCarPassengerLiabilityCoverage!=0){
					insur+="<article class=\"orderat3\">";
					insur+="<span class=\"orderat3sp1\">乘客座位险</span>";
					insur+="<p><em>保额：</em><span>"+"0.00"+"</span></p><p><em class=\"orderat3em1\">保费：</em><span>"+(result.quotation.inCarPassengerLiabilityCoverage).toFixed(2)+"</span></p>";
					//insur+="<p><em>绝对赔付额：</em><span>0.00</span></p><p><em class=\"orderat3em1\">是否投保不计免：</em><span>是</span></p>";
					insur+="</article>";
				}
				
				//是否选择玻璃险，未选择=-1
				if(result.carInsurance.glassBrokenCoverage!=-1){
					insur+="<article class=\"orderat3\">";
					insur+="<span class=\"orderat3sp1\">玻璃破碎险</span>";
					insur+="<p><em>保额：</em><span>"+"0.00"+"</span></p><p><em class=\"orderat3em1\">保费：</em><span>"+(result.quotation.glassBrokenCoverage).toFixed(2)+"</span></p>";
					//insur+="<p><em>绝对赔付额：</em><span>0.00</span></p><p><em class=\"orderat3em1\">是否投保不计免：</em><span>是</span></p>";
					insur+="</article>";
				}
				//是否选择盗抢险
				if(result.carInsurance.theftCoverage==true){
					insur+="<article class=\"orderat3\">";
					insur+="<span class=\"orderat3sp1\">全车盗抢险</span>";
					insur+="<p><em>保额：</em><span>"+"0.00"+"</span></p><p><em class=\"orderat3em1\">保费：</em><span>"+(result.quotation.theftCoverage).toFixed(2)+"</span></p>";
					//insur+="<p><em>绝对赔付额：</em><span>0.00</span></p><p><em class=\"orderat3em1\">是否投保不计免：</em><span>是</span></p>";
					insur+="</article>";
				}
				//是否选择自然险
				if(result.carInsurance.selfIgniteCoverage==true){
					insur+="<article class=\"orderat3\">";
					insur+="<span class=\"orderat3sp1\">自燃损失险</span>";
					insur+="<p><em>保额：</em><span>"+"0.00"+"</span></p><p><em class=\"orderat3em1\">保费：</em><span>"+(result.quotation.selfIgniteCoverage).toFixed(2)+"</span></p>";
					//insur+="<p><em>绝对赔付额：</em><span>0.00</span></p><p><em class=\"orderat3em1\">是否投保不计免：</em><span>是</span></p>";
					insur+="</article>";
				}
				//是否选择划痕险，未选择=0，已选择=选择保险额度
				if(result.carInsurance.carBodyPaintCoverage!=0){
					insur+="<article class=\"orderat3\">";
					insur+="<span class=\"orderat3sp1\">车身划痕险</span>";
					insur+="<p><em>保额：</em><span>"+"0.00"+"</span></p><p><em class=\"orderat3em1\">保费：</em><span>"+(result.quotation.carBodyPaintCoverage).toFixed(2)+"</span></p>";
					//insur+="<p><em>绝对赔付额：</em><span>0.00</span></p><p><em class=\"orderat3em1\">是否投保不计免：</em><span>是</span></p>";
					insur+="</article>";
				}
				
				//是否选择涉水险
				if(result.carInsurance.waterCoverage==true){
					insur+="<article class=\"orderat3\">";
					insur+="<span class=\"orderat3sp1\">涉水险</span>";
					insur+="<p><em>保额：</em><span>"+"0.00"+"</span></p><p><em class=\"orderat3em1\">保费：</em><span>"+(result.quotation.waterCoverage).toFixed(2)+"</span></p>";
					//insur+="<p><em>绝对赔付额：</em><span>0.00</span></p><p><em class=\"orderat3em1\">是否投保不计免：</em><span>是</span></p>";
					insur+="</article>";
				}
				
				//是否选择涉水险不计免赔
				if(result.carInsurance.waterCoverageDeductibleSpecialClause==true){
					insur+="<article class=\"orderat3\">";
					insur+="<span class=\"orderat3sp1\">不计免涉水</span>";
					insur+="<p><em>保额：</em><span>"+"0.00"+"</span></p><p><em class=\"orderat3em1\">保费：</em><span>"+(result.quotation.waterCoverageDeductibleSpecialClause).toFixed(2)+"</span></p>";
					//insur+="<p><em>绝对赔付额：</em><span>0.00</span></p><p><em class=\"orderat3em1\">是否投保不计免：</em><span>是</span></p>";
					insur+="</article>";
				}
				
				//是否选择三者不计免赔
				if(result.carInsurance.thirdPartyLiabilityExemptDeductibleSpecialClause==true){
					insur+="<article class=\"orderat3\">";
					insur+="<span class=\"orderat3sp1\">不计免三者</span>";
					insur+="<p><em>保额：</em><span>"+"0.00"+"</span></p><p><em class=\"orderat3em1\">保费：</em><span>"+(result.quotation.thirdPartyLiabilityExemptDeductibleSpecialClause).toFixed(2)+"</span></p>";
					//insur+="<p><em>绝对赔付额：</em><span>0.00</span></p><p><em class=\"orderat3em1\">是否投保不计免：</em><span>是</span></p>";
					insur+="</article>";
				}
				//是否选择盗抢不计免赔
				if(result.carInsurance.theftCoverageExemptDeductibleSpecialClause==true){
					insur+="<article class=\"orderat3\">";
					insur+="<span class=\"orderat3sp1\">不计免盗窃</span>";
					insur+="<p><em>保额：</em><span>"+"0.00"+"</span></p><p><em class=\"orderat3em1\">保费：</em><span>"+(result.quotation.theftCoverageExemptDeductibleSpecialClause).toFixed(2)+"</span></p>";
					//insur+="<p><em>绝对赔付额：</em><span>0.00</span></p><p><em class=\"orderat3em1\">是否投保不计免：</em><span>是</span></p>";
					insur+="</article>";
				}
				//是否选择车损险不计免赔
				if(result.carInsurance.damageLossExemptDeductibleSpecialClause==true){
					insur+="<article class=\"orderat3\">";
					insur+="<span class=\"orderat3sp1\">不计免车损</span>";
					insur+="<p><em>保额：</em><span>"+"0.00"+"</span></p><p><em class=\"orderat3em1\">保费：</em><span>"+(result.quotation.damageLossExemptDeductibleSpecialClause).toFixed(2)+"</span></p>";
					//insur+="<p><em>绝对赔付额：</em><span>0.00</span></p><p><em class=\"orderat3em1\">是否投保不计免：</em><span>是</span></p>";
					insur+="</article>";
				}	
				//是否选择车损人员不计免赔
				if(result.carInsurance.inCarPersonLiabilityExemptDeductibleSpecialClause==true){
					insur+="<article class=\"orderat3\">";
					insur+="<span class=\"orderat3sp1\">不计免乘客</span>";
					insur+="<p><em>保额：</em><span>"+"0.00"+"</span></p><p><em class=\"orderat3em1\">保费：</em><span>"+(result.quotation.inCarPersonLiabilityExemptDeductibleSpecialClause).toFixed(2)+"</span></p>";
					//insur+="<p><em>绝对赔付额：</em><span>0.00</span></p><p><em class=\"orderat3em1\">是否投保不计免：</em><span>是</span></p>";
					insur+="</article>";
				}
				//是否选择第三方特约
				if(result.carInsurance.riderExemptDeductibleSpecialClause==true){
					insur+="<article class=\"orderat3\">";
					insur+="<span class=\"orderat3sp1\">第三方特约</span>";
					insur+="<p><em>保额：</em><span>"+"0.00"+"</span></p><p><em class=\"orderat3em1\">保费：</em><span>"+(result.quotation.riderExemptDeductibleSpecialClause).toFixed(2)+"</span></p>";
					//insur+="<p><em>绝对赔付额：</em><span>0.00</span></p><p><em class=\"orderat3em1\">是否投保不计免：</em><span>是</span></p>";
					insur+="</article>";
				}
				
				//是否选择自然险不计免赔
				if(result.carInsurance.selfIgniteCoverageDeductibleSpecialClause==true){
					insur+="<article class=\"orderat3\">";
					insur+="<span class=\"orderat3sp1\">不计免自燃</span>";
					insur+="<p><em>保额：</em><span>"+"0.00"+"</span></p><p><em class=\"orderat3em1\">保费：</em><span>"+(result.quotation.selfIgniteCoverageDeductibleSpecialClause).toFixed(2)+"</span></p>";
					//insur+="<p><em>绝对赔付额：</em><span>0.00</span></p><p><em class=\"orderat3em1\">是否投保不计免：</em><span>是</span></p>";
					insur+="</article>";
				}
				//是否选择划痕险不计免赔
				if(result.carInsurance.carBodyPaintCoverageDeductibleSpecialClause==true){
					insur+="<article class=\"orderat3\">";
					insur+="<span class=\"orderat3sp1\">不计免划痕</span>";
					insur+="<p><em>保额：</em><span>"+"0.00"+"</span></p><p><em class=\"orderat3em1\">保费：</em><span>"+(result.quotation.carBodyPaintCoverageDeductibleSpecialClause).toFixed(2)+"</span></p>";
					//insur+="<p><em>绝对赔付额：</em><span>0.00</span></p><p><em class=\"orderat3em1\">是否投保不计免：</em><span>是</span></p>";
					insur+="</article>";
				}
				
				
				//是否选择指定修理厂险
				if(result.carInsurance.appointedRepairFactorySpecialClause==true){
					insur+="<article class=\"orderat3\">";
					insur+="<span class=\"orderat3sp1\">指定修理厂险</span>";
					insur+="<p><em>保额：</em><span>"+"0.00"+"</span></p><p><em class=\"orderat3em1\">保费：</em><span>"+(result.quotation.appointedRepairFactorySpecialClause).toFixed(2)+"</span></p>";
					//insur+="<p><em>绝对赔付额：</em><span>0.00</span></p><p><em class=\"orderat3em1\">是否投保不计免：</em><span>是</span></p>";
					insur+="</article>";
				}
				
				
				//是否选择第三方险
				if(result.carInsurance.escapeCoverage==true){
					insur+="<article class=\"orderat3\">";
					insur+="<span class=\"orderat3sp1\">第三方险</span>";
					insur+="<p><em>保额：</em><span>"+"0.00"+"</span></p><p><em class=\"orderat3em1\">保费：</em><span>"+(result.quotation.escapeCoverage).toFixed(2)+"</span></p>";
					//insur+="<p><em>绝对赔付额：</em><span>0.00</span></p><p><em class=\"orderat3em1\">是否投保不计免：</em><span>是</span></p>";
					insur+="</article>";
				}	
				 
				$(".orderxq").html(insur);
				
				//车辆与车主信息				
				$("#autoCode").html(result.carInsurance.licenseNo);								
				//$("#mailBox").html(data.orderInfo.vc2Mailbox);
				$("#vin").html(result.carInsurance.frameNo);
				$("#enginNum").html(result.carInsurance.engineNo);
				$("#brand").html(result.carInsurance.moldName);	
				$.each(result.contact,function(i, n) {
					 if(n.type==1){
						 $("#telBtn").attr("href","tel:"+txtFormatter(n.phone));
					 }else if(n.type==3){
						$("#ownerName").html("车主姓名："+txtFormatter(n.name));				
						$("#certCode").html("身份证号："+txtFormatter(n.certificateNumber));	
						$("#phoneNum").html(n.phone);
					}					
				});				
				
				var carState = 2;//data.orderInfo.numCarState;
				//1-新车 2-旧车
				if(carState==1){
					$("#imgHGZ").attr("src",imgUrl+data.orderInfo.vc2CertificatePath);
					$("#imgFP").attr("src",imgUrl+data.orderInfo.vc2BuyInvoicePath);
					$(".newCar").show();
				}else{
					$(".newCar").hide();
				}				
				
				$("#policyLinkBtn").attr("href",orderDetail.link);
				
				var payBtn = "再次购买";
				var payUrl = "";
				if(payStatus==7){
					payBtn = "去支付";
					payUrl = getUrl("ajax/content/pay/pay.html?flag=1");
					
				}else{
					payUrl = getUrl("ajax/content/record/carInfo.html");
				}
				$("#payBtn").html(payBtn);
				$("#payBtn").attr("href",payUrl);
				
				sessionStorage.setItem("orderDetail",JSON.stringify(orderDetail));
				sessionStorage.removeItem("orderId");
				sessionStorage.setItem("orderId",orderId);
			}
		}
	});		
	//车辆与车主信息---更多
	$(".carzinfo a").click(function(){
		if($(this).html()=='更多'){
	        $(this).html('收起');
	          $('.carzinfoat').removeClass('carzinfoath');
	    }else{
	       $(this).html('更多');
	       $('.carzinfoat').addClass('carzinfoath');
	    }
	});
	
});
//商业险查看详情
function showXQA(){
	var obj = $(".xqa");
	if(obj.children('i').hasClass('addimg')){
	   obj.html('<i></i>查看详情');
       $('.orderxq').slideToggle();
    }else{
	   obj.html('<i class="addimg"></i>收起');
       $('.orderxq').slideToggle();
    }
}

