var carInfo = JSON.parse(sessionStorage.getItem("carInfo"));
//console.log(carInfo);
var upFlag = getQueryString("flag");
var userId = sessionStorage.getItem("userId");
if(userId==null){
	userId = "e04c8f7692c74314a9708d27fdb264fb";
}
$(function(){ 	
	if(upFlag==1 && carInfo!=null){
		$("#ownerName").val(carInfo.ownerName);
		$("#ownerCredentialsNo").val(carInfo.ownerCredentialsNo);
		if(carInfo.newCar==2){
			var carNo = carInfo.licenseNo;
			$('#shortName').val(carNo.substring(0,1));		
			$('#licenseNo').val(carNo.substring(1));
			$("#insuranceCompany").val(carInfo.insuranceCompany);
			showOldCar();
		}else{
			showNewCar();		
		}		
	}else{
		//旧车
		showOldCar();
	}	

    //车牌选择
    $('.carbrand select').change(function(){
        $(this).prevAll('span').html($(this).find("option:selected").text());        
    });
   
    //车牌号显示
    $('.checkp span').click(function(){    	
        if($(this).hasClass('checkps1')){         	
        	showOldCar();     
        }else{  
        	showMessage("请联系客服：<br><a href=\"tel:400-888-6666\" style=\"display:block;margin-top:0.75rem;text-align:center;font-size:0.9rem;\">400-888-6666</a>");
        	//showNewCar();
        }
    });
    
    //下拉框
    $('#selInsurCompany').change(function(){
        $(this).parent('span').prevAll('input').val($(this).find("option:selected").text());
        $("#insuranceCompanyId").val($(this).find("option:selected").val());
    });    
  
	//投保车辆确认
	$("#confirmBtn").click(function(){
		confirmInsureCar();
	});
	
	//点击遮罩层，关闭提示信息
	$(".lady").click(function(){
		closeMsg();
		$(".popbox").hide();
	});
})
/**
 * 新车
 */
function showNewCar(){
	$(".checkp span").addClass('checkps1');
	$('#ifNewCar').attr('checked',false);
    $('.carbdiv').hide();
    $('.lastComp').hide();
    $('#ifNewCar').val("1");  
    $('#licenseNo').val('');
    $("#insuranceCompanyId").val('');
    $("#insuranceCompany").val('');
}

/**
 * 旧车
 */
function showOldCar(){
	$(".checkp span").removeClass('checkps1');
	$('#ifNewCar').attr('checked',true);
    $('.carbdiv').show();
    $('.lastComp').show();
    $('#ifNewCar').val("2");
    getProvinceShortName();
    getInsurCompany();
}
/**
 * 获取省简称
 */
function getProvinceShortName(){
	$("#selShortName").empty();
	$.ajax({
		type:"post",
		url:getUrl("testdata/provinceData.json"),		
		dataType:"json",
		data:{parentId:0},
		success:function(data){	
			if(data.success){
				var result = data.result;
				//var result = eval('('+data.result+')');
				//console.log(result);
				var option = "";
				$.each(result,function(i, n) {
					option += '<option value="'+n.numCityid+'">'+n.shortname+'</option>';
				});
				$("#selShortName").append(option);
			}
			
		}
	});
}
/**
 * 获取保险公司
 */
function getInsurCompany(){
	$("#selInsurCompany").empty();
	$.ajax({
		type:"post",
		url:getUrl("testdata/insureCompanyData.json"),		
		dataType:"json",
		data:{parentId:0},
		success:function(data){	
			if(data.success){			
				var result = data.result;
				//console.log(result);
				if(result!=null){
					var option = "<option value=''>请选择</option>";
					$.each(result,function(i, n) {						
						option += '<option value="'+n.numInsurcompanyid+'">'+n.vc2Insurcompanyshortname+'</option>';
					});
					$("#selInsurCompany").append(option);
				}				
			}			
		}
	});
}
/**
 * 获取车辆信息
 * @param carNo
 */
function getCarInfo(carNo){
	if(carNo!=""){
		carNo = carNo.toUpperCase();
		$("#licenseNo").val(carNo);
		var licenseNo = $("#shortName").text()+carNo;		
		$.ajax({
			type:"post",
			url:getUrl("carInfo/getCarInfo.do"),		
			dataType:"json",
			data:{"licenseNo":licenseNo,"userId":userId},
			beforeSend:function(){alertMsg("正在获取续保信息...")},
			success:function(data){
				closeMsg();
				if(data.success){
					var result = eval('('+data.result+')');
					//console.log(result.responseBody);	
					if(result.responseBody!=null){
						$("#ownerName").val(result.responseBody.carInfo.ownerName);
						$("#ownerCredentialsNo").val(result.responseBody.carInfo.ownerCredentialsNo);
						var compId = result.responseBody.quotePrice.insuranceCompany;
						$("#insuranceCompanyId").val(compId);
						$("#insuranceCompany").val(getInsureCompanyName(compId,true));
						sessionStorage.setItem("renewalInfo",JSON.stringify(result.responseBody));
						sessionStorage.setItem("carInfo",JSON.stringify(result.responseBody.carInfo));
					}else{
						$("#ownerName").val("");
						$("#ownerCredentialsNo").val("");
						$("#insuranceCompany").val("");		
						sessionStorage.removeItem("renewalInfo");
						sessionStorage.removeItem("carInfo");
					}					
				}else{					
					$("#ownerName").val("");
					$("#ownerCredentialsNo").val("");
					$("#insuranceCompany").val("");
					$("#insuranceCompanyId").val("");
				}				
			},
			error:function(){
				closeMsg();
			}
		});
	}	
}
/**
 * 确认投保车辆
 */
function confirmInsureCar(){	
	var carInfo = JSON.parse(sessionStorage.getItem("carInfo"));
	var ifNewCar = $("#ifNewCar").val();
	var licenseNo = "";
	var insuranceCompanyId="";
	var insuranceCompany="";
	//旧车有车牌号
	if(ifNewCar==2){
		if($("#licenseNo").val()==""){
			showMessage("请输入车牌号！");
			return;
		}
		licenseNo = $("#shortName").text()+$("#licenseNo").val();
		insuranceCompanyId = $("#insuranceCompanyId").val();
		insuranceCompany = $("#insuranceCompany").val();
	}
	
	var ownerName = $("#ownerName").val();
	if(ownerName==""){
		showMessage("请输入姓名！");
		return;
	}
	
	var reg=/^[\u2E80-\u9FFF]+$/;//Unicode编码中的汉字范围
	if(!reg.test(ownerName)){
		showMessage("只能输入汉字");
		return;
	}
	
	var ownerCredentialsNo = $("#ownerCredentialsNo").val();
	if(ownerCredentialsNo==""){
		showMessage("请输入身份证号！");
		return;
	}
	
	if(!IdentityCodeValid(ownerCredentialsNo)){
		showMessage("请输入正确的身份证号！");
		return;
	}				
	carInfo.ownerName = ownerName;
	carInfo.ownerCredentialsNo = ownerCredentialsNo;		
	carInfo.newCar = ifNewCar;
	carInfo.insuranceCompanyId = insuranceCompanyId;
	carInfo.insuranceCompany = insuranceCompany;
	carInfo.licenseNo=licenseNo;
	carInfo.imgCode=$("#licenseNo").val();
	sessionStorage.setItem("carInfo",JSON.stringify(carInfo));	
	location.href = getUrl("ajax/content/record/carInfoX.html?updateFlag="+upFlag);	
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