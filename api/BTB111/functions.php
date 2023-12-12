<?php
    class functions {
        // data members or member variables
        private $db;
        private $sql;
        private $result;
        private $fnum;

        // constructor
        function __construct()
        {
            require_once('DbConnection.php');
            // create an object of class DbConnection
            $this->db = new DbConnection();
            // call the method: connect() of class DbConnection
            $this->db->connect();
        }
        // destructor\
        function __destruct()
        {
            // call the method: close() of class DbConnection
            $this->db->close();   
        }
        // member methods
        public function insert_data($tablename,$fields,$values){
            // count fields in array
            $this->fnum = count($fields);
            // generate insert statement
            // INSERT INTO tablename(col1,col2,...) VALUES(val1,val2,...);
            // INSERT INTO tbluser(UserName,UserPassword,FullName) VALUES('admin','admin123','Sokhmean');
            $this->sql = "INSERT INTO $tablename(";
            for($i=0; $i<$this->fnum; $i++){
                $this->sql .= $fields[$i];
                if($i<$this->fnum-1){
                    $this->sql .= ",";
                }else{
                    $this->sql .= ") VALUES(";
                }
            }
            for($i=0; $i<$this->fnum; $i++){
                $this->sql .= "'".$values[$i]."'";
                if($i<$this->fnum-1){
                    $this->sql .= ",";
                }else{
                    $this->sql .= ");";
                }
            }
            // execute insert statement
            $this->result = mysqli_query($this->db->connect(),$this->sql);
            if($this->result){
                return true;
            }else{
                return false;
            }
        }
       
        public function login_user($tablename, $username, $userpass) {
            $user = mysqli_real_escape_string($this->db->connect(), $username);
            $pwd = mysqli_real_escape_string($this->db->connect(), $userpass);
            $stmt = $this->db->connect()->prepare("SELECT * FROM $tablename WHERE UserName=? AND UserPassword=?");
            $stmt->bind_param("ss", $user, $pwd);
            $stmt->execute();
            $result = $stmt->get_result();
            if ($result->num_rows == 1) {
                return $result->fetch_assoc();
            } else {
                return false;
            }
        }

        public function update_data($tablename, $fields, $values, $fid, $vid){
            $this->fnum = count($fields);
            // generate update statment

            //Update tablename set col1=val1, col2=val2,... WHERE condition;
            $this->sql = "UPDATE $tablename SET ";
            for($i=0; $i<$this->fnum; $i++){
                $this->sql .= $fields[$i]."='".$values[$i]."'";
                if($i<$this->fnum-1){
                    $this->sql .=",";
                }else{
                    $this->sql .= "WHERE $fid = '$vid';";
                }
            }
            $this->result = mysqli_query($this->db->connect(),$this->sql);
            if($this->result){
                return true;
            }else{
                return false;
            }

        }
        public function delete_data($tablename, $fid, $vid){
            $vid = mysqli_real_escape_string($this->db->connect(), $vid);
            $this->sql = "DELETE FROM $tablename WHERE $fid = '$vid';";
            $this->result = mysqli_query($this->db->connect(), $this->sql);
            
            if($this->result){
                return true;
            } else {
                return false;
            }
        }
        
        
    }
?>
