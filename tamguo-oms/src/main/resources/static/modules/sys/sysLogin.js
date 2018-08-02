$("#username, #password").on("focus blur", function() {
    var a = this;
    setTimeout(function() {
        var b = $(a).css("borderColor");
        if (b != "") {
            $(a).prev().css("color", b)
        }
    }, 100)
}).blur();
$("#loginForm").validate({
    submitHandler: function(c) {
        var d = $("#username").val()
          , a = $("#password").val()
          , b = $("#validCode").val();
        js.ajaxSubmitForm($(c), function(f, e, g) {
            if (f.code == 0) {
                js.loading($("#btnSubmit").data("loading"));
                location = ctx + "index"
            } else {
            	js.showMessage(f.message)
            }
        }, "json", true, $("#btnSubmit").data("loginValid"));
        $("#username").val(d);
        $("#password").val(a).select().focus();
        $("#validCode").val(b)
    }
});
