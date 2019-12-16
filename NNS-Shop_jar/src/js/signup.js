$(document).ready(function () {
    $("#eye_password").on('click', function(){
        show_hidePassword("password_input", "show_password_eye");
    });
    $("#eye_password_confirm").on('click', function(){
        show_hidePassword("password_confirmation_input", "show_password_eye_confirmation");
    });
    $("#phone_input").on('input', function(){
        phoneValidOrInvalid("phone_input");
    });
    $("#spare_phone_input").on('input', function(){
        phoneValidOrInvalid("spare_phone_input");
    });
    $("#surname_input").on('input', function(){
        surname_nameValidOrInvalid("surname_input");
    });$("#name_input").on('input', function(){
        surname_nameValidOrInvalid("name_input");
    });

    //2. Получить элемент, к которому необходимо добавить маску
    $("#phone_input").mask('(999) 999-9999', {
        placeholder: "(___) ___-____"
    });
    $("#spare_phone_input").mask('(999) 999-9999', {
        placeholder: "(___) ___-____"
    });
    $("#patronymic_input").on('input', function(){
        patronymicValidOrInvalid();
    });
    $("#date_of_birth_input").mask('99.99.9999', {
        placeholder: "__.__.____"
    });
    try {
        $("#date_of_birth_input").datepicker({
            maxDate: new Date(),
            onSelect: function (fd, date) {
                let date_of_birth_input = document.getElementById("date_of_birth_input");
                if (date !== "")
                    date_of_birth_input.className = "form-control datepicker-here is-valid";
                else
                    date_of_birth_input.className = "form-control datepicker-here is-invalid";
            }
        });
    }catch (e) {
        alert(e);
    }
});

class User{
    surname = "";
    name = "";
    patronymic = "";
    sex = "";
    email = "";
    phone = "";
    spare_phone = "";
    date_of_birth = "";
    password = "";
}

user = new User();

function show_hidePassword(input, image_eye) {
    let password_field = document.getElementById(input);
    let password_button_eye = document.getElementById(image_eye);
    if (password_field.type === "password") {
        password_field.type = "text";
        password_button_eye.className = "far fa-eye";
    } else {
        password_field.type = "password";
        password_button_eye.className = "far fa-eye-slash";
    }
}

function phoneValidOrInvalid(input) {
    let phone_input = document.getElementById(input);
    //alert(phone_input.value.length);
    let phone = phone_input.value.replace(/[\s()-]/g, '').trim().toLowerCase();
    if (phone.match(/[0-9]{10}/g) == phone) {
        phone_input.className = "form-control is-valid";
    } else
        if (input === "spare_phone_input" && phone.length === 0){
            phone_input.className = "form-control";
        }else {
            phone_input.className = "form-control is-invalid";
        }
}

function emailValidOrInvalid() {
    let email_input = document.getElementById("email_input");
    //alert(phone_input.value.length);
    /*alert(email_input.value.match(/[a-zA-Z0-9]+[a-zA-Z0-9_.-]*[a-zA-Z0-9]+@[a-zA-Z]+[.][a-zA-Z]+/g));
    alert(email_input.value.match(/[a-zA-Z0-9]+[a-zA-Z0-9_.-]*[a-zA-Z0-9]+@[a-zA-Z]+[.][a-zA-Z]+/g) == email_input.value);*/
    if (email_input.value.match(/[a-zA-Z0-9]+[a-zA-Z0-9_.-]*[a-zA-Z0-9]+@[a-zA-Z0-9]+[.][a-zA-Z]+/g) == email_input.value && email_input.value.length >= 5 &&  email_input.value.length <= 350) {
        email_input.className = "form-control is-valid";
    } else {
        email_input.className = "form-control is-invalid";
    }
}
function surname_nameValidOrInvalid(input) {
    let surname_name_input = document.getElementById(input);
    //alert(phone_input.value.length);
    if (surname_name_input.value.length > 0 && surname_name_input.value.length <= 50
        && surname_name_input.value.match(/[a-zA-Z0-9а-яА-ЯёЁ]+[\sa-zA-Z0-9а-яА-ЯёЁ]*/g) == surname_name_input.value ) {
        surname_name_input.className = "form-control is-valid";
    } else {
        surname_name_input.className = "form-control is-invalid";
    }
}

