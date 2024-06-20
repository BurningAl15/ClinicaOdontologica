window.addEventListener('load', function () {
    (function(){

      //con fetch invocamos a la API de peliculas con el método GET
      //nos devolverá un JSON con una colección de peliculas
      const url = '/turnos';
      const settings = {
        method: 'GET'
      }

      fetch(url,settings)
      .then(response => response.json())
      .then(data => {
       console.log('Received data:', data);
      //recorremos la colección de peliculas del JSON
         for(let turno of data){
            //por cada pelicula armaremos una fila de la tabla
            //cada fila tendrá un id que luego nos permitirá borrar la fila si eliminamos la pelicula
            var table = document.getElementById("turnoTable");
            var turnoRow =table.insertRow();
            let tr_id = 'tr_' + turno.id;
            turnoRow.id = tr_id;

            //armamos cada columna de la fila
            //como primer columna pondremos el boton modificar
            //luego los datos de la pelicula
            //como ultima columna el boton eliminar
            turnoRow.innerHTML = '<td>' + turno.id + '</td>' +
                    '<td class=\"td_paciente_nombre\">' + turno.paciente.nombre.toUpperCase() + '</td>' +
                    '<td class=\"td_paciente_apellido\">' + turno.paciente.apellido.toUpperCase() + '</td>' +
                    '<td class=\"td_paciente_cedula\">' + turno.paciente.cedula.toUpperCase() + '</td>' +
                    '<td class=\"td_odontologo_nombre\">' + turno.odontologo.nombre.toUpperCase() + '</td>' +
                    '<td class=\"td_odontologo_apellido\">' + turno.odontologo.apellido.toUpperCase() + '</td>' +
                    '<td class=\"td_odontologo_matricula\">' + turno.odontologo.matricula.toUpperCase() + '</td>' +
                    '<td class=\"td_fecha\">' + turno.fecha.toUpperCase() + '</td>';

        };

    })
    })

    (function(){
      let pathname = window.location.pathname;
      if (pathname == "/get_turnos_user.html") {
          document.querySelector(".nav .nav-item a:last").addClass("active");
      }
    })


    })

