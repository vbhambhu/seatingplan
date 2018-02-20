$.fn.exists = function(callback) {
    var args = [].slice.call(arguments, 1);

    if (this.length) {
        callback.call(this, args);
    }
    return this;
};

$('.select2-multiple').exists(function() {
$('.select2-multiple').select2();
});


$('.datatable').exists(function() {
    $(".datatable").DataTable();
});

$('.datepicker').exists(function() {
    $(".datepicker").datepicker({
        format: 'dd-mm-yyyy'
    });
});


$('.color').exists(function() {

    $( ".color" ).each(function( index ) {
        $(this).css("color", "#"+$( this ).data('color'));
    });

});

