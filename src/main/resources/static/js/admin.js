import {Role, User} from "./model.js";
import {getAll, getRoles, saveUser} from "./api.js";
import * as message from "./message.js";

$(document).ready(function () {
    let users = [];
    let possibleRoles = [];
    getRoles().then(data => {
        $.each(data, function (key, value) {
            let role = new Role();
            role.id = value.id
            role.name = value.name;
            possibleRoles.push(role);
        })
        $('#createUserContainer')
            .append($('<form id="createUserForm" method="post">')
                .append(createUser(possibleRoles))
                .append($('<button type="submit" class="btn btn-primary w-50">').text(message.TEXT_CREATE))
                .submit(function (event) {
                    event.preventDefault();
                    const dataArray = $(this).serializeArray(),
                        dataObj = new User();
                    console.log(dataArray)
                    $(dataArray).each(function (i, field) {
                        dataObj[field.name] = field.value;
                    });
                    console.log(dataObj)
                }))
    })

    getAll().then(data => {
        data.forEach((u) => {
            let user = new User();
            user = u
            user.roles = u.roles
            users.push(u);
        })
        users.forEach((value, index) => {
            $('#admin-table').find('tbody')
                .append($('<tr>').attr('id', 'user_row_' + index)
                    .append($('<td>').text(value.id))
                    .append($('<td>').text(value.firstName))
                    .append($('<td>').text(value.lastName))
                    .append($('<td>').text(value.age))
                    .append($('<td>').text(value.email))
                    .append($('<td>').attr('id', 'role_col' + index))
                    .append($('<td>').attr('id', 'edit_col' + index))
                    .append($('<td>').attr('id', 'delete_col' + index))
                )
            value.roles.forEach(r => {
                $('#role_col' + index)
                    .append($('<span>')
                        .append($('<small>').text(r.name.replace("ROLE_", "") + " "))
                    )
            })
            $('#edit_col' + index).append($(modalEdit(index, value)))
            $('#delete_col' + index).append($(modalDelete(index, value)))
        })
    });
});

function createUser(value) {
    return ($(inputForm('firstName', 'text', message.TEXT_NAME_FIRST, 'firstName', '', false)))
        .append($(inputForm('lastName', 'text', message.TEXT_NAME_LAST, 'lastName', '', false)))
        .append($(inputForm('age', 'text', message.TEXT_AGE, 'age', '', false)))
        .append($(inputForm('email', 'text', message.TEXT_EMAIL, 'email', '', false)))
        .append($(inputForm('password', 'password', message.TEXT_PASSWORD, 'password', '', false)))
        .append($(selectedForm('roles', message.TEXT_ROLE, value, false)))
}

function modalEdit(id, value) {
    return $('<form>')
        .append($('<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#user-edit-modal" data-whatever="@mdo"></button>')
            .attr('data-target', '#user-edit-modal' + id)
            .text(message.TEXT_EDIT))
        .append('<input type="hidden" name="_method" value="edit">')
        .append($('<div class="modal fade" tabindex="-1" aria-labelledby="user-edit-modal-label" aria-hidden="true">')
            .attr('id', 'user-edit-modal' + id)
            .append($('<div class="modal-dialog">')
                .append($('<div class="modal-content">')
                    .append($('<div class="modal-header">')
                        .append($('<h5 class="modal-title" id="user-edit-modal-label">').text(message.TEXT_EDIT_USER))
                        .append('<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>'))
                    .append($(' <div class="modal-body">')
                        .append($('<form method="post">')
                            .append($(getForms('-edit' + id, value, false)))
                            .append($('<div class="modal-footer">')
                                .append($('<button type="button" class="btn btn-secondary" data-dismiss="modal">').text(message.TEXT_CLOSE))
                                .append($('<button type="submit" class="btn btn-primary">').text(message.TEXT_EDIT)))
                        )))))
}

function modalDelete(id, value) {
    return $('<form>')
        .append($('<button type="button" class="btn btn-danger" data-toggle="modal" data-target="#user-delete-modal" data-whatever="@mdo"></button>')
            .attr('data-target', '#user-delete-modal' + id)
            .text(message.TEXT_DELETE))
        .append('<input type="hidden" name="_method" value="delete">')
        .append($('<div class="modal fade" tabindex="-1" aria-labelledby="user-delete-modal-label" aria-hidden="true">')
            .attr('id', 'user-delete-modal' + id)
            .append($('<div class="modal-dialog">')
                .append($('<div class="modal-content">')
                    .append($('<div class="modal-header">')
                        .append($('<h5 class="modal-title" id="user-delete-modal-label">').text(message.TEXT_DELETE_USER))
                        .append('<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>'))
                    .append($(' <div class="modal-body">')
                        .append($('<form method="post">')
                            .append($(getForms('-delete' + id, value, true)
                                .append($('<div class="modal-footer">')
                                    .append($('<button type="button" class="btn btn-secondary" data-dismiss="modal">').text(message.TEXT_CLOSE))
                                    .append($('<button type="submit" class="btn btn-danger">').text(message.TEXT_DELETE))))))))))
}

function getForms(id, value, disable) {
    return ($(inputForm(id, 'text', message.TEXT_ID, 'id', value.id, disable)))
        .append($(inputForm(id, 'text', message.TEXT_NAME_FIRST, 'firstName', value.firstName, disable)))
        .append($(inputForm(id, 'text', message.TEXT_NAME_LAST, 'lastName', value.lastName, disable)))
        .append($(inputForm(id, 'text', message.TEXT_AGE, 'age', value.age, disable)))
        .append($(inputForm(id, 'text', message.TEXT_EMAIL, 'email', value.email, disable)))
        .append($(inputForm(id, 'password', message.TEXT_PASSWORD, 'password', value.password, disable)))
        .append($(selectedForm(id, message.TEXT_ROLE, value.roles, disable)))
}

function inputForm(id, type, text, name, value, disable) {
    let input = $('<input required class="form-control">').attr('type', type).attr('id', 'user-' + name + id).attr('name', name).attr('value', value);
    if (disable === true) {
        $(input).attr('readonly', true);
    }
    return $('<div class="form-group">')
        .append($('<label class="col-form-label">').attr('for', 'user-' + name + id).text(text))
        .append($(input));
}

function selectedForm(id, text, roles, disabled) {
    let select = $('<select multiple class="form-select w-100" size="3" aria-label="size 3 select example" name="roles">').attr('id', 'user-role' + id);
    if (disabled === true) {
        $(select).attr('disabled', true);
    }
    $.each(roles, function (i, role) {
        $(select)
            .append($('<option>', {
                value: role.id,
                text: role.name
            }));
    });
    return $('<div class="form-group mb-3">')
        .append($('<label>').attr('for', 'user-role' + id).text(text)).append($(select))
}
