

$(document).ready(function () {
    $("#label_is_favorite").on('click', function () {
        set_unsetFavorite(!$("#is_favorite").is(':checked'));
    });
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

function set_unsetFavorite(is_checked){
    let params = window.location.search.replace('?', '');
    let id_product_param = params.match(/id=[\d]+/)[0];
    let id_product = id_product_param.split("=")[1];
    let data_ajax = "id_product=" + encodeURIComponent(id_product);
    if (is_checked){
        $.ajax({
            url: "/favorite?" + data_ajax,
            method: 'POST',
            data: '',
            success: function (json_object, textStatus, jqXHR) {
                location.reload();
            },
            error: function (jqXHR, exception) {

            }
        });
    }else{
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
}