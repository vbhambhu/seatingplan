$.fn.exists = function(callback) {
    var args = [].slice.call(arguments, 1);
    if (this.length) {
        callback.call(this, args);
    }
    return this;
};




$('.datepicker').exists(function() {
    $('.datepicker').datepicker({
        format: 'dd-M-yyyy'
    });
});

$('#builder-basic').exists(function() {
$('#builder-basic').queryBuilder({

    filters: [
        {
            id: 'name',
            label: 'Name',
            type: 'string'
        },
        {
            id: 'in_stock',
            label: 'In stock',
            type: 'integer'
        },
        {
            id: 'created_date',
            label: 'Created at',
            type: 'date',
            plugin: 'datepicker',
            plugin_config: {
                format: 'yyyy/mm/dd',
                autoclose: true
            }
        }
    ]
});
});

$('#usertable').exists(function() {

    $('#user-query-builder').queryBuilder({
        //plugins: ['bt-tooltip-errors'],
        filters: [
            {
                "id": "symplecticUserId",
                "label": "Symplectic ID",
                "type": "integer",
            },
            {
                "id": "username",
                "label": "Author Username",
                "type": "string"
            },
            {
                "id": "firstName",
                "label": "First Name",
                "type": "string"
            },
            {
                "id": "lastName",
                "label": "Last Name",
                "type": "string"
            },
            {
                "id": "email",
                "label": "Email",
                "type": "string"
            },
            {
                id: 'startDate',
                label: 'Author Start Date',
                type: 'date',
                plugin: 'datepicker',
                validation: {
                    callback: function(value, rule) {
                        return true;
                    }
                },

                plugin_config: {
                    format: 'yyyy/mm/dd',
                    autoclose: true
                }
            },
            {
                id: 'endDate',
                label: 'Author End Date',
                type: 'date',
                plugin: 'datepicker',
                validation: {
                    callback: function(value, rule) {
                        return true;
                    }
                },

                plugin_config: {
                    format: 'yyyy/mm/dd',
                    autoclose: true
                }
            },
            {
                "id": "isPI",
                "label": "Author is PI",
                "type": "boolean",
                input: 'radio',
                values: {
                    true: 'Yes',
                    false: 'No'
                },
                operators: ['equal']
            },
            {
                id: 'updated',
                label: 'Record Updated',
                type: 'date',
                plugin: 'datepicker',
                validation: {
                    callback: function(value, rule) {
                        return true;
                    }
                },

                plugin_config: {
                    format: 'yyyy/mm/dd',
                    autoclose: true
                }
            },
            {
                id: 'created',
                label: 'Record Created',
                type: 'date',
                plugin: 'datepicker',
                validation: {
                    callback: function(value, rule) {
                        return true;
                    }
                },

                plugin_config: {
                    format: 'yyyy/mm/dd',
                    autoclose: true
                }
            }
        ],
    });


    var table = $('#usertable').DataTable( {
        "scrollY": "550px",
        "scrollX": "600px",
        lengthMenu: [[10, 25, 100, -1], [10, 25, 100, "All"]],
        dom: 'lBfrtip',
        buttons: [
            {
                extend: 'excel',
                className: 'btn btn-warning btn-sm pull-right',
                text: '<i class="fa fa-download" aria-hidden="true"></i> Excel Export',
                exportOptions: {
                    columns: ':visible',
                    modifier: {
                        search: 'applied',
                        order: 'applied'
                    }
                }
            }
        ],
        "processing": true,
        "serverSide": true,
        "searching": false,
        ajax: {
            "url": "/api/users/datatable",
            "data": function (data) {
                var mq = $('#user-query-builder').queryBuilder('getMongo');
                data.mongoQuery = JSON.stringify(mq);
                planify(data);
            }
        },
        "columns": [
            {data: "symplecticUserId",
                "visible": true,
                "render": function(data, type, row, meta){
                    if(type === 'display'){
                        data = '<a href="/user/details?id=' + row.id + '">' + data + '</a>';
                    }

                    return data;
                }
            },
            {data: "username"},
            {data: "title"},
            {data: "firstName"},
            {data: "lastName"},
            {data: "email","visible": false},
            {data: "primaryGroup"},
            {data: "academic" },
            {data: "currentStaff"},
            {data: "pi"},
            {data: "startDate",
                "visible": true,
                "render": function(data, type, row, meta){
                    if(type === 'display'){
                        if(data !== 0){
                            var newDate = new Date();
                            newDate.setTime(data);
                            data = formatDate(newDate);
                        } else{
                            data = null;
                        }

                    }

                    return data;
                }
            },
            {data: "endDate",
                "visible": true,
                "render": function(data, type, row, meta){
                    if(type === 'display'){

                        if(data !== 0){
                            var newDate = new Date();
                            newDate.setTime(data);
                            data = formatDate(newDate);
                        } else{
                            data = null;
                        }

                    }

                    return data;
                }
            },
            {data: "updatedAt",
                "visible": false,
                "render": function(data, type, row, meta){
                    if(type === 'display'){
                        var newDate = new Date();
                        newDate.setTime(data);
                        data = newDate.toUTCString();
                    }

                    return data;
                }
            },
            {data: "createdAt",
                "visible": false,
                "render": function(data, type, row, meta){
                    if(type === 'display'){
                        var newDate = new Date();
                        newDate.setTime(data);
                        data = newDate.toUTCString();
                    }

                    return data;
                }
            }
        ]
    });
//
// var table = $('#usertable').DataTable({
//     "scrollY": "550px",
//     "scrollX": "600px",
// });

    $('.usercheckall').click(function(){
        var checked = $(this).prop('checked');
        $( ".userreport-col" ).each(function( index ) {
            var column = table.column( $(this).attr('data-column') );
            $(this).prop('checked', checked);
            column.visible( checked);
        });
    });

    $(".userreport-col").change(function(e) {
        e.preventDefault();
        var column = table.column( $(this).attr('data-column') );
        if (true === $(this)[0].checked) {
            column.visible( true);
        } else{
            column.visible( false);
        }
    });


    $('#user-apply-filters').on('click', function() {
        $('#usertable').DataTable().ajax.reload();
    });


});


