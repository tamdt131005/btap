<?php
include 'db.php';

$data = json_decode(file_get_contents("php://input"));

if (!isset($data->username) || !isset($data->password)) {
    echo json_encode(['status' => 'error', 'message' => 'Invalid input. Please provide username and password.']);
    exit();
}

$username = $conn->real_escape_string($data->username);
$password = $conn->real_escape_string($data->password);

$sql = "SELECT id, username, password, email FROM users WHERE username = '$username'";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    $user = $result->fetch_assoc();

    // Verify the password
    if (password_verify($password, $user['password'])) {
        // Don't send the password hash back to the client
        unset($user['password']);

        echo json_encode(['status' => 'success', 'message' => 'Login successful.', 'user' => $user]);
    } else {
        echo json_encode(['status' => 'error', 'message' => 'Invalid username or password.']);
    }
} else {
    echo json_encode(['status' => 'error', 'message' => 'Invalid username or password.']);
}

$conn->close();
?>