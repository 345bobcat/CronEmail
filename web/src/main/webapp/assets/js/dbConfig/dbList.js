$(document).ready(function(){
    buildMainTable();
    $('#dbSource').on("click",".showDbConfigDetail" ,function(data){
        $("#dataInfoContent").html('');
        var id = $(this).attr("dataId");
        $.post('/dbConfig/getInfo', {id: id}, function (data) {
            $("#dataInfoContent").html(data);
            $("#showDataModal").modal("show");
            $("#showDataModal").draggable({handle: ".modal-header"});
        },'html');
    });
});

function buildMainTable() {
    DtTablePaging.mainTableInfo.ajax.url = "/dbConfig/sourceData";
    DtTablePaging.mainTableInfo.columns = [
        { "data": "id" , "width": "5%",
            "render": function ( data, type, full, meta ) {
                return data+'<button class="btn btn-default btn-sm" style="margin-left: 10px"  onclick="DbListJs.testDC('+data +')">测试</button>';
            }},
        { "data": "name" , "width": "8%"},
        { "data": "jdbcUrl" , "width": "15%"},
        { "data": "user" , "width": "8%"},
        { "data": "password" , "width": "8%"},
        { "data": "gmtCreated" , "width": "9%",
            "render": function ( data, type, full, meta ) {
                return (new Date(data)).Format("yyyy-MM-dd hh:mm:ss");
            }},
        { "data": "gmtModified" , "width": "9%",
            "render": function ( data, type, full, meta ) {
                return (new Date(data)).Format("yyyy-MM-dd hh:mm:ss");
            }},
        { "data": "id" , "width": "15%",
            "render": function ( data, type, full, meta ) {
                var result = '<div style="text-align: center"><button sqlSourceId="'+ data +'" class="btn btn-info btn-sm showDbSourceInfo" ><span class="glyphicon glyphicon-search"></span> 查看</button>';
                result+= '<button class="btn btn-warning btn-sm" style="margin-left: 10px"  onclick="DbListJs.addOrEditDC('+data +')"><span class="glyphicon glyphicon-edit"></span> 修改</button>';
                result+= '<button class="btn btn-danger btn-sm" style="margin-left: 10px"  onclick="DbListJs.delDC('+data +')"><span class="glyphicon glyphicon-remove"></span> 删除</button></div>';
                return result;
            }
        }
    ];

    DtTablePaging.mainTableInfo.initComplete = function () {
        $(".dataTables_length").append('<button type="button" style="margin-left: 20px; margin-bottom: 5px" class="btn btn-info btn-sm" onclick="DbListJs.addOrEditDC()"><span class="glyphicon glyphicon-plus"></span>添加</button>');
    };
    $('#dbSource').dataTable(DtTablePaging.mainTableInfo);

    //查看数据源信息
    $('#dbSource').on("click",".showDbSourceInfo" ,function(data){
        $("#dataInfoContent").html('');
        var id = $(this).attr("sqlSourceId");
        $.post('/dbConfig/getInfo', {id: id}, function (data) {
            $("#dataInfoContent").html(data);
            $("#showDataModal").modal("show");
            $("#showDataModal").draggable({handle: ".modal-header"});
        },'html');
    });
};

var DbListJs = window.DbListJs || {};

DbListJs.addOrEditDC = function (id) {
    $("#dataInfoContent").html('');
    $.post('/dbConfig/editInfo', {id: id}, function (data) {
        $("#dataInfoContent").html(data);
        $("#dbEditModal").modal("show");
        $("#dbEditModal").draggable({handle: ".modal-header"});
    },'html');
};

DbListJs.saveDC = function () {
    var params = $('#dbDataForm').serialize();
    $.post("saveEdit", params,function(data){
        var result = JSON.parse(data);
        if(result.success){
            location.reload();
        }else{
            alert("保存失败！")
        }
    });
};

DbListJs.delDC = function (id) {
    var msg=confirm("确定删除数据吗?");
    if(msg==false)
    {
        return false;
    }

    $.post("delById", {id: id},function(data){
        location.reload();
    });
};

DbListJs.testDC = function (id) {
    $.post("testDb", {id: id},function(data){
        if(data=="true"){
            alert("测试成功！");
        }else{
            alert("测试失败！");
        }
    });
};