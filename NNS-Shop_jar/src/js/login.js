$(document).ready(function () {
   $("#password_show_button").on('click', function () {
       showPassword();
   });
});

function showPassword() {
    let password_input = document.getElementById("password_input");
    let password_button_eye = document.getElementById("show_password_eye");
    if (password_input.type === "password") {
        password_input.type = "text";
        password_button_eye.className = "far fa-eye";
    } else {
        password_input.type = "password";
        password_button_eye.className = "far fa-eye-slash";
    }
}



function enter(e){
    e.preventDefault();
    let login = $("#login_input").val().trim();
    let password = $("#password_input").val().trim();
    try {
        let data_ajax = "login=" + encodeURIComponent(login.trim().toLowerCase()) +
            "&password=" + encodeURIComponent(password.trim());
        $.ajax({
            url: "/login?" + data_ajax,
            method: 'POST',
            data: '',
            success: function (json_object, textStatus, jqXHR) {
                try {
                   /* json_object.password = decodeURIComponent(json_object.password);
                    Date.prototype.addDays = function(days) {
                        let date = new Date(this.valueOf());
                        date.setDate(date.getDate() + days);
                        return date;
                    };
                    var date = new Date();s
                    date.addDays(7);
                    let date_string = date.toUTCString();
                    alert(date_string);
                    document.cookie = "login=" +  encodeURIComponent(json_object.login.trim().toLowerCase()) + "; path=/; expires=" + date_string;
                    document.cookie = "password=" +  encodeURIComponent(json_object.password.trim()) + "; path=/; expires=" + date_string;*/
                    location.href = "http://localhost/";
                }catch (e) {
                    alert(e);
                }
            },
            error: function (jqXHR, exception) {
                //$('#signup_button').prop('disabled', false);
                if (jqXHR.status === 409){
                        bootbox.alert({
                            size: "medium",
                            title: "Неверный логин или пароль",
                            message: "Вы ввели логин (номер мобильного телефона или email), который не привязан ни к какому пользователю или же вы ввели неверный пароль<br>Проверьте введённые данные и попробуйте войти ещё раз",
                            buttons: {
                                ok: {
                                    label: 'Принято',
                                    className: 'btn btn-danger'
                                }
                            },
                            callback: function(){ /* your callback code */ }
                        });
                    }
                else
                    if (jqXHR.status === 422){
                        bootbox.alert({
                            size: "medium",
                            title: "Неверный логин или пароль",
                            message: "Вы ввели логин (номер мобильного телефона или email), который не привязан ни к какому пользователю, или же Вы ввели неверный пароль<br>Проверьте введённые данные и попробуйте войти ещё раз",
                            buttons: {
                                ok: {
                                    label: 'Принято',
                                    className: 'btn btn-danger'
                                }
                            },
                            callback: function(){ /* your callback code */ }
                        });
                    }else
                        if (jqXHR.status === 400){
                        }
            }
        });
    }catch (e) {
        alert(e);
    }
}