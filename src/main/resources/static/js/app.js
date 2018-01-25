
var floorId = $("#floor-list").val();

$.get("/api/design/get", { floorid: floorId } ).done(function( response ) {

    var draw = SVG('seating-plan').size($("#seating-plan").width(),$(window).height()- 10);
    draw.svg(response.svgContent);

});
