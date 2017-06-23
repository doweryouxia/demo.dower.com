
 var policyNo = "";
 var sySalefee = 0;
 var jqSalefee = 0;
 var cSalefee = 0;
 var total =0;
 var userId = sessionStorage.getItem('userId');
 var telImei = sessionStorage.getItem('telImei');
 var orderId = sessionStorage.getItem('orderId');
 var licenseNo = sessionStorage.getItem('licenseNo');
 var signId=0;
 var numIsOperat ="";//营运非营运
$(function(){
	 $('.lady').show();
     $('.issuesuccess').show();
     $('#mpId').html("即将进行录屏、录像、录音<br>请点<a>“确定”</a>进入录单流程");
	 $('#btnId').click(function(){
		 $('.lady').hide();
	     $('.issuesuccess').hide();
	     getLoginUser("","onLonginRecordError");
	 });
	 
	 
	 //获取保单号
	 var ciPolicyNo = sessionStorage.getItem('ciPolicyNo');
	 var biPolicyNo = sessionStorage.getItem('biPolicyNo');
	 if(ciPolicyNo!=null && biPolicyNo!=null){
		 policyNo = biPolicyNo;
	 }else{
		 if(ciPolicyNo!=null){
			 policyNo = ciPolicyNo;
		 }
		 if(biPolicyNo!=null){
			 policyNo = biPolicyNo;
		 }
	 }
	 
	//获取车辆核保信息
	 //交强险投保单号
	$("#insurep").html("投保单号："+ciPolicyNo+"<br/>货币单位：人民币元");
	$("#insurep2").html("投保单号："+ciPolicyNo);
	//商业险投保单号
    $("#insurep1").html("投保单号："+biPolicyNo+"<br/>货币单位：人民币元");
    $("#insurep3").html("商业险投保单号："+biPolicyNo); 
	
	//获取险种信息
	getPolicyDetail();
	//获取订单信息
	getOrderDetail();
    //获取车辆出险信息
    getCarClaimInfo();
	
    getDateYearMonth();
	 
    //签名
	$("#signId2").click(function(){
		 signature(1);
	})
})

//获取险种信息
function getPolicyDetail(){
	 $.ajax({
		    type : "POST",
		    url : getUrl("policy/getPolicyDetail.do?params="+orderId),
		    dataType : "json",
		    success : function(data) {
		    	if(data.success == true){
		    		var cusTel="";
		    		var insurTypeHtml='<tr><th>承保险别</th><th>保险金额<br/>赔偿限额（元）</th><th>保险费（元）</th></tr>';
			        var insurTypeHtml1='';
		    	    //险种信息
	                 $.each(data.result, function(k, value) {
	                	 numIsOperat= value.numIsOperat;
	                	 cusTel =  value.vc2CusTel;
	                	 if(value.numInsurType == 2){
	                		var premium = value.numBIPremium * value.numBICommission;
	      					insurTypeHtml+='<tr><td>'+value.vc2InsurName+'</td><td>'+value.vc2InsurRateContent+'</td><td>'+(value.numPremium).toFixed(2)+'</td></tr>';
	                		insurTypeHtml1+='<tr><td>'+value.vc2InsurName+'：<br/><label>'+value.vc2InsurRateContent+' / '+(value.numPremium).toFixed(2)+' / <strong>'+premium+'</strong></label></td></tr>';
	      				
	                	 }
	                 });
	                 if(numIsOperat == 1){
	                	 numIsOperat = "营运";
	                 }else{
	                	 numIsOperat ="非营运";
	                 }
	                 $("#insurTypeId").html(insurTypeHtml);
	                 $("#insurTypeId1").html(insurTypeHtml);
	                 $("#kfId1").html(cusTel);
                	 $("#kfId2").html(cusTel);
 	    			 $("#kfId").html(cusTel);
		    	}else{
		    		//alert("异常错误!");
		    	}
		    }
	 	});
}


