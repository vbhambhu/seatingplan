$('.user-select').select2();


var floorId = $("#floorid").val();
var selectedShape = null;
var draw;
var userData;

$.get("/api/design/get", { floorid: floorId } ).done(function( response ) {

    draw = SVG('drawing').size($("#drawing").width(),$(window).height()- 10);
    draw.svg(response.svgContent);

    userData = response.users;

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
    if(userId == 0){
        $(".not-assigned").removeClass("invisible").addClass("visible");
        $(".assigned").removeClass("visible").addClass("invisible");
    } else{
        $(".assigned").removeClass("invisible").addClass("visible");
        $(".not-assigned").removeClass("visible").addClass("invisible");
        var user = getUserById(userId)[0];
        $(".eu-name").text(user.firstName + " " + user.lastName);
        $(".eu-group").text(user.groups[0].name);
        $(".eu-username").text(user.username);
        $(".eu-email").text(user.email);
    }
}


function getUserById(id) {
    return userData.filter(
        function(userData){ return userData.id == id }
    );
}


$( "#seat_user_id" ).change(function() {
    var userId = $(this).val();

    if(selectedShape !== null) {
        selectedShape.data('user-id', userId);
        selectedShape.selectize(false);
        $.post( "/api/user/floor/update", {floorid: floorId, userid: userId }).done(function( response ) {
            save_svg();
        });

    } else{
        $.notify("Please select a seat to assign to user.","error");
    }

});


function save_svg() {
    var svg_content = "";
    draw.each(function() {
        if(this.type !== 'defs'){
            svg_content += this.svg();
        }
    })
    $.post( "/api/seat/save", {
        floorid: floorId, svg_content: svg_content }).done(function( response ) {
        location.reload();
    });
}