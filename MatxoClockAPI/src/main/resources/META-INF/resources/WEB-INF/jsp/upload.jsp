<html>
    <head>
        <title>Import reads</title>
    </head>
    <body>
        <form action="/api/race/${race}/control/${control}/import" method="POST" enctype="multipart/form-data">
            <input type="text" name="psw" value="">
            <input type="file" name="file" />
            <button type="submit">Go!</button>
        </form>
    </body>

</html>