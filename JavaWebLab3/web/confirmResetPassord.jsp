<%-- 
    Document   : confirmResetPassord
    Created on : Nov 5, 2020, 7:53:58 AM
    Author     : Treater
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Confirm Reset Password Page</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    </head>
    <body>
        <div class="d-flex justify-content-center" style="margin-top: 5%">
            <div class="card border-secondary w-50">
                <div class="card-header">
                    Reset Password
                </div>
                <div class="card-body">
                    <form action="confirmResetPasswordMember" method="POST">
                        <div class="form-group">
                            <label>Old password: </label>
                            <input class="form-control col-6" type="password" name="txtOldPassword" value="" /> 
                        </div>
                        <div class="form-group">
                            <label>New password: </label>
                            <input class="form-control col-6" type="password" name="txtNewPassword" value="" /> 
                        </div>
                        <div class="form-group">
                            <c:if test="${not empty requestScope.ERROR_NEW_PASSWORD}">
                                <div class="alert alert-danger col-10" role="alert">
                                    ${requestScope.ERROR_NEW_PASSWORD}
                                </div>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <label>Confirm new password: </label>
                            <input class="form-control col-6" type="password" name="txtConfirmNewPassword" value="" /> 
                        </div>
                        <div class="form-group">
                            <c:if test="${not empty requestScope.ERROR_CONFIRM_PASSWORD}">
                                <div class="alert alert-danger col-10" role="alert">
                                    ${requestScope.ERROR_CONFIRM_PASSWORD}
                                </div>
                            </c:if>
                        </div>
                        <input type="hidden" name="txtRegistrationId" value="${param.txtUserId}" />
                        <input type="submit" value="Login" class="btn btn-primary" name="btAction"/>
                        <a href="searchPage">Cancel</a>
                    </form>
                        <div class="form-group">
                            <c:if test="${not empty requestScope.ERROR_UPDATE}">
                                <div class="alert alert-danger col-10" role="alert">
                                    ${requestScope.ERROR_UPDATE}
                                </div>
                            </c:if>
                        </div>
                </div>
            </div>
        </div>    
    </body>
</html>
