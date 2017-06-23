var carInfo = JSON.parse(sessionStorage.getItem("carInfo"));
//console.log(carInfo);
$(function(){	
	//测试
	//getDriveLinceInfo();	
	
	var ifNewCar = carInfo.newCar;	
	if(ifNewCar==1){
		showNewCar();		
	}else{
		showOldCar();			
	}
	var upFlag = getQueryString("updateFlag");	

	//编辑获取session数据
	if(upFlag==1){
		$("#vinNo").val(carInfo.carVin);		
		if(ifNewCar==1){
			$("#carBrand").val(carInfo.brand);
			$("#carType").val(carInfo.brandModle);
			$("#datSalecarDate").val(carInfo.registerDate);
			var carCertPath= sessionStorage.getItem('carCertPath');			
			$("#carCertBtn").attr("src","data:image/png;base64,"+carCertPath);
			$("#carCertPath").val(carCertPath);
			var invoicePath = sessionStorage.getItem('invoicePath');
			$("#invoiceBtn").attr("src","data:image/png;base64,"+invoicePath);
			$("#invoicePath").val(invoicePath);	
		}else{
			$("#vc2Brand").val(carInfo.brand);
			$("#brandModle").val(carInfo.brandModle);
			$("#registerDate").val(carInfo.registerDate);
		}		
		$("#engineNo").val(carInfo.engineNo);
		
	}	
	
	//下拉框
    $('.selep span select').change(function(){
        $(this).parent('span').prevAll('input').val($(this).find("option:selected").text());
    }); 
    
 	var extra = "";
	//车辆行驶证
	$("#driveBtn").click(function(){		
		var extraParam = {"imgType":"1"};
		extra = JSON.stringify(extraParam);	
		$(".list").show();		
		setTimeout(function(){			
			$(".slide").show();
			$(".slide").slideDown();
		},300);
	});
	//车辆合格证
	$("#carCertBtn").click(function(){		
		var extraParam = {"imgType":"2"};
		extra = JSON.stringify(extraParam);	
		$(".list").show();		
		setTimeout(function(){			
			$(".slide").show();
			$(".slide").slideDown();
		},300);
	});	
	//购车发票
	$("#invoiceBtn").click(function(){	
		var vinNo = $("#vinNo").val();
		var extraParam = {"imgType":"3"};
		extra = JSON.stringify(extraParam);	
		$(".list").show();		
		setTimeout(function(){			
			$(".slide").show();
			$(".slide").slideDown();
		},300);
	});	
	//相册
	$("#albumBtn").click(function(){	
		uploadImage(extra,0);
	});	
	//拍照
	$("#photoBtn").click(function(){		
		uploadImage(extra,1);
	});	
	//点击遮罩层
	$(".list").click(function(){		
		$(".slide").slideUp();	
		setTimeout(function(){			
			$(".list").hide();
		},300);									
	});
	//取消
	$("#cancelBtn").click(function(){		
		$(".slide").slideUp();	
		setTimeout(function(){			
			$(".list").hide();
		},300);									
	});
	//被保险人信息确认
	$("#confirmBtn").click(function(){
		
		if(ifNewCar==1){
			var vc2CertificatePath = $("#carCertPath").val();
			if(vc2CertificatePath==""){
				showMessage("请上传车辆合格证！");
				return;
			}	
			
			var vc2BuyInvoicePath = $("#invoicePath").val();
			if(vc2BuyInvoicePath==""){
				showMessage("请上传购车发票！");
				return;
			}	
			
			var vc2Brand = $("#carBrand").val();
			if(vc2Brand==""){
				showMessage("请输入车辆品牌！");
				return;
			}			
			var brandModle = $("#carType").val();
			if(brandModle==""){
				showMessage("请输入选择车辆型号！");
				return;
			}
			var datSalecarDate = $("#datSalecarDate").val();
			if(datSalecarDate==""){
				showMessage("请选择购车日期！");
				return;
			}
			carInfo.brand = vc2Brand;
			carInfo.brandModle = brandModle;
			carInfo.vc2BuyInvoicePath = vc2BuyInvoicePath;
			carInfo.vc2CertificatePath = vc2CertificatePath;
			carInfo.registerDate = datSalecarDate;
		}else{
			var drivePath = $("#drivePath").val();
			/*if(drivePath==""){
				showMessage("请上传行驶证！");
				return;
			}*/						
			var vc2Brand = $("#vc2Brand").val();
			if(vc2Brand==""){
				showMessage("请输入品牌型号！");
				return;
			}
			
			var brandModle = $("#brandModle").val();
			if(brandModle==""){
				showMessage("请输入选择车型！");
				return;
			}
			
			var registerDate = $("#registerDate").val();
			if(registerDate==""){
				showMessage("请选择注册日期！");
				return;
			}
			carInfo.brand = vc2Brand;
			carInfo.brandModle = brandModle;
			carInfo.vc2DrivelicensePath = drivePath;
			carInfo.registerDate = registerDate;
		}
		
		var vinNo = $("#vinNo").val();
		if(vinNo==""){
			showMessage("请输入车辆识别码！");
			return;
		}	
		
		var vc2Seat = $("#vc2Seat").val();
		/*if(vc2Seat==""){
			showMessage("请输入车座！");
			return;
		}*/
		
		var engineNo = $("#engineNo").val();
		if(engineNo==""){
			showMessage("请输入发动机号！");
			return;
		}	
		carInfo.vinNo = vinNo;		
		carInfo.vc2Seat = "1";
		carInfo.engineNo = engineNo;	
		if(ifNewCar==1){
			$.ajax({
				type:"post",
				url:getUrl("carInfo/confirmCarInfo.do"),		
				dataType:"json",
				data:carInfo,
				success:function(data){
					data = eval(data);						
					if(data.success){
						if(ifNewCar==1){
							var vc2CertificatePath=data.vc2CertificatePath;
							if(vc2CertificatePath==null){
								vc2CertificatePath = "";
							}
							carInfo.vc2CertificatePath = vc2CertificatePath;
							var vc2BuyInvoicePath = data.vc2BuyInvoicePath;
							if(vc2BuyInvoicePath==null){
								vc2BuyInvoicePath="";
							}
							carInfo.vc2BuyInvoicePath = vc2BuyInvoicePath;
						}else{
							var vc2DrivelicensePath = data.vc2DrivelicensePath;
							if(vc2DrivelicensePath==null){
								vc2DrivelicensePath="";
							}
							carInfo.vc2DrivelicensePath = vc2DrivelicensePath;
						}
						//setTimeout(function(){alert("==="+JSON.stringify(carInfo));}, 1000);
					}
				}
			});	
		}
		
		sessionStorage.setItem("carInfo",JSON.stringify(carInfo));					
		//跳转至险种选择
		location.href=getUrl("ajax/content/order/insurChoice.html");
	});
	
	//点击遮罩层，关闭提示信息
	$(".lady").click(function(){
		closeMsg();
		$(".popbox").hide();
	});
})

