/**
 * Created by yinfeifei on 2016/3/24.
 * $(".fotmenu a").each(function(){
		$(this).removeClass('selea');
	});
	$(this).addClass('selea');
 */

function getUrl(url){
    return "http://localhost:8080/"+url;
    //return "http://124.127.240.51:8099/demo.dower.vc/"+url;
}
/**
 * 获取url参数
 * @param name
 * @returns
 */
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return r[2]; return null;
}

//验证手机号码
function checkMobile(mobile){
    var mobileReg =/^((\(\d{3}\))|(\d{3}\-))?(1(3\d|5\d|8\d|7[0678]))\d{8}?$/;
    if(!mobileReg.test(mobile)) {
        return false;
    }else{
        return true;
    }
}
//验证邮箱
function checkEmail(str){
    var re = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/
    if(!re.test(str)){
        return false;
    }else{
        return true;
    }
}

var publicCarNameLength=4;
/*
 验证身份证的合法性
 根据〖中华人民共和国国家标准 GB 11643-1999〗中有关公民身份号码的规定，公民身份号码是特征组合码，由十七位数字本体码和一位数字校验码组成。排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，三位数字顺序码和一位数字校验码。
 地址码表示编码对象常住户口所在县(市、旗、区)的行政区划代码。
 出生日期码表示编码对象出生的年、月、日，其中年份用四位数字表示，年、月、日之间不用分隔符。
 顺序码表示同一地址码所标识的区域范围内，对同年、月、日出生的人员编定的顺序号。顺序码的奇数分给男性，偶数分给女性。
 校验码是根据前面十七位数字码，按照ISO 7064:1983.MOD 11-2校验码计算出来的检验码。

 出生日期计算方法。
 15位的身份证编码首先把出生年扩展为4位，简单的就是增加一个19或18,这样就包含了所有1800-1999年出生的人;
 2000年后出生的肯定都是18位的了没有这个烦恼，至于1800年前出生的,那啥那时应该还没身份证号这个东东，⊙﹏⊙b汗...
 下面是正则表达式:
 出生日期1800-2099  (18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01])
 身份证正则表达式 /^\d{6}(18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i
 15位校验规则 6位地址编码+6位出生日期+3位顺序号
 18位校验规则 6位地址编码+8位出生日期+3位顺序号+1位校验位

 校验位规则     公式:∑(ai×Wi)(mod 11)……………………………………(1)
 公式(1)中：
 i----表示号码字符从由至左包括校验码在内的位置序号；
 ai----表示第i位置上的号码字符值；
 Wi----示第i位置上的加权因子，其数值依据公式Wi=2^(n-1）(mod 11)计算得出。
 i 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2 1
 Wi 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2 1

 */
//身份证号合法性验证
//支持15位和18位身份证号
//支持地址编码、出生日期、校验位验证
function IdentityCodeValid(code) {

    var city={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江 ",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北 ",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏 ",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外 "};
    var tip = "";
    var pass= true;

    if (!code || !/^[1-9][0-9]{5}(19[0-9]{2}|200[0-9]|2010)(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])[0-9]{3}[0-9xX]$/i.test(code)) {
        tip = "身份证号格式错误";
        pass = false;
    }

    else if(!city[code.substr(0,2)]){
        tip = "地址编码错误";
        pass = false;
    }
    else{
        //18位身份证需要验证最后一位校验位
        if(code.length == 18){
            code = code.split('');
            //∑(ai×Wi)(mod 11)
            //加权因子
            var factor = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ];
            //校验位
            var parity = [ 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 ];
            var sum = 0;
            var ai = 0;
            var wi = 0;
            for (var i = 0; i < 17; i++)
            {
                ai = code[i];
                wi = factor[i];
                sum += ai * wi;
            }
            var last = parity[sum % 11];
            if(parity[sum % 11] != code[17].toUpperCase()){
                tip = "校验位错误";
                pass =false;
            }
        }
    }

    return pass;
}

//计算天数差的函数，通用
function GetDateDiff(startDate,endDate) {
    var startTime = new Date(Date.parse(startDate.replace(/-/g,   "/"))).getTime();
    var endTime = new Date(Date.parse(endDate.replace(/-/g,   "/"))).getTime();
    var dates = Math.ceil((startTime - endTime)/(1000*60*60*24));
    return  dates;
}

//获取当前日期格式：2016-03-22
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate;
    return currentdate;
}

//转换str为JSON
function stringToJson(body){
    return eval("(" + body + ")");
}

/*格式化时间戳，用于剩余多少时间，返回格式：几天,时:分:秒 */
function time_format(s) {
	var t;
	if(s > -1){
		hour = Math.floor(s/3600);
		min = Math.floor(s/60) % 60;
		sec = s % 60;
		day = parseInt(hour / 24);
		if (day > 0) {
			hour = hour - 24 * day;
			t = day + "天," + hour + ":";
        }else{
			t = hour + ":";
		}

		if(min < 10){t += "0";}
        t += min + ":";
		if(sec < 10){t += "0";}
        t += sec;
	}else{
		t = "0:00:0x";
	}
	return t;
}

