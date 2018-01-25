$(function () {
    $('[data-toggle="tooltip"]').tooltip()
})


var floorId = $("#floor-list").val();





var draw = SVG('drawing').size($("#drawing").width(),$(window).height()- 10);


$.get("/api/design/get", { floorid: floorId } ).done(function( response ) {


    draw.svg(response.svgContent);

});



var shapes = [];
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
    shape.isSelected = true;

    //on click
    shape.click(function() {
        unselectAll();
        this.selectize().resize();
        this.isSelected = true;
    });

    shape.mouseover(function() {
        this.attr('cursor', 'move');
    })

    shapes.push(shape);
    showPropertyPanel();
}




draw.mouseup(function() {
    showPropertyPanel();
});




$( "#save_svg" ).click(function() {
    unselectAll();

    var floorId = $("#floor-list").val();

    $.post( "/api/design/save", {floorid: floorId, svg_content: draw.svg() })
        .done(function( response ) {
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
}


function showPropertyPanel(){
    console.log("show showPropertyPanel");

    for (i = 0; i < shapes.length; i++) {
        var obj = shapes[i];

        //console.log(obj.isSelected)

        if(obj.isSelected){


            $('#x_pos').val(obj.x());
            $('#y_pos').val(obj.y());
            $('#w_pos').val(obj.width());
            $('#h_pos').val(obj.height());
            $('#stroke_width').val(obj.attr('stroke-width'));

            $('#rotation').val(obj.transform().rotation);




            // console.log(obj.transform().rotation)






            $( "#rect_prop" ).css('display' , 'block');





        }

    }












}


$( "#shape_color" ).change(function() {

    var new_color = '#'+$( "#shape_color" ).val();

    for (i = 0; i < shapes.length; i++) {
        var obj = shapes[i];

        if(obj.isSelected){
            obj.stroke({ color: new_color})
        }
    }

});


$( "#x_pos" ).change(function() {
    var new_val = $(this).val();
    selectedShape().x(new_val);
});


function selectedShape() {
    for (i = 0; i < shapes.length; i++) {
        var obj = shapes[i];
        if(obj.isSelected){
            return obj;
        }
    }
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