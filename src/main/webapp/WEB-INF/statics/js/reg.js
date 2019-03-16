/**
 * Created by cxd on 2016/10/18.
 */

//检查密码
function pwdtest() {
    var password = $("#password").val();
    var numasc = 0;
    var charasc = 0;
    if (0 == password.length) {
        $("#error").html("密码不能为空");
    } else if (password.length < 6 || password.length > 12) {
        if (password.length > 12) {
            $("#error").html("密码最多12个字符");
        } else {
            $("#error").html("密码至少6个字符");
        }
    } else {
        for (var i = 0; i < password.length; i++) {
            var asciiNumber = password.substr(i, 1).charCodeAt();
            if (asciiNumber >= 48 && asciiNumber <= 57) {
                numasc += 1;
            }
            if ((asciiNumber >= 65 && asciiNumber <= 90) || (asciiNumber >= 97 && asciiNumber <= 122)) {
                charasc += 1;
            }
        }
        if (0 == numasc) {
            $("#error").html("密码必须含有数字");
        } else if (0 == charasc) {
            $("#error").html("密码必须含有字母");
        } else {
            $("#error").html("");
        }
    }
}

function yanzheng() {
    var password1 = $("#password1").val();
    var password = $("#password").val();
    if (password.trim() == password1.trim() && password != "" && password != "") {
        $("#error").html("");
    } else {
        $("#showWhat").html('<div class="ub ub-ac bc-head ub-pc bc-text-head uc-a" style="height:2.5em" id="resgister" onclick="submitSt()">注册 </div>');
        $("#error").html("两次输入密码不一致！");
    }
}

//检查昵称
function checknick() {
    var nickname = $("#nickname").val();
    if (/[\u4E00-\u9FA5]/i.test(nickname)) {
        $("#showWhat").html('<div class="ub ub-ac bc-head ub-pc bc-text-head uc-a" style="height:2.5em" id="resgister" onclick="submitSt()">注册 </div>');
        $("#error").html("用户名不能为中文！");
    } else if (nickname.trim() == "") {
        $("#showWhat").html('<div class="ub ub-ac bc-head ub-pc bc-text-head uc-a" style="height:2.5em" id="resgister" onclick="submitSt()">注册 </div>');
        $("#error").html("用户名不能为空！");
    } else {
        $.ajax({
            type: "POST",
            url: "/tutetube/checknickname.from?nickname=" + nickname.trim() + "",
            dataType: "html",
            success: function (msg) {
                if (msg.trim() == "1") {
                    $("#error").html("");
                } else {
                    $("#showWhat").html('<div class="ub ub-ac bc-head ub-pc bc-text-head uc-a" style="height:2.5em" id="resgister" onclick="submitSt()">注册 </div>');
                    $("#error").html("该用户名已注册！");
                }
            }
        });
    }
}

//开始注册
function submitSt() {
    $("#showWhat").html('<div class="ub ub-ac bc-head ub-pc bc-text-head uc-a" style="height:2.5em" id="waitfor">等待中...... </div>');
    var error = $("#error").html();
    var sex = $('input:radio[name="sex"]:checked').val();
    if (error.trim() == "") {
        var nickname = $("#nickname").val();
        if (/[\u4E00-\u9FA5]/i.test(nickname)) {
            $("#showWhat").html('<div class="ub ub-ac bc-head ub-pc bc-text-head uc-a" style="height:2.5em" id="resgister" onclick="submitSt()">注册 </div>');
            alert("用户名不能为中文！");
        } else {
            var password = $("#password").val();
            var password1 = $("#password1").val();
            var nicknamecn = $("#nicknamecn").val();
            if (password.trim() == "" || password1.trim() == "" || nickname.trim() == "" || nicknamecn == "" || sex == "") {
                $("#showWhat").html('<div class="ub ub-ac bc-head ub-pc bc-text-head uc-a" style="height:2.5em" id="resgister" onclick="submitSt()">注册 </div>');
                alert("请正确填写信息！");
            } else {
                $.ajax({
                    type: "POST",
                    url: "/tutetube/register.from?nickname=" + nickname.trim() + "&password=" + password.trim() + "&nicknamecn=" + nicknamecn.trim() + "&sex=" + sex + "",
                    dataType: "html",
                    success: function (msg) {
                        if (msg.trim() == "1") {
                            alert("注册成功！");
                            window.location.href = "login.html";
                        } else if (msg.trim() == "0") {
                            $("#showWhat").html('<div class="ub ub-ac bc-head ub-pc bc-text-head uc-a" style="height:2.5em" id="resgister" onclick="submitSt()">注册 </div>');
                            alert("注册失败！");
                        } else if (msg.trim() == "401") {
                            $("#showWhat").html('<div class="ub ub-ac bc-head ub-pc bc-text-head uc-a" style="height:2.5em" id="resgister" onclick="submitSt()">注册 </div>');
                            alert("用户名不能为中文！");
                        }
                    }
                });
            }
        }
    } else {
        $("#showWhat").html('<div class="ub ub-ac bc-head ub-pc bc-text-head uc-a" style="height:2.5em" id="resgister" onclick="submitSt()">注册 </div>');
        alert("请正确填写信息！");
    }
}
