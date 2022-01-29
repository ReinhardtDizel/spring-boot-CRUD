export async function getUser() {
    const url = "http://localhost:8080/rest/user";
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

export async function saveUser(user) {
    const url = "http://localhost:8080/rest/admin";
    try {
        const response = await fetch(url, {
            method: 'POST',
            body: JSON.stringify(user),
            headers: {
                'Content-Type': 'application/json'
            }
        });
        const json = await response.json();
        console.log('Успех:', JSON.stringify(json));
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