function patronymicValidOrInvalid() {
    let patronymic_input = document.getElementById("patronymic_input");
    //alert(phone_input.value.length);
    if (patronymic_input.value.length > 0 && patronymic_input.value.length <= 50
        && patronymic_input.value.match(/[a-zA-Z0-9а-яА-ЯёЁ]+[\sa-zA-Z0-9а-яА-ЯёЁ]*/g) == patronymic_input.value ) {
        patronymic_input.className = "form-control is-valid";
    } else if (patronymic_input.value.length === 0) {
        patronymic_input.className = "form-control";
    }else{
        patronymic_input.className = "form-control is-invalid";
    }

}

function changeGender() {
    let select_gender = document.getElementById("sex_select");
    let gender_icon = document.getElementById("sex_icon");
    try {
        if (select_gender.options[select_gender.selectedIndex].value === "1") {
            gender_icon.className = "fas fa-mars";
        } else if (select_gender.options[select_gender.selectedIndex].value === "2") {
            gender_icon.className = "fas fa-mars-stroke-v";
        } else {
            gender_icon.className = "fas fa-venus-mars";
        }
    }catch (e) {
        alert(e);
    }
}

function register(e){
    e.preventDefault();
    user.surname        = $("#surname_input").val().trim();
    user.name           = $("#name_input").val().trim();
    user.patronymic     = $("#patronymic_input").val().trim();
    user.sex            = $("#sex_select").val().trim().toLowerCase();
    if (user.sex === "1") user.sex = "мужской";
    else if (user.sex === "2") user.sex = "женский";
        else user.sex = "не_определён";
    try {
        user.email          = $("#email_input").val().toLowerCase().trim();
        user.phone          = "7" + $("#phone_input").val().toLowerCase().trim();
        user.phone = user.phone.replace(/[\s()-]/g, '');
    }catch (e) {
        alert(e);
    }
    user.spare_phone    = $("#spare_phone_input").val().toLowerCase().trim();
    if (user.spare_phone !== "")
        user.spare_phone = "7" + user.spare_phone.replace(/[()\s-]/g, "");
    if (user.phone === user.spare_phone){
        let spare_phone_input = document.getElementById("spare_phone_input");
        spare_phone_input.className = "form-control is-invalid";
        bootbox.alert({
            size: "medium",
            title: "Запасной телефонный номер равен основному",
            message: "Введите другой запасной мобильный телефон, отличный от основного<br>",
            buttons: {
                ok: {
                    label: 'Принято',
                    className: 'btn btn-danger'
                }
            },
            callback: function(){ /* your callback code */ }
        });
        return;
    }
    user.date_of_birth  = $("#date_of_birth_input").val().trim().toLowerCase();
    user.password       = $("#password_input").val().trim();
    if (user.password === $("#password_confirmation_input").val().trim()) {
        try {
            let data_ajax = "surname=" + encodeURIComponent(user.surname.trim()) +
                "&name=" + encodeURIComponent(user.name.trim()) +
                (user.patronymic.trim() === "" ? "" : "&patronymic=" + encodeURIComponent(user.patronymic.trim())) +
                "&sex=" + encodeURIComponent(user.sex.trim().toLowerCase()) +
                "&email=" + encodeURIComponent(user.email.trim().toLowerCase()) +
                "&phone=" + encodeURIComponent(user.phone.trim().toLowerCase()) +
                (user.spare_phone.trim() === "" ? "" : "&spare_phone=" + encodeURIComponent(user.spare_phone.trim())) +
                "&date_of_birth=" + encodeURIComponent(user.date_of_birth.trim().toLowerCase()) +
                "&password=" + encodeURIComponent(user.password.trim());
            $('#signup_button').prop('disabled', true);
            $.ajax({
                url: "/signup?" + data_ajax,
                method: 'POST',
                data: '',
                success: function (json_object, textStatus, jqXHR) {
                    location.href = "http://localhost/signup-success";
                    $('#signup_button').prop('disabled', false);
                },
                error: function (jqXHR, exception) {
                    $('#signup_button').prop('disabled', false);
                    if (jqXHR.status === 500){
                        let json_object = JSON.parse(jqXHR.responseText);
                        if (json_object.is_email_sent === "no") {
                            bootbox.alert({
                                size: "medium",
                                title: "Ошибка отправки email-подтверждения",
                                message: "Невозможно отправить email-подтверждение для завершения регистрации<br><u>Попробуйте зарегестрироваться через " + json_object.hours_to_wait + " час(а)(ов)</u>",
                                buttons: {
                                    ok: {
                                        label: 'Принято',
                                        className: 'btn btn-danger'
                                    }
                                },
                                callback: function () { /* your callback code */
                                }
                            })
                        }
                    }
                    if (jqXHR.status === 550) {
                        let email_input = document.getElementById("email_input");
                        email_input.value = user.email;
                        email_input.className = "form-control is-invalid";
                        bootbox.alert({
                            size: "medium",
                            title: "Ошибка отправки email-подтверждения",
                            message: "Ваш email не существует<br><u>Введите другой email</u>",
                            buttons: {
                                ok: {
                                    label: 'Принято',
                                    className: 'btn btn-danger'
                                }},
                                callback: function () { /* your callback code */
                            }
                        });
                    }

                    if (jqXHR.status === 400){
                        bootbox.alert({
                            size: "medium",
                            title: "Произошла непредвиденная ошибка",
                            message: "Произошла непредвиденная ошибка<br><u>Повторите попытку регистрации позже</u>",
                            buttons: {
                                ok: {
                                    label: 'Принято',
                                    className: 'btn btn-danger'
                                }},
                            callback: function(){ /* your callback code */ }
                        });
                    }
                    let json_array;
                    if (jqXHR.status === 409) {
                        let json_object = JSON.parse(jqXHR.responseText);
                        if (json_object.occupied_data.includes('_')) {
                            json_array = json_object.occupied_data.split('_');
                        } else {
                            json_array = [json_object.occupied_data];
                        }
                        let message_text = "";
                        json_array.forEach(function (value) {
                            if (value.toLowerCase() === "phone") {
                                let phone_input = document.getElementById("phone_input");
                                phone_input.className = "form-control is-invalid";
                                message_text += "Мобильный телефон: +" + user.phone + "<br>";
                            } else if (value.toLowerCase() === "email") {
                                let email_input = document.getElementById("email_input");
                                email_input.value = user.email;
                                email_input.className = "form-control is-invalid";
                                message_text += "Email: " + user.email + "<br>";
                            } else if (value.toLowerCase() === "sparephone") {
                                let spare_phone_input = document.getElementById("spare_phone_input");
                                spare_phone_input.className = "form-control is-invalid";
                                message_text += "Дополнительный мобильный телефон: " + user.spare_phone + "<br>";
                            }
                        });
                        bootbox.alert({
                            size: "medium",
                            title: "Часть данных уже занята",
                            message: "Следущие данные уже заняты другими клиентами:<br><br>" + message_text + "<br>Необходимо заменить эти данные",
                            buttons: {
                                ok: {
                                    label: 'Принято',
                                    className: 'btn btn-danger'
                                }
                            },
                            callback: function () { /* your callback code */
                            }
                        });
                    }
                }
            });
        }catch (e) {
            alert(e);
        }
    }else{
        alert("Введите пароль повторно верно");
    }
}
