
<br />
<br />
${requestScope.mensajeParaElUsuario!=null && 
!requestScope.mensajeParaElUsuario.equals("registro") &&
!requestScope.mensajeParaElUsuario.equals('tarea')  && 
!requestScope.mensajeParaElUsuario.equals('editar') ? 
'<div class="alert alert-danger alert-dismissable">' += requestScope.mensajeParaElUsuario += '  </div>' : ''}

${requestScope.mensajeParaElUsuario.equals("tarea") ? '<div class="alert alert-success alert-dismissable">' += '' += '  </div>' : ''}
${requestScope.mensajeParaElUsuario.equals("editar") ? '<div class="alert alert-success alert-dismissable">' += 'Tarea modificada satisfactoriamente' += '  </div>' : ''}

${requestScope.mensajeVerde!=null? 
'<div class="alert alert-success alert-dismissable">' += requestScope.mensajeVerde += '  </div>' : ''}