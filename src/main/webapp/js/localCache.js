var ALERT_ALL_KEY="ALERT_ALL";


function getFromStorage(key){
   if(!sessionStorage || !sessionStorage.getItem(key)) return null;
   if(sessionStorage.getItem(key+"_stale") && Number(sessionStorage.getItem(key+"_stale")<new Date().getTime())){
	   sessionStorage.removeItem(key);
	   sessionStorage.removeItem(key+"_stale");
	   return null;
   }
   return JSON.parse(sessionStorage.getItem(key));
	
}
function setInStorage(key,object,time){
    if(!sessionStorage) return null;
    sessionStorage.setItem(key,JSON.stringify(object));
    if(time) sessionStorage.setItem(key+"_stale", new Date().getTime() + time * 60000);	
}

function evictCache(key){
	if(!sessionStorage || !sessionStorage.getItem(key)) return;
	sessionStorage.removeItem(key);
    sessionStorage.removeItem(key+"_stale");	
}

function clearLocalStorage(){
	if(!sessionStorage) sessionStorage.clear();
}