<%-- 
    Document   : loadBookedRoomListMember
    Created on : Nov 3, 2020, 1:26:30 PM
    Author     : Treater
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Feed back Page</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    </head>
    <body>
        <c:set var="NAME_ROLE" value="${requestScope.NAME_ROLE}"/>
        <c:set var="roomTypeMap" value="${requestScope.ROOM_TYPE_MAP}"/>
        <c:set var="hotelNameMap" value="${requestScope.HOTEL_NAME_MAP}"/>
        <c:set var="listInvoice" value="${requestScope.LIST_INVOICE}"/>
        <c:if test="${NAME_ROLE != 'member'}">
            <c:redirect url="login"/>
        </c:if>
        <table class="table table-hover">
            <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Hotel Name</th>
                    <th scope="col">Room type</th>
                    <th scope="col">Rating</th>
                    <th scope="col">Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${listInvoice}" var="invoice" varStatus="counter">
                <form action="feedBack">
                    <tr>
                        <th scope="row">${counter.count}</th>
                        <td>${hotelNameMap.get(invoice.idHotel)}</td>
                        <td>${roomTypeMap.get(invoice.idRoomType)}</td> 
                        <td>
                            <select class="form-control">
                                <c:forEach begin="0" end="10" step="1" var="rate">
                                    <option>${rate}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>
                            <input type="hidden" name="txtInvoiceId" value="${invoice.invoiceId}" />
                            <input type="hidden" name="txtHotelId" value="${invoice.idHotel}"/>
                            <input type="hidden" name="txtRoomTypeId" value="${invoice.idRoomType}"/>
                            <input type="submit" value="Feedback" class="btn btn-primary"/>
                        </td>
                    </tr>
                </form>

            </c:forEach>
        </tbody>
    </table>
</body>
</html>
