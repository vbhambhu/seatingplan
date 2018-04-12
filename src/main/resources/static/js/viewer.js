var windowWidth,windowHeight,userData;

var floorId = $("#sfloor_id").val();
var draw = SVG('seating-plan');
var pop_template = '<div class="popover user-details-block" role="tooltip"><div class="arrow"></div><div class="popover-body"></div></div>';


$.ajax({
    type:"get",
    url:"/api/design/get?floorid="+floorId,
    beforeSend: function(  ) {
        $(".loading-page").css("display", "block");
    }
})
    .done(function( response ) {
        //hide your loading file here
        windowWidth = response.width;
        windowHeight = response.height;
        userData = response.users;
        draw.svg(response.svgContent);
        modifyElements();
        $(".loading-page").css("display", "none");


        //add group colour coding
        var garr = [];






});


function modifyElements(){

    draw.each(function() {

        if(typeof this.data('user-id') != "undefined" && this.data('user-id') != 0){

            this.animate({ duration: 250 }).flip('x');

            var user = getUserById(this.data('user-id'))[0];

            //fill group color
            this.fill('#'+user.groups[0].color);
            //add user name on seat
            var username = user.firstName + "\n" + user.lastName;
            var group = draw.group();
            group.add(this);
            group.addClass("seat");
            group.data('user-id', this.data('user-id'));
            var text = group.text(username).font({ fill: '#000', family: 'Poppins', size:16 });
            text.move(this.x() + 5,this.y() + (this.height() / 2) - (text.bbox().h /2));

            group.mouseover(function() {
                this.attr('cursor', 'pointer');
            });
        }
    });


    draw.viewbox(0,0,windowWidth ,windowHeight)


    $('.seat').mouseenter(function() {
        var e=$(this);
        e.off('hover');
        $.get('/api/user/details',{ id: e.data('user-id')}, function(userdata) {

            var grps = [];
            $.each( userdata.groups, function( key, value ) {
                grps.push(value.name);
            });

            var details = '<div class="row cdcol"><div class="col-sm-5 udrow">Name:</div><div class="col-sm-7">'+userdata.firstName + " " + userdata.lastName+'</div></div>';
            details += '<div class="row cdcol"><div class="col-sm-5 udrow">Start Date: </div><div class="col-sm-7">'+ moment(userdata.startDate).format('MMMM Do YYYY') +'</div></div>';
            details += '<div class="row cdcol"><div class="col-sm-5 udrow">End Date: </div><div class="col-sm-7">'+ moment(userdata.endDate).format('MMMM Do YYYY') +'</div></div>';
            details += '<div class="row cdcol"><div class="col-sm-5 udrow">Phone: </div><div class="col-sm-7">'+userdata.phone +'</div></div>';
            details += '<div class="row cdcol"><div class="col-sm-5 udrow">Email: </div><div class="col-sm-7">'+userdata.email +'</div></div>';
            details += '<div class="row cdcol"><div class="col-sm-5 udrow">Computer: </div><div class="col-sm-7">'+userdata.computerDetails +'</div></div>';
            details += '<div class="row cdcol"><div class="col-sm-5 udrow">Group(s): </div><div class="col-sm-7">'+ grps.join(", ")  +'</div></div>';
            details += '<div class="row cdcol"><div class="col-sm-5 udrow">Notes: </div><div class="col-sm-7">'+userdata.notes +'</div></div>';
            e.popover({
                title: userdata.firstName + " " + userdata.lastName,
                content: details,
                template: pop_template,
                html:true,
                container: 'body'
            }).popover('show');
        });
    });

    $('.seat').mouseleave(function() {
        $(this).popover('dispose')
    });

}


function getUserById(id) {
    return userData.filter(
        function(userData){ return userData.id == id }
    );
}


$('#search-by-group-on-floor').keyup(function() {

    $(".loading-page").css("display", "block");

    delay(function(){
        var query =  $('#search-by-group-on-floor').val().toLowerCase();
        draw.each(function() {

            if(typeof this.data('user-id') != "undefined" && this.type == "g" || this.data('type') == "crect"){

                var user = getUserById(this.data('user-id'))[0];
                var group = user.groups[0].name;

                if(!group.toLowerCase().includes(query)){
                    this.hide();
                } else{
                    this.show();
                }
            }
        });
        $(".loading-page").css("display", "none");
    }, 100 );
});



$('#search-by-name-on-floor').keyup(function() {

    $(".loading-page").css("display", "block");

    delay(function(){
        var query =  $('#search-by-name-on-floor').val().toLowerCase();

        draw.each(function() {
            if(typeof this.data('user-id') != "undefined" && this.type == "g" || this.data('type') == "crect"){

                var user = getUserById(this.data('user-id'))[0];
                var username = user.firstName + " " + user.lastName;

                if(!username.toLowerCase().includes(query)){
                    this.hide();
                } else{
                    this.show();
                }
            }
        });
        $(".loading-page").css("display", "none");
    }, 100 );
});






var delay = (function(){
    var timer = 0;
    return function(callback, ms){
        clearTimeout (timer);
        timer = setTimeout(callback, ms);
    };
})();