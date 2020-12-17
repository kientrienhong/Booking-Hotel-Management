<%-- 
    Document   : bookingHistory
    Created on : Nov 2, 2020, 9:05:10 PM
    Author     : Treater
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>History Booking Page</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    </head>
    <body>
        <c:set var="ERROR" value="${requestScope.ERROR}"/>
        <div class="d-flex justify-content-center" style="margin-top: 2%">
            <div class="card" style="width: 50%">
                <div class="card-body">
                    <form action="searchHistoryBookingMember">
                        <div class="form-group row">
                            <div class="col-2">
                                <label class="align-middle" for="inputName">Hotel name</label>
                            </div>
                            <div class="col-4">
                                <input class="form-control mr-sm-2" id="inputName" type="search" placeholder="Name..." aria-label="Search" name="txtName" value="${param.txtName}">
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="text-center" style="padding-left: 50%">OR</label>
                        </div>
                        <div class="form-group row">
                            <div class="col-2">
                                <label class="align-middle">Booking date</label>
                            </div>
                            <div class="col-10">
                                <input class="form-control mr-sm-2" type="date" name="txtBookingDate" value="${param.txtBookingDate}">
                            </div>
                        </div>
                        <div class="form-group row">
                            <c:if test="${not empty ERROR.searchError}">
                                <div class="alert alert-danger col-10" role="alert">
                                    ${ERROR.searchError}
                                </div>
                            </c:if>
                        </div>
                        <a href="searchPage">Back to search page</a>
                        <div class="d-flex justify-content-center">
                            <input type="hidden" name="txtUserId" value="${param.txtUserId}" />
                            <input class="btn btn-outline-success my-2 my-sm-0" type="submit" value="Search"/>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <c:if test="${(not empty param.txtName || not empty param.txtBookingDate) && empty ERROR}">
            <c:set var="list" value="${requestScope.LIST_BOOKING}"/>
            <c:if test="${not empty list}">
                <c:forEach items="${list}" var="bookingDTO" varStatus="vs">
                    <form action="deleteHistoryBookingMember">
                        <div class="card mb-3" style="max-width: 70%; margin: 0 auto">
                            <div class="card-body">
                                <div class="form-row">
                                    <h4 class="card-title col-8">ID booking: ${bookingDTO.id}</h4>
                                    <input type="submit" class="btn btn-danger col-2" style="margin-left: 100px" value="Delete Article"/>
                                </div>
                                <p class="card-text">Check in date: ${bookingDTO.checkInDate}</p>
                                <p class="card-text">Check out date: ${bookingDTO.checkOutDate}</p>
                                <p class="card-text">Booking date: ${bookingDTO.dateBooking}</p>
                                <p class="card-text">Total price: ${bookingDTO.totalPrice}</p>
                                <c:if test="${not empty requestScope.ERROR_DELETE && bookingDTO.id eq param.txtInvoiceId}">
                                    <div class="alert alert-danger col-6" role="alert">
                                        ${requestScope.ERROR_DELETE}
                                    </div>
                                </c:if>
                            </div>
                        </div>
                        <input type="hidden" name="txtName" value="${param.txtName}" />
                        <input type="hidden" name="txtBookingDate" value="${param.txtBookingDate}" />
                        <input type="hidden" name="txtUserId" value="${sessionScope.DTO.id}" />
                        <input type="hidden" name="txtInvoiceId" value="${bookingDTO.id}" />
                    </form>

                </c:forEach>
            </c:if>
            <c:if test="${empty list}">
                NOT FOUND!
            </c:if>
        </c:if>
    </body>
</html>
