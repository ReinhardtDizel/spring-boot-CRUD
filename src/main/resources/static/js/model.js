"use strict";

export class User {
    id;
    firstName;
    age;
    email;
    lastName;
    roles = []

    set setRoles(value) {
        this.roles.push(value);
    }
}

export class Role {
    id;
    name;

    constructor(id, name) {
        this.id = id;
        this.name = name;
    }
}