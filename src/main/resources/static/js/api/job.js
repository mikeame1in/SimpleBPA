$(document).ready( function () {

    // $.getJSON("http://localhost:8080/job?orderId=43", function(data) {
    //
    //     $.each(data, function(key, value) {
    //
    //         $(".data-job-js").append(
    //             "<tr>" +
    //             "<td>" + value + "</td>"
    //             // +
    //             // "<td>" + value.price + "</td></tr>"
    //         );
    //     });
    //
    // });

    // $.getJSON('http://localhost:8080/job?orderId=43', function(data) {
    //     var items = [];
    //     $.each(data, function(key, val) {
    //         items.push('<li id="' + key + '">' + val.note + '</li>');
    //     });
    //     $('<ul/>', {
    //         'class': 'my-new-list',
    //         html: items.join('')
    //     }).appendTo(".data-job-js");
    // });

    $.getJSON('http://localhost:8080/job?orderId=43', function(data) {
        var items = [];
        $.each(data, function(key, job) {
            $(".data-job-js").append(
                `<tr><td>${job.id}</td><input type='hidden' name='id' value="${job.id} + ' '">
                            <td>${job.ready}</td><input type='hidden' name='ready' value="${job.ready}">
                            <td>${job.serviceName}</td><input type='hidden' name='serviceName' value="${job.serviceName}">
                            <td>${job.note}</td><input type='hidden' name='note' value="${job.note}">
                            <td>${job.price}</td><input type='hidden' name='price' value="${job.price}"></tr>`);
        });
    });

})