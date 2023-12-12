<?php
    include('functions.php');
    $result = array("success"=>0,"error"=>0);
    if(isset($_POST["CategoryName"]) && isset($_POST["Description"])){
        $categoryName = $_POST["CategoryName"];
        $description = $_POST["Description"];
        $createBy = "vithei";
        $fields = array("CategoryName","Description","CreateBy");
        $values = array($categoryName,$description,$createBy);

        // create an object of class functions
        $func = new functions();
        // call the method: insert_data()
        $insert = $func->insert_data('tblcategories',$fields,$values);
        if($insert == true){
            $result["success"] = 1;
            $result["msg_success"] = "Category create successfully.";
            print json_encode($result);
        }else{
            $result["error"] = 2;
            $result["msg_error"] = "Failed to create the Category.";
            print json_encode($result);
        }
    }else{
        $result["error"] = 1;
        $result["msg_error"] = "Access denied...BTB111";
        print json_encode($result);
    }
?>