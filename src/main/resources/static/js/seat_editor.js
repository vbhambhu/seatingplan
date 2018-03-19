
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
            unselectAll();
            this.selectize();
            this.isSelected = true;
            selectedShape = this;
            showPropertyPanel();
        });


        if(typeof this.data('user-id') != "undefined" && this.data('user-id') != 0){
            this.fill('#'+this.data('group-color'));
        }




    })

    draw.mousedown(function(){
        unselectAll();
        hidePropertyPanel();
    });

});




function add_shape(type){
    var shape;
    unselectAll();
    switch (type) {
        case 'orect':
            shape = draw.polygon('10,10 100,10 100,100 10,100').fill('none').stroke({ width: 10 });
            break;
        case 'crect':
            shape = draw.rect(100, 100).fill('#fff');
            break;
        case 'line':
            shape = draw.line(30, 30, 100, 30).stroke({ color: '#000', width: 10 })
            break;
        case 'hrect':
            shape = draw.polyline('10,10 10,100 100,100').fill('none').stroke({ width: 10 });
            break;
    }
    shape.draggable().selectize().resize();
    shape.data('type', type);
    shape.isSelected = true;
    //on click
    shape.click(function() {
        unselectAll();
        this.selectize().resize();
        this.isSelected = true;
        selectedShape = this;
        showPropertyPanel();
    });
    shape.mouseover(function() {
        this.attr('cursor', 'move');
    });
    selectedShape = shape;
    showPropertyPanel();
}


$( "#save_svg" ).click(function() {
    unselectAll();
    var svg_content = "";

    var userids = [];

    draw.each(function() {
        if(this.type !== 'defs'){
            svg_content += this.svg();

            if(typeof this.data('user-id') != "undefined" && this.data('user-id') != 0){
                userids.push(this.data('user-id'));
            }
        }
    })
    $.post( "/api/seat/save", {
        floorid: floorId, svg_content: svg_content }).done(function( response ) {
        $.notify(response,"success");
    });

    // $.post( "/api/user/floor/update", {floorid: floorId, userids: userids }).done(function( response ) {
    //     //alert(response);
    // });

});



function unselectAll(){
    draw.each(function() {
        if(this.type !== 'defs' && this.type !== 'g' && this.isSelected){
            this.selectize(false).resize(false);
        }
    });
}

function hidePropertyPanel(){
    $("#prop_panel").css('display' , 'none');
}

function showPropertyPanel(){
    var userId = (typeof selectedShape.data('user-id') === "undefined") ? 0 : selectedShape.data('user-id');
    $('#seat_user_id').val(userId).trigger('change');
    $( "#prop_panel" ).css('display' , 'block');
}



$( "#seat_user_id" ).change(function() {
    var userId = $(this).val();
    var groupName = $(this).find(':selected').data('group-name');
    var userName = $(this).find(':selected').data('user-name');
    var groupColor = $(this).find(':selected').data('group-color');

    if(selectedShape !== null) {
        selectedShape.data('user-id', userId);
        selectedShape.data('group-name', groupName);
        selectedShape.data('user-name', userName);
        selectedShape.data('group-color', groupColor);
    }
});