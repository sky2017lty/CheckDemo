function isNull(str) {
    return str === null || str === undefined || str.length === 0;
}

// 检查是否登陆
function loginCheck() {
    var cookies = getCookie("username");
    if (isNull(cookies)) {
        window.location.href = "./login.html";
    } else {
        $(".username").text(cookies);
    }
}

//退出登录
function exitLogin() {
    cleanCookies("username");
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