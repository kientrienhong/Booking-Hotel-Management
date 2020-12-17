<%-- 
    Document   : createAccount
    Created on : Sep 15, 2020, 9:29:38 AM
    Author     : Treater
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create new account</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    </head>
    <body>
        <c:set var="error" value="${requestScope.ERROR}"></c:set>

            <div class="d-flex justify-content-center" style="margin-top: 10%">
                <div class="card border-secondary w-50">
                    <div class="card-header">
                        Sign Up
                    </div>
                    <div class="card-body">
                        <form action="signUpServlet" method="POST">
                            <div class="form-group">
                                <label>Email: </label>
                                <input class="form-control col-6" type="text" name="txtEmail" value="${param.txtEmail}" /> 
                        </div>
                        <c:if test="${not empty error.emailError}">
                            <div class="alert alert-danger col-6" role="alert">
                                ${error.emailError}
                            </div>
                        </c:if>
                        <c:if test="${not empty error.emailExisted}">
                            <div class="alert alert-danger col-6" role="alert">
                                ${error.emailExisted}
                            </div>
                        </c:if>
                        <div class="form-group">
                            <label>Password: </label>
                            <input class="form-control col-6" type="password" name="txtPassword" value="" /> 
                        </div>
                        <c:if test="${not empty error.passwordError}">
                            <div class="alert alert-danger col-6" role="alert">
                                ${error.passwordError}
                            </div>
                        </c:if>
                        <div class="form-group">
                            <label>Confirm password: </label>
                            <input class="form-control col-6" type="password" name="txtConfirmPassword" value="" /> 
                        </div>
                        <c:if test="${not empty error.passwordConfirmError}">
                            <div class="alert alert-danger col-6" role="alert">
                                ${error.passwordConfirmError}
                            </div>
                        </c:if>
                        <div class="form-group">
                            <label>Name: </label>
                            <input class="form-control col-6" type="text" name="txtNameRegistraion" value="${param.txtNameRegistraion}" /> 
                        </div>
                        <c:if test="${not empty error.nameError}">
                            <div class="alert alert-danger col-6" role="alert">
                                ${error.nameError}
                            </div>
                        </c:if>
                        <div class="form-group">
                            <label>Address: </label>
                            <input class="form-control col-6" type="text" name="txtAddress" value="${param.txtAddress}" /> 
                        </div>
                        <c:if test="${not empty error.addressError}">
                            <div class="alert alert-danger col-6" role="alert">
                                ${error.addressError}
                            </div>
                        </c:if>
                        <div class="form-group">
                            <label>Phone: </label>
                            <input class="form-control col-6" type="text" name="txtPhone" value="${param.txtPhone}" /> 
                        </div>
                        <c:if test="${not empty error.phoneError}">
                            <div class="alert alert-danger col-6" role="alert">
                                ${error.phoneError}
                            </div>
                        </c:if>
                        <div class="form-group">
                            <input type="submit" value="Sign up" class="btn btn-primary" name="btAction"/>
                            <a href="searchPage" class="btn btn-danger" style="color: white">Go to booking again</a>                                
                            <a href="login" class="btn btn-danger" style="color: white">Already have account?</a>                                
                        </div>
                    </form>

                </div>
            </div>
        </div>  
    </body>
</html>
