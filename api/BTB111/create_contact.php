<?php
    include('functions.php');
    $result = array("success"=>0,"error"=>0);
    if(isset($_POST["ContactName"]) && isset($_POST["UserPhone"])){
        $contactName = $_POST["ContactName"];
        $phone = $_POST["UserPhone"];
        $email = $_POST["UserEmail"];
        $favorites = "0";
        $image = $_POST['UserImage'];
        $image_name = $contactName.".jpg";
        $path = "images/" .$image_name;
        
        $fields = array("contactName","contactNumber","contactEmail","contactImage","favorites");
        $values = array($contactName, $phone, $email, $image_name, $favorites);

        // create an object of class functions
        $func = new functions();
        // call the method: insert_data()
        $insert = $func->insert_data('tblcontact',$fields,$values);
        if($insert == true){
            file_put_contents($path, base64_decode($image));
            $result["success"] = 1;
            $result["msg_success"] = "Contact create successfully.";
            print json_encode($result);
        }else{
            
            $result["error"] = 2;
            $result["msg_error"] = "Failed to create the Contact.";
            print json_encode($result);
        }
    }else{
        $result["error"] = 1;
        $result["msg_error"] = "Access denied...BTB111";
        print json_encode($result);
    }
?>