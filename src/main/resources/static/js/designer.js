$(function () {
    $('[data-toggle="tooltip"]').tooltip()
})


var floorId = $("#floorid").val();
var copiedShape = null;
var selectedShape = null;
var draw;

$.get("/api/design/get", { floorid: floorId } ).done(function( response ) {

    draw = SVG('drawing').size($("#drawing").width(),$(window).height()- 10);
    draw.svg(response.svgContent);

    draw.each(function(i, children) {
        this.draggable();
        //on click
        this.click(function() {
            unselectAll();
            this.selectize().resize();
            this.isSelected = true;
            selectedShape = this;
            showPropertyPanel();
        });
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

    var box = draw.viewbox();



    var svg_content = "";
    draw.each(function() {
        if(this.type !== 'defs'){
            svg_content += this.svg();
        }
    })

    $.post( "/api/design/save", {
        floorid: floorId,
        svg_content: svg_content,
        width: box.width,
        height: box.height,
    }).done(function( response ) {
        $.notify(response,"success");
    });
});


$('body').keydown(function(e){


    //delete
    if((e.keyCode == 8 || e.keyCode == 46) && !editingProp()){
        selectedShape.selectize(false).resize(false).remove();
    }


    //copy
    if (e.keyCode == 67 && (e.ctrlKey || e.metaKey && !editingProp())){
        if(selectedShape !== null) copiedShape = selectedShape;
    }

    //paste
    if (e.keyCode == 86 && (e.ctrlKey || e.metaKey && !editingProp())){
        console.log('Paste Key Pressed');
        add_shape(copiedShape.data('type'));
        if(selectedShape == null) return;
        selectedShape.attr({
            fill: copiedShape.attr('fill')
            , stroke: copiedShape.attr('stroke')
            , 'stroke-width': copiedShape.attr('stroke-width')
        })
        selectedShape.width(copiedShape.width())
        selectedShape.height(copiedShape.height())
        selectedShape.x(copiedShape.cx())
        selectedShape.y(copiedShape.cy())
    }

});


function editingProp() {

    return  $("#shape_color").is(":focus") || $("#x_pos").is(":focus") || $("#y_pos").is(":focus") || $("#w_pos").is(":focus")
        || $("#h_pos").is(":focus") || $("#stroke_width").is(":focus") || $("#rotation").is(":focus");

}

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

    if(selectedShape.data('type') == 'crect'){
        var color = selectedShape.attr('fill');
    } else{
        var color = selectedShape.attr('stroke');
    }

    $('#shape_color').css({ 'background': color });
    $('#shape_color').val(color);
    $('#x_pos').val(selectedShape.x());
    $('#y_pos').val(selectedShape.y());
    $('#w_pos').val(selectedShape.width());
    $('#h_pos').val(selectedShape.height());
    $('#stroke_width').val(selectedShape.attr('stroke-width'));
    $('#rotation').val(selectedShape.transform().rotation);
    $( "#prop_panel" ).css('display' , 'block');
}

$( "#shape_color" ).change(function() {
    var new_color = '#'+$( "#shape_color" ).val();
    if(selectedShape == null){
        return;
    } else if(selectedShape.data('type') == 'crect'){
        selectedShape.fill({ color: new_color})
    } else {
        selectedShape.stroke({ color: new_color})
    }
});



$( "#x_pos" ).change(function() {
    var new_val = $(this).val();
    if(selectedShape !== null) selectedShape.x(new_val);
});


$( "#y_pos" ).change(function() {
    var new_val = $(this).val();
    if(selectedShape !== null) selectedShape.y(new_val);
});


$( "#w_pos" ).change(function() {
    var new_val = $(this).val();
    if(selectedShape !== null) selectedShape.width(new_val);
});

$( "#h_pos" ).change(function() {
    var new_val = $(this).val();
    if(selectedShape !== null) selectedShape.height(new_val);
});


$( "#stroke_width" ).change(function() {
    var new_val = $(this).val();
    if(selectedShape !== null) selectedShape.stroke({width: new_val });
});



$( "#rotation" ).change(function() {
    var new_val = $(this).val();
    if(selectedShape !== null) selectedShape.transform( { rotation: new_val } );
});

$( "#to_front" ).click(function() {
    if(selectedShape !== null) selectedShape.front();
});

$( "#to_back" ).click(function() {
    if(selectedShape !== null) selectedShape.back();
});

