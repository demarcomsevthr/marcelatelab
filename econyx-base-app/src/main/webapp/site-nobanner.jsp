<!doctype html>

<%
  String pageName = (String)request.getAttribute("pageName");
%>

<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title><%=pageName%></title>
    <script type="text/javascript">
      var startupModule = "site";
    </script>
    <script type="text/javascript" language="javascript" src="/main/main.nocache.js"></script>
    <link rel="stylesheet" href="/styles/app-custom.css"></link>
  </head>
  <body>
    <iframe src="javascript:''" id="__gwt_historyFrame" tabIndex='-1' style="position:absolute;width:0;height:0;border:0"></iframe>
  </body>
</html>
