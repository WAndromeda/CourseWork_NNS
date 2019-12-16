let payment_methods_id = [];

$(document).ready(function () {
    $("#recipient_phone_input").mask('(999) 999-9999', {
        placeholder: "(___) ___-____"
    });
    $("#radio_pay_method_" + payment_methods_id[0]).prop('checked', true);
    $("#card_pay_method_" +  payment_methods_id[0]).attr('class', 'card payment-method-checked m-2');
});

function addPaymentMethodId(id){
    payment_methods_id.push(id);
}

function changePaymentMethod(id){
    let radio_input = $("#radio_pay_method_" + id);
    radio_input.prop('checked', true);
    $("#card_pay_method_" + id).attr('class', 'card payment-method-checked m-2');
    payment_methods_id.forEach(function (pay_id) {
        if (pay_id !== id){
            $("#card_pay_method_" + pay_id).attr('class', 'card payment-method m-2');
        }
    })
}


function confirmOrder(event) {
    try {
        event.preventDefault();
        let recipient_name = $("#recipient_name_input").val();
        let recipient_email = $("#recipient_email_input").val().toLowerCase().trim();
        let recipient_phone = "7" + $("#recipient_phone_input").val().toLowerCase().trim().replace(/[()\s-]/g, "");
        let id_payment_method = $("input[name='radio_pay_method']:checked").val() ;
        let address_shop = $("#address_shop_input").val().trim();
        let data_ajax =
            "recipient_name=" + encodeURIComponent(recipient_name.trim())
            + "&recipient_email=" + encodeURIComponent(recipient_email.trim().toLowerCase())
            + "&recipient_phone=" + encodeURIComponent(recipient_phone.trim().toLowerCase())
            + "&id_payment_method=" + encodeURIComponent(id_payment_method.trim())
            + "&address_shop=" + encodeURIComponent(address_shop.trim());
        $.ajax({
            url: "/purchase?" + data_ajax,
            method: 'POST',
            data: '',
            success: function (json_object, textStatus, jqXHR) {
                let jsObject = JSON.parse(jqXHR.responseText);
                location.href = "http://localhost/purchase-success?id_order=" + jsObject.id_order;
            },
            error: function (jqXHR, exception) {

            }
        });
    }catch (e) {
        alert(e);
    }

}