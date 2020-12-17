<%-- 
    Document   : login
    Created on : Nov 6, 2020, 7:05:46 PM
    Author     : Treater
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Login Page</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <script src="https://www.google.com/recaptcha/api.js" async defer></script>
    </head>
    <body>
        <div class="d-flex justify-content-center" style="margin-top: 10%">
            <div class="card border-secondary w-50">
                <div class="card-header">
                    Login Page
                </div>
                <div class="card-body">
                    <form action="loginServlet" method="POST" id="yourFormId" >
                        <div class="form-group">
                            <label>Email: </label>
                            <input class="form-control col-6" type="text" name="txtEmail" value="" /> 
                        </div>
                        <div class="form-group">
                            <label>Password: </label>
                            <input class="form-control col-6" type="password" name="txtPassword" value="" /> 
                        </div>
                        <div class="g-recaptcha" data-sitekey="6LfDYN4ZAAAAANKhMIqe5kXS5Rk_9zZi3U_iAbgL"></div>
                        <input type="button" onclick="checkCaptcha()" value="Login" class="btn btn-primary">
                    </form>
                    <a href="createAccountJsp">Don't have account</a>
                    <a href="searchPage">Back to store page</a>
                </div>
            </div>
        </div>
        <script>
            function checkCaptcha() {
                if (!grecaptcha.getResponse()) {
                    alert("You need to prove that you're not a robot");
                } else {
                    document.getElementById('yourFormId').submit();
                }
            }
        </script>
    </body>
</html>
