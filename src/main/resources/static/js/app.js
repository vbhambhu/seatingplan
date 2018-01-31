
var floorId = $("#floor-list").val();
//
// $('.user-datails').hover(function() {
//
//     console.log("dsadadada")
//     var e=$(this);
//     e.off('hover');
//
//     e.popover({content: "fdfssf dfsfsfs"}).popover('show');
//
//     // $.get(e.data('poload'),function(d) {
//     //
//     // });
// });


$(".user-datails").on("mouseenter", function() {
    console.log(this)
});

$.get("/api/design/get", { floorid: floorId } ).done(function( response ) {

    var draw = SVG('seating-plan').size($("#seating-plan").width(),$(window).height()- 10);
    draw.svg(response.svgContent);


    draw.each(function(i, children) {
        //console.log(this);

        var elem = this;
        elem.attr('cursor', 'auto');

        var userId = (typeof elem.data('user-id') === "undefined") ? 0 : elem.data('user-id');

        if(userId !== 0){

            var cx = elem.cx()



            $.get("/api/user/get", { userid: userId } ).done(function( userdata ) {


                var text = draw.text(userdata.firstName + " " + userdata.lastName.substring(0,1));
                text.move(elem.cx() - (text.length()/2),elem.cy() - 10).font({ fill: '#000' , weight: 700 });

                elem.fill('#'+userdata.group.color)


                elem.addClass('user-datails')

                text.mouseover(function() {
                    this.attr('cursor', 'pointer');


                   console.log(this)



                })


                elem.mouseover(function() {
                    this.attr('cursor', 'pointer');
                })



            });






            //console.log()

           // text.move(this.x() + 10,this.y()).font({ fill: '#000', family: 'Inconsolata' }).lines(3)

        }

    })



});