//获取订单详细
function getOrderDetail(){
	$.ajax({
		type:"post",
		url:getUrl("mine/getOrderDetail.do?orderId="+orderId),		
		dataType:"json",
		success:function(data){	
			if(data.success){
				var result = eval('('+data.result+')');
				console.log(result);
				 var startTimesy;
    			 var startTimejq;
    			 var chesun="0.00";
    			 var userHtml='<tr><th colspan="2">客户信息</th></tr>';
    			 var insuredIdHtml='';
	             var insuredId1Html='';
	             var carInfoHtml='';
	             var carInfoHtml1='';
	             var carInfoHtml2='';
	             var companyName = getInsureCompanyName(result.carInsurance.spName,false);
	             $("#companyId1").html(companyName);
	        	 $("#companyId2").html(companyName);
	        	 $("#companyId3").html(companyName);
	        	 $("#companyId4").html(companyName);
	        	 $("#companyId5").html(companyName);
	        	 $("#companyId6").html(companyName);
	        	 
	            //总计保费
				total = result.quotation.total;
				//车船税保费
				var cSalefee = result.quotation.vehicleVesselUsageTax;
				//交强险保费
				var jqSalefee = result.quotation.insuranceOfVehicleTrafficAccident;	
					
	             //商业险起保日期
	             var syxDate = result.carInsurance.startingDateOfBusiness;
	             if(syxDate!=""){
	            	   sySalefee = (parseFloat(total)-parseFloat(cSalefee)-parseFloat(jqSalefee));
	            	   startTimesy = syxDate;
						var bizDate2=new Date(syxDate); 
						bizDate2.setFullYear(bizDate2.getFullYear()+1); 
						bizDate2.setDate(bizDate2.getDate()-1); 
						//截止日期
						var endDate = new Date(bizDate2).Format("yyyy-MM-dd");	
						$("#syDateId").html('商业险保险期限：<br/>'+syxDate+' 至  '+endDate+'24时止');
						$("#syDateId1").html('自    '+syxDate+'  00:00:00 <br/>至    '+endDate+'24时止');
	             }
					
	             //是否投保交强险
	             if(result.carInsurance.insuranceOfVehicleTrafficAccident){
	            	 //交强险起保日期
	            	 var jqDate = result.carInsurance.startingDateOfVehicleTrafficAccident;
	            	 var jqDate2=new Date(syxDate); 
	            	 jqDate2.setFullYear(jqDate2.getFullYear()+1); 
	            	 jqDate2.setDate(jqDate2.getDate()-1); 
					 //截止日期
					 var endDate = new Date(jqDate2).Format("yyyy-MM-dd");
            		 $("#jqDateId1").html('自    '+jqDate+'  00:00:00 <br/>至    '+endDate+'24时止');
            		 startTimejq = jqDate;
	             }
	             
	             //投被保人信息
	             var userHtml="";
	             var userHtml1="";
            	 var userHtml2="";
                 $.each(result.contact, function(k, value) {
                	//投保人信息
                	 if(value.type=="1"){
                	   var address=value.address == null?"":value.address;
                	   userHtml+='<tr> <td colspan="2">投保人：<label>'+value.name+'</label></td><td>联系电话<label>'+value.phone+'</label></td> </tr>'
                	   userHtml1+='<tr> <td colspan="2">投保人：<label>'+value.name+'</label></td> </tr>'
	              			 +'<tr><td colspan="2">地址：<label>'+address+'</label></td></tr>'
	              			 +'<tr><td colspan="2">邮编：<label></label></td></tr>'
	              			 +'<tr><td colspan="2">联系电话：<label>'+value.phone+'</label></td></tr>'
	              			 +'<tr><td colspan="2">证件号: <label>'+value.certificateNumber+'</label></td></tr>';
		                	 $("#carNoId").html(value.certificateNumber);
                	 }else if(value.type=="2"){
                		 userHtml2+='<tr> <td colspan="2">被投保人：<label>'+value.name+'</label></td> </tr>'
                			 +'<tr><td colspan="2">地址：<label>'+address+'</label></td></tr>'
                			 +'<tr><td colspan="2">邮编：<label></label></td></tr>'
                			 +'<tr><td colspan="2">联系电话：<label>'+value.phone+'</label></td></tr>'
                			 +'<tr><td colspan="2">E-mail：<label></label></td></tr>'
                			 +'<tr><td colspan="2">身份证号码（组织机构代码）: <label>'+value.certificateNumber+'</label></td></tr>';
                	 }else if(value.type=="3"){
                		 var sex = "";
                		 var tabHtml='';
                		  //获取性别
                         if (parseInt(value.certificateNumber.substr(16, 1)) % 2 == 1) { 
                        	 sex="男"; 
                         }else{ 
                        	 sex="女"; 
                         } 
                         //获取年龄
                         var myDate = new Date(); 
                         var month = myDate.getMonth() + 1; 
                         var day = myDate.getDate();
                         var age = myDate.getFullYear() - UUserCard.substring(6, 10) - 1; 
                         if (UUserCard.substring(10, 12) < month || UUserCard.substring(10, 12) == month && UUserCard.substring(12, 14) <= day) { 
                        	 age++; 
                         } 
                         tabHtml+='<tr><td colspan="2">指定驾驶员姓名：<label>'+value.name+'</label></td></tr>'
                        	 +'<tr><td colspan="2">驾驶证号：<label>'+value.certificateNumber+'</label></td></tr>'
                        	 +' <tr><td>性别：<label>'+sex+'</label></td><td>年龄：<label>'+age+'</label></td></tr>';
                         $("#zdTabId").html();
                	 }
                 });
                 insuredIdHtml = userHtml+userHtml2;
                 insuredId1Html = userHtml1+userHtml2;
                 
                 var carNo = result.carInsurance.moldName;
                 var vinNo = result.carInsurance.frameNo;
                 var enginNo = result.carInsurance.engineNo;
                 
                 
                 //车辆信息
                	 carInfoHtml+='<tr><th colspan="2">保险车辆信息</th></tr>'
		            			 +'<tr><td colspan="2">行驶证车主：<label>'+result.carInsurance.owner+'</label></td></tr>'
		            			 +'<tr><td colspan="2">识别代码（车架号）:<label>'+vinNo+'</label></td></tr>'
		            			 +'<tr><td>车牌号码：<label>'+carNo+'</label></td><td>新车购置价：<label>0.00元</label></td></tr>'
		            			 +'<tr><td colspan="2">厂牌型号：<label>'+result.carInsurance.moldName+'</label></td></tr>'
		            			 +'<tr><td>排量：<label></label></td><td>座位数／吨位数：<label></label></td></tr>'
		            			 +'<tr><td>发动机号码：<label>'+enginNo+'</label></td><td>车辆使用性质：<label>'+numIsOperat+'</label></td></tr>'
		            			 +'<tr><td colspan="2">行驶区域:<label>'+result.carInsurance.cityName+'</label></td></tr>'
		            			 +'<tr><td colspan="2">初次登记日期:<label>'+result.carInsurance.enrollDate+'</label></td></tr>'
		            			 +'<tr><td colspan="2">行驶证发证日期:<label></label></td></tr>';
        			 
                	 carInfoHtml1+='<tr><td>车主姓名：<label>'+result.carInsurance.owner+'</label></td><td>车牌号码：<label>'+carNo+'</label></td></tr>'
            			 +'<tr> <td>发动机号：<label>'+enginNo+'</label></td><td>号牌种类：<label>小型汽车</label></td></tr>'
            			 +'<tr><td colspan="2">厂牌车型：<label>'+result.carInsurance.moldName+'</label></td></tr>'
            			 +'<tr><td colspan="2">实际价值：<label>'+chesun+'</label></td></tr>'
            			 +'<tr><td colspan="2">识别代码（车架号）：<label>'+vinNo+'</label></td></tr>';
//	                	if( carState == 2){
                	 //旧车赔付系数显示
	                		var tableHtml = '<tr><td>理赔纪录浮动系数：<label>0.8500</label></td></tr>'
	                			+'<tr><td>自主渠道系数：<label>0.8500</label></td></tr>'
	                			+'<tr><td>自主核保系数：<label>0.8500</label></td></tr>'
	                			+'<tr><td>交通违法系数：<label>0.8500</label></td></tr>'
	                			+'<tr><td>最终浮动系数：<label>0.6141</label></td></tr>'
	                			+'<tr><td>本次保险您应缴纳保险费共计：人民币    '+total+'    元（大写：'+DX(total)+'）</td></tr>';
	                		$("#tableId").html(tableHtml);
		                	$("#jqDateId").html('浮动因素计算区间：<br/>'+StringToDate(startTimejq));
	                		carInfoHtml1+='<tr> <td colspan="2">商业险浮动因素计算区间：<label>'+StringToDate(startTimesy)+'</label></td></tr>';
//	                	}
	                	 carInfoHtml2+='<tr><td>车主姓名：<label>'+result.carInsurance.owner+'</label></td><td>车牌号码：<label>'+result.carInsurance.licenseNo+'</label></td></tr>'
	            			 +'<tr><td>发动机号：<label>'+enginNo+'</label></td><td>号牌种类：<label>小型汽车</label></td></tr>'
	            			 +'<tr> <td colspan="2">识别代码（车架号）：<label>'+vinNo+'</label></td></tr>';
//                 });
                 
                 $("#vinNoId").html(vinNo);
                 $("#engNoId").html(enginNo);
                 $("#carNoId").html(carNo);
                 $("#carInfoId1").html(carInfoHtml1);	
                 $("#carInfoId2").html(carInfoHtml2);
                 $("#insuredCarInfo1").html(userHtml+insuredIdHtml+carInfoHtml);
                 $("#insuredCarInfo2").html(userHtml+insuredId1Html+carInfoHtml);
			}else{
				console.log("获取异常!");
			}
		}
	})
}

