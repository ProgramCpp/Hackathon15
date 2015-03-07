<?php
 
class DB_Functions {
 
    private $db;
 
    //put your code here
    // constructor
    function __construct() {
        include_once './db_connect.php';
        // connecting to database
	$db = new DB_Connect();
	$this->db = $db -> connect();
    }
 
    // destructor
    function __destruct() {
         
    }
 
    /**
     * Storing new user
     * returns user details
     */
    public function storeUser($name, $email, $gcm_regid) {
        // insert user into database
	try {
        $result = $db->exec("INSERT INTO gcm_users(name, email, gcm_regid, created_at) VALUES('$name', '$email', '$gcm_regid', NOW())");
        // check for successful store
	if ($result) 
	{
            // get user details
            $id = $db->lastInsertId();
            $stmt = $db->query("SELECT * FROM gcm_users WHERE id = $id");
            // return user details
		if ($stmt->rowCount() > 0) 
		{
			$results = $stmt->fetchAll(PDO::FETCH_ASSOC);
        	return $results;
		}
		else
		{
			return false;
		}
    } 
	else
	{
         return false;
    }
	
    }
	catch(PDOException $ex){
            return false;
        }
    }
 
    /**
     * Getting all users
     */
    public function getAllUsers() {
	$stmt = $this->db->query("select * FROM gcm_users");
        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }
    
}
 
?>