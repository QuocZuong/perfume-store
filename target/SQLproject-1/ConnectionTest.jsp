<%-- Document : index Created on : Jun 1, 2023, 7:55:22 AM Author : Long --%>

<%@page import="java.sql.Statement" %>
<%@page import="java.sql.ResultSet" %>
<%@page import="java.sql.DriverManager" %>
<%@page import="java.sql.Connection" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
<!--                <script src="https://code.jquery.com/jquery-3.5.1.js"></script>
<script src="https://cdn.datatables.net/1.13.4/js/jquery.dataTables.min.js"></script>
<script>
$(document).ready(function () {
$('#dataBase').DataTable();
});
</script>
<link href="https://cdn.datatables.net/1.13.4/css/jquery.dataTables.min.css" rel="bsStyle"/>-->

        <script src="https://code.jquery.com/jquery-3.5.1.js"></script>
        <script src="https://cdn.datatables.net/1.13.4/js/jquery.dataTables.min.js"></script>
        <script>
            $(document).ready(function () {
                $('#displayData').DataTable();
            });
        </script>
        <link rel="stylesheet"
              href="https://cdn.datatables.net/1.13.4/css/jquery.dataTables.min.css" />

    </head>

    <body>

        <table border="1" id="displayData">
            <thead>
                <tr>
                </tr>
            </thead>
            <tbody>

            </tbody>
        </table>
    </body>

</html>