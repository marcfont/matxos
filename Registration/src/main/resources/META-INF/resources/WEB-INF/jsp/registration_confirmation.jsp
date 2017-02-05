<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ca">
<head>
    <meta charset="UTF-8">
    <title>Registration - ${title} </title>
    <jsp:include page="bootstrap.jsp"/>
</head>
<body>

<div class="container-fluid">
    <div class="row" style="margin-bottom: 40px;">
        <div class="col-md-12" style="background-color: #FF5640; font-size: xx-large; color: azure; padding: 6%;">
            ${title}
        </div>
    </div>

    <div class="row">
        <div class="col-md-8 col-md-offset-2">

            <c:choose>
                <c:when test="${waiting.equals('waiting')}">
                    <form action="/registration/race/${race}/waiting-confirmation" method="POST">
                </c:when>
                <c:otherwise>
                    <form action="/payment/race/${race}" method="POST">
                </c:otherwise>
            </c:choose>

                <input type="hidden" name="id" value="${id}">
                <div class="form-group">
                    <span>Confirma que les dades que has introduit son correctes. En cas contari torna enrere i rectifica-les</span>
                </div>
                <div class="form-group">
                    <label>Nom:</label>
                    <span>${registration.name}</span>
                </div>
                <div class="form-group">
                    <label>Primer cognom:</label>
                    <span>${registration.surname1}</span>
                </div>
                <div class="form-group">
                    <label>Segon cognom:</label>
                    <span>${registration.surname2}</span>
                </div>
                <div class="form-group">
                    <label>Sexe:</label>
                    <span>${registration.gender}</span>
                </div>
                <div class="form-group">
                    <label>Nom dorsal:</label>
                    <span>${registration.bibname}</span>
                </div>
                <div class="form-group">
                    <label>Data naixement:</label>
                    <span>${registration.birthday}</span>
                </div>
                <div class="form-group">
                    <label>Talla samarreta:</label>
                    <span>${registration.size}</span>
                </div>
                <div class="form-group">
                    <label>Email:</label>
                    <span>${registration.email}</span>
                </div>
                <div class="form-group">
                    <label>DNI/NIE:</label>
                    <span>${registration.dni}</span>
                </div>
                <div class="form-group">
                    <label>Telf:</label>
                    <span>${registration.telf}</span>
                </div>
                <div class="form-group">
                    <label>Poblaci&oacute;:</label>
                    <span>${registration.town}</span>
                </div>
                <div class="form-group">
                    <label>Club:</label>
                    <span>${registration.club}</span>
                </div>
                <div class="form-group">
                    <label>N&uacute;mero federat FEEC</label>
                    <span>${registration.feec}</span>
                </div>
                <div class="form-group">
                    <label>Nom i Telf. cas emer&egrave;ngcia:</label>
                    <span>${registration.telfemer}</span>
                </div>

                <div class="form-group">
                    <c:choose>
                        <c:when test="${waiting.equals('waiting')}">
                            <button type="submit" class="btn btn-primary btn-lg btn-block">Confirma petici&oacute; llista espera</button>
                        </c:when>
                        <c:otherwise>
                            <button type="submit" class="btn btn-primary btn-lg btn-block">Iniciar pagament</button>
                        </c:otherwise>
                    </c:choose>

                </div>

            </form>

        </div>
    </div>
</div>
</body>
</html>