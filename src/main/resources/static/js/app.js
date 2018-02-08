
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



$.get("/api/design/get", { floorid: floorId } ).done(function( response ) {

    var draw = SVG('seating-plan').size($("#seating-plan").width(),$(window).height()- 10);
    draw.svg(response.svgContent);


    draw.each(function(i, children) {
        //console.log(this);


        var elem = this;
        elem.attr('cursor', 'auto');

        var userId = (typeof elem.data('user-id') === "undefined") ? 0 : elem.data('user-id');

        if(userId !== 0){

            //animate on load
            elem.animate().flip('x');

            var cx = elem.cx()



            $.get("/api/user/get", { userid: userId } ).done(function( userdata ) {


                var tooltip;


               // var text = draw.text(userdata.firstName + " " + userdata.lastName.substring(0,1));
                var text = draw.text(userdata.firstName);
                text.move(elem.cx() - (text.length()/2),elem.cy() - 10).font({ fill: '#000' , weight: 300, size:12 });

                elem.fill('#'+userdata.group.color)
                elem.addClass('user-datails');



                //add top layer
                var toplayer = draw.rect(60,60).move(elem.x(), elem.y()).attr('fill-opacity',0);



                //
                // text.mouseover(function() {
                //     this.attr('cursor', 'pointer');
                //    console.log(this)
                // })


                toplayer.mouseover(function() {
                    this.attr('cursor', 'pointer');
                    tooltip = draw.rect(250,350).radius(5).fill({ color: '#f06' }).move(toplayer.width() + 15, elem.cy());
                })

                toplayer.mouseout(function() {
                    tooltip.remove()
                })



            });






            //console.log()

           // text.move(this.x() + 10,this.y()).font({ fill: '#000', family: 'Inconsolata' }).lines(3)

        }

    })



});

//
// $(document).ready(function () {
//
//     $(".user-datails").bind("click", function() {
//         console.log("clicked")
//     });
//
// });