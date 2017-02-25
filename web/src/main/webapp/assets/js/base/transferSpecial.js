var SpecialJS = window.SpecialJS || {};

//普通字符转换成转意符
SpecialJS.html2Escape = function(sHtml) {
    return sHtml==null?"":(sHtml.replace(/[<>&"]/g,function(c){return {'<':'&lt;','>':'&gt;','&':'&amp;','"':'&quot;'}[c];}));
};

//转意符换成普通字符
SpecialJS.escape2Html = function(str) {
    var arrEntities={'lt':'<','gt':'>','nbsp':' ','amp':'&','quot':'"'};
    return str==null?"":str.replace(/&(lt|gt|nbsp|amp|quot);/ig,function(all,t){return arrEntities[t];});
};