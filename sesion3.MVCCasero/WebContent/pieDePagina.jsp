
<br />
<br />
${requestScope.mensajeParaElUsuario!=null && 
!requestScope.mensajeParaElUsuario.equals('editar') ? 
'<div class="alert alert-danger alert-dismissable">
<a href="#" class="close" data-dismiss="alert" aria-label="close">×</a>
<span class="glyphicon glyphicon-remove"></span>'
 += requestScope.mensajeParaElUsuario += '  </div>' : ''}

${requestScope.mensajeParaElUsuario.equals("editar") ? '<div class="alert alert-success alert-dismissable"><span class="glyphicon glyphicon-ok"></span> ' += 'Tarea modificada satisfactoriamente' += '  </div>' : ''}

${requestScope.mensajeVerde!=null? 
'<div class="alert alert-success alert-dismissable">
<a href="#" class="close" data-dismiss="alert" aria-label="close">×</a><span class="glyphicon glyphicon-ok"></span> ' 
+= requestScope.mensajeVerde += '  </div>' : ''}