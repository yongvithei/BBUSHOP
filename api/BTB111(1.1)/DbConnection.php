<?php
    class DbConnection {
        // data member or member variable
        private $conn;
        // constructor
        function __construct()
        {
            require_once('config.php');
            $this->connect();   // call the method: connect()
        }
        // destructor
        function __destruct()
        {
            $this->close(); // call the method: close()
        }
        // member methods
        public function connect(){
            $this->conn = mysqli_connect(DB_HOST,DB_USER,DB_PWD,DB_NAME);
            if($this->conn){
                return $this->conn;
            }else{
                die("Cannot connect to database!\n".mysqli_error($this->connect()));
            }
        }
        public function close(){
            mysqli_close($this->connect());
        }
    }
?>