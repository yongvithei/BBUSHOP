<?php
    include('functions.php');
    $result = array("success"=>0,"error"=>0);
    if(isset($_POST["UserNameRegister"]) && isset($_POST["PasswordRegister"])){
        $username = $_POST["UserNameRegister"];
        $userpass = md5($_POST["PasswordRegister"]);
        $fullname = $_POST["FullNameRegister"];

        $fields = array("UserName","UserPassword","FullName");
        $values = array($username,$userpass,$fullname);

        // create an object of class functions
        $func = new functions();
        // call the method: insert_data()
        $insert = $func->insert_data('tbluser',$fields,$values);
        if($insert == true){
            $result["success"] = 1;
            $result["msg_success"] = "Registered user successfully.";
            print json_encode($result);
        }else{
            $result["error"] = 2;
            $result["msg_error"] = "Failed to register the user.";
            print json_encode($result);
        }
    }else{
        $result["error"] = 1;
        $result["msg_error"] = "Access denied...BTB111";
        print json_encode($result);
    }
?>