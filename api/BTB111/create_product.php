<?php
    include('functions.php');
    $result = array("success"=>0,"error"=>0);
    if(isset($_POST["ProductName"]) && isset($_POST["CategoryID"])){
        $productName = $_POST["ProductName"];
        $categorrID = $_POST["CategoryID"];
        $barcode = $_POST["Barcode"];
        $qty = $_POST["Qty"];
        $unitPrice = $_POST["UnitPrice"];
        $unitPriceOut = $_POST["UnitPriceOut"];
        $fields = array("ProductName","CategoryID","Barcode","Qty","UnitPrice","UnitPriceOut");
        $values = array($productName,$categorrID,$barcode,$qty,$unitPrice,$unitPriceOut);

        // create an object of class functions
        $func = new functions();
        // call the method: insert_data()
        $insert = $func->insert_data('tblproducts',$fields,$values);
        if($insert == true){
            $result["success"] = 1;
            $result["msg_success"] = "Product create successfully.";
            print json_encode($result);
        }else{
            $result["error"] = 2;
            $result["msg_error"] = "Failed to create the Product.";
            print json_encode($result);
        }
    }else{
        $result["error"] = 1;
        $result["msg_error"] = "Access denied...BTB111";
        print json_encode($result);
    }
?>