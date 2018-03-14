var windowWidth = $("#seating-plan").width();
var windowHeight = $(window).height() - 10;


var draw = SVG('seating-plan');
var loader;
var floorBoundaryEle;

$.ajax({
    type:"get",
    url:"/api/design/get?floorid=1",
    beforeSend: function(  ) {
        // load loading bar while floor plan load
        loader = draw.rect(60,60);
        loader.animate(3000).move(100, 100).once(0.5, function(pos, eased) {
        }, false)
        console.log("loading data")
    }
})
    .done(function( response ) {
        //hide your loading file here
        loader.remove();
        draw.svg(response.svgContent);
        //draw.size(100,100);
        modifyElements();
    });


function modifyElements(){
    console.log("draw elements");

    // var floorBoundary = draw.select('.floor').fill('#f06');
    // console.log(floorBoundary)

    draw.each(function(i,chi) {

        if(this.hasClass('floor')){
            if(floorBoundaryEle == null){
                floorBoundaryEle = this;
            }
        }



        if(typeof this.data('user-id') != "undefined" && this.data('user-id') != 0){
            this.animate({ duration: 250 }).flip('x');
            this.addClass("seat");
            //fill group color
            this.fill('#'+this.data('group-color'));
            //add user name on seat
            var username = (typeof this.data('user-name') === "undefined") ? '' : this.data('user-name');
            var x =  this.x() + 5;
            var y =  this.cy() - 15;
            console.log(username)
            var text = draw.text(username)
            text.move(x,y).font({ fill: '#FFF', family: 'Inconsolata' }).addClass("nametext")
            // text.mouseover(function() {
            //
            //     console.log("do nams")
            //
            // })
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
    });


    //make full size
     //draw.viewbox(0,0,floorBoundaryEle.width() + 20,floorBoundaryEle.height())

   // console.log(floorBoundaryEle.height())
    draw.viewbox(0,0,windowWidth ,windowHeight)



    var pop_template = '<div class="popover" role="tooltip"><div class="arrow"></div><h3 class="popover-header"></h3><div class="popover-body"></div></div>';
    $('.seat').mouseenter(function() {
        var e=$(this);
        e.off('hover');
        $.get('/api/user/details',{ id: e.data('user-id')}, function(userdata) {
            var details = '<table class="table table-striped">';
            details += '<tr><td>Name:</td><td>'+userdata.firstName + " " + userdata.lastName+'</td></tr>';
            details += '<tr><td>Start Date: </td><td>'+userdata.startDate + '</td></tr>';
            details += '<tr><td>endDate: </td><td>'+userdata.endDate + '</td></tr>';
            details += '<tr><td>Phone: </td><td>423343</td></tr>';
            details += '<tr><td>Email:</td><td>'+userdata.email+'</td></tr>';
            details += '<tr><td>Computer:</td><td>'+userdata.computerAddress + '</td></tr>';
            details += '<tr><td>Groups: </td><td>'+userdata.endDate + '</td></tr>';
            details += '<tr><td>Notes:</td><td>'+userdata.comment + '</td></tr>';
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

//
// draw.panZoom({zoomMin: 0.5, zoomMax: 20})
//
//     draw.zoom(1) // uses center of viewport by default
//         .animate()
//         .zoom(2, {x:100, y:100}) // zoom into specified point

}