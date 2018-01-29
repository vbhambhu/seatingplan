$(function () {
    $('[data-toggle="tooltip"]').tooltip()
})




var floorId = $("#floor-list").val();
var shapes = [];


var draw;

    $.get("/api/design/get", { floorid: floorId } ).done(function( response ) {

     draw = SVG('drawing').size($("#drawing").width(),$(window).height()- 10);


    // draw.each(function(i, children) {
    //     this.fill({ color: '#f06' })
    //     this.draggable().selectize().resize();
    //     //this.type = type;
    // })
    //
    //     unselectAll();


    draw.mousedown(function(){
        unselectAll();
        hidePropertyPanel();
    });


    draw.mouseup(function() {
        //showPropertyPanel();
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
        case 'text':
            shape = draw.plain("--Text--").move(20,20).font({ fill: '#000', family: 'Inconsolata' })
            break;
        case 'line':
            shape = draw.line(30, 30, 100, 30).stroke({ color: '#000', width: 10 })
            break;
        case 'hrect':
            shape = draw.polyline('10,10 10,100 100,100').fill('none').stroke({ width: 10 });
            break;
    }

    shape.draggable().selectize().resize();

    shape.type = type;
    shape.isSelected = true;

    //on click
    shape.click(function() {
        unselectAll();
        this.selectize().resize();
        this.isSelected = true;
        showPropertyPanel();
    });

    shape.mouseover(function() {
        this.attr('cursor', 'move');
    })

    shapes.push(shape);
    showPropertyPanel();
}




$( "#save_svg" ).click(function() {
    unselectAll();
    $.post( "/api/design/save", {floorid: floorId, svg_content: draw.svg() }).done(function( response ) {
        alert(response);
    });
});


$('body').keydown(function(e){

    if(e.keyCode == 8 || e.keyCode == 46){
        console.log('Delete Key Pressed');
        for (i = 0; i < shapes.length; i++) {
            var shape = shapes[i];
            if(shape.isSelected){
                shape.selectize(false).resize(false);
                shape.isSelected = false;
                shape.remove();
                shapes.splice(i, 1);
            }
        }
    }
});


function unselectAll(){
    for (i = 0; i < shapes.length; i++) {
        var shape = shapes[i];
        shape.selectize(false).resize(false);
        shape.isSelected = false;
    }

    //also hide property panel




}

function hidePropertyPanel(){
    $("#prop_panel").css('display' , 'none');
}

function showPropertyPanel(){

    for (i = 0; i < shapes.length; i++) {
        var obj = shapes[i];

        if(obj.isSelected){

            //get color of selected item
            var color;
            if(obj.type == 'crect' || obj.type == 'text'){
                color = obj.attr('fill');
            } else{
                color = obj.attr('stroke');
            }
            $('#shape_color').css({ 'background': color });
            $('#shape_color').val(color);



            $('#x_pos').val(obj.x());
            $('#y_pos').val(obj.y());
            $('#w_pos').val(obj.width());
            $('#h_pos').val(obj.height());
            $('#stroke_width').val(obj.attr('stroke-width'));
            $('#rotation').val(obj.transform().rotation);

            //display block
            $( "#prop_panel" ).css('display' , 'block');
        }

    }

}


$( "#shape_color" ).change(function() {

    var new_color = '#'+$( "#shape_color" ).val();
    var shape = selectedShape();
    if(shape == null){
        return;
    } else if(shape.type == 'crect' || shape.type == 'text'){
        shape.fill({ color: new_color})
    } else {
        shape.stroke({ color: new_color})
    }
});


$( "#x_pos" ).change(function() {
    var new_val = $(this).val();
    var shape = selectedShape();
    shape.x(new_val);
});


function selectedShape() {
    for (i = 0; i < shapes.length; i++) {
        var obj = shapes[i];
        if(obj.isSelected){
            return obj;
        }
    }
    return null;
}


$( "#y_pos" ).change(function() {
    var new_val = $(this).val();
    for (i = 0; i < shapes.length; i++) {
        var obj = shapes[i];

        if(obj.isSelected){
            obj.y(new_val);
        }
    }
});


$( "#w_pos" ).change(function() {
    var new_val = $(this).val();
    for (i = 0; i < shapes.length; i++) {
        var obj = shapes[i];

        if(obj.isSelected){
            obj.width(new_val);
        }
    }
});

$( "#h_pos" ).change(function() {
    var new_val = $(this).val();
    for (i = 0; i < shapes.length; i++) {
        var obj = shapes[i];

        if(obj.isSelected){
            obj.height(new_val);
        }
    }
});


$( "#stroke_width" ).change(function() {
    var new_val = $(this).val();
    for (i = 0; i < shapes.length; i++) {
        var obj = shapes[i];

        if(obj.isSelected){
            obj.stroke({width: new_val })
        }
    }
});



$( "#rotation" ).change(function() {
    var new_val = $(this).val();
    for (i = 0; i < shapes.length; i++) {
        var obj = shapes[i];

        if(obj.isSelected){
            obj.transform( { rotation: new_val } )
        }
    }
});

$( "#to_front" ).click(function() {
    for (i = 0; i < shapes.length; i++) {
        var obj = shapes[i];

        if(obj.isSelected){
            obj.front()
        }
    }
});

$( "#to_back" ).click(function() {
    for (i = 0; i < shapes.length; i++) {
        var obj = shapes[i];

        if(obj.isSelected){
            obj.back()
        }
    }
});