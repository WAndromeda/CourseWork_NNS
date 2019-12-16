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
            alert(jqXHR.status);
            if (jqXHR.status === 307){
                bootbox.alert({
                    size: "medium",
                    title: "Случилось переброска",
                    message: "Введите другой запасной мобильный телефон, отличный от основного<br>",
                    buttons: {
                        ok: {
                            label: 'Принято',
                            className: 'btn btn-danger'
                        }
                    },
                    callback: function(){ /* your callback code */ }
                });
            }
        }
    });
}