import {User, Role} from "./model.js";
import {getUser} from "./api.js";

$(document).ready(function () {

    getUser().then(u => {
        let user = u;
        $('#table').find('tbody')
            .append($('<tr>')
                .append($('<td>').text(user.id))
                .append($('<td>').text(user.firstName))
                .append($('<td>').text(user.lastName))
                .append($('<td>').text(user.age))
                .append($('<td>').text(user.email))
                .append($('<td>'))
            )
        user.roles.forEach(r => {
            $('#table td:last-child')
                .append($('<span>')
                    .append($('<small>').text(r.name.replace("ROLE_", "") + " "))
                )
        })

    });
});
