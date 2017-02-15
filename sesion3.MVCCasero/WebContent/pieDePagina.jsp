
<br />
<br />
${requestScope.mensajeParaElUsuario!=null && 
!requestScope.mensajeParaElUsuario.equals("registro") &&
!requestScope.mensajeParaElUsuario.equals('tarea')  ? 
'<div class="alert alert-danger alert-dismissable">' += requestScope.mensajeParaElUsuario += '  </div>' : ''}

${requestScope.mensajeParaElUsuario.equals("registro") ? '<div class="alert alert-success alert-dismissable">' += 'Usuario registrado correctamente' += '  </div>' : ''}
${requestScope.mensajeParaElUsuario.equals("tarea") ? '<div class="alert alert-success alert-dismissable">' += 'Tarea creada satisfactoriamente' += '  </div>' : ''}
