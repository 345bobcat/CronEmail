$(document).ready(function(){
    buildMainTable();
});

function buildMainTable() {
    DtTablePaging.mainTableInfo.ajax.url = "/emailConfig/sourceData";
    DtTablePaging.mainTableInfo.columns = [
        { "data": "ecId" , "width": "4%" },
        { "data": "tcId" , "width": "4%" },
        { "data": "receivers" , "width": "9%",
            "render": function ( data, type, full, meta ) {
                if(data == null){return "";}
                var params = data.split(";");
                return params.join(";<br>")
            }},
        { "data": "emailCc" , "width": "9%",
            "render": function ( data, type, full, meta ) {
                if(data == null){return "";}
                var params = data.split(";");
                return params.join(";<br>")
            }},
        { "data": "subject" , "width": "15%"},
        { "data": "gmtCreated" , "width": "9%",
            "render": function ( data, type, full, meta ) {
                return (new Date(data)).Format("yyyy-MM-dd hh:mm:ss");
            }},
        { "data": "ecId" , "width": "14%",
            "render": function ( data, type, full, meta ) {
                var result = '<div style="text-align: center"><button class="btn btn-info btn-sm" onclick="EcListJs.showEC('+data +')"><span class="glyphicon glyphicon-search"></span> 查看</button>';
                result+= '<button class="btn btn-warning btn-sm" style="margin-left: 10px"  onclick="EcListJs.addOrEditEC('+data +')"><span class="glyphicon glyphicon-edit"></span> 修改</button>';
                result+= '<button class="btn btn-danger btn-sm" style="margin-left: 10px"  onclick="EcListJs.delEC('+data +')"><span class="glyphicon glyphicon-remove"></span> 删除</button></div>';
                return result;
            }
        }
    ];

    DtTablePaging.mainTableInfo.initComplete = function () {
        $(".dataTables_length").append('<button type="button" style="margin-left: 20px; margin-bottom: 5px" class="btn btn-info btn-sm" onclick="EcListJs.addOrEditEC()"><span class="glyphicon glyphicon-plus"></span>添加</button>');
    };
    $('#ecSource').dataTable(DtTablePaging.mainTableInfo);
};

var EcListJs = window.EcListJs || {};

EcListJs.addOrEditEC = function (ecId) {
    $("#dataInfoContent").html('');
    $.post('/emailConfig/openInfo', {ecId: ecId}, function (data) {
        $("#dataInfoContent").html(data);
        $("#ecEditModal").modal("show");
        $("#ecEditModal").draggable({handle: ".modal-header"});
        $('#tcId').selectpicker();
    },'html');
};

EcListJs.showEC = function (ecId) {
    $("#dataInfoContent").html('');
    $.post('/emailConfig/getInfo', {ecId: ecId}, function (data) {
        $("#dataInfoContent").html(data);
        $("#showDataModal").modal("show");
        $("#showDataModal").draggable({handle: ".modal-header"});
    },'html');
};

EcListJs.saveEC = function () {
    var params = $('#ecDataForm').serialize();
    $.post("saveEdit", params,function(data){
        var result = JSON.parse(data);
        if(result.success){
            location.reload();
        }else{
            alert("保存失败！")
        }
    });
};

EcListJs.delEC = function (ecId) {
    var msg=confirm("确定删除数据吗?");
    if(msg==false)
    {
        return false;
    }
    $.post("delById", {ecId: ecId},function(data){
        location.reload();
    });
};