<?php

    include('functions.php');
    $result = array("success"=>0,"error"=>0);
    if(isset($_POST['UserFullName']) && isset($_POST['UserName'])){
        $id = $_POST['UserID'];
        $fullname = $_POST['UserFullName'];
        $username = $_POST['UserName'];
        $email = $_POST['UserEmail'];
        $image = $_POST['UserImage'];
        $image_name = $username.".jpg";
        $path = "images/" .$image_name;

        $fields = array("UserName","FullName","Email","UserImage");
        $values = array($username,$fullname,$email,$image_name);

        $func = new functions();
        $update= $func->update_data('tbluser',$fields,$values,'UserID',$id);
        
        if($update == true){
            file_put_contents($path, base64_decode($image));
            $result["success"]=1;
            $result["msg_success"]="Update Successfull";
            $result["UserName"]=$username;
            $result["UserFullName"]= $fullname;
            $result["UserEmail"]= $email;
            $result["UserImage"]= $image;
            print json_encode($result);
        }else{
            $result["errors"]=2;
            $result["msg_error"]="Failed to Login the user.";
            print json_encode($result);
        }

    }else{
            $result["errors"]=1;
            $result["msg_error"]="Access denied...";
            print json_encode($result);
        

    }

    

?>