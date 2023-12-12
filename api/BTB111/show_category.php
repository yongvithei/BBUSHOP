<?php
    include 'functions.php';
    $func = new functions();
    $data = $func->show_all_data('tblcategories','categories');
    print $data;


?>