//获取车辆出险信息
function getCarClaimInfo(){
	$.ajax({
		type:"post",
		url:getUrl("policy/getCarClaimInfo.do"),		
		dataType:"json",
		data:{"licenseNo":licenseNo,"userId":userId},
		success:function(data){	
			if(data.success){
				var result = eval('('+data.result+')');
				var cxTabIdHtml = '<tr><td>序号</td><td>出险时间</td><td>结案时间</td><td>赔款金额</td><td>投保保险公司</td></tr>';
				if(result.errorCode=="A0000000"){
					if(result.responseBody.List !=null){
						 $.each(result.responseBody.List, function(k, value) {
							 cxTabIdHtml += '<tr><td>'+(k+1)+'</td><td>'+value.claimTime+'</td><td>'+value.endCaseTime+'</td><td>'+value.claimAmount.toFixed(2)+'</td><td>直赔</td></tr>';
						 });
						$("#cxTabId").html(cxTabIdHtml);
					}
				}else{
					console.log(result.errorMsg);
				}
			}else{
//				alert("获取异常!");
			}
		}
	})
}
//解析定位、手机信息、打开双录
function  setLogin(result){
	var obj = eval('('+result+')');
	if(obj.code ==0){
		setTimeout(getPolicy(),1000);
	}else{
		setTimeout(function(){alert(obj.message);}, 1000);
	}
}


