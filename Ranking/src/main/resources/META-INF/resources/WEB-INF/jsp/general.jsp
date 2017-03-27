<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ca">
<head>
    <meta charset="UTF-8">
    <title>Ranking - ${title} </title>
    <jsp:include page="bootstrap.jsp"/>
</head>
<body>
<noscript><h2 style="color: #ff0000">Seems your browser doesn't support Javascript! Websocket relies on Javascript being
    enabled. Please enable
    Javascript and reload this page!</h2></noscript>
<script>

    var control = "${controltitle}";
    var bibFil = "${param.bib}";
    var nameFil = "${param.name}";
    var routeFil = "${param.route}";
    var genderFil = "${param.gender}";

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
        console.log(r);

        var filtered = false;

        if (bibFil.length != 0 && r.bib != bibFil){
            filtered = true;
        }

        var regex = new RegExp("[a-z ]*"+ nameFil.toLowerCase()+"[a-z ]*");
        if (!filtered && nameFil.length != 0 && !regex.test(r.name.toLowerCase())){
            filtered = true;
        }

        if (!filtered && routeFil.length != 0 && r.route.toUpperCase() != routeFil.toUpperCase()){
            filtered = true;
        }

        if (!filtered && genderFil.length != 0 && r.gender.toUpperCase() != genderFil.toUpperCase()){
            filtered = true;
        }

        if (!filtered){

            //remove previous one
            $('#bib_' + r.bib).remove();

            var rows = $('#ranking-body tr');
            var added = false;

            var tr = $('<tr id="bib_' + r.bib + '" data-control="'+ r.controlWeight +'" data-time="'+ r.timeMs +'" >');
            tr.append("<td>" + r.bib + "</td>");
            tr.append('<td><a href="/ranking/race/${race}/bibs/' + r.bib + '/ranking">' + r.name + "</a></td>");
            tr.append("<td>" + r.control + "</td>");
            tr.append("<td>" + r.time + "</td>");
            tr.append("<td>" + r.route + "</td>");
            tr.append("</tr>");

            $.each(rows, function( index, row ) {

                if (
                        ($(row).data('control') < r.controlWeight)
                        ||
                        ( ($(row).data('control') == r.controlWeight) && $(row).data('time') < r.timeMs )
                ) {
                    tr.insertBefore(row) ;
                    added = true;
                }
            });

            if (!added){
                $('#ranking-body').append(tr);
            }
        }

    }
</script>
<div class="container-fluid">
    <div class="row" style="margin-bottom: 40px; background-color: #FF5640; font-size: xx-large; color: azure;">
        <div class="col-md-8 col-md-offset-2" style="padding: 6%;">
            ${title}
        </div>
    </div>
    <div class="row" style="margin-bottom: 10px;">
        <div class="col-md-1"><a href="/ranking/race/${race}/ranking"><b>GENERAL</b></a></div><br>
        <c:forEach items="${controls}" var="c">
            <div class="col-md-1"><a href="/ranking/race/${race}/control/${c.id}/ranking"><b>${c.name}</b></a></div>
        </c:forEach>
    </div>
    <div class="row ">
        <div class="col-md-8 col-md-offset-2">
            <div class="panel panel-default ">
                <div class="panel-body">
                    <form action="/ranking/race/${race}/ranking" class="form-inline">
                        <div class="form-group">
                            <div class="form-group">
                                <input type="text"
                                       name="bib"
                                       size="5"
                                       class="form-control"
                                       placeholder="Dorsal"
                                       value="${param.bib}">
                            </div>
                            <div class="form-group">
                                <input type="text"
                                       name="name"
                                       placeholder="Nom/Cognoms"
                                       class="form-control"
                                       value="${param.name}">
                            </div>
                            <div class="form-group">
                                <select class="form-control" name="route" >
                                    <option value="">Totes</option>
                                    <c:forEach items="${routes}" var="r">
                                        <option value="${r.name}" ${param.route.equals(r.name) ? ' selected ' : ''} >${r.name}</option>
                                    </c:forEach>

                                </select>
                            </div>
                            <div class="form-group">
                                <select class="form-control" name="gender">
                                    <option value="">General</option>
                                    <option value="M" ${param.gender.equals('M') ? ' selected ' : ''}>Home</option>
                                    <option value="F" ${param.gender.equals('F') ? ' selected ' : ''}>Dona</option>
                                </select>
                            </div>
                            <input type="submit" class="btn btn-primary" value="Cerca"></p>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-8 col-md-offset-2">

            <table class="table">
                <thead>
                    <th><b>Dorsal</b></th>
                    <th><b>Cognoms, Nom</b></th>
                    <th><b>&Uacute;ltim control</b></th>
                    <th><b>Temps</b></th>
                    <th><b>Ruta prevista</b></th>
                </thead>
                <tbody id="ranking-body">
                    <c:forEach items="${rankings}" var="r">
                        <tr id="bib_${r.bib}" data-control="${r.controlWeight}" data-time="${r.timeMs}">
                            <td>${r.bib}</td>
                            <td><a href="/ranking/race/${race}/bibs/${r.bib}/ranking">${r.name}</a></td>
                            <td>${r.control}</td>
                            <c:choose>
                                <c:when test="${r.control.equals('Sortida')}">
                                    <td>00h 00m 00s</td>
                                </c:when>
                                <c:otherwise>
                                    <td>${r.time}</td>
                                </c:otherwise>
                            </c:choose>
                            <td>${r.route}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>