$(document).ready(function(){
    buildMainTable();
});

function buildMainTable() {
    DtTablePaging.mainTableInfo.ajax.url = "/cronShow/sourceData";
    DtTablePaging.mainTableInfo.columns = [
        { "data": "scheduleJobId" , "width": "5%" },
        { "data": "jobName" , "width": "6%"},
        { "data": "jobGroup" , "width": "6%"},
        { "data": "status" , "width": "6%"},
        { "data": "cronExpression" , "width": "8%"},
        { "data": "isSync" , "width": "6%"},
        { "data": "ecId" , "width": "6%"},
        { "data": "gmtCreate" , "width": "10%",
            "render": function ( data, type, full, meta ) {
                return (new Date(data)).Format("yyyy-MM-dd hh:mm:ss");
            }},
        { "data": "gmtModify" , "width": "10%",
            "render": function ( data, type, full, meta ) {
                return (new Date(data)).Format("yyyy-MM-dd hh:mm:ss");
            }},
        { "data": "scheduleJobId" , "width": "19%",
            "render": function ( data, type, full, meta ) {
                var result = '<div style="text-align: center"><button sqlSourceId="'+ data +'" class="btn btn-default btn-sm" onclick="CsListJs.pause('+data +')"><span class="glyphicon glyphicon-pause"></span> 暂停</button>';
                result+= '<button class="btn btn-default btn-sm" style="margin-left: 10px"  onclick="CsListJs.resume('+data +')"><span class="glyphicon glyphicon-play"></span> 恢复</button>';
                result+= '<button class="btn btn-default btn-sm" style="margin-left: 10px"  onclick="CsListJs.runOnce('+data +')"> 运行一次</button>';
                result+= '</div><div style="text-align: center;margin-top: 5px"><button class="btn btn-warning btn-sm" onclick="CsListJs.addOrEdit('+data +')"><span class="glyphicon glyphicon-edit"></span> 修改</button>';
                result+= '<button class="btn btn-danger btn-sm" style="margin-left: 10px"  onclick="CsListJs.delete('+data +')"><span class="glyphicon glyphicon-remove"></span> 删除</button></div>';
                return result;
            }
        }
    ];

    DtTablePaging.mainTableInfo.initComplete = function () {
        $(".dataTables_length").append('<button type="button" style="margin-left: 20px; margin-bottom: 5px" class="btn btn-info btn-sm" onclick="CsListJs.addOrEdit()"><span class="glyphicon glyphicon-plus"></span>添加</button>');
    };
    $('#cronShow').dataTable(DtTablePaging.mainTableInfo);
};

var CsListJs = window.CsListJs || {};

CsListJs.addOrEdit = function (id) {
    $("#dataInfoContent").html('');
    $.post('/cronShow/openInfo', {id: id}, function (data) {
        $("#dataInfoContent").html(data);
        $("#csEditModal").modal("show");
        $("#csEditModal").draggable({handle: ".modal-header"});

        $('#ecId').selectpicker();
    },'html');
};

CsListJs.save = function () {
    var params = $('#csDataForm').serialize();
    $.post("saveEdit", params,function(data){
        var result = JSON.parse(data);
        if(result.success){
            location.reload();
        }else{
            alert("保存失败！")
        }
    });
};

CsListJs.pause = function (id) {
    location.href="pause?scheduleJobId="+id;
};

CsListJs.resume = function (id) {
    location.href="resume?scheduleJobId="+id;
};

CsListJs.runOnce = function (id) {
    location.href="runOnce?scheduleJobId="+id;
};

CsListJs.delete = function (id) {
    var msg=confirm("确定删除数据吗?");
    if(msg==false)
    {
        return false;
    }

    $.post("delById", {id: id},function(data){
        location.reload();
    });
};
