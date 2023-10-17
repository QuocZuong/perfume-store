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
    List<Map<Object, Object>> list = new ArrayList<Map<Object, Object>>();

    for (int i = 0; i < listProduct.size(); i++) {
        if (listProduct.get(i).getGender().equals("Male")) {
            male++;
        } else if (listProduct.get(i).getGender().equals("Female")) {
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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <style>
            body{
                height: 100vh;
                display: flex;
                justify-content: center;
                align-items: center;
            }
        </style>
        <script type="text/javascript">
            window.onload = function () {

                var chart = new CanvasJS.Chart("chartContainer", {
                    theme: "light2", // "light1", "dark1", "dark2"
                    exportEnabled: true,
                    animationEnabled: true,
                    title: {
                        text: "Best Selling Product"
                    },
                    data: [{
                            type: "pie",
                            toolTipContent: "<b>{label}</b>: {y}%",
                            indexLabelFontSize: 16,
                            indexLabel: "{label} - {y}%",
                            dataPoints: <%out.print(dataPoints);%>
                        }]
                });
                chart.render();

            }
        </script>
    </head>
    <body>
        <div id="chartContainer" style="height: 370px; width: 100%;"></div>
        <script src="https://cdn.canvasjs.com/canvasjs.min.js"></script>
    </body>
</html> 