var windowWidth = $("#seating-plan").width();
var windowHeight = $(window).height() - 10;

var draw = SVG('seating-plan').size(windowWidth,windowHeight);
var loader;

$.ajax({
    type:"post",
    url:"/api/design/get",
    data:"id=1",
    beforeSend: function(  ) {
        // load your loading fiel here

        loader = draw.rect(60,60);

        loader.animate(3000).move(100, 100).once(0.5, function(pos, eased) {
            // do something
        }, false)

        console.log("loading data")
    }
})
    .done(function( data ) {
        //hide your loading file here

        loader.remove();

        console.log("loaded")
        console.log(data)
    });