function StringToDate(DateStr) {
	var result ="";
	if(DateStr !=undefined && DateStr!=""){
		var converted = Date.parse(DateStr);
		var arys = DateStr.split('-');
		var upYear = arys[0]-1;
		var upDay = arys[2]-1;
		if(upDay >= 0 && upDay <= 9){
			upDay="0"+upDay;
		}
		result = upYear+'年'+arys[1]+'月'+arys[2]+'日  00:00:00  至   '+arys[0]+'年'+arys[1]+'月'+upDay+'日24时止';
	}
	return result;
}

function getDateYearMonth(){
	var date = new Date();
	var month = date.getMonth() + 1;
	var strDate = date.getDate();
	if (month >= 1 && month <= 9) {
	    month = "0" + month;
	}
	if (strDate >= 0 && strDate <= 9) {
	    strDate = "0" + strDate;
	}
	var hours = date.getHours();
	var currentdate1 = "<strong> "+date.getFullYear() + " </strong>年<strong> " + month+" </strong>月<strong> "+strDate+" </strong>日<strong> "+hours +" </strong>";   
	$('#labId').html(currentdate1);
	var currentdate = "<label>"+date.getFullYear()+ "</label>年<label>" + month+"</label>月<label>"+strDate+"</label>日";
	$('#dataId1').html(currentdate);
	$('#dataId2').html(currentdate);
	$('#dataId3').html(currentdate);
	$('#dataId4').html(currentdate);
	$('#dataId5').html(currentdate);
	$('#dataId6').html(currentdate);
}

