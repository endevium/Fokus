document.addEventListener('DOMContentLoaded', function() {
    // Signup functionality
    const signupForm = document.getElementById('signup-form');
    if (signupForm) {
        signupForm.addEventListener('submit', function(event) {
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
            .then(response => {
                // Check if the response is JSON or an error page
                const contentType = response.headers.get("content-type");
                if (contentType && contentType.indexOf("application/json") !== -1) {
                    return response.json(); // Parse JSON if response is valid JSON
                } else {
                    return response.text(); // Otherwise, get the text (likely HTML error page)
                }
            })
            .then(data => {
                if (typeof data === 'object') {
                    if (data.message) {
                        alert(data.message); // Success message
                        window.location.href = 'login.html'; // Redirect to login page
                    } else {
                        alert('Signup failed: ' + data.errors.email[0]); // Validation errors
                    }
                } else {
                    console.error('Received non-JSON response:', data); // Log HTML error response
                    alert('An error occurred. Check the console for details.');
                }
            })
            .catch((error) => console.error('Error:', error));
        });
    }

    // Login functionality
    const loginForm = document.getElementById('login-form');
    if (loginForm) {
        loginForm.addEventListener('submit', function(event) {
            event.preventDefault(); // Prevent the default form submission

            const email = document.getElementById('email').value;
            const password = document.getElementById('password').value;

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
    }
});
