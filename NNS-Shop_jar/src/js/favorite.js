$(document).ready(function () {
});

function addToBasket(id_product) {
    let data_ajax = "id_product=" + encodeURIComponent(id_product);
    $.ajax({
        url: "/basket?" + data_ajax,
        method: 'POST',
        data: '',
        success: function (json_object, textStatus, jqXHR) {
            location.href = "http://localhost/basket";
        },
        error: function (jqXHR, exception) {

        }
    });
}

function delete_favorite(id_product) {
    let data_ajax = "id_product=" + encodeURIComponent(id_product);
    $.ajax({
        url: "/favorite?" + data_ajax,
        method: 'DELETE',
        data: '',
        success: function (json_object, textStatus, jqXHR) {
            location.reload();
        },
        error: function (jqXHR, exception) {

        }
    });
}