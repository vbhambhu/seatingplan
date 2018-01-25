$(function () {
    $('[data-toggle="tooltip"]').tooltip()
})

// var tools = SVG('shape-listww').size(200,45);

// var s1 = tools.rect(30, 30).fill('#fff').move(5,10);


// var s2 = tools.rect(30, 30).fill('#fff').move(45,10);

// var s3 = tools.line(130, 25, 160, 25).stroke({ width: 5, color: '#000' })

//s3.toParent(s2)

// var s1 = tools.rect(30, 30).fill('#fff').move(5,10);
// s1.data('toggle', 'tooltip');
// s1.data('placement', 'right');
// s1.data('title', 'Seat');

// s1.click(function() {
//   add_shape('crect');
// });

// s1.mouseover(function() {
//   this.attr('cursor', 'pointer');
// });





// var s2 = tools.rect(25, 25).fill('none').move(50,12).stroke({ width: 5, color: '#fff' })
// s2.data('toggle', 'tooltip');
// s2.data('placement', 'right');
// s2.data('title', 'Room');

// s2.mouseover(function() {
//   this.attr('cursor', 'pointer');
// });
// s2.click(function() {
//   add_shape('crect');
// });


// var s3 = tools.polyline('100,10 100,37 120,37').fill('none').stroke({ width: 5, color: '#fff' })


// var s4 = tools.line(130, 25, 160, 25).fill('none').stroke({ width: 5, color: '#fff' })

// var s5 = tools.text("Text").move(180, 12)



var draw = SVG('drawing').size($("#drawing").width(),$(window).height()- 10);



var shapes = [];
function add_shape(type){

    var shape;
    var copiedShape;

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

    console.log("new shape added");

}




draw.mouseup(function() {
    showPropertyPanel();
});




$( "#save_svg" ).click(function() {
    unselectAll();

    var floorId = $("#floors").val();

    $.post( "/api/design/save", {floorId: floorId, svg_data: draw.svg() })
        .done(function( data ) {
            console.log( "Data Loaded: " + data );
        });



});

$('body').keydown(function(e){


    if(e.keyCode == 46){
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

    //copy
    if (e.keyCode == 67 && (e.ctrlKey || e.metaKey)){
        for (i = 0; i < shapes.length; i++) {
            var shape = shapes[i];
            if(shape.isSelected){
                copiedShape = shape;
            }
        }
    }

    //paste
    if (e.keyCode == 86 && (e.ctrlKey || e.metaKey)){
        //copiedShape.clone();
        console.log(copiedShape);
    }

    console.log(shapes.length)
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
    for (i = 0; i < shapes.length; i++) {
        var obj = shapes[i];

        if(obj.isSelected){
            obj.x(new_val);
        }
    }
});


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





//polyline.plot([[0,0], [100,50], [50,100], [150,50], [200,50]])

//var b = draw.viewbox({ x: 0, y: 0, width: 297, height: 210 })
