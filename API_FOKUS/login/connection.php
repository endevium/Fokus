<?php


$dbhost = "localhost";
$dbuser = "root";
$dbpass = "";
$dbname = "user_login";

// Establish connection to the database
if (!$con = mysqli_connect($dbhost, $dbuser, $dbpass, $dbname))

// Check connection
if (!$con) {
    die("Failed to connect: " . mysqli_connect_error());
}
