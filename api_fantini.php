<?php
require_once "Database.php";

if (isset($_GET['id_contrada'])) {
    
    $conn = Database::getConnection();
    $stmt = $conn->prepare("SELECT fan_id, fan_con_id FROM palio_fantini WHERE fan_con_id = ?");
    $stmt->execute([ $_GET['id_contrada'] ]);
    $risultati = $stmt->fetchAll(PDO::FETCH_ASSOC);
    
    header('Content-Type: application/json');
    echo json_encode($risultati);
}
?>