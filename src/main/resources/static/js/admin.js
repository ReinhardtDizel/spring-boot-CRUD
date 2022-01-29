import {User} from "./model.js";
import {deleteUser, editUser, getAll, getRoles, saveUser} from "./api.js";
import * as message from "./message.js";

$(document).ready(function () {
    adminTable();
    createUser();
});

function adminTable() {
    $('#admin-table').find('tbody').empty();
    getAll().then(data => {
        $.each(data, function (key, value) {
            $('#admin-table').find('tbody')
                .append(userRowForAdminTable(value))
        })
    })
}

function userRowForAdminTable(user) {
    let rolesViewText = $('<span>')
    $.each(user.roles, function (i, role) {
        $(rolesViewText)
            .append($('<small>').text(role.name.replace("ROLE_", "") + " "))
    })
    return $('<tr>').attr('id', 'user_row_' + user.id).append($('<td>').text(user.id))
        .append($('<td>').text(user.firstName))
        .append($('<td>').text(user.lastName))
        .append($('<td>').text(user.age))
        .append($('<td>').text(user.email))
        .append($('<td>').attr('id', 'role_col' + user.id)
            .append($(rolesViewText)))
        .append($('<td>').attr('id', 'edit_col' + user.id)
            .append($(modalEdit(user.id, user))))
        .append($('<td>').attr('id', 'delete_col' + user.id)
            .append($(modalDelete(user.id, user)))
        );
}

function createUser() {

    let createUserForm = ($(inputForm('firstName', 'text', message.TEXT_NAME_FIRST, 'firstName', '', false)))
        .append($(inputForm('lastName', 'text', message.TEXT_NAME_LAST, 'lastName', '', false)))
        .append($(inputForm('age', 'text', message.TEXT_AGE, 'age', '', false)))
        .append($(inputForm('email', 'text', message.TEXT_EMAIL, 'email', '', false)))
        .append($(inputForm('password', 'password', message.TEXT_PASSWORD, 'password', '', false)))
        .append($(selectedFormWithRoles('-roles', message.TEXT_ROLE)))

    $('#createUserContainer')
        .append($('<form id="createUserForm" method="post">')
            .append($(createUserForm))
            .append($('<button type="submit" class="btn btn-primary w-50">').text(message.TEXT_CREATE))
            .submit(function (event) {
                event.preventDefault();

                const dataObj = createDto($(this));
                saveUser(dataObj).then(u => {
                    $('.nav-tabs a[data-target="#tabone"]').tab('show');
                    $('#admin-table').find('tbody')
                        .append(userRowForAdminTable(u))
                })
            })
        );
}

function createDto(element) {
    const result = new User();
    const dataArray = $(element).serializeArray();
    $(dataArray).each(function (i, field) {
        if (field.name !== 'roles') {
            result[field.name] = field.value;
        } else {
            result.setRoles = field.value;
        }
    });
    return result;
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
                        .append($('<form method="post">').attr('id', 'user-edit-modal-form' + id)
                            .submit(function (event) {
                                event.preventDefault();
                                const dataObj = createDto($(this));
                                editUser(dataObj).then(b => {
                                    adminTable();
                                })
                                $('#user-edit-modal' + id).modal('hide')
                                $('body').removeClass('modal-open');
                                $('.modal-backdrop').remove();
                            })
                            .append($(getInputForms('-edit' + id, value, false)))
                            .append($(selectedFormWithRoles('-roles' + id, message.TEXT_ROLE, value.roles)))
                            .append($('<div class="modal-footer">')
                                .append($('<button type="button" class="btn btn-secondary" data-dismiss="modal">').text(message.TEXT_CLOSE))
                                .append($('<button type="submit" class="btn btn-primary">').text(message.TEXT_EDIT)
                                )))))))
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
                        .append($('<form method="post">').attr('id', 'user-delete-modal-form' + id)
                            .submit(function (event) {
                                event.preventDefault();
                                const userId = $(this).find('input[name="id"]').val();
                                deleteUser(userId).then(b => {
                                    adminTable();
                                })
                                $('#user-delete-modal' + id).modal('hide')
                                $('body').removeClass('modal-open');
                                $('.modal-backdrop').remove();
                            })
                            .append($(getInputForms('-delete' + id, value, true)))
                            .append($(selectedForm(id, message.TEXT_ROLE, value.roles, true)))
                            .append($('<div class="modal-footer">')
                                .append($('<button type="button" class="btn btn-secondary" data-dismiss="modal">').text(message.TEXT_CLOSE))
                                .append($('<button type="submit" class="btn btn-danger">').text(message.TEXT_DELETE))
                            ))))));
}

function getInputForms(id, value, disable) {
    return ($(inputForm(id, 'text', message.TEXT_ID, 'id', value.id, disable)))
        .append($(inputForm(id, 'text', message.TEXT_NAME_FIRST, 'firstName', value.firstName, disable)))
        .append($(inputForm(id, 'text', message.TEXT_NAME_LAST, 'lastName', value.lastName, disable)))
        .append($(inputForm(id, 'text', message.TEXT_AGE, 'age', value.age, disable)))
        .append($(inputForm(id, 'text', message.TEXT_EMAIL, 'email', value.email, disable)))
        .append($(inputForm(id, 'password', message.TEXT_PASSWORD, 'password', value.password, disable)))
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

function selectedFormWithRoles(id, text, roles) {
    let select = $('<select multiple class="form-select w-100" size="3" aria-label="size 3 select example" name="roles">').attr('id', 'user-role' + id);
    getRoles().then(data => {
        $.each(data, function (i, role) {
            $(select)
                .append($('<option>', {
                    value: role.id,
                    text: role.name.replace("ROLE_", "")
                }))
        });
        $.each(roles, function (i, role) {
            $(select).find('option[value=' + role.id + ']').attr('selected', 'true')
        })
    })
    return $('<div class="form-group mb-3">')
        .append($('<label>').attr('for', 'user-role' + id).text(text)).append($(select))
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
                text: role.name.replace("ROLE_", "")
            }));
    });
    return $('<div class="form-group mb-3">')
        .append($('<label>').attr('for', 'user-role' + id).text(text)).append($(select))
}
