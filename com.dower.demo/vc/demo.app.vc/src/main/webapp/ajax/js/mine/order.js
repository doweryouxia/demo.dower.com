
var userId = sessionStorage.getItem('agentId');
if(userId==null){
	userId = 1464;
}
// 初始页数
var pageNo = 1;
// 每页显示条数
var pageSize = 10;

function bindAllEvent() {		
	var orderStatus = "";
	var unPayCount = getQueryString("UN_PAY_COUNT");
	//console.log("=="+unPayCount);
	if(unPayCount!=0 && unPayCount!=null ){
		orderStatus = 7;
		$(".ordernav a").each(function(index){
			$(this).removeClass('sel');
			if(index==1){
				$(this).addClass('sel');
			}
		})		
	}else{
		orderStatus = getQueryString("CHR_PAY_STATUS");
	}	
	queryMethod("",orderStatus);

	var h=0;
	$("#con").bind("scroll", function(){			
		var scrollTop = $(this).scrollTop();
		var windowHeight = $(this).height();			
		var is = $(this).get(0).scrollHeight;//元素里面内容的实际高度
		if(scrollTop +windowHeight >= is && h<is){
			h=is;
			pageNo+=1;
			queryMethod(pageNo,orderStatus);		
		}
	});
	$(".ordernav a").click(function(){
		$(".ordernav a").each(function(){
			$(this).removeClass('sel');
		})
		$(this).addClass('sel');		
		//console.log($(this).attr("id"));
		var payStatus = $(this).attr("id");
		queryMethod("",payStatus);
	});	
}
	


function queryMethod(num,orderStatus) {
	//console.log("==="+payStatus)
	if (num == undefined || num=="") {
		pageNo = 1;
		$('#con').html("");
	} else {
		pageNo = num;
	}	
	
	var postData = {
		"pageNo" : pageNo,
		"pageSize" : pageSize,
		"userId": userId,
		"payStatus":orderStatus
	};
	var isHtml = "";
	$.ajax({
		url :getUrl("mine/queryOrder.do") ,
		type : 'post',
		dataType : "json",
		data : postData,
		async: false, 
		success : function(data) {
			if(data.success){		
				var pageIndex = 0;
				if (data.hasNextPage) {
					pageIndex = data.nextPage - 1;
				} else {
					pageIndex = data.totalPages;
				}
				if(!data.hasNextPage){
					hasNextPage = false;
				}
				if (data.rows != undefined) {
					var numTatolprice = 0;
					$.each(data.rows,function(i, n) {
						var payStatus = "去支付";
						if(n.numIsDebits!=0&&n.numIsDebits!=null){
							payStatus="再次购买";
						}	
						if(n.numTatolprice!=null){
							numTatolprice=n.numTatolprice;
						}
						
						isHtml+="<li onclick=\"getOrderDetail("+n.numOrderId+",'"+n.vc2OrderNo+"',"+n.numIsDebits+","+n.numIssuing+",'"+n.vc2CiPolicyNo+"','"+n.vc2BiPolicyNo+"','"+n.vc2PolicyLink+"')\"><h3><span class=\"odhsp2\">"+payStatus+"</span><span class=\"odhsp1\">"+txtFormatter(n.vc2Insurcompanyshortname)+"</span></h3>" +
								"<div class=\"orderdiv\">" +
								"<span class=\"orderlisp\">保费：<i>"+numTatolprice.toFixed(2)+"</i>元</span><span class=\"orderlisp\">车牌号：<i>"+txtFormatter(n.vc2AutoCode)+"</i></span>" +
								"<span class=\"orderlisp\">投保人：<i>"+txtFormatter(n.policyHolder)+"</i></span><span class=\"orderlisp\">被保险人：<i>"+txtFormatter(n.insureder)+"</i></span>" +
								"<p class=\"orderp\">下单时间：<i>"+new Date(n.datCreatetime).Format("yyyy-MM-dd hh:mm")+"</i></p>" +
								//"<p class=\"orderp\">下单时间：<i>"+n.datCreatetime+"</i></p>" +
								"<p class=\"orderp\">订单号：<i>"+n.vc2OrderNo+"</i></p>" +
								"</div></li>";	
						});
				}
				$('#con').append(isHtml);
			}else{
				$("#orderMain").html("服务器开小差，请求异常！");
			}			
		},
		error:function(){
			$("#orderMain").html("服务器开小差，请求异常！");
		}
	});
}

/**
 * 查看订单详情
 * @param orderId
 * @param orderNo
 * @param isDebits
 * @param isSuing
 * @param ciNo
 * @param biNO
 * @param link
 */
function getOrderDetail(orderId,orderNo,isDebits,isSuing,ciNo,biNo,link){
	var orderDetail = {};
	orderDetail.orderId=orderId;
	orderDetail.isDebits=isDebits;
	orderDetail.isSuing=isSuing;
	orderDetail.orderNo=orderNo;
	orderDetail.ciPolicyNo=ciNo;
	orderDetail.biPolicyNo=biNo;
	orderDetail.link=link;
	sessionStorage.setItem("orderDetail",JSON.stringify(orderDetail));
	window.location.href = getUrl("ajax/content/mine/detail.html");
}