function formatDate(d) {
    var month = String(d.getMonth() + 1);
    var day = String(d.getDate());
    var year = String(d.getFullYear());

    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;

    return month + "/" + day + "/" + year;
}


$('#query-builder').exists(function() {




$('#query-builder').queryBuilder({
    //plugins: ['bt-tooltip-errors'],
    filters: [
        {
            "id": "symplecticId",
            "label": "Symplectic ID",
            "type": "integer",
        },
        {
            "id": "pubmedId",
            "label": "PubMed ID",
            "type": "integer"
        },
        {
            "id": "title",
            "label": "Paper Title",
            "type": "string"
        },
        {
            "id": "publicationDay",
            "label": "Publication Day",
            "type": "integer"
        },
        {
            "id": "publicationMonth",
            "label": "Publication Month",
            "type": "integer"
        },
        {
            "id": "publicationYear",
            "label": "Publication Year",
            "type": "integer"
        },
        {
            "id": "type",
            "label": "Journal Type",
            "type": "string",
            "input": 'select',
            "values": {
                "Book": "Book",
                "Chapter": "Chapter",
                "Conference": "Conference",
                "Journal article": "Journal article",
                "Other": "Other",
                "Patent": "Patent",
                "Poster":"Poster"
            },
        },
        {
            "id": "journal",
            "label": "Journal Name",
            "type": "string"
        },
        {
            "id": "volume",
            "label": "Volume",
            "type": "string"
        },
        {
            "id": "issue",
            "label": "Issue",
            "type": "string"
        },
        {
            "id": "issn",
            "label": "ISSN",
            "type": "string"
        },
        {
            "id": "eissn",
            "label": "EISSN",
            "type": "string"
        },
        {
            "id": "doi",
            "label": "DOI",
            "type": "string"
        },
        {
            "id": "language",
            "label": "Language",
            "type": "string"
        },
        {
            "id": "kirpub",
            "label": "Is KIR Publication",
            "type": "boolean",
            input: 'radio',
            values: {
                true: 'Yes',
                false: 'No'
            },
            operators: ['equal']
        },
        {
            "id": "publicationStatus",
            "label": "Publication Status",
            "type": "string",
            "input": 'select',
            "values": {
                "Published": "Published",
                "Published online": "Published online",
                "Accepted": "Accepted"
            },
        },
        {
            "id": "authors",
            "label": "Authors",
            "type": "string"
        },
        {
            "id": "author.username",
            "label": "Author Username",
            "type": "string"
        },
        {
            "id": "author.symplecticUserId",
            "label": "Symplectic UserId",
            "type": "string"
        },
        {
            "id": "author.name",
            "label": "Author Full Name",
            "type": "string"
        },
        {
            "id": "author.email",
            "label": "Author Email",
            "type": "string"
        },
        {
            id: 'author.startDate',
            label: 'Author Start Date',
            type: 'date',
            plugin: 'datepicker',
            validation: {
                callback: function(value, rule) {
                    return true;
                }
            },

            plugin_config: {
                format: 'yyyy/mm/dd',
                autoclose: true
            }
        },
        {
            id: 'author.endDate',
            label: 'Author End Date',
            type: 'date',
            plugin: 'datepicker',
            validation: {
                callback: function(value, rule) {
                    return true;
                }
            },

            plugin_config: {
                format: 'yyyy/mm/dd',
                autoclose: true
            }
        },
        {
            "id": "author.isPI",
            "label": "Author is PI",
            "type": "string"
        },
        {
            id: 'updated',
            label: 'Record Updated',
            type: 'date',
            plugin: 'datepicker',
            validation: {
                callback: function(value, rule) {
                    return true;
                }
            },

            plugin_config: {
                format: 'yyyy/mm/dd',
                autoclose: true
            }
        },
        {
            id: 'created',
            label: 'Record Created',
            type: 'date',
            plugin: 'datepicker',
            validation: {
                callback: function(value, rule) {
                    return true;
                }
            },

            plugin_config: {
                format: 'yyyy/mm/dd',
                autoclose: true
            }
        }
    ],
});



var table = $('#datatable').DataTable( {
    "scrollY": "450px",
    "scrollX": "600px",
    lengthMenu: [[10, 25, 100, -1], [10, 25, 100, "All"]],
    dom: 'lBfrtip',
    buttons: [
        {
            extend: 'excel',
            className: 'btn btn-warning btn-sm pull-right',
            text: '<i class="fa fa-download" aria-hidden="true"></i> Excel Export',
            exportOptions: {
                columns: ':visible',
                modifier: {
                    search: 'applied',
                    order: 'applied'
                }
            }
        }
    ],
    "processing": true,
    "serverSide": true,
    "searching": false,
    ajax: {
        "url": "/api/report/data",
        "data": function (data) {
            var mq = $('#query-builder').queryBuilder('getMongo');
            data.mongoQuery = JSON.stringify(mq);
            planify(data);
        }
    },
    "columns": [
        {data: "symplecticId",
            "visible": true,
            "render": function(data, type, row, meta){
                if(type === 'display'){
                    data = '<a href="/publication/details?id=' + row.id + '">' + data + '</a>';
                }

                return data;
            }
            },
        {data: "author.symplecticUserId" , "visible": false},
        {data: "author.name"},
        {data: "author.username" , "visible": false},
        {data: "author.startDate" ,"visible": false},
        {data: "author.endDate" ,"visible": false},
        {data: "title"},
        {data: "authors","visible": false},
        {data: "publicationDay","visible": false},
        {data: "publicationMonth","visible": false},
        {data: "publicationYear"},
        {data: "type" },
        {data: "journal" , "visible": false},
        {data: "volume" , "visible": false},
        {data: "issue" , "visible": false},
        {data: "issn" , "visible": false},
        {data: "eissn" , "visible": false},
        {data: "doi" , "visible": false},
        {data: "language" , "visible": false},
        {data: "location", "visible": false},
        {data: "medium", "visible": false},
        {data: "publicationStatus", "visible": false},
        {data: "beginPage", "visible": false},
        {data: "endPage", "visible": false},
        {data: "citeCount", "visible": false},
        {data: "impactFactor", "visible": false},
        {data: "updated", "visible": false},
        {data: "created", "visible": false}
    ]
});






$('.checkall').click(function(){
    var checked = $(this).prop('checked');
    $( ".report-col" ).each(function( index ) {
        var column = table.column( $(this).attr('data-column') );
        $(this).prop('checked', checked);
        column.visible( checked);
    });
});

$(".report-col").change(function(e) {
    e.preventDefault();
    var column = table.column( $(this).attr('data-column') );
    if (true === $(this)[0].checked) {
        column.visible( true);
    } else{
        column.visible( false);
    }
});




$('#apply-filters').on('click', function() {
    $('#datatable').DataTable().ajax.reload();
});


});


