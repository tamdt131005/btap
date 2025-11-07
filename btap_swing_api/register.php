<?php
include 'db.php';

// Get the posted data.
$data = json_decode(file_get_contents("php://input"));

// Basic validation
if (!isset($data->username) || !isset($data->password) || !isset($data->email)) {
    echo json_encode(['status' => 'error', 'message' => 'Invalid input. Please provide username, password, and email.']);
    exit();
}

$username = $conn->real_escape_string($data->username);
$email = $conn->real_escape_string($data->email);
// Hash the password for security
$password_hash = password_hash($conn->real_escape_string($data->password), PASSWORD_BCRYPT);

// Check if username or email already exists
$sql_check = "SELECT id FROM users WHERE username = '$username' OR email = '$email'";
$result_check = $conn->query($sql_check);

if ($result_check->num_rows > 0) {
    echo json_encode(['status' => 'error', 'message' => 'Username or email already exists.']);
} else {
    // Insert the new user
    $sql_insert = "INSERT INTO users (username, password, email) VALUES ('$username', '$password_hash', '$email')";

    if ($conn->query($sql_insert) === TRUE) {
        // Get the new user's ID
        $new_user_id = $conn->insert_id;
        echo json_encode(['status' => 'success', 'message' => 'User registered successfully.', 'user_id' => $new_user_id]);
    } else {
        echo json_encode(['status' => 'error', 'message' => 'Error during registration: ' . $conn->error]);
    }
}

$conn->close();
?>