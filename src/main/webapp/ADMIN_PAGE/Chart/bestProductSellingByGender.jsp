<%@page import="Models.Product"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.google.gson.Gson"%>
<%@ page import="com.google.gson.JsonObject"%>

<%
    Gson gsonObj = new Gson();
    List<Product> listProduct = (List<Product>) request.getAttribute("listProduct");

    float male = 0;
    float female = 0;
    float unisex = 0;

    Map<Object, Object> map = null;
    List<Map<Object, Object>> list = new ArrayList<>();

    for (int i = 0; i < listProduct.size(); i++) {
        if (listProduct.get(i).getGender().equals("Male") || listProduct.get(i).getGender().equals("Nam")) {
            male++;
        } else if (listProduct.get(i).getGender().equals("Female") || listProduct.get(i).getGender().equals("Nữ")) {
            female++;
        } else if (listProduct.get(i).getGender().equals("Unisex")) {
            unisex++;
        }

    }

    map = new HashMap<>();
    map.put("label", "Male");
    map.put("y", String.valueOf((male / listProduct.size() * 100)));
    list.add(map);
    map = new HashMap<>();
    map.put("label", "Female");
    map.put("y", String.valueOf((female / listProduct.size() * 100)));
    list.add(map);
    map = new HashMap<>();
    map.put("label", "Unisex");
    map.put("y", String.valueOf((unisex / listProduct.size() * 100)));
    list.add(map);

    String dataPoints = gsonObj.toJson(list);
%>

<!DOCTYPE HTML>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ"
            crossorigin="anonymous">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link
            href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond&family=Josefin+Sans:wght@200&family=Josefin+Slab&display=swap"
            rel="stylesheet">
        <link rel="stylesheet" href="/RESOURCES/admin/chart/css/style.css">
        <link rel="icon" href="/RESOURCES/images/icons/icon.webp">
        <link href="https://cdn.jsdelivr.net/gh/hung1001/font-awesome-pro-v6@44659d9/css/all.min.css" rel="stylesheet" type="text/css" />
        <title>Thống kê</title>
        <script type="text/javascript">
            window.onload = function () {

                var chart = new CanvasJS.Chart("chartContainer", {
                    theme: "light2", // "light1", "dark1", "dark2"
                    exportEnabled: true,
                    animationEnabled: true,
                    title: {
                        text: "Best Selling Product By Gender"
                    }, 
                    legend: {
                        verticalAlign: "bottom"
                    },
                    data: [{
                            type: "pie",
                            showInLegend: true,
                            indexLabel: "{y}%",
                            indexLabelPlacement: "inside",
                            legendText: "{label}: {y}%",
                            toolTipContent: "<b>{label}</b>: {y}%",
                            dataPoints: <%out.print(dataPoints);%>
                        }]
                });
                chart.render();
            };
        </script>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 nav">
                    <jsp:include page="/NAVBAR/AdminNavbar.jsp"></jsp:include>
                </div>
            </div>
            <div class="row d-flex justify-content-center chart">
                <div id="chartContainer" style="height: 370px; width: 100%;"></div>
            </div>
        </div>

        <script src="https://cdn.canvasjs.com/canvasjs.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
        crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" referrerpolicy="no-referrer"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.21.1/axios.min.js"></script>
        <!-- For changing tag -->
        <script src="/RESOURCES/admin/user/public/js/main.js"></script>
        <!--Jquery Validation-->
        <script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.5/dist/jquery.validate.js"></script>
    </body>
</html> 