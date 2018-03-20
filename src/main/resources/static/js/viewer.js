var windowWidth,windowHeight;

//var seatsData = [];

var floorId = $("#sfloor_id").val();
var draw = SVG('seating-plan');
var loader;

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
        draw.svg(response.svgContent);
        //draw.size(100,100);
        modifyElements();
        $(".loading-page").css("display", "none");

});


function modifyElements(){


    // var floorBoundary = draw.select('.floor').fill('#f06');
    // console.log(floorBoundary)

    draw.each(function(i,chi) {


        if(typeof this.data('user-id') != "undefined" && this.data('user-id') != 0){

            this.animate({ duration: 250 }).flip('x');

            //fill group color
            this.fill('#'+this.data('group-color'));
            //add user name on seat
            var username = (typeof this.data('user-name') === "undefined") ? '' : this.data('user-name');

            var group = draw.group();
            group.add(this);
            group.addClass("seat");
            group.data('user-id', this.data('user-id'));
            group.data('user-name', this.data('user-name'));
            group.data('group-name', this.data('group-name'));

            var text = group.text(username + "\nS").font({ fill: '#000', family: 'Poppins', size:16 });
            text.move(this.x() + 5,this.y() + (this.height() / 2) - (text.bbox().h /2));



            // var x =  this.x() + 5;
            // var y =  this.cy() - 15;
            // var text = draw.text(username)
            // text.move(x,y).font({ fill: '#FFF', family: 'Inconsolata' }).addClass("nametext").data('user-id', this.data('user-id'));
            // var wrap = draw.rect(this.width(), this.height()).move(this.x() , this.y()).opacity(0);
            // wrap.addClass("seat");
            // wrap.data('user-id', this.data('user-id'))


            // var group = draw.group();
            // group.add(selectedShape)
            //
            // var text = group.text("Hello World").font({ fill: '#FFF', family: 'Inconsolata', size:20 });
            // //var txt_y = (selectedShape.height() / 2) - (text.bbox().h /2);



            //seatsData.push({name:username, group: "dsd", data: this});

            // text.mouseover(function() {
            //
            //     console.log("do nams")
            //
            // })
            group.mouseover(function() {
                this.attr('cursor', 'pointer');
            });
            // wrap.mouseout(function() {
            //     //this.attr('cursor', 'pointer');
            //     //this.animate({ duration: 250 }).rotate(-90)
            // });
        }
        //var userId = (typeof elem.data('user-id') === "undefined") ? 0 : elem.data('user-id');
    });


    //make full size
     //draw.viewbox(0,0,floorBoundaryEle.width() + 20,floorBoundaryEle.height())


    console.log("Screen  Width=" + windowWidth + " and height =" + windowHeight)

    draw.viewbox(0,0,windowWidth ,windowHeight)
   // draw.viewbox(0,0,windowWidth ,windowHeight)



    var pop_template = '<div class="popover user-details-block" role="tooltip"><div class="arrow"></div><div class="popover-body"></div></div>';

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
            details += '<div class="row cdcol"><div class="col-sm-5 udrow">Groups: </div><div class="col-sm-7">'+ grps.join(", ")  +'</div></div>';
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

//
// draw.panZoom({zoomMin: 0.5, zoomMax: 20})
//
//     draw.zoom(1) // uses center of viewport by default
//         .animate()
//         .zoom(2, {x:100, y:100}) // zoom into specified point

}




$('#search-by-group-on-floor').keyup(function() {

    $(".loading-page").css("display", "block");

    delay(function(){
        var query =  $('#search-by-group-on-floor').val().toLowerCase();
        draw.each(function() {

            if(typeof this.data('user-id') != "undefined" && this.type == "g" || this.data('type') == "crect"){
                var group = (typeof this.data('group-name') === "undefined") ? '' : this.data('group-name');
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
                var username = (typeof this.data('user-name') === "undefined") ? '' : this.data('user-name');
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