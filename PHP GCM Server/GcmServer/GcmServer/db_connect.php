<?php
  
class DB_Connect {
  
    // constructor
    function __construct() {
  
    }
  
    // destructor
    function __destruct() {
        // $this->close();
    }
  
    // Connecting to database
    public function connect() {
	$db = new PDO('mysql:host=localhost;dbname=winappgcm;charset=utf8', 'root');
	$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	$db->setAttribute(PDO::ATTR_EMULATE_PREPARES, false);
        // return database handler
        return $db;
    }
  
    // Closing database connection
    public function close() {
        /*mysql_close();*/
    }
  
} 
?>