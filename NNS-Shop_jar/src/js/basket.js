$(document).ready(function () {
    $("#purchase_button").on('click', function () {
        location.href = "http://localhost/purchase";
    });
});

function deleteProductFromBasket(id_product) {
    let data_ajax = "id_product=" + encodeURIComponent(id_product);
    $.ajax({
        url: "/basket?" + data_ajax,
        method: 'DELETE',
        data: '',
        success: function (json_object, textStatus, jqXHR) {
            location.reload();
        },
        error: function (jqXHR, exception) {

        }
    });
}

function changeAmountInBasket(id_product) {
    let amount_select = document.getElementById("amount_select_" + id_product);
    let amount = amount_select.options[amount_select.selectedIndex].value;
    if (amount < 1 || amount > 5)
        amount = 1;
    let data_ajax =
        "id_product=" + encodeURIComponent(id_product)
        + "&amount=" + encodeURIComponent(amount);
    $.ajax({
        url: "/basket/change-amount?" + data_ajax,
        method: 'PUT',
        data: '',
        success: function (json_object, textStatus, jqXHR) {
            location.reload();
        },
        error: function (jqXHR, exception) {

        }
    });
}