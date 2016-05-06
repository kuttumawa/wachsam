<jsp:include page="cabecera.jsp"/>

<pre>
1.0.3 ()
------------------------------------------------------------------------------
- Tags.Se crea alias corto para metalenguaje.
- Mapa para alertas
- Tipos de sitio en BD
- layout: footer
- Iconos para tipos de sitios
- widget: cloud tag :: http://bl.ocks.org/ericcoopey/6382449
- Revisión algoritmo de dispersión
- Tags ampliar funcionalidad.ie: asociar icono a un dato
- Reducir tamaño del punto azul 
- bug: widget map se repiten algunos puntos.Ocurre en el mapa general.ej Mauritania
- bug: revisar cacheo para que se active con todos los parámetros.ejemplo caducado.
-----------------------------------------------------------------------------


1.0.2 (30-04-2016)
------------------------------------------------------------------------------
- Nuevo nivel de alerta: Informativo (nuevo icono blue en widget)
- Se añade en Sitios:: Cueva,Pico,Mercado,Playa
- Mejora visual
- Mapa para Sitios
-----------------------------------------------------------------------------



1.0.1-BETA-1 (20-04-2016)
-------------------------------------------------------------------------------
-Implementa Metalenguaje para el texto en Alertas y Sitio
-FileUpload.Permite carga masiva de objetos por fichero. (Solo para sitios por ahora)
TODO
-Modelo de datos: Data: value (String 500)
-Carga masiva : no carga lugar?

-------------------------------------------------------------------------------

FUTURO
-------------------------------------------------------------------------------
1-Cargar datos de cámaras hiperbáricas.(fuente http://www.e-med.co.uk/hyperbaric_locator/regions.php)
2-Cargar datos de rutas aéreas.
3-Integración con Elastic Search
3.1 Authentication: Shield plugin
3.2 Create Interface app to elastic
3.2.1 Borrar índices
3.2.2 Crear índices
3.2.3 Console to send request
3.2.3 Mark in BD if object exists in elastic
3.3 Kibana
4.TOKENS: generador de tokens.Permite generar tokens de acceso a la aplicación.Granularidad Objeto.
   CU1: Acceso a las alertas de Gripe en España del último año.Periodo: Durante la próxima semana.
   CU2: Acceso a las alertas de Gripe en España del último año.Periodo: Durante la próxima semana.Limitar a 1000 accesos hora.
5.Auditoría de accesos-tokens
- Datos privados para un usuario.

-------------------------------------------------------------------------------
</pre>