/**
 * Created by chenyingyan on 2017/1/17.
 */
var sends = {
    checked:1,
    send:function(){
        var numbers = /^1\d{10}$/;
        var val = $('#phone').val().replace(/\s+/g,""); //获取输入手机号码
        if( $('.regul li a,.iodath ul li a').attr('class') == 'send1'){
            if(!numbers.test(val) || val.length ==0){
                alert("手机号为空，或格式错误");
                return false;
            }
        }
        if(numbers.test(val)){
            var time = 30;
            $('.div-phone span').remove();
            function timeCountDown(){
                if(time==0){
                    clearInterval(timer);
                    $('.regul li a,.iodath ul li a').addClass('send1').removeClass('send0').html("发送验证码");
                    sends.checked = 1;
                    return true;
                }
                $('.regul li a,.iodath ul li a').html(time+"S后再次发送");
                time--;
                return false;
                sends.checked = 0;
            }
            $('.regul li a,.iodath ul li a').addClass('send0').removeClass('send1');
            timeCountDown();
            var timer = setInterval(timeCountDown,1000);
        }
    }
}
