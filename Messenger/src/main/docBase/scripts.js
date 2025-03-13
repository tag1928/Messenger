async function createAccount()
{
    const a = document.getElementById('create-account').getElementsByTagName('input');

    const user =
    {
        username: a.username.value,
        password: a.password.value
    };

    if (user.username === '' || user.password === '')
    {
        alert('Username and/or password cannot be empty.');
        return null;
    }

    const response = await fetch('http://localhost:8080/user',
    {
        method: 'POST',
        body: JSON.stringify(user)
    });

    if (response.status === 403)
    {
        alert('Sorry, user already exists.');
        return null;
    }

    a.username.value = '';
    a.password.value = '';

    alert(`Welcome ${user.username}!`);
}

async function sendMessage()
{
    const a = document.getElementById('send-message').getElementsByTagName('input');

    const message =
    {
        receiver_username: a.receiver_username.value,
        message: a.message.value
    };

    if (message.receiver_username === '' || message.message === '')
    {
        alert('Username and/or message cannot be empty.');
        return null;
    }

    const response = await fetch('http://localhost:8080/message',
    {
        method: 'POST',
        body: JSON.stringify(message)
    });

    if (response.status === 403)
    {
        alert('Sorry, the user does not exist.');
        return null;
    }

    a.receiver_username.value = '';
    a.message.value = '';

    alert(`Successfully sent a message to ${message.receiver_username}.`);
}

async function getMessages()
{
    const a = document.getElementById('get-messages').getElementsByTagName('input');

    const user =
    {
        username: a.username.value,
        password: a.password.value
    };

    if (user.username === '' || user.password === '')
    {
        alert('Username and/or password cannot be empty.')
        return null;
    }

    const response = await fetch(`http://localhost:8080/message?username=${user.username}&password=${user.password}`,
    {
        method: 'GET'
    });

    const json = response.json();

    json.then((array) =>
    {
        const inbox = document.getElementById('inbox');
        let html = '';

        for (let i = 0; i < array.length; i++)
        {
            html += `<li>${array[i]}</li>`;
        }

        inbox.innerHTML = html;
    }).catch((error) =>
    {
        alert('Wrong password and/or username.')
        return null;
    });

    a.username.value = '';
    a.password.value = '';
}