export class User {

    set id(value) {
        this._id = value;
    }

    set firstName(value) {
        this._firstName = value;
    }

    set age(value) {
        this._age = value;
    }

    set email(value) {
        this._email = value;
    }

    set lastName(value) {
        this._lastName = value;
    }

    set roles(value) {
        this._roles.push(value);
    }

    _id;
    _firstName;
    _age;
    _email;
    _lastName;
    _roles = []
}

export class Role {

    set id(value) {
        this._id = value;
    }

    set name(value) {
        this._name = value.replace("ROLE_", "");
    }

    get name() {
        return "ROLE_" + this._name;
    }

    _id;
    _name;
}