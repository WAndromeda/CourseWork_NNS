$(document).ready(function () {
    $("#phone_span").mask('9 (999) 999-9999', {
        placeholder: "(___) ___-____"
    });

});

function goTo(url) {
    document.location.href = url;
}
