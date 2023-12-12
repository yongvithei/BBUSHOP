<?php

include('functions.php');
$result = array("success" => 0, "error" => 0);

if (isset($_POST['ContactID'])) {
    $id = $_POST['ContactID'];

    $func = new functions();
    $row = $func->show_data_by_id('tblcontact', 'contactID', $id);

    // Check if the record exists
    if ($row) {
        // Delete the record
        $delete = $func->delete_data('tblcontact', 'contactID', $id);

        if ($delete) {
            // If deletion is successful
            $result["success"] = 1;
            $result["msg_success"] = "Delete Successful";
            print json_encode($result);
        } else {
            // If deletion fails
            $result["error"] = 2;
            $result["msg_error"] = "Failed to delete the contact.";
            print json_encode($result);
        }
    } else {
        // If the record does not exist
        $result["error"] = 3;
        $result["msg_error"] = "Contact not found.";
        print json_encode($result);
    }
} else {
    // If ContactID is not set
    $result["error"] = 1;
    $result["msg_error"] = "Access denied...";
    print json_encode($result);
}
?>