/*获取当前日期，格式yyyy-MM-dd HH:MM:SS*/
function getNowFormatDateTime() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
        + " " + date.getHours() + seperator2 + date.getMinutes()
        + seperator2 + date.getSeconds();
    return currentdate;
}

//日期格式：2016-03-22
function getStringFormatDate(date) {
    //var date = new Date();
    var seperator1 = "-";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate;
    return currentdate;
}

/*获取当前时间戳*/
function getNowTimestamp() {
	return Date.parse(new Date());
}

//字符串转成Time(dateDiff)所需方法 时间格式：yyyy-MM-dd hh:mm:ss
function stringToTime(string){
    var f = string.split(' ', 2);
    var d = (f[0] ? f[0] : '').split('-', 3);
    var t = (f[1] ? f[1] : '').split(':', 3);
    return (new Date(
        parseInt(d[0], 10) || null,
        (parseInt(d[1], 10) || 1)-1,
        parseInt(d[2], 10) || null,
        parseInt(t[0], 10) || null,
        parseInt(t[1], 10) || null,
        parseInt(t[2], 10) || null
    )).getTime();
}

/*获取某个日期的时间戳 时间格式：yyyy-MM-dd hh:mm:ss*/
function getDateTimestamp(date){
	var type = typeof date;
	if(type == 'string'){
		date = stringToTime(date);
	}else if(date.getTime){
		date = date.getTime();
	}

    return date;//结果是毫秒
}

//核保查询
function getUnderwritingFlag(orderId,isNotPay){
    $(".indexZZ").show();
    $.ajax({
        url: "/getUnderwritingFlag",
        type: "get",
        contentType: "application/json",
        dataType: "json",
        data: {
            orderId: orderId
        },
        success: function(data) {
           // alert(JSON.stringify(data));
            $(".indexZZ").hide();
            var msg = "订单核保中，无法支付，详细信息可电话咨询!";
            try {
                if(data.status != 2){//没成功
                    if(data.status == 1){//核保中
                        alert(msg);
                    }else{//核保失败
                        var message = data.msg;
                        if(message){
                            alert("订单核保失败，无法支付，失败原因：" + message + "，详细信息可电话咨询!");
                        }else{
                            alert("订单核保失败，无法支付，详细信息可电话咨询!");
                        }
                    }
                    if(isNotPay){
                    }else{
                        location.href="/myOrder";
                    }
                } else{
                    //alert("订单核保成功，可以支付!");
                    if(isNotPay){
                        alert("订单核保成功，可以支付!");
                    }else{
                        payOrder(data.orderId);//支付
                    }
                }
            }catch (e){
                alert(JSON.stringify(e));
                alert(msg);
                location.href="/myOrder";
            }
        },error: function(e) {
            $(".indexZZ").hide();
            alert(msg);
            location.href="/myOrder";
        }
    });
}

//支付订单
function payOrder(orderId){
    try{
        var user = sessionStorage["user"];
        user = eval("(" + user + ")");
        var payInfo="";
        $.ajax({
            url: "/confirmPay",
            type: "get",
            async: true,
            contentType: "application/json",
            dataType: "json",
            data: {
                orderId: sessionStorage["orderId"],
                openId: user.userOpenId
            },
            success: function (data) {
                payInfo = data;
                alert("私车支付信息:"+JSON.stringify(payInfo));
                post("http://chexian.chelenet.com/agentPayment",{chargeInfo:JSON.stringify(payInfo),numSalePrice:sessionStorage["totalBasePrice"],orderId:sessionStorage["orderId"],carTypeValue:1,hebaoState:2});
               }
        });
    }catch(e){
        window.location.href="/myOrder";
    }

}
/**
 * 获取保险公司名称
 * @param compId
 * @param ifShortName
 */
function getInsureCompanyName(compId,ifShortName){
	
	var name = "";	
	if(compId==1){
		if(ifShortName){ 
			name='太平洋';
		}else{
			name='中国太平洋保险公司';
		}
	}else if(compId==2){
		if(ifShortName){ name='平安';}else{name='中国平安保险'}
	}else if(compId==3){
		if(ifShortName){ name='人保';}else{name='中国人民保险'}
	}else if(compId==4){
		if(ifShortName){ name='永诚保险';}else{name='永诚保险'}
	}else if(compId==5){
		if(ifShortName){ name='人寿保险';}else{name='人寿保险'}
	}
	return name;
}

/**
 * 小写数字转中文大写
 * @param n
 * @returns {String}
 */
function DX(n) {
    if (!/^(0|[1-9]\d*)(\.\d+)?$/.test(n))
        return "数据非法";
    var unit = "千百拾亿千百拾万千百拾元角分", str = "";
        n += "00";
    var p = n.indexOf('.');
    if (p >= 0)
        n = n.substring(0, p) + n.substr(p+1, 2);
        unit = unit.substr(unit.length - n.length);
    for (var i=0; i < n.length; i++)
        str += '零壹贰叁肆伍陆柒捌玖'.charAt(n.charAt(i)) + unit.charAt(i);
    return str.replace(/零(千|百|拾|角)/g, "零").replace(/(零)+/g, "零").replace(/零(万|亿|元)/g, "$1").replace(/(亿)万|壹(拾)/g, "$1$2").replace(/^元零?|零分/g, "").replace(/元$/g, "元整");
}

