<jsp:include page="cabecera.jsp"/>

<script>
var objectoSistema="Sitio";
$.fn.serializeObject = function()
{
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};
function buscar(page){
	$('#page').val(page || 0);
	 $.ajax({
		    type:"POST",
		    url: "BuscarObject",
		    data:"object="+objectoSistema+"&filter="+JSON.stringify($('form').serializeObject()),
		    success: function(data) {
		    	paintData(data);
		    }
		  });
		  return false;
}
function paintData(data){
	$('#resultado').children().remove();
	makeTable($('#resultado'),data.data);
	makePagination($('#resultado'),data);	
}
function makePagination(container,data){
	var list = $("<ul/>").addClass('pagination pagination-sm');
	var NUMBER_PAGES_TO_SHOW=5;
	var desde=Math.max(0,data.currentpage-1);
    var hasta=Math.min(NUMBER_PAGES_TO_SHOW+data.currentpage,data.numpages);
	for(i = desde;i<= hasta;i++){
		 var li = $("<li/>");
		 if(data.currentpage==i) li.addClass('active');
		 var link=$("<a/>");		
		 li.append(link);
		 list.append(li);
		 if(i==hasta && hasta<data.numpages) link.attr('href','javascript:buscar('+i+')').text('..');
		 else{
			  link.attr('href','javascript:buscar('+i+')').text(i);			
		 }
	 };	
	 return container.append(list);	 
}
function makeTable(container, data) {
    var table = $("<table/>").addClass('table table-condensed  table-striped small');
    var theader=$("<tr/>");
    theader.append($("<th/>").text("id"));
    theader.append($("<th/>").text("nombre"));
    theader.append($("<th/>").text("lugar"));
    theader.append($("<th/>").text("dirección"));
    
    table.append(theader);
    $.each(data, function(rowIndex, d) {
        var row = $("<tr/>");
        row.append($("<td/>").text(d.id));
        row.append($("<td/>").text(d.nombre));
        if(d.lugarObj){ row.append($("<td/>").text(d.lugarObj.nombre))}
        else{ row.append($("<td/>").text("--"))};
        row.append($("<td/>").text(d.direccion));       
        table.append(row);
    });
    return container.append(table);
}
$(function() {
    $('form').submit(function() {
        $('#result').text(JSON.stringify($('form').serializeObject()));
        return false;
    });
});
</script>
<form action="">
page:<input type=text name="page" id="page"/>
order:<input type=text name="order"/>
id:<input type="text" name="id"/>
nombre:<input type="text" name="nombre"/>
lugarId:<input type="text" name="lugarId"/>
tipoId:<input type="text" name="tipoId"/>
<input type="button" onclick="buscar()" value="Buscar"/>;
</form>

<div id="resultado"/>