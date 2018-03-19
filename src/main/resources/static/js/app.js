$.fn.exists = function(callback) {
    var args = [].slice.call(arguments, 1);

    if (this.length) {
        callback.call(this, args);
    }
    return this;
};




$('.datepicker').exists(function() {
    $(".datepicker").datepicker({
        format: 'yyyy-mm-dd'
    });
});


$('.datatable').exists(function() {
    $(".datatable").DataTable();
});




if(localStorage.getItem("toggle") == "no"){

    $('#sidebar, #content').css("transition" , 'none');
    $('#sidebar, #content').toggleClass('active');
    $('.toggle-icon').toggleClass("fa-align-right");
}


$('#sidebarCollapse').on('click', function () {

    $('#sidebar, #content').toggleClass('active');
    $('.toggle-icon').toggleClass("fa-align-right");

    if (localStorage.getItem("toggle") === null) {
        //hide or show menu by default
        //do not forget to set a htlm5 cookie
        localStorage.setItem("toggle", "yes");

    } else{

        if(localStorage.getItem("toggle") == "yes"){
            //code when menu is visible
            localStorage.setItem("toggle", "no");
        } else{
            //code when menu is hidden
            localStorage.setItem("toggle", "yes");
        }
    }

});

$('.select2-multiple').exists(function() {
    $('.select2-multiple').select2();
});

$('.select2_tags').exists(function() {
    $('.select2_tags').select2();
});

$('.s2-option').exists(function() {
    $('.s2-option').select2();
});



$('.color').exists(function() {
    $( ".color" ).each(function( index ) {
        $(this).css("color", "#"+$( this ).data('color'));
    });
});



$('#sfloor_id').change(function(){
    $('#sfloor_form').submit();
});



$('.select2-global-search').select2({
    ajax: {
        url: "/api/user/search",
        dataType: 'json',
        delay: 250,
        data: function (params) {
            return {
                q: params.term, // search term
                page: params.page
            };
        },
        processResults: function (data, params) {
            params.page = 1;

            return {
                results: data
            };
        },
        cache: true
    },
    placeholder: 'Search for a user',
    escapeMarkup: function (markup) { return markup; },
    minimumInputLength: 1,
    templateResult: formatDropdownList,
    templateSelection: formatDropdownValue
});



function formatDropdownList (result) {
    if (result.loading) return "searching...";
    return  result.firstName + " " + result.lastName;
}


function formatDropdownValue (result) {
    return result.username || result.text;
}


$('.select2-global-search').on("change", function(e) {

    var fid = $(this).select2('data')[0].floorId;
    window.location.replace("/?floorid="+fid+"&uid="+$(this).val());

});

// $('.select2-global-search').on("select2:select", function(e) {
//     console.log($(this))
// });




function getCookie(cname) {
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for(var i = 0; i <ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}