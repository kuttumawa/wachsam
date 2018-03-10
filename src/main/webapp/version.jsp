<jsp:include page="cabecera.jsp"/>

<pre>



1.4.0 (11-03-2017)
- Integración con ELK


1.3.1 (7-08-2017)
- API:
   Riesgo: Obtiene los riesgos de un lugar
   - parameter:
      countryCode -Riesgo por código ISO_3166_1_alpha2
      countryId   -Riesgos por id
      peligroId   -Filtra por id interno peligro
      lang        -filtra por lenguaje
      max         -número máximo de resultados (default 100)
      skip        -indice sobre los resultados devueltos.
   -metadata in header
     Results-Matching  - Resultados totales encontrados (no devueltos)
     Results-Skipped   - Indice desde que se devolvió.
     Results-Presented - número de resultados devueltos
   - ejemplos:
         /riesgos?id=1,11,33
		 /riesgos?countryCode=AL,BE,GB&max=50&index=10
		 /riesgos?peligroId=3,23,123
		 /riesgos?peligroId=3,23,123&countryCode=AL,BE&max=50&index=10&lang=en

         

 


1.3.0 (22-07-2017)
-----------------------------------------------------------------------------
- Nuevo Riesgo:
id,lugar_id,peligro_id,probabilidad,fechapub,caducidad,fechaactivacion,mesactivacion,diaactivacion,texto,text,formuladisipacion,desactivado
- Funciones de disipación:
     how: Ajustan la probabilidad inicial,decrementandola durante el tiempo
     3 tipos: 
             - homogenea: P(t)= Pinicial
             - lineal: P(t) = Pinicial - (Pinicial/Caducidad)*t
             - Asintótica decreciente: P(t)= (1/t)*Pinicial 

-Peligro añadido texto/text
-Tendencia: Compara los 6 últimos meses actuales, con los pasados para lugar y peligro. 

=======
futuro
------------------------------------------------------------------------------
- Mejoras en tags: organización, más datos(categoría,único por objeto)
- Búsqueda avanzada con tags
- Elastic Search integration


1.2.0 (2-10-2016)
------------------------------------------------------------------------------
- Búsqueda objetos pesados.
- Descarga formato csv de los resultados de una búsqueda.
- Eliminación del combo del id de las páginas, sustituirlo por búsqueda
- Fix error grabación lugares
- Nuevo Objeto Recurso: Metadatos(nombre,autor,descripción,permiso..)  + url a a un fichero físico. Implementar con S3 
- Nuevo objeto Mitigación: (Riesgo,Factor,valor(-3,-2,-1,0,1,2,3)) ie: [[Malaria, Mosquitera,-3].
- Dendrograma para lugares.

1.1.0 (12-08-2016)
------------------------------------------------------------------------------
- Cacheo local de objetos pesados, preparar browser bd.
- Generalización de la subida de ficheros.
- Definición de metadatos para subida de ficheros.
- Nuevo objeto: Riesgo.[peligro,lugar,probabilidad]
- Nuevo objeto Tipo sitio.
- Icono Heimdallar.
- Visualización Actividad: bar code (main page)
- Correción bug en permisos de modificación en todos los objetos.
- Cargas de datos:
     -códigos países ISO 3166(alpha2.alpha3,num)
     -Aguas de baño Europa

------------------------------------------------------------------------------

1.0.4 (30-05-2016)
------------------------------------------------------------------------------
- Corrige bugs permisos
- Nuevo modelo para los datos asociados
- Nuevo interfaz para los datos asociados
- Nuevo visualización de red para los datos de un objeto
- Clonar alertas
------------------------------------------------------------------------------



1.0.3 (15-05-2016)
------------------------------------------------------------------------------
- Tags.Se crea alias corto para metalenguaje.
- Mapa para alertas
- Iconos para tipos de sitios
- Revisión algoritmo de dispersión
- Reducir tamaño del punto azul 
- Se añade en Sitios:: Carceles,Campos de Refugiados
- Auditoría de operaciones
- Autenticación (nuevo login) y autorización sobre operaciones.
- Permisos
- Componente html input date

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

TODO
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
- Datos: pemitir datos privados para un usuario:
- TK (to come).Idea colaborativa: Ejemplo publicar un TK: El agente infeccioso TK puede afectar al país TK por los evntos detectados TK* 
- Tags ampliar funcionalidad.ie: asociar icono a un dato
- Tipos de sitio en BD
- layout: footer
- widget: cloud tag :: http://bl.ocks.org/ericcoopey/6382449
- bug: widget map se repiten algunos puntos.Ocurre en el mapa general.ej Mauritania
- bug: revisar cacheo para que se active con todos los parámetros.ejemplo caducado.
- Configurable Alarms
- Nuevo objeto Artículo.CU: Se crea un artículo,nombre,descripción, subject,url.Se encola para indexarlo en ES.  
- Nuevo objeto Consulta
- Nuevo objeto Medicamento
- kafka http://kafka.apache.org/
- API REST
- Objetos Secundarios,  objetos de importancia secundaria sin geolocalización: Ejemplo Droga,Vacuna,Medicamento
- AWS lambda use cases.
- AWS S3 integration
- Nuevo objeto: Teoría.un pregunta que debe ser verificado por una/varias hipótesis..
- Nuevo objeto: Hipótesis.Es una teóría a probar.campos [nombre,descripción,grado de verificación,lista de evidencias(evidencia,peso)]
- Nuevo objeto (data): Evidencia. campos[nombre,descripción,fuente]
- Modulo de contrastación de hipótesis(ACH).Permite visualizar(gráficamente, matricialmente) las evidencias frente a n evidencias, 
  asignarle peso a las evidencias y obtener un resultado...
  (https://www.cia.gov/library/center-for-the-study-of-intelligence/csi-publications/books-and-monographs/psychology-of-intelligence-analysis/art11.html)
       hipotesis\evidencias   h1   h2  
                e1             -        
                e2             +        
                e3             +    + 
                e4                  +
                e5                  +
    ¿Ejemplo?
     Teoría: ¿por qué las bicis no se adaptan al tráfico?
     h1.Desconocimiento de las normas de circulación
     h2.Normas creadas para vehículos a motor
     
     
     ---
     e1. El 66% de los ciclistas urbanos tienen carnet de conducir (fuente f1)
     e2. El ayuntamiento hace la vista gorda con los ciclistas.    (fuente f2)
     e3. Noticia: 40% de los ciclistas no respetan los semáforos.  
     e4. El 99% de los coches no respetan la velocidad de 30
     e5. hay calles en Madrid que son autopistas urbanas. (fuente f.)
                
-  Buscador con descarga csv. 
-  En cargas masivas permitir hacer copia de seguridad del objeto. Definir sistema de versiones.
-  Cambio librería JSON org.json por Jackson.
- Rollback de cargas masivas
- Mejoras en tags: organización,más datos(categoría,único por objeto)
-------------------------------------------------------------------------------
-------------------------------------------------------------------------------


</pre>