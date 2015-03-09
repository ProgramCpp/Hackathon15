<?php

// response json
$json = array();
 
/**
 * Registering a user device
 * Store reg id in users table
 */
error_log("received", 0);
if (isset($_POST["name"]) && isset($_POST["email"]) && isset($_POST["regId"])) {
    $name = $_POST["name"];
error_log("$name", 0);
    $email = $_POST["email"];
    $gcm_regid = $_POST["regId"]; // GCM Registration ID
    // Store user details in db
    include_once './db_functions.php';
    include_once './GCM.php';
 
    $db = new DB_Functions();
    $gcm = new GCM();
    $res = $db->storeUser($name, $email, $gcm_regid);
$no_of_users =count($res);

    $registatoin_ids = array($gcm_regid);
    $message = array("product" => "shirt");
 
    $result = $gcm->send_notification($registatoin_ids, $message);
 
    echo $result;
} else {
    // user details missing
}
?>