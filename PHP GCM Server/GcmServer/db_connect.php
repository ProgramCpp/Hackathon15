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
	$db = new PDO('mysql:host=' + DB_HOST +';dbname=' + DB_DATABASE + ';charset=utf8', DB_USER, DB_PASSWORD);
        // return database handler
        return $db;
    }
  
    // Closing database connection
    public function close() {
        mysql_close();
    }
  
} 
?>