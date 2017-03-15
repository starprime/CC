<html>
 <head>
 <meta charset="UTF-8">
  <title>PHP Test</title>
 </head>
 <body>
 <form action="Cloudpi.php" method="get">
Input: <input type="text" name="inputdata" ><br>
<input align="center" type="submit">
</form>
<!-- <form action="url.php?inputdata">
<table border="0">
<tr> 
	<td>Input</td>
	<td align="center"><input type="text" name="inputdata" size="30" /></td>
</tr>
<tr>
	<td colspan="2" align="center"><input type="submit"  onclick="callphp()" method="post"/></td>
</tr>

</table>
</form>  -->
<font face="century gothic" size="20px">
	<center> </br></br>
	<?php 
	
		echo "Query for:";
		// echo $_GET["inputdata"]; 
		//echo $_POST["inputdata"];
		 $inputdata = $_GET["inputdata"]; 
		 $url = "http://localhost:8080/AWSCloudComputingProj-0.0.1-SNAPSHOT/Cloudpi?inputdata=".$inputdata;
		 
  	$post_params_s = ["inputdata"=>$inputdata];

  	$curl = curl_init();
	curl_setopt($curl, CURLOPT_URL, $url);
	curl_setopt ( $ch, CURLOPT_POST          , TRUE ) ;
	curl_setopt ( $ch, CURLOPT_POSTFIELDS    , $post_params_s ) ;
	curl_setopt ( $ch, CURLOPT_RETURNTRANSFER, TRUE ) ; 
	curl_exec($curl);
	curl_close($curl);












////////////////////////////////////////////////////////////////////////////////////////////////
		// echo $inputdata;
		// //header("Location:http://localhost/url.php?inputdata=".$inputdata);
		// //file_get_contents("http://localhost:8080/CloudComputingProj/Cloudpi")
		// // echo file_get_contents("http://localhost:8080/CloudComputingProj/Cloudpi");
		// # WARNING : maybe you should provide your context in the URL : provide the same URL that you use in your browser for example
		
		// // #provide your parameters like : ?param1=value1&param2=value2&.....&paramN=valueN
		//$post_params_s = ["inputdata"=>$inputdata];
		// //echo $post_params_s;
		// $ch  = curl_init() ;
		// curl_setopt($ch, CURLOPT_URL, $url)
		// curl_exec($ch);
		// curl_close($ch);
		// curl_setopt ( $ch, CURLOPT_POST          , TRUE ) ;
		// curl_setopt ( $ch, CURLOPT_POSTFIELDS    , $post_params_s ) ;
		// curl_setopt ( $ch, CURLOPT_RETURNTRANSFER, TRUE ) ;             // -- put it to FALSE, write directly in main output
		// curl_exec   ( $ch ) ;
		// curl_close  ( $ch ) ;
?></center>
</font>

 </body>
</html>



