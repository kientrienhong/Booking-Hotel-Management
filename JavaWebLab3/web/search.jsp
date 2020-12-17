<%-- 
    Document   : search
    Created on : Oct 20, 2020, 9:48:40 PM
    Author     : Treater
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Page</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <style>
            .card-columns{
                width: 80%; 
                margin-left: 20%;
                column-count: 4;
                margin-top: 2%;
            }
        </style>
    </head>
    <body>
        <c:set var="DTO" value="${sessionScope.DTO}"/>
        <c:set var="NAME_ROLE" value="${requestScope.NAME_ROLE}"/>
        <c:set var="SEARCH_ERROR" value="${requestScope.ERROR_SEARCH}"/>
        <c:set var="CART" value="${sessionScope.CART}"/>
        <nav class="navbar navbar-light bg-light justify-content-between" style="position: relative; width: 100%">
            <a class="navbar-brand">Hotel</a>
            <c:if test="${empty DTO}" var="checkLogin">
                <form action="login">
                    <input type="submit" value="Log in" class="btn btn-primary"/>
                </form>
                <form action="createAccountJsp">
                    <input type="submit" value="Sign Up" class="btn btn-primary" />
                </form>
            </c:if>
            <c:if test="${!checkLogin}">
                <form class="form-inline" action="logOut">
                    <h4 class="mr-sm-2" style="position: absolute; left: 50%; top: 50%; transform: translate(-50%, -50%)">
                        ${DTO.name}
                    </h4>
                    <input style="position: absolute; right: 2%; top: 20%;" class="btn btn-danger" type="submit" value="Log out"/>
                </form>
            </c:if>
            <c:if test="${NAME_ROLE eq 'member'}">
                <a href="loadInfoMapMember?txtCheckIn=${param.txtCheckIn}&txtCheckOut=${param.txtCheckOut}" style="position: absolute; right: 10%; top: 20%;" class="btn btn-primary">Cart(${requestScope.CART_SIZE})</a>
                <form action="bookingHistoryPageMember">
                    <input type="hidden" name="txtUserId" value="${DTO.id}" />
                    <input type="submit" value="History Booking" class="btn btn-primary" style="position: absolute; right: 20%; top: 20%;"/>
                </form>
                <a href="loadBookedRoomListMember?txtUserId=${DTO.id}" style="position: absolute; right: 30%; top: 20%;" class="btn btn-primary">Feedback</a>
                <a href="sendConfirmLinkMember?txtUserId=${DTO.id}" style="position: absolute; left: 30%; top: 20%;" class="btn btn-danger">Reset password</a>
            </c:if>
        </nav>
        <div class="d-flex justify-content-center" style="margin-top: 2%">
            <div class="card" style="width: 50%">
                <div class="card-body">
                    <form action="search">
                        <div class="form-group row">
                            <div class="col-2">
                                <label class="align-middle" for="inputName">Hotel Name</label>
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
                                <label class="align-middle">Hotel Area</label>
                            </div>
                            <div class="col-10">
                                <input class="form-control mr-sm-2" placeholder="Hotel area..." name="txtArea" value="${param.txtArea}">
                            </div>
                        </div>
                        <div class="form-group row" style="margin-left: 10%">
                            <c:if test="${not empty SEARCH_ERROR.areaError}">
                                <div class="alert alert-danger col-10" role="alert">
                                    ${SEARCH_ERROR.areaError}
                                </div>
                            </c:if>
                        </div>
                        <div class="form-group row">
                            <label class="text-center" style="padding-left: 50%">AND</label>
                        </div>
                        <div class="form-group row">
                            <div class="col-2">
                                <label class="align-middle">Date check in</label>
                            </div>
                            <div class="col-3">
                                <input class="form-control mr-sm-2" type="date" name="txtCheckIn" value="${param.txtCheckIn}">
                            </div>
                            <div class="col-3">
                                <label class="align-middle">Date check out</label>
                            </div>
                            <div class="col-3">
                                <input class="form-control mr-sm-2" type="date"  name="txtCheckOut" value="${param.txtCheckOut}">
                            </div>
                        </div>
                        <div class="form-group row" style="margin-left: 10%">
                            <c:if test="${not empty SEARCH_ERROR.checkInError}">
                                <div class="alert alert-danger col-10" role="alert">
                                    ${SEARCH_ERROR.checkInError}
                                </div>
                            </c:if>
                        </div>
                        <div class="form-group row" style="margin-left: 10%">
                            <c:if test="${not empty SEARCH_ERROR.checkOutError}">
                                <div class="alert alert-danger col-10" role="alert">
                                    ${SEARCH_ERROR.checkOutError}
                                </div>
                            </c:if>
                        </div>
                        <div class="form-group row" style="margin-left: 10%">
                            <c:if test="${not empty SEARCH_ERROR.invalidDateError}">
                                <div class="alert alert-danger col-10" role="alert">
                                    ${SEARCH_ERROR.invalidDateError}
                                </div>
                            </c:if>
                        </div>
                        <div class="form-group row">
                            <div class="col-2">
                                <label class="align-middle">Amount room</label>
                            </div>
                            <div class="col-10">
                                <input class="form-control mr-sm-2" name="txtAmount" value="${param.txtAmount}">
                            </div>
                        </div>
                        <div class="form-group row" style="margin-left: 10%">
                            <c:if test="${not empty SEARCH_ERROR.amountError}">
                                <div class="alert alert-danger col-10" role="alert">
                                    ${SEARCH_ERROR.amountError}
                                </div>
                            </c:if>
                        </div>
                        <div class="d-flex justify-content-center">
                            <input class="btn btn-outline-success my-2 my-sm-0" type="submit" value="Search"/>
                        </div>
                        <div class="form-group row" style="margin-left: 20%">
                            <c:if test="${not empty SEARCH_ERROR.emptyError}">
                                <div class="alert alert-danger col-10" role="alert">
                                    ${SEARCH_ERROR.emptyError}
                                </div>
                            </c:if>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <c:set var="error" value="${requestScope.ERROR_SEARCH}"/>
        <c:if test="${not empty param.txtName || not empty param.txtArea && not empty param.txtCheckIn && not empty param.txtCheckOut && not empty param.txtAmount}">
            <c:if test="${empty error}">
                <c:set var="map" value="${requestScope.SEARCH_RESULTS}"></c:set>
                <c:if test="${not empty map}">
                    <c:set var="roomTypeMap" value="${requestScope.ROOM_TYPE_MAP}"/>
                    <div class="card-columns">
                        <c:forEach var="entry" items="${map}">
                            <c:forEach var="room" items="${entry.value}">
                                <div class="card" style="width: 18rem;">
                                    <div class="card-body text-center">
                                        <h5 class="card-title">${entry.key.name}</h5>
                                        <p class="card-text">Area: ${entry.key.area}</p>
                                        <p class="card-text">Room Type: ${roomTypeMap.get(room.idRoomType)}</p>
                                        <p class="card-text">Price: ${room.price}</p>
                                        <p class="card-text">Remain: ${room.totalAmount}</p>
                                    </div>
                                    <c:if test="${NAME_ROLE == 'member'}">
                                        <form action="addToCartServletMember">
                                            <div class="card-footer col-12">
                                                <input type="hidden" name="txtName" value="${param.txtName}" />
                                                <input type="hidden" name="txtArea" value="${param.txtArea}">
                                                <input type="hidden" name="txtCheckIn" value="${param.txtCheckIn}">
                                                <input type="hidden"  name="txtCheckOut" value="${param.txtCheckOut}">
                                                <input type="hidden" name="txtAmount" value="${param.txtAmount}">

                                                <input type="hidden" name="txtHotelId" value="${entry.key.id}" />
                                                <input type="hidden" name="txtIdRoomType" value="${room.idRoomType}" />
                                                <input style="margin-left: 30%" type="submit" value="Add to cart" class="btn btn-outline-success"/>
                                                <c:if test="${not empty requestScope.ERROR_ADD_CART 
                                                              && param.txtHotelId eq entry.key.id 
                                                              && param.txtIdRoomType eq room.idRoomType}">
                                                      <div class="alert alert-danger col-10" role="alert">
                                                          ${requestScope.ERROR_ADD_CART}
                                                      </div>
                                                </c:if>
                                            </div>
                                        </form>

                                    </c:if>
                                </div> 
                            </c:forEach>
                        </c:forEach>
                    </div>
                </c:if>
                <c:if test="${empty map}">
                    Not Found!!
                </c:if>
            </c:if>
        </c:if>                  
    </body>
</html>
