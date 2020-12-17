<%-- 
    Document   : viewCart
    Created on : Oct 31, 2020, 9:14:51 PM
    Author     : Treater
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Cart</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">    </head>
    <body>
        <c:set var="NAME_ROLE" value="${requestScope.NAME_ROLE}"/>
        <c:if test="${NAME_ROLE != 'member'}">
            <c:redirect url="login"/>
        </c:if>
        <h1 class="text-center">Your cart</h1>
        <c:set var="DTO" value="${sessionScope.DTO}"/>
        <c:set var="cart" value="${sessionScope.CART}"/>
        <c:set var="items" value="${cart.getItems()}"/>
        <c:set var="mapRoom" value="${requestScope.MAP_ROOM_TYPE}"/>
        <c:set var="mapHotel" value="${requestScope.MAP_HOTEL_NAME}"/>
        <c:set var="time" value="${sessionScope.PREVIOUS_SEARCH}"/>
        <c:set var="error" value="${requestScope.ERROR_CHECK_OUT}"></c:set>

        <c:if test="${not empty items}">
            <table class="table table-sm table-bordered">
                <thead>
                    <tr>
                        <th scope="col">No.</th>
                        <th scope="col">Hotel</th>
                        <th scope="col">Type Room</th>
                        <th scope="col">Quantity</th>
                        <th scope="col">Price</th>
                        <th scope="col">Total</th>
                        <th scope="col">Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${items.keySet()}" varStatus="counter">
                        <c:set var="room" value="${items.get(item)}"/>
                        <c:set var="splitedKey" value="${fn:split(item, '_')}" />  
                    <form action="deleteItemInCartMember">
                        <div class="modal fade" id="confirmModal${counter.index}" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLabel">Are you sure want to delete this item?</h5> 
                                        <input type="hidden" name="txtId" value="${item}" />
                                        <input type="hidden" name="txtCheckIn" value="${param.txtCheckIn}" />
                                        <input type="hidden" name="txtCheckOut" value="${param.txtCheckOut}" />
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                        <input type="submit" value="Delete item" class="btn btn-danger" name="btAction"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form> 
                    <tr>
                        <td scope="row">${counter.count}</td>
                        <td>${mapHotel.get(splitedKey[0])}</td>
                        <td>${mapRoom.get(splitedKey[1])}</td>
                        <td>
                            <form action="modifyQuantityMember">
                                <input type="hidden" name="txtTime" value="${time}" />
                                <input type="hidden" name="txtId" value="${item}" />
                                <input type="submit" value="-" class="btn btn-primary"/>
                                <input type="hidden" name="txtIsIncrease" value="no" />
                            </form>
                            ${room.totalAmount}
                            <form action="modifyQuantityMember">
                                <input type="hidden" name="txtTime" value="${time}" />
                                <input type="hidden" name="txtId" value="${item}" />
                                <input type="hidden" name="txtIsIncrease" value="yes" />
                                <input type="submit" value="+" class="btn btn-primary"/>
                            </form>
                            <c:if test="${not empty requestScope.ERROR_OUT_STOCK && item eq param.txtId}">
                                <div class="alert alert-danger col-12" role="alert">
                                    ${requestScope.ERROR_OUT_STOCK}
                                </div>
                            </c:if>
                        </td>
                        <td>$${room.price}</td>
                        <td>$${room.price * room.totalAmount}</td>
                        <td>
                            <button type="button" data-toggle="modal" data-target="#confirmModal${counter.index}" class="btn btn-danger col-2" style="margin-left: 100px">Delete Room</button>
                        </td>
                    </tr>                        
                </c:forEach>
                <tr>
                    <td scope="row" colspan="6">Total Price: x${sessionScope.DIFFERENCE_DATE} day(s) </td>
                    <td colspan="2">$${cart.totalPrice() * sessionScope.DIFFERENCE_DATE}</td>
                </tr>
                <tr>
                    <td scope="row" colspan="6">                                
                        <a href="searchPage">Go to shopping again</a>
                    </td>
                    <td colspan="2">
                        <button type="button" data-toggle="modal" data-target="#checkOutForm" class="btn btn-primary" >Check out</button>
                    </td>
                </tr>
            </tbody>
        </table>
        <c:if test="${not empty requestScope.CHANGE_DATE}">
            <div class="alert alert-success col-12" role="alert">
                ${requestScope.CHANGE_DATE}
            </div>
        </c:if>
    </c:if>
    <c:if test="${empty items}">
        <p class="text-center">Your cart doesn't exist</p>
        <div class="text-center">
            <a href="searchPage">Back to store</a>
        </div>
    </c:if>
    <c:if test="${not empty error.outOfStockError}">
        <div class="alert alert-danger col-12 text-center" role="alert">
            ${error.outOfStockError}
        </div>
    </c:if>
    <div class="modal fade" id="checkOutForm" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Check out form</h5> 
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <form action="loadInfoMapMember">
                    <div class="modal-body">
                        <div class="form-group row">
                            <label class="col-3 col-form-label">Recipient's Name: </label>
                            <div class="col-5">
                                <input type="text" name="txtRecipientName" 
                                       value="${DTO.name}"
                                       class="form-control"/>
                            </div>
                        </div>
                        <c:if test="${not empty error.nameError}">
                            <div class="form-group row">
                                <label class="col-3 col-form-label"></label>
                                <div class="col-5">
                                    <div class="alert alert-danger" role="alert">
                                        ${error.nameError}
                                    </div>
                                </div>
                            </div>
                        </c:if>
                        <c:set var="splitedTime" value="${fn:split(time, '_')}" />  

                        <div class="form-group row">
                            <label class="col-3 col-form-label">Check In:  </label>
                            <div class="col-5">
                                <input type="date" name="txtCheckIn" 
                                       class="form-control"
                                       value="${splitedTime[0]}"/>
                            </div>
                        </div>    
                        <c:if test="${not empty error.checkInDateError}">
                            <div class="form-group row">
                                <label class="col-3 col-form-label"></label>
                                <div class="col-5">
                                    <div class="alert alert-danger" role="alert">
                                        ${error.checkInDateError}
                                    </div>
                                </div>
                            </div>
                        </c:if>
                        <div class="form-group row">
                            <label class="col-3 col-form-label">Check Out: </label>
                            <div class="col-5">
                                <input type="date" name="txtCheckOut" 
                                       value="${splitedTime[1]}" class="form-control"/>
                            </div>
                        </div>
                        <c:if test="${not empty error.checkOutDateError}">
                            <div class="form-group row">
                                <label class="col-3 col-form-label"></label>
                                <div class="col-5">
                                    <div class="alert alert-danger" role="alert">
                                        ${error.checkOutDateError}
                                    </div>
                                </div>
                            </div>
                        </c:if>
                        <div class="form-group row">
                            <label class="col-3 col-form-label">Phone: </label>
                            <div class="col-5">
                                <input type="text" name="txtPhone" 
                                       value="${DTO.phone}" class="form-control"/>
                            </div>
                        </div>
                        <c:if test="${not empty error.phoneError}">
                            <div class="form-group row">
                                <label class="col-3 col-form-label"></label>
                                <div class="col-5">
                                    <div class="alert alert-danger" role="alert">
                                        ${error.phoneError}
                                    </div>
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${not empty error.dateError}">
                            <div class="form-group row">
                                <label class="col-3 col-form-label"></label>
                                <div class="col-5">
                                    <div class="alert alert-danger" role="alert">
                                        ${error.dateError}
                                    </div>
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${not empty requestScope.COUPON}">
                            <input type="hidden" name="txtValidCoupon" value="${requestScope.COUPON}" />
                        </c:if>
                        <input type="hidden" name="txtUserId" value="${DTO.id}" />
                        <input type="hidden" name="txtValidateCheckOut" value="yes" />
                        <div class="modal fade" id="confirmModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLabel">Confirm check out</h5> 
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-footer">
                                        <form action="validateCheckOut">
                                            <input type="submit" value="Check out" class="btn btn-success" name="btAction"/>
                                        </form>
                                        <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                    <button type="button" data-toggle="modal" data-target="#confirmModal" class="btn btn-primary" >Check out</button>
                </div>
            </div>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</body>
</html>