function planify(data) {
    for (var i = 0; i < data.columns.length; i++) {
        column = data.columns[i];
        column.searchRegex = column.search.regex;
        column.searchValue = column.search.value;
        delete(column.search);
    }
}


$('#publications-by-user').exists(function() {


$.getJSON("api/overview/publications-by-user", function (json) {

    var labels = [];
    var dataz = [];

    $.each(json, function (index, item) {
        var name = item.first_name + " " + item.last_name;
        labels.push(name);
        dataz.push(item.count);
    });


    var data = {
        labels: labels,
        datasets: [
            {
                label: "# of publications",
                backgroundColor: [
                    'rgba(255, 99, 132, 0.2)',
                    'rgba(54, 162, 235, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(75, 192, 192, 0.2)',
                    'rgba(153, 102, 255, 0.2)',
                    'rgba(255, 159, 64, 0.2)'
                ],
                borderWidth: 1,
                data: dataz
            }
        ]
    };


    var chart3 = new Chart(document.getElementById("publications-by-user"), {
        type: 'bar',
        data: data,
        options: {
            scales: {
                xAxes: [{
                    ticks: {
                        display: false
                    }
                }]
            },
            tooltips: {
                mode: 'nearest'
            }
        }
    })

});



$.getJSON("api/overview/user-publication-count", function (json) {

    var data = {
        labels: ["Users", "Publications"],
        datasets: [
            {
                label: "#",
                backgroundColor: [
                    'rgba(255, 99, 132, 0.2)',
                    'rgba(54, 162, 235, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(75, 192, 192, 0.2)',
                    'rgba(153, 102, 255, 0.2)',
                    'rgba(255, 159, 64, 0.2)'
                ],
                borderWidth: 1,
                data: [json.totalUsers, json.totalPublications]
            }
        ]
    };


    console.log(json);
    var chart = new Chart(document.getElementById("user-publication-count"), {
        type: 'pie',
        data: data,
        options: {
            tooltips: {
                mode: 'nearest'
            }
        }
    })

});


$.getJSON("api/overview/publication-by-year", function (json) {

    var labels = [];
    var data = [];

    $.each(json, function (index, item) {
        labels.push(item._id);
        data.push(item.total);
    });


    var datax = {
        labels: labels,
        datasets: [
            {
                label: "# of publications",
                backgroundColor: [
                    'rgba(255, 99, 132, 0.2)',
                    'rgba(54, 162, 235, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(75, 192, 192, 0.2)',
                    'rgba(153, 102, 255, 0.2)',
                    'rgba(255, 159, 64, 0.2)'
                ],
                borderWidth: 1,
                data: data
            }
        ]
    };

    var pubByYearEl = document.getElementById("publications-by-year");
    var chart = new Chart(pubByYearEl, {
        type: 'line',
        data: datax,
        options: {
            tooltips: {
                mode: 'nearest'
            }
        }
    })
});

});