<jsp:include page="cabecera.jsp"/>

<pre>
1.1.0 ()
------------------------------------------------------------------------------
- B�squeda para todo los objetos.
- Cacheo local de objetos pesados, preparar browser bd.
- Eliminaci�n del combo del id de las p�ginas, sustituirlo por b�squeda.
- Generalizaci�n de la subida de ficheros.
- Definici�n de metadatos para subida de ficheros:
    {objeto"datumid":"nombreTag","columnas":["nombre","descripcion","lugar"],"data":[]}
    
    
- Nuevo objeto: Riesgo.[peligro,lugar,probabilidad,fuente,descripcion]
- Nuevo objeto: Agua.
- Objetos Secundarios, agrupaci�n de objetos de importancia secundaria sin geolocalizaci�n: 
- Ejemplo Droga,Vacuna,Medicamento
- Clonar en todos los objetos
- Carga calidad Aguas
- Carga c�digos  pa�ses ISO 3166
- Marca para cada objeto de la �ltima modificaci�n.
- Icono Heimdallar.
- Visualizaci�n bar code (main page)
- Tipos de sitio en BD.
- A�adir campos longitud latitud en sitio.
- Sitio: A�adir longitud y latitud. 
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
- Objetos Secundarios,  objetos de importancia secundaria sin geolocalizaci�n: Ejemplo Droga,Vacuna,Medicamento
- AWS lambda use cases.
- AWS S3 integration

- Nuevo objeto: Teor�a.un pregunta que debe ser verificado por una/varias hip�tesis..
- Nuevo objeto: Hip�tesis.Es una te�r�a a probar.campos [nombre,descripci�n,grado de verificaci�n,lista de evidencias(evidencia,peso)]
- Nuevo objeto (data): Evidencia. campos[nombre,descripci�n,fuente]
- Modulo de contrastaci�n de hip�tesis(ACH).Permite visualizar(gr�ficamente, matricialmente) las evidencias frente a n evidencias, 
  asignarle peso a las evidencias y obtener un resultado...
  (https://www.cia.gov/library/center-for-the-study-of-intelligence/csi-publications/books-and-monographs/psychology-of-intelligence-analysis/art11.html)
       hipotesis\evidencias   h1   h2  
                e1             -        
                e2             +        
                e3             +    + 
                e4                  +
                e5                  +
    �Ejemplo?
     Teor�a: �por qu� las bicis no se adaptan al tr�fico?
     h1.Desconocimiento de las normas de circulaci�n
     h2.Normas creadas para veh�culos a motor
     
     
     ---
     e1. El 66% de los ciclistas urbanos tienen carnet de conducir (fuente f1)
     e2. El ayuntamiento hace la vista gorda con los ciclistas.    (fuente f2)
     e3. Noticia: 40% de los ciclistas no respetan los sem�foros.  
     e4. El 99% de los coches no respetan la velocidad de 30
     e5. hay calles en Madrid que son autopistas urbanas. (fuente f.)
                
-  Buscador con descarga csv. 
-  En cargas masivas permitir hacer copia de seguridad del objeto. Definir sistema de versiones.
-------------------------------------------------------------------------------
-------------------------------------------------------------------------------


ADENDA CARGA MASIVA
valores: modo [R]  R_eave default overwrite data if exists
datumId: Utiliza el dato asociado al objeto para localizarlo, si no es �nico NOP.
funciones: ISO-3166-1-num_to_lugar() --> Convierte  ISO a Lugar_id
 ----------------------
CU0- Cargar el dato ISO-3166-1-numeric para lugares
-metadata
{"objeto":"lugar","id":"0","data":["ISO-3166-1-num"],"modo":"O"}
-fichero
	1,23
	3,233
-----------------------
CU1- Cargar en la tabla lugares el dato poblaci�n y n� de camas de hospital por 1000, insertar y sobreescibir si existe.
Metadata:
{"objeto":"lugar","datumId":"ISO-3166-1-num","data":["poblacion","camas-hos-1000"],"modo":"IO"}
Fichero:
	248,1000000,50
	50,9939938,33
..
-----------------------
CU2-Cargar Campos de refugiados en objeto Sitio geolocalizados con dato ISO-31661
-Fichero:
pais,ISO-3166-1,nombre,poblaci�n
campo1,camp1,6,248,4,100000
-Metadata:
{"objeto":"sitio","col":["nombre":"0","nombreEn":"1","tipo":"2","lugar":"ISO-3166-1-num_to_lugar(3)","valoracion":"4"],
 "data":["poblacion"],"modo":"I"}
-----------------------
</pre>