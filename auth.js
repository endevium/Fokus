// auth.js

// Signup functionality
document.getElementById('signup-form').addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent the default form submission

    const username = document.getElementById('signup-username').value;
    const email = document.getElementById('signup-email').value;
    const password = document.getElementById('signup-password').value;

    fetch('http://127.0.0.1:8000/api/FokusApp', { // Adjust the URL based on your setup
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            username: username,
            email: email,
            password: password,
        }),
    })
    .then(response => response.json())
    .then(data => {
        if (data.message) {
            alert(data.message); // Alert success message
            // Optionally, redirect to login page after signup
            window.location.href = 'login.html'; // Redirect to login page
        } else {
            alert('Signup failed: ' + data.errors.email[0]); // Display validation errors
        }
    })
    .catch((error) => console.error('Error:', error));
});

// Login functionality
document.getElementById('login-form').addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent the default form submission

    const email = document.getElementById('login-email').value;
    const password = document.getElementById('login-password').value;

    fetch('http://127.0.0.1:8000/api/login', { // Adjust the URL based on your setup
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            email: email,
            password: password,
        }),
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            alert('Login successful!'); // Alert success message
            localStorage.setItem('token', data.token); // Store the token for authenticated requests
            window.location.href = 'notes.html'; // Redirect to notes page
        } else {
            alert('Login failed: ' + data.message); // Display error message
        }
    })
    .catch((error) => console.error('Error:', error));
});
