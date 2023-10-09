<?php
    include('functions.php');
    $result = array("success"=>0,"error"=>0);
if(isset($_POST['UserNameLogin']) && isset($_POST['UserPassLogin'])){
    $username=$_POST['UserNameLogin'];
    $userpass = md5($_POST['UserPassLogin']);
    $url="http://localhost/BTB111/images/";
    //create object of class functions
    $func= new functions();
    $login= $func->login_user('tbluser',$username,$userpass);
    if($login!=false){
        $result["success"]=1;
        $result["msg_success"]="Login Successfully";
        $result["UserID"]=$login['UserID'];
        $result["UserName"]=$login['UserName'];
        $result["UserPassword"]=$login["UserPassword"];
        $result["UserFullname"]=$login["FullName"];
        $result["UserType"]=$login["UserType"];
        $result["UserEmail"]=$login['Email'];
        $result["UserImage"] = base64_encode(file_get_contents($url.$login['UserImage']));
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