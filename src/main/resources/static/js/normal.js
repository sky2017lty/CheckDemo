function isNull(str) {
    return str === null || str === undefined || str.length === 0;
}

function getDate() {
    var date = new Date();
    var year = date.getFullYear(); //获取当前年份
    var mon = date.getMonth() + 1; //获取当前月份
    var da = date.getDate(); //获取当前日
    return year + '-' + mon + '-' + da;
}

function getDateTime() {
    var date = new Date();
    var h = date.getHours(); //获取小时
    if (h < 10) {
        h = "0" + h;
    }
    var m = date.getMinutes(); //获取分钟
    if (m < 10) {
        m = "0" + m;
    }
    var s = date.getSeconds(); //获取秒
    if (s < 10) {
        s = "0" + s;
    }
    return h + ':' + m + ':' + s;
}

function getTime() {
    var date = new Date();
    var year = date.getFullYear(); //获取当前年份
    var mon = date.getMonth() + 1; //获取当前月份
    var da = date.getDate(); //获取当前日
    var h = date.getHours(); //获取小时
    if (h < 10) {
        h = "0" + h;
    }
    var m = date.getMinutes(); //获取分钟
    if (m < 10) {
        m = "0" + m;
    }
    var s = date.getSeconds(); //获取秒
    if (s < 10) {
        s = "0" + s;
    }
    return year + '年' + mon + '月' + da + '日' + ' ' + h + ':' + m + ':' + s;
}

function getUrlVariable(variable) {
    var query = window.location.toString();
    var urlParams = query.split("?");
    var vars = urlParams[1].split("&");
    for (var i = 0; i < vars.length; i++) {
        var pair = vars[i].split("=");
        if (pair[0] === variable) {
            return pair[1];
        }
    }
    return false;
}

//重新渲染表单
function renderForm() {
    layui.use('form', function () {
        var form = layui.form;//高版本建议把括号去掉，有的低版本，需要加()
        form.render();
    });
}

//重新渲染表格
function reloadTable(id) {
    layui.use('table', function () {
        var table = layui.table;//高版本建议把括号去掉，有的低版本，需要加()
        table.reload(id);
    });
}

// 检查是否登陆
function loginCheck() {
    var cookies = getCookie("username");
    var name = getCookie("name");
    if (!checkSession(cookies)) {
        window.location.href = "./login.html";
    } else {
        $(".username").text(name);
    }
}

//退出登录
function exitLogin() {
    cleanCookies("username");
    delSession("username");
    cleanCookies("name");
    delSession("name");
    window.location.href = "./login.html";
}

//获取cookies
function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i].trim();
        if (c.indexOf(name) == 0) return c.substring(name.length, c.length);
    }
    return "";
}

//增加cookies（浏览器关闭就需要重新登录）
function setCookie(cname, cvalue) {
    document.cookie = cname + "=" + cvalue;
}

//增加cookies
function setCookieDays(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
    var expires = "expires=" + d.toGMTString();
    document.cookie = cname + "=" + cvalue + "; " + expires;
}

//清除cookies
function cleanCookies(cname) {
    setCookieDays(cname, "", -1);
}

function checkSession(sname) {
    var flag = false;
    $.ajax({
        url: "/checkSession",    //请求的url地址
        dataType: "json",   //返回格式为json
        data: {sname: sname},    //参数值
        type: "POST",   //请求方式
        async: false,
        success: function (req) {
            //请求成功时处理
            flag = req.code === 0;
        }
    });
    return flag;
}

function delSession(sname) {
    var flag = false;
    $.ajax({
        url: "/delSession",    //请求的url地址
        dataType: "json",   //返回格式为json
        data: {sname: sname},    //参数值
        type: "POST",   //请求方式
        async: false,
        success: function (req) {
            //请求成功时处理
            flag = req.code === 0;
        }
    });
    return flag;
}

function checkCode(code) {
    switch (code) {
        //成功
        case 0:
            break;
        //管理员登录成功
        case 1:
            break;
        //失败
        case 200:
            break;
        //未登录
        case 201:
            window.location.href = "./login.html";
            break;
    }
}

const audioFailed = new Audio('audio/failed.m4a');
const audioSuccess = new Audio('audio/success.m4a');

function playAudio(flag) {
    if (flag) {
        audioSuccess.play();
    } else {
        audioFailed.play();
    }
}

var first = 0, second = 0;

function checkInput(inputId) {
    $("#" + inputId).keyup(function (e) {
        if ($(this).val().length % 2 != 0) {
            first = new Date().valueOf();
        } else {
            second = new Date().valueOf();
        }
        //通过判断两次输入的时间 间隔是否为手动输入.这里面限制100ms.
        if ($(this).val().length > 1 && Math.abs(first - second) > 100) {
            $(this).val('');
        }
    });
}

function mesNo(mes, no, fno, fid, tid) {
    if (isNull(mes) || isNull(no)) {
        playAudio(false);
        $("#" + fno).val('');
        $("#" + fid).val('').focus();
    } else {
        $("#" + tid).val('').focus();
    }
}

function checkMES(mes, no, type) {
    if (!isNull(mes)) {
        if (!isNull(no)) {
            return !isNull(type);
        }
    }
    return false;
}

function checkType(lastId, thisId) {
    if (isNull(thisId)) {
        return false;
    }
    if (lastId === thisId) {
        return true;
    } else {
        return false;
    }
}
