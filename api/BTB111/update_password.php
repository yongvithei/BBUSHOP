<?php

    include('functions.php');
    $result = array("success"=>0,"error"=>0);
    if(isset($_POST['UserID']) && isset($_POST['UserPassword'])){
        $id = $_POST['UserID'];
        $userpassword = $_POST['UserPassword'];
        
        $fields = array("UserPassword");
        $values = array($userpassword);

        $func = new functions();
        $update= $func->update_data('tbluser',$fields,$values,'UserID',$id);
        
        if($update == true){
            $result["success"]=1;
            $result["msg_success"]="Update Password Successfull";
            print json_encode($result);
        }else{
            $result["errors"]=2;
            $result["msg_error"]="Failed to Change the Password.";
            print json_encode($result);
        }

    }else{
            $result["errors"]=1;
            $result["msg_error"]="Access denied...";
            print json_encode($result);
    }

    

?>