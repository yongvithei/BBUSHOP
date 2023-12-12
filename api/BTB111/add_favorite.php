<?php

include('functions.php');
$result = array("success"=>0,"error"=>0);
if(isset($_POST['ContactAddFavorite'])){
    $id = $_POST['ContactID'];
    $add = $_POST['ContactAddFavorite'];
    
    $fields = array("favorites");
    $values = array($add);

    $func = new functions();
    $row = $func->show_data_by_id('tblcontact','contactID',$id);
    $update= $func->update_data('tblcontact', $fields, $values, 'contactID', $id);
    
    if($update == true){
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