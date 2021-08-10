<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021/8/2
  Time: 23:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%--引入jquery库--%>
    <script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
</head>
<body>
    <select name="" id="sheng">
        <option value="1">河南省</option>
    </select>
    <select name="" id="city">
        <option value="1">郑州市</option>
    </select>
    <select id="qu">
        <%--<option value="1">管城回族区</option>--%>
    </select>

    <script>
        $(function () {
            //1.页面一加载  ajax 访问后台
            $.ajax({
                url:'/AllCityServlet',
                type:'GET',//请求类型   增加是post
                data:'',
                dataType:'JSON',//返回的是什么
                success:function (res) {//请求成功是success
                    console.log(res);
                    $.each(res.citylist,function (index, item) {
                        //console.log(index)

                        $("#sheng").empty();//先清空
                        $("#sheng").append(" <option value="+item.p+">"+item.p+"</option> ")
                        $.each(item.c,function (index,item) {
                            $("#city").empty();
                            $("#city").append(" <option value="+item.n+">"+item.n+"</option>")
                            $.each(item.a , function (index,item) {
                                console.log(item)
                                console.log($(this))
                                //  $("#qu").empty();
                                $("#qu").append(" <option value="+item.s+">"+item.s+"</option>")
                            });
                        });
                    });
                }
            });
        });
    </script>
</body>
</html>
