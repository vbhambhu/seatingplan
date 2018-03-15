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




if(getCookie('sidebarActive') == 'true'){

    $('#sidebar, #content').css("transition" , 'none');
    $('#sidebar, #content').toggleClass('active');
    $('.toggle-icon').toggleClass("fa-align-right");
}


$('#sidebarCollapse').on('click', function () {

    $('#sidebar, #content').toggleClass('active');
    $('.toggle-icon').toggleClass("fa-align-right");

    if(getCookie('sidebarActive') == "" || getCookie('sidebarActive') == "true"){
        document.cookie = "sidebarActive=false";
    } else{
        document.cookie = "sidebarActive=true";
    }

});

$('.select2-multiple').exists(function() {
    $('.select2-multiple').select2();
});

$('.select2_tags').exists(function() {
    $('.select2_tags').select2();
});

$('.color').exists(function() {
    $( ".color" ).each(function( index ) {
        $(this).css("color", "#"+$( this ).data('color'));
    });
});


$('.select2-global-search').select2({
    ajax: {
        url: "https://api.github.com/search/repositories",
        dataType: 'json',
        delay: 250,
        data: function (params) {
            return {
                q: params.term, // search term
                page: params.page
            };
        },
        processResults: function (data, params) {
            // parse the results into the format expected by Select2
            // since we are using custom formatting functions we do not need to
            // alter the remote JSON data, except to indicate that infinite
            // scrolling can be used
            params.page = params.page || 1;

            return {
                results: data.items,
                pagination: {
                    more: (params.page * 30) < data.total_count
                }
            };
        },
        cache: true
    },
    placeholder: 'Search for a user',
    escapeMarkup: function (markup) { return markup; },
    minimumInputLength: 1,
    templateResult: formatRepo,
    templateSelection: formatRepoSelection
});


function formatRepo (repo) {
    if (repo.loading) {
        return repo.text;
    }

    var markup = "<div class='select2-result-repository clearfix'>" +
        "<div class='select2-result-repository__avatar'><img src='" + repo.owner.avatar_url + "' /></div>" +
        "<div class='select2-result-repository__meta'>" +
        "<div class='select2-result-repository__title'>" + repo.full_name + "</div>";

    if (repo.description) {
        markup += "<div class='select2-result-repository__description'>" + repo.description + "</div>";
    }

    markup += "<div class='select2-result-repository__statistics'>" +
        "<div class='select2-result-repository__forks'><i class='fa fa-flash'></i> " + repo.forks_count + " Forks</div>" +
        "<div class='select2-result-repository__stargazers'><i class='fa fa-star'></i> " + repo.stargazers_count + " Stars</div>" +
        "<div class='select2-result-repository__watchers'><i class='fa fa-eye'></i> " + repo.watchers_count + " Watchers</div>" +
        "</div>" +
        "</div></div>";

    return markup;
}

function formatRepoSelection (repo) {
    return repo.full_name || repo.text;
}


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