function setSignature(result){
	var obj = eval('('+result+')');
	if(obj.code == 0){
		$("#signId").css("display","block");
		$("#signId1").css("display","none");
		 var imagePath = obj.result.imgStr;
//		 sessionStorage.setItem('signImagePath',imagePath);
		 $("#imagePathId1").attr("src","data:image/png;base64,"+imagePath);
		 $("#imagePathId2").attr("src","data:image/png;base64,"+imagePath);
		 $("#imagePathId3").attr("src","data:image/png;base64,"+imagePath);
		 $("#imagePathId4").attr("src","data:image/png;base64,"+imagePath);
		 $("#imagePathId5").attr("src","data:image/png;base64,"+imagePath);
		 $("#imagePathId6").attr("src","data:image/png;base64,"+imagePath);
		 $("#imagePathId7").attr("src","data:image/png;base64,"+imagePath);
//		 alert("orderId====="+orderId);
		 if(orderId !="" && orderId !=null){
			 var jsonParam ={"imagePath":encodeURIComponent(imagePath),"orderId": orderId};
			 var params = JSON.stringify(jsonParam);  
			 $.ajax({
			    type : "POST",
			    url : getUrl("policy/uploadSignImage.do?params="+params),
			    dataType : "json",
			    success : function(data) {
			    	if(data.success == true){
			    		var objDate = data.result;
			    		if(objDate.success==true){
			    			console.log("上传成功!");
			    		}else{
			    			console.log("上传失败!");
			    		}
			    	}else{
			    		console.log("异常错误!");
			    	}
			    }
		 	});
		 }
	 }
}


function btnok(){
	var agentId = sessionStorage.getItem('agentId');
	var extraParam = {"numId":agentId,"flag":"1","telImei":telImei,"chrInUpload":"1","recType":"1","orderId":orderId};
	var extra = JSON.stringify(extraParam); 
	var upURL=getUrl("agent/upLoadUrl.do");
	//结束双录
	stopRecording(extra,upURL,0);
}

//结束双录
function setStopRecording(result){
	var obj = eval('('+result+')');
	
	var payParams =  {"policyNo":policyNo,"sySalefee":sySalefee,"jqSalefee":jqSalefee,"cSalefee":cSalefee,"total":total};
	var payParam = JSON.stringify(payParams);
	sessionStorage.setItem('payParam',payParam);
	if(obj.code == 0){
//		window.location.href=getUrl("ajax/content/pay/payType.html");
		window.location.href=getUrl("ajax/content/pay/pay.html");
	}
	if(obj.code == 1){
		window.location.href=getUrl("ajax/content/order/confirmInsureInfoy.html");
	}
}
