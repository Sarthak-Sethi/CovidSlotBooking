<?php

if ($_SERVER['REQUEST_METHOD'] =='POST'){

    $name = $_POST['name'];
    $gender = $_POST['gender'];
    $currentaddress = $_POST['currentaddress'];
    $peraddress = $_POST['peraddress'];
    $city = $_POST['city'];
    $pincode = $_POST['pincode'];
    $password = $_POST['password'];
    $phoneno = $_POST['phoneno'];

    $password = password_hash($password, PASSWORD_DEFAULT);

    require_once 'connect.php';

    $sql = "INSERT INTO user (name, gender,currentaddress,peraddress,city,pincode,password,phoneno)
    VALUES ('$name', '$gender', '$currentaddress' , '$peraddress','$city','$pincode','$password','$phoneno')";

    if ( mysqli_query($conn, $sql) ) {
        $result["success"] = "1";
        $result["message"] = "success";

        echo json_encode($result);
        mysqli_close($conn);

    } else {

        $result["success"] = "0";
        $result["message"] = "error";

        echo json_encode($result);
        mysqli_close($conn);
    }
}

?>
