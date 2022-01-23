$(document).ready(function () {
    $.ajax({
        type : "GET",
        url: "http://localhost:8080/user"
    }).then(function (data) {
        $('#user_info_table').bootstrapTable({
            data: data
        })
    });
});