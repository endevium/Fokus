// Assuming you have a form submission handler for creating notes
document.getElementById('note-form').addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent the default form submission

    const title = document.getElementById('note-title').value;
    const content = document.getElementById('note-content').value;
    const token = localStorage.getItem('token'); // Retrieve the stored token from login

    fetch('http://127.0.0.1:8000/api/fokus_notes', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`, // Pass the token for authentication
        },
        body: JSON.stringify({
            title: title,
            content: content,
        }),
    })
    .then(response => response.json())
    .then(data => {
        if (data.message) {
            alert(data.message); // Alert success message
        } else {
            alert('Note creation failed: ' + data.errors[0]); // Display validation errors
        }
    })
    .catch((error) => console.error('Error:', error));
});
