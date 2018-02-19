
var floorId = $("#floor-list").val();




$.get("/api/design/get", { floorid: floorId } ).done(function( response ) {

    var windowWidth = $("#seating-plan").width();
    var windowHeight = $(window).height() - 10;

    var draw = SVG('seating-plan').size(windowWidth,windowHeight);
    draw.svg(response.svgContent);



    draw.each(function() {

        if(typeof this.data('user-id') != "undefined" && this.data('user-id') != 0){
            this.animate({ duration: 250 }).flip('x');
            this.addClass("seat");


            this.mouseover(function() {
                this.attr('cursor', 'pointer');
               // this.animate({ duration: 250 }).rotate(90)
            });

            this.mouseout(function() {
                //this.attr('cursor', 'pointer');
                //this.animate({ duration: 250 }).rotate(-90)

            });
        }


        //var userId = (typeof elem.data('user-id') === "undefined") ? 0 : elem.data('user-id');

    })


   var pop_template = '<div class="popover" role="tooltip"><div class="arrow"></div><h3 class="popover-header"></h3><div class="popover-body"></div></div>';



        $('.seat').mouseenter(function() {
        var e=$(this);
        e.off('hover');
        $.get('/api/user/details',{ id: e.data('user-id')}, function(userdata) {

            var details = '<table class="table table-striped">';
            details += '<tr><td>Name:</td><td>dddd</td></tr>';
            details += '<tr><td>startDate</td><td>dddd</td></tr>';
            details += '<tr><td>endDate</td><td>dddd</td></tr>';
            details += '<tr><td>Phone</td><td>'+userdata.email+'</td></tr>';
            details += '<tr><td>Email</td><td>'+userdata.email+'</td></tr>';
            details += '<tr><td>computerAddress</td><td>dddd</td></tr>';
            details += '<tr><td>Groups</td><td>dddd</td></tr>';
            details += '</table>';

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


});



function getTooltipPosition(toplayer, floor_orig_width, floor_orig_height) {

    var ttH = 300;
    var ttW = 200;
    var x, y;

    console.log("Box height" +floor_orig_height)
    console.log("Square y" + toplayer.y())

    //try right side
    if (toplayer.x() + toplayer.width() + 10 + ttW < floor_orig_width && toplayer.y() + toplayer.height() + ttH < floor_orig_height){
        x = toplayer.x() + toplayer.width() + 5;
        y = toplayer.cy()
    } else if(toplayer.x() - ttW - 10 > 0 && toplayer.y() - ttH - 10 > 0){ //try left side
        x = toplayer.x() - ttW - 5;
        y = toplayer.cy() - ttH;
    }else if(toplayer.x() - 10 > 0 && toplayer.y() + ttH + 10 < floor_orig_height){ //right top side
        x = toplayer.x() - ttW - 5;
        y = toplayer.cy();
    } else if(toplayer.x() + ttW + 10 < floor_orig_width && toplayer.y() - 10 > 0){ //left top side
        x = toplayer.cx() + (toplayer.width()/2) + 5;
        y = toplayer.cy() - ttH;
    } else {
        x = 10;
        y = 10;
    }

    //     toplayer.cy() < floor_orig_height) {
    //     x = toplayer.x() + toplayer.width() + 5;
    //     y = toplayer.cy();
    // } else if (true) {


    return {'x': x, y: y};
}