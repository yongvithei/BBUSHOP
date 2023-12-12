<?php

include('functions.php');
$result = array("success"=>0,"error"=>0);
if(isset($_POST['ContactName']) && isset($_POST['ContactPhone'])){
    $id = $_POST['ContactID'];
    $name = $_POST['ContactName'];
    $phone = $_POST['ContactPhone'];
    $email = $_POST['ContactEmail'];
    $image = $_POST['ContactImage'];
    $image_name = $name.".jpg";
    $path = "images/" .$image_name;

    if($image=="NoChange"){
        $fields = array("contactName","contactNumber","contactEmail");
        $values = array($name,$phone,$email);
    }else{
        $fields = array("contactName","contactNumber","contactEmail","contactImage");
        $values = array($name,$phone,$email,$image_name);
    }

    $func = new functions();
    $row = $func->show_data_by_id('tblcontact','contactID',$id);
    $update= $func->update_data('tblcontact', $fields, $values, 'contactID', $id);
    
    if($update == true){
        if($image != "NoChange"){
            file_put_contents($path,base64_decode($image));
        }
        $result["success"]=1;
        $result["msg_success"]="Update Successfull";
        print json_encode($result);
    }else{
        $result["errors"]=2;
        $result["msg_error"]="Failed to the user.";
        print json_encode($result);
    }

}else{
        $result["errors"]=1;
        $result["msg_error"]="Access denied...";
        print json_encode($result);
    

}



?>