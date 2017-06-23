/**
 * Created by chenyingyan on 2016/3/23.
 */
$(function(){
    //下拉框
    $('.selep span select').on('change',function(){
        $(this).parent('span').prevAll('input').val($(this).find("option:selected").text());
    });
    //复选按钮
    /*$('.checkp').click(function(){
        if($(this).children('span').hasClass('checkps1')){
            $(this).find('input').attr('checked',true);
            $(this).children('span').removeClass('checkps1');
        }else{
            $(this).find('input').attr('checked',false);
            $(this).children('span').addClass('checkps1');

        }
    });*/
    //单选按钮
    $('.radiop').each(function(){
        $(this).click(function(){
            $('.radiop input').attr('checked',false);
            $('.radiop span').addClass('checkps1');
            $(this).find('input').attr('checked',true);
            $(this).children('span').removeClass('checkps1');
        })
    })
    //日期
    $('.date .inpdate').click(function(){alert($(this).val());
        $(this).prevAll('.inptxt').val($(this).val());
    });
   /* $('.insdiv3 dd p').click(function(){
        if($(this).hasClass('bluep')){
           $(this).removeClass('bluep');
        }else{
           $(this).addClass('bluep');
        }
    });*/
    //不计免赔
    $('#notlose').click(function(){
        if($(this).children('input').attr('checked')=='checked'){
            $('.insurance1 ul li .checkp input').each(function(index){
               /* alert($(this).attr('checked'));
                 alert(index);*/
                  if(index==2 && $('.insurance1 ul li .checkp input:eq('+index+')').attr('checked')=='checked'){
                     $('#lose01').addClass('bluep');
                  }else if(index==3 && $('.insurance1 ul li .checkp input:eq('+index+')').attr('checked')=='checked'){
                      $('#lose02').addClass('bluep');
                  }else if(index==4 && $('.insurance1 ul li .checkp input:eq('+index+')').attr('checked')=='checked'){
                      $('#lose03').addClass('bluep');
                  }else if(index==6 && $('.insurance1 ul li .checkp input:eq('+index+')').attr('checked')=='checked'){
                      $('#lose04').addClass('bluep');
                  }else if(index==7 && $('.insurance1 ul li .checkp input:eq('+index+')').attr('checked')=='checked'){
                      $('#lose05').addClass('bluep');
                  }else if(index==9 && $('.insurance1 ul li .checkp input:eq('+index+')').attr('checked')=='checked'){
                      $('#lose07').addClass('bluep');
                  }else if(index==10 && $('.insurance1 ul li .checkp input:eq('+index+')').attr('checked')=='checked'){
                      $('#lose06').addClass('bluep');
                  }else if(index==12 && $('.insurance1 ul li .checkp input:eq('+index+')').attr('checked')=='checked'){
                      $('#lose08').addClass('bluep');
                  }

            })
        }else{
            $('.insdiv3 dd p').removeClass('bluep');
        }
    })
   //收起
   $('.details a').click(function(){
       if($(this).hasClass('retract')){
           $(this).children('span').html('查看详情');
           $(this).removeClass('retract');
           $('.insurance1').slideUp();
       }else{
           $(this).children('span').html('收起');
           $(this).addClass('retract');
           $('.insurance1').slideDown();
       }
   });
    //发送弹出框
    /*$('.lady').height($(document).height());
    $('.lgbtn').click(function(){
        $('.lady').show();
        $('.issuesuccess').show();
    });
    $('.issuesuccess header a').click(function(){
        $('.lady').hide();
        $('.issuesuccess').hide();
    })*/
    //投保单确认查看详情
   $('.insurep1').click(function(){
       $(this).prevAll('.insurediv').slideDown();
       $(this).hide();
   })
    //投保单详情查看详情
    $('.xqa').click(function(){
       if($(this).children('i').hasClass('addimg')){
           $(this).html('<i></i>查看详情');
           $('.orderxq').slideToggle();
       }else{
           $(this).html('<i class="addimg"></i>收起');
           $('.orderxq').slideToggle();
       }
    });
    $('.carzinfo').each(function(){
        $(this).children('a').click(function(){
          if($(this).children('a').html()=='更多'){
            $(this).children('a').html('收起');
              $('.carzinfoat').removeClass('carzinfoath');
          }else{
           $(this).children('a').html('更多');
           $('.carzinfoat').addClass('carzinfoath');
          }
        })
    });
    //重置密码
    $('.showpsw').click(function(){
     if($('.pswinp').attr('type')=='password'){
        $('.pswinp').attr('type','text')}else{
        $('.pswinp').attr('type','password')
     }
    })
})