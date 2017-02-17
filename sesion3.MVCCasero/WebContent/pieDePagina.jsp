
<br />
<br />
${requestScope.mensajeParaElUsuario!=null && 
!requestScope.mensajeParaElUsuario.equals('editar') ? 
'<div class="alert alert-danger alert-dismissable">
<a href="#" class="close" data-dismiss="alert" aria-label="close">×</a>'
 += requestScope.mensajeParaElUsuario += '  </div>' : ''}

${requestScope.mensajeParaElUsuario.equals("editar") ? '<div class="alert alert-success alert-dismissable">' += 'Tarea modificada satisfactoriamente' += '  </div>' : ''}

${requestScope.mensajeVerde!=null? 
'<div class="alert alert-success alert-dismissable">
<a href="#" class="close" data-dismiss="alert" aria-label="close">×</a>' 
+= requestScope.mensajeVerde += '  </div>' : ''}