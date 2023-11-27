<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%
    // Retrieve parameters from the request
    String code = request.getParameter("code");
    String sessionState = request.getParameter("session_state");
%>

<!DOCTYPE html>
<html>
<head>
    <!-- Include jQuery library -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>

<body>
    <script type="text/javascript">
        
        // Function to make a POST request
        function createPostRequest() {
            // Define the URL for the OAuth token endpoint
            var url = 'https://api.asgardeo.io/t/wathsala/oauth2/token';

            // Retrieve code and session state from server-side variables
            var code = encodeURIComponent('<%= code %>');
            var sessionState = encodeURIComponent('<%= sessionState %>');
            
            // Store session state in localStorage
            localStorage.setItem('state', sessionState);

            // OAuth client credentials
            var client_Id = 'eVE1H6WT0us9ZuPJf23iZhfCus0a';
            var client_secret = '71WvULacsxlUBvSm3WfcM2r0n2tegwoLhs4YSkNenRAa';
            var redirect_uri = 'http://localhost:8081/RouteRover/routeroverauthorize.jsp';

            // Prepare request body parameters
            var params = new URLSearchParams();
            params.append('code', code);
            params.append('grant_type', 'authorization_code');
            params.append('client_id', client_Id);
            params.append('client_secret', client_secret);
            params.append('redirect_uri', redirect_uri);

            // Define request options
            var requestOptions = {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                data: params.toString() // Convert bodyParams to a string
            };

            // Make the POST request using jQuery AJAX
            $.ajax(url, requestOptions)
                .done(function (data) {
                    // Handle the successful response data here
                    console.log(data.access_token);
                    
                    // Store access token and ID token in localStorage
                    var access_token  = data.access_token;
                    var id_token = data.id_token;
                    localStorage.setItem('access_token', access_token);
                    localStorage.setItem('id_token', id_token);
                    
                    // Redirect to the home page
                    window.location.href = "./pages/home.jsp";
                })	
                .fail(function (error) {
                    // Handle any errors here
                    console.error('Error:', error);
                    
                    // Redirect to the index page in case of failure
                    window.location.href = "./index.jsp";
                });                
        }

        // Call the function to make the POST request
        createPostRequest();
    </script>
</body>
</html>
