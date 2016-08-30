<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
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
function buscar(){
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
	console.log(data);
}
$(function() {
    $('form').submit(function() {
        $('#result').text(JSON.stringify($('form').serializeObject()));
        return false;
    });
});
</script>
<form action="">
page:<input type=text name="page"/>
order:<input type=text name="order"/>
id:<input type="text" name="id"/>
nombre:<input type="text" name="nombre"/>
lugarId:<input type="text" name="lugarId"/>
tipoId:<input type="text" name="tipoId"/>
<input type="submit"/>
</form>

<pre id="result">
<div id="resultado"/>