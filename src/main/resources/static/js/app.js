
var floorId = $("#floor-list").val();

$.get("/api/design/get", { floorid: floorId } ).done(function( response ) {

    var draw = SVG('seating-plan').size($("#seating-plan").width(),$(window).height()- 10);
    draw.svg(response.svgContent);


    draw.each(function(i, children) {
        console.log(this);
        this.attr('cursor', 'auto');

        var userId = (typeof this.data('user-id') === "undefined") ? 0 : this.data('user-id');
        if(userId !== 0){

            var cx = this.cx()
            console.log(cx);

            var text = draw.text('Volodymyr N');
            text.move(this.cx() - (text.length()/2),this.cy() - 10).font({ fill: '#000' , weight: 700 });

            //console.log()

           // text.move(this.x() + 10,this.y()).font({ fill: '#000', family: 'Inconsolata' }).lines(3)

        }

    })


});
