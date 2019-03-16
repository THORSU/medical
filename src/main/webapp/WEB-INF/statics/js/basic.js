$(function () {
    /*确认退出层*/
    $('.sign-out>a').click(function () {
        $(this).parents('body').find('.outlayer').slideDown(200);
    });
    $('.tip-box .cancel').click(function () {
        $(this).parents('.outlayer').slideUp(200);
    });

    /*展示选择框*/
    $('.show-btn').click(function () {
        $(this).siblings('.checkshow').toggle().siblings('.goin').toggle();
        $(this).parents('body').find('.cover-bg').show();
    });
    $('.close').click(function () {
        $(this).parent('.checkshow').hide().parents('body').find('.cover-bg').hide();
    });
    $('.checkshow>p').click(function () {
        $(this).addClass('checked').siblings().removeClass('checked');
    });
    $('.sex>label').click(function () {
        $(this).addClass('sex-check').siblings().removeClass('sex-check');
    });
    $('.hd>li').click(function () {
        var index = $(this).index();
        console.log(index)
        $(this).addClass('active').siblings().removeClass('active');
        $('.bd>div').eq(index).show().siblings().hide();
    });
    $('.notice-content a:even').css('background', '#eee');

    /* $(document).on("click", ".page-item", function (e) {
          $(this).addClass('active');
        });*/

    var touch_ele = ".touch,a,button";//Elements that need to be clicked
    touch_feedback(touch_ele);//Add touch feedback
    function touch_feedback(ele) {
        $('body').on("touchstart touchend touchmove tap", ele, function (e) {
            var $this = $(this)
            // e.preventDefault();
            switch (event.type) {
                case "touchstart":
                    $this.addClass("activated");
                    break;
                case "touchmove":
                    $(this).removeClass("activated");
                    break;
                case "touchend":
                    $(this).removeClass("activated");
                    break;
                case "tap":
                    var url = $this.attr("data-url")
                    if (url)
                        window.location.href = url;
                    break;
            }
        });
    };//touch_feedback
});

//去掉加载
$(document).ready(function () {
    setTimeout(function () {
        $("#bigLoadingHolder").addClass("display-none");
    }, 1500);
});

document.documentElement.addEventListener('dblclick', function (e) {
    e.preventDefault();
});

//if((navigator.userAgent.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i))) {
//    window.location.href="http://m.tutetube.cn:9012";
//}else{
//
//    window.location.href="http://www.tutetube.cn:9012";
//}