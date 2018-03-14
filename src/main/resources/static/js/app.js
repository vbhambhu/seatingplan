$.fn.exists = function(callback) {
    var args = [].slice.call(arguments, 1);

    if (this.length) {
        callback.call(this, args);
    }
    return this;
};


$('.datatable').exists(function() {
    $(".datatable").DataTable();
});

$('#sidebarCollapse').on('click', function () {
    $('#sidebar, #content').toggleClass('active');
    //$('.collapse.in').toggleClass('in');
    //$('a[aria-expanded=true]').attr('aria-expanded', 'false');
});