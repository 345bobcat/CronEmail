$(document).ready(function(){
    buildMainTable();
});

function buildMainTable() {
    $('#cronRun').dataTable(DtTableNoPaging.mainTableInfo);
};