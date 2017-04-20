<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ca">
<head>
    <meta charset="UTF-8">
    <title>Ranking - ${runner} - ${title} </title>
    <jsp:include page="bootstrap.jsp"/>
</head>
<body>
<noscript><h2 style="color: #ff0000">Seems your browser doesn't support Javascript! Websocket relies on Javascript being
    enabled. Please enable
    Javascript and reload this page!</h2></noscript>
<script>

    var bib = ${bib};

    var socket = new SockJS('/ws-ranking');
    var stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/ranking', function (read) {
            newRead(read);
        });
    });

    $(window).on('unload', function(){
        if (stompClient != null) {
            stompClient.disconnect();
        }
        console.log("Disconnected");
    });

    function newRead(read) {
        var r = JSON.parse(read.body);

        if (r.bib == bib) {
            var tr = $("<tr>");
            tr.append("<td>" + r.control + "</td>");
            tr.append("<td>" + r.time + "</td>");
            tr.append("</tr>");

            $('#ranking-body').append(tr);
        }
    }
</script>

<div class="container-fluid">
    <div class="row" style="margin-bottom: 40px; background-color: #FF5640; font-size: xx-large; color: azure;">
        <div class="col-md-8 col-md-offset-2" style="padding: 6%;">
            ${title}<br>
            <b>${runner}</b><br>
            Dorsal: <b>${bib}</b><br>
            Itinerari: ${route}
        </div>
        <div class="pull-right" style="font-size: x-large; padding: 10px;"><a style="color: white" href="/ranking/race/${race}/ranking">Torna Ranking</a></div>
    </div>

    <div class="row">
        <div class="col-md-8 col-md-offset-2">

            <table class="table">
                <thead>
                    <th><b>Control</b></th>
                    <th><b>Temps</b></th>
                </thead>
                <tbody id="ranking-body">
                    <c:forEach items="${rankings}" var="r">
                        <tr>
                            <td>${r.control}</td>
                            <c:choose>
                                <c:when test="${r.control.equals('Sortida')}">
                                    <td>00h 00m 00s</td>
                                </c:when>
                                <c:otherwise>
                                    <td>${r.time}</td>
                                </c:otherwise>
                            </c:choose>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>