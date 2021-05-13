<?php
    //obtem a informaçao do json
    $file = 'json/imagem.json';
    $json = file_get_contents($file,0,null,null); 
    $objs = json_decode($json,true);
    
    foreach ($objs as $value) {

        //mostra a informaçao em array e conversao base64_encode para as imagens
        echo $value['solution'];
        echo '<img src="data:img/png;base64,'.base64_encode($value['path']); echo '" >';

        // exemplo que vi num site (teste com uma imagem da internet) -> se quiseres podes tirar so deixei para veres
        $image = "http://qrfree.kaywa.com/?l=1&s=8&d=google.com";
        $imageData = base64_encode(file_get_contents($image));
        $src = 'data:image/png;base64,'.$imageData;
        echo '<img src="'.$src.'">';
    }
?>
