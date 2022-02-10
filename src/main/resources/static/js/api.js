export async function saveUser(user) {
    const url = "http://localhost:8080/rest/admin";
    try {
        const response = await fetch(url, {
            responseType: "json",
            method: 'POST',
            body: JSON.stringify(user),
            headers: {
                "Access-Control-Allow-Origin": "*",
                "Access-Control-Allow-Methods": "GET,PUT,POST,DELETE",
                "Access-Control-Allow-Credentials": true,
                "Content-Type": "application/json"
            }
        });
        return await response.json();
    } catch (error) {
        console.error('Ошибка:', error);
    }
}

export async function editUser(user) {
    const url = "http://localhost:8080/rest/admin";
    try {
        const response = await fetch(url, {
            responseType: "json",
            method: 'PUT',
            body: JSON.stringify(user),
            headers: {
                "Access-Control-Allow-Origin": "*",
                "Access-Control-Allow-Methods": "GET,PUT,POST,DELETE",
                "Access-Control-Allow-Credentials": true,
                "Content-Type": "application/json"
            }
        });
        return await response.json();
    } catch (error) {
        console.error('Ошибка:', error);
    }
}

export async function deleteUser(id) {
    const url = "http://localhost:8080/rest/admin/" + id;
    try {
        const response = await fetch(url, {
            responseType: "json",
            method: 'DELETE',
            headers: {
                "Access-Control-Allow-Origin": "*",
                "Access-Control-Allow-Methods": "GET,PUT,POST,DELETE",
                "Access-Control-Allow-Credentials": true,
                "Content-Type": "application/json"
            }
        });
        return await response.json();
    } catch (error) {
        console.error('Ошибка:', error);
    }
}

export async function getAll() {
    const url = "http://localhost:8080/rest/admin";
    return await fetch(url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json()).then(data => {
            return data;
        })
        .catch(error => {
            console.error(error);

        });
}

export async function getRoles() {
    const url = "http://localhost:8080/rest/role";
    return await fetch(url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json()).then(data => {
            return data;
        })
        .catch(error => {
            console.error(error);
        });
}