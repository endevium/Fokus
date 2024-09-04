<?php


$dbhost = "localhost";
$dbUser = "root";
$dbPass = "";
$dbName = "user_login";

// Establish connection to the database
$con = mysqli_connect($dbhost, $dbUser, $dbPass, $dbName);

// Check connection
if (!$con) {
    die("Failed to connect: " . mysqli_connect_error());
}
