<jsp:include page="cabecera.jsp"/>

<pre>
1.0.5 ()
------------------------------------------------------------------------------
- B�squeda para todo los objetos.
- Cacheo local de objetos pesados, preparar browser bd.
- Eliminaci�n del combo del id de las p�ginas, sustituirlo por b�squeda.
- Generalizaci�n de la subida de ficheros.
- Nuevo objeto: Riesgo.[peligro,lugar,probabilidad,fuente,descripcion]
- Nuevo objeto: Agua.
- Objetos Secundarios, agrupaci�n de objetos de importancia secundaria sin geolocalizaci�n: 
  Ejemplo Droga,Vacuna,Medicamento
- Clonar en todos los objetos
- Carga calidad Aguas
- Carga c�digos  pa�ses ISO 3166
- Marca para cada objeto de la �ltima modificaci�n.
- Icono Heimdallar.
- Visualizaci�n bar code (main page)
- Tipos de sitio en BD.
- A�adir campos longitud latitud en sitio.
- Sitio: Crear auto completable para lugares en sitio apartir de longitud y latitud. 
------------------------------------------------------------------------------

1.0.4 (30-05-2016)
------------------------------------------------------------------------------
- Corrige bugs permisos
- Nuevo modelo para los datos asociados
- Nuevo interfaz para los datos asociados
- Nuevo visualizaci�n de red para los datos de un objeto
- Clonar alertas
------------------------------------------------------------------------------



1.0.3 (15-05-2016)
------------------------------------------------------------------------------
- Tags.Se crea alias corto para metalenguaje.
- Mapa para alertas
- Iconos para tipos de sitios
- Revisi�n algoritmo de dispersi�n
- Reducir tama�o del punto azul 
- Se a�ade en Sitios:: Carceles,Campos de Refugiados
- Auditor�a de operaciones
- Autenticaci�n (nuevo login) y autorizaci�n sobre operaciones.
- Permisos
- Componente html input date

-----------------------------------------------------------------------------


1.0.2 (30-04-2016)
------------------------------------------------------------------------------
- Nuevo nivel de alerta: Informativo (nuevo icono blue en widget)
- Se a�ade en Sitios:: Cueva,Pico,Mercado,Playa
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
1-Cargar datos de c�maras hiperb�ricas.(fuente http://www.e-med.co.uk/hyperbaric_locator/regions.php)
2-Cargar datos de rutas a�reas.
3-Integraci�n con Elastic Search
3.1 Authentication: Shield plugin
3.2 Create Interface app to elastic
3.2.1 Borrar �ndices
3.2.2 Crear �ndices
3.2.3 Console to send request
3.2.3 Mark in BD if object exists in elastic
3.3 Kibana
4.TOKENS: generador de tokens.Permite generar tokens de acceso a la aplicaci�n.Granularidad Objeto.
   CU1: Acceso a las alertas de Gripe en Espa�a del �ltimo a�o.Periodo: Durante la pr�xima semana.
   CU2: Acceso a las alertas de Gripe en Espa�a del �ltimo a�o.Periodo: Durante la pr�xima semana.Limitar a 1000 accesos hora.
5.Auditor�a de accesos-tokens
- Datos: pemitir datos privados para un usuario:
- TK (to come).Idea colaborativa: Ejemplo publicar un TK: El agente infeccioso TK puede afectar al pa�s TK por los evntos detectados TK* 
- Tags ampliar funcionalidad.ie: asociar icono a un dato
- Tipos de sitio en BD
- layout: footer
- widget: cloud tag :: http://bl.ocks.org/ericcoopey/6382449
- bug: widget map se repiten algunos puntos.Ocurre en el mapa general.ej Mauritania
- bug: revisar cacheo para que se active con todos los par�metros.ejemplo caducado.
- Configurable Alarms
- Nuevo objeto Art�culo.CU: Se crea un art�culo,nombre,descripci�n, subject,url.Se encola para indexarlo en ES.  
- Nuevo objeto Consulta
- Nuevo objeto Medicamento
- Nuevo objeto Riesgo
- kafka http://kafka.apache.org/
- API REST
- Objetos Secundarios, agrupaci�n de objetos de importancia secundaria sin geolocalizaci�n: Ejemplo Droga,Vacuna,Medicamento
-------------------------------------------------------------------------------
</pre>