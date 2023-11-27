<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!--font-family-->
<link
	href="https://fonts.googleapis.com/css?family=Poppins:100,100i,200,200i,300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Rufina:400,700"
	rel="stylesheet">

<title>RouteRover</title>


<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/index.css">


</head>
<body>
	<section id="home" class="welcome-hero">
		<div class="container">
			<div class="welcome-hero-txt">
				<h2>Welcome to RouteRover</h2>
				<p>"Drive Your Dreams: Reserve Your Ride Today!"</p>
				<button class="welcome-btn"
					onclick="window.location.href='https://api.asgardeo.io/t/wathsala/oauth2/authorize?response_type=code&client_id=eVE1H6WT0us9ZuPJf23iZhfCus0a&scope=openid%20email%20phone%20profile&redirect_uri=http%3A%2F%2Flocalhost%3A8081%2FRouteRover%2Frouteroverauthorize.jsp'">Log
					In</button>
				<button class="welcome-btn"
					onclick="window.location.href='https://console.asgardeo.io/'">Register</button>
			</div>
		</div>
	</section>



	<!-- Include all js compiled plugins (below), or include individual files as needed -->
	<script src="js/jquery.js"></script>
	<script src="js/index.js"></script>

</body>
</html>