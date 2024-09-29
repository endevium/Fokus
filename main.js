// You can include your global event listeners or utility functions here
document.addEventListener('DOMContentLoaded', function () {
    // Example of a global click event
    document.body.addEventListener('click', function (event) {
        if (event.target.matches('.some-button-class')) {
            alert('Button clicked!');
        }
    });
});
