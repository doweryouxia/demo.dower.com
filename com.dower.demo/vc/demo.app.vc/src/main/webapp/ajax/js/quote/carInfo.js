$("#newCar").click(function(){
	$("#oldCar").removeClass("sela");
	$("#newCar").addClass("sela");
	$("#oldCarNo").hide();
});
$("#oldCar").click(function(){
	$("#newCar").removeClass("sela");
	$("#oldCar").addClass("sela");
	$("#oldCarNo").show();
});

$("#privateCar").click(function(){
	$("#carOwnerLabel").html("车主姓名");
	$("#cardNoLabel").html("身份证号");
});
$("#businessCar").click(function(){
	//企业名称，社会信用码
	$("#carOwnerLabel").html("企业名称");
	$("#cardNoLabel").html("社会信用码");
});
$("#nextBut").click(function(){
	var carInfo = {};
	
	//是否是新车
	var newCar = 1;
	if($('#oldCar').is('.sela')){
		newCar = 0;
		carInfo.carNo = $("#carNo").val();
	}
	carInfo.newCar = newCar;
	//车辆性质，个人：0 ；公司：1；
	carInfo.carNature = $("input[name='carNature']:checked").val();
	//alert($("input[name='carNature']:checked").val());
	//车主姓名
	var carOwner = $("#carOwner").val();
	if(carOwner.length == 0){
	    $("#carOwner").next().css("display", "inline");
	    //return;
	}else{
	    $("#carOwner").next().css("display", "none");
	}
	carInfo.carOwner = $("#carOwner").val();
	//身份证号码
	var cardNo = $("#cardNo").val();
	if(!IdentityCodeValid(cardNo)){
		//return;
	}else{
		
	}
	carInfo.cardNo = $("#cardNo").val();
	//手机号
	var phone = $("#phone").val();
	if(!checkMobile(phone)){
		//return;
	}else{
		
	}
	carInfo.phone = $("#phone").val();
	//邮箱
	var email = $("#email").val();
	if(!checkEmail(email)){
		//return;
	}else{
		
	}
	carInfo.email = $("#email").val();
	//车牌号
	
	//车架号
	var carVin = $("#carVin").val();
	if(carVin.length > 20 || carVin.length == 0){
        $("#carVin").next().css("display", "inline");
        //return;
    }else{
        $("#carVin").next().css("display", "none");
    }
	carInfo.carVin = $("#carVin").val();
	//发动机号
	var engineNo = $("#engineNo").val();
	if(engineNo.length > 20 || engineNo.length == 0){
        $("#engineNo").next().css("display", "inline");
        //return;
    }else{
        $("#engineNo").next().css("display", "none");
    }
	carInfo.engineNo = $("#engineNo").val();
	//品牌型号
	var brand = $("#brand").val();
	 if(brand.length > 40 || brand.length == 0){
         $("#brand").next().css("display", "inline");
         //return;
     }else{
         $("#brand").next().css("display", "none");
     }
	carInfo.brand = $("#brand").val();
	
	sessionStorage["carInfo"] = JSON.stringify(carInfo)
	window.location.href="insurChoice.html";
});
$(".iconphoto").click(function(){
	scanIDCard(1);
});

function setScanIDCard(obj){
	/*"gender" : "男", //性别
	"code" : "340825198703273810", //身份证号码
	"address" : "安徽省芜湖市镜湖区渡春路13号",//身份证地址
	"name" : "胡楚", //姓名
	"type" : "1", //1表示正面， 2表示反面
	"nation" : "汉"//民族
	 */
	obj = stringToJson(obj);
	//alert("obj.result.name======"+obj.result.name);
	if(obj.code==0){
		$("#carOwner").val(obj.result.name);
		$("#cardNo").val(obj.result.code);
	}else if(obj.code==1||obj.code==1){
		alert(obj.message);
	}
}