/**
 * 上传图片
 * @param result
 */
function setUploadImage(result){	
	
	var obj = eval('('+result+')');
	if(obj.code == 0){			
		var imgPath = obj.result.imgStr;
		//var imagePath = obj.result.cropImgStr;
		var extra = obj.extra;
		var imgType = eval('('+extra+')').imgType;		
		//var imgType = extra.imgType;
		//setTimeout(function(){alert("imgType===="+obj.result);}, 1000);
		if(imgType==1){			
			sessionStorage.setItem('drivePath',imgPath);
			$("#driveBtn").attr("src","data:image/png;base64,"+imgPath);						
			//$("#drivePath").val(imgPath);
			getDriveLinceInfo(imgPath);
		}else if(imgType==2){			
			sessionStorage.setItem('carCertPath',imgPath);
			$("#carCertBtn").attr("src","data:image/png;base64,"+imgPath);						
			$("#carCertPath").val(imgPath);
		}else if(imgType==3){
			sessionStorage.setItem('invoicePath',imgPath);
			$("#invoiceBtn").attr("src","data:image/png;base64,"+imgPath);
			$("#invoicePath").val(imgPath);			
		}						
	 }		
}
/**
 * 新车
 */
function showNewCar(){
	$(".carimgli").hide();
	$(".regLi").hide();
	$(".carP").hide();
	$(".unCarP").show();	
	$(".newCar").show();
	$(".saleLi").show();
}
/**
 * 旧车
 */
function showOldCar(){			
	if(carInfo!=null){
		$("#vinNo").val(carInfo.vinNo);
		$("#vc2Brand").val(carInfo.brandModle);
		$("#brandModle").val(carInfo.brandModle);
		//$("#vc2Seat").val(GetCarInfo.vc2Seat);
		$("#engineNo").val(carInfo.engineNo);
		$("#registerDate").val(carInfo.registerDate);
	}		
	$(".newCar").hide();
	$(".saleLi").hide();
	$(".unCarP").hide();
	$(".carP").show()
	$(".carimgli").show();
	$(".regLi").show();
}

function showMessage(message){
	$('.lady').show();
	$('.popbox').show();
	$('#messageId').html(message);
	  $('.popbox article .mqd').click(function () {
	      $('.lady').hide();
	      $('.popbox').hide();
	  })
}

/**
 * 扫描车辆行驶证获取信息
 */
function getDriveLinceInfo(imgStr){	
	var licenseNo=carInfo.imgCode;
	$.ajax({
		type:"post",
		url:getUrl("carInfo/getDriveLicenseInfo.do"),		
		dataType:"json",
		data:{"imgStr":imgStr,"licenseNo":licenseNo},
		beforeSend:function(){alertMsg("正在识别行驶证信息...")},
		success:function(data){
			closeMsg();
			//console.log(data.result);	
			if(data.success){
				$("#drivePath").val(data.vc2DrivelicensePath);
				var result = eval('('+data.result+')');				
				if(result!=""){					
					$("#vinNo").val(result.vehicle_license_main_vin);
					$("#vc2Brand").val(result.vehicle_license_main_model);
					$("#brandModle").val(result.vehicle_license_main_model);
					$("#engineNo").val(result.vehicle_license_main_engine_no);
					$("#registerDate").val(result.vehicle_license_main_register_date);
				}
			}
		},
		error:function(){
			closeMsg();
		}
	});
}

function alertMsg(msg){
	$(".prom").html(msg);
	$(".prom").show();
	$(".lady").show();
}
function closeMsg(){
	$(".prom").html("");
	$(".prom").hide();
	$(".lady").hide();
}