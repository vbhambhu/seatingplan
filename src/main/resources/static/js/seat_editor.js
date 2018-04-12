
$('.user-select').select2();


var floorId = $("#floorid").val();
var selectedShape = null;
var draw;

$.get("/api/design/get", { floorid: floorId } ).done(function( response ) {

    draw = SVG('drawing').size($("#drawing").width(),$(window).height()- 10);
    draw.svg(response.svgContent);

    draw.each(function() {
        //on click
        this.click(function() {
            this.selectize();
            selectedShape = this;
            selectUser();
        });

    })

    draw.mousedown(function(){
        //hide selected
       if(selectedShape !== null){
           selectedShape.selectize(false);
       }
    });

});


function selectUser(){
    var userId = (typeof selectedShape.data('user-id') === "undefined") ? 0 : selectedShape.data('user-id');
    console.log(userId);
   // $('#seat_user_id').val(userId);
}


// $( "#save_svg" ).click(function() {
//
//     var svg_content = "";
//     var userids = [];
//
//     draw.each(function() {
//         if(this.type !== 'defs'){
//             svg_content += this.svg();
//
//             if(typeof this.data('user-id') != "undefined" && this.data('user-id') != 0){
//                 userids.push(this.data('user-id'));
//             }
//         }
//     })
//     $.post( "/api/seat/save", {
//         floorid: floorId, svg_content: svg_content }).done(function( response ) {
//         $.notify(response,"success");
//     });
//
//
// });





$( "#seat_user_id" ).change(function() {
    var userId = $(this).val();

    if(selectedShape !== null) {

        $.post( "/api/user/floor/update", {floorid: floorId, userid: userId }).done(function( response ) {
            console.log(response);
        });

        $.notify("Updated!","success");

    } else{
        $.notify("Please select a seat to assign to user.","error");
    }

});