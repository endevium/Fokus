<?php
if (session_status() == PHP_SESSION_NONE) 
    session_start();

function check_login($con) {
    if (isset($_SESSION['user_id'])) {  // if the user is logged in
        
        $id = $_SESSION['user_id'];
        $query = "SELECT * FROM users WHERE user_id = ? LIMIT 1";

        // to prevent SQL injection
        if ($stmt = mysqli_prepare($con, $query)) {
            mysqli_stmt_bind_param($stmt, "i", $id); // "i" indicates the type is integer
            mysqli_stmt_execute($stmt);
            $result = mysqli_stmt_get_result($stmt);

            if ($result && mysqli_num_rows($result) > 0) {
                $user_data = mysqli_fetch_assoc($result);
                mysqli_stmt_close($stmt);
                return $user_data;
            }

            mysqli_stmt_close($stmt);
        }
    }

    // Redirect to login if not logged in
    header("Location: login.php");
    die;
}

function random_num($length) {
    $text = "";
    
    if ($length < 5) {
        $length = 5;
    }

    $len = rand(4, $length);

    for ($i = 0; $i < $len; $i++) {
        $text .= rand(0, 9);
    }

    return $text;
}
