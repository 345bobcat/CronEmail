var TcListJs = window.TcListJs || {};
TcListJs.codeMirror = null;
TcListJs.contentMirror = null;
TcListJs.sqlMirror = null;

TcListJs.contentEditor = null;

$(document).ready(function(){
    buildMainTable();

    //模板设计
    $('#tcSource').on("click",".showContentDesignDetail" ,function(data){
        $("#showDataDesignModal").modal("show");
        //modal拖动
        $("#showDataDesignModal").draggable({handle: ".modal-header"});
        $("#tcId").val($(this).attr("tcId"));

        //销毁后重新创建，保证ked打开界面在html模式
        KindEditor.remove('#templateContent');
        TcListJs.contentEditor = KindEditor.create('#templateContent', {
            width : '100%',
            height:'700px',
            resizeType : 1,
     //       designMode : false,
            allowPreviewEmoticons : false,
            allowFileManager : true,
            allowImageRemote : false,
            urlType: 'absolute',
            uploadJson : '/ked/uploadFile',
            fileManagerJson : '/ked/manageFile',
            items : [
                'source','fontname', 'fontsize','|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
                'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
                'insertunorderedlist', '|', 'table','link'], //'image',
        });
        TcListJs.contentEditor.html($(this).attr("content"));
    });

    //模板代码
    $('#tcSource').on("click",".showContentDetail" ,function(data){
        $("#showDataCodeModal").modal("show");
        //modal拖动
        $("#showDataCodeModal").draggable({handle: ".modal-header"});
        $("#tcId").val($(this).attr("tcId"));

        //每次打开弹层都需要刷新内容，采用先删除后创建的方式
        $('.CodeMirror').remove();
        //编辑sql
        var mixedMode = {
            name: "htmlmixed",
            scriptTypes: [{matches: /\/x-handlebars-template|\/x-mustache/i,
                mode: null},
                {matches: /(text|application)\/(x-)?vb(a|script)/i,
                    mode: "vbscript"}]
        };
        TcListJs.contentMirror = CodeMirror.fromTextArea(document.getElementById('contentCode'), {
            mode: mixedMode,
            theme: 'rubyblue',
            indentWithTabs: true,
            smartIndent: true,
            lineNumbers: true,
            styleActiveLine: true,
            matchBrackets: true,
            autoRefresh: true
        });
        TcListJs.contentMirror.setSize('auto','450px');
        TcListJs.contentMirror.setValue($(this).attr("content"));
    });

    //列表sql代码编辑
    $('#tcSource').on("click",".showSqlDetail" ,function(data){
        $("#showSqlModal").modal("show");
        //modal拖动
        $("#showSqlModal").draggable({handle: ".modal-header"});
        $("#tcId").val($(this).attr("tcId"));

        //每次打开弹层都需要刷新内容，采用先删除后创建的方式
        $('.CodeMirror').remove();
        //编辑sql
        TcListJs.sqlMirror = CodeMirror.fromTextArea(document.getElementById('code'), {
            mode: 'text/x-mysql',
            theme: 'rubyblue',
            indentWithTabs: true,
            smartIndent: true,
            lineNumbers: true,
            styleActiveLine: true,
            matchBrackets: true,
            autoRefresh: true
        });
        TcListJs.sqlMirror.setSize('auto','370px');
        TcListJs.sqlMirror.setValue($(this).attr("data"));
    });
});

function buildMainTable() {
    DtTablePaging.mainTableInfo.ajax.url = "/templateConfig/sourceData";
    DtTablePaging.mainTableInfo.columns = [
        { "data": "tcId" , "width": "5%" },
        { "data": "name" , "width": "10%"},
        /*{ "data": "savePath" , "width": "15%"},
        { "data": "suffix" , "width": "10%"},
        { "data": "fileName" , "width": "10%"},*/
        { "data": "content" , "width": "8%",
            "render": function ( data, type, full, meta ) {
                if(full.sqlSourceId != null){
                    return '<div style="text-align: center"><button tcid="'+ full.tcId +'" content="'+ SpecialJS.html2Escape(data) +'" class="btn btn-sm showContentDetail"><span class="glyphicon glyphicon-pencil"></span> 模板代码</button></div>';
                }
                return '<div style="text-align: center"><button tcid="'+ full.tcId +'" content="'+ SpecialJS.html2Escape(data) +'" class="btn btn-info btn-sm showContentDesignDetail"><span class="glyphicon glyphicon-pencil"></span> 模板设计</button></div>';
            }},
        { "data": "sqlSentence" , "width": "6%",
            "render": function ( data, type, full, meta ) {
                if(full.sqlSourceId == null){
                    return "";
                }
                return '<div style="text-align: center"><button tcid="'+ full.tcId +'" data="'+ SpecialJS.html2Escape(data) +'" class="btn btn-default btn-sm showSqlDetail"><span class="glyphicon glyphicon-pencil"></span> 详细</button></div>';
            }},
        { "data": "sqlSourceId" , "width": "6%",
            "render": function ( data, type, full, meta ) {
                if(data==null){
                    return "";
                }
                return '<div style="text-align: center"><button sqlSourceId="'+ data +'" class="btn btn-default btn-sm showDbSourceInfo">查看</button></div>';
            }},
        { "data": "backUser" , "width": "8%"},
        { "data": "comment" , "width": "15%"},
        { "data": "gmtCreated" , "width": "11%",
            "render": function ( data, type, full, meta ) {
                return (new Date(data)).Format("yyyy-MM-dd hh:mm:ss");
            }},
        { "data": "gmtModified" , "width": "11%",
            "render": function ( data, type, full, meta ) {
                return (new Date(data)).Format("yyyy-MM-dd hh:mm:ss");
            }},
        { "data": "tcId" , "width": "15%",
            "render": function ( data, type, full, meta ) {
                var result = '<div style="text-align: center"><button class="btn btn-warning btn-sm editTcConfigDetail" style="margin-left: 10px" onclick="TcListJs.addOrEditTemplate('+data +')"><span class="glyphicon glyphicon-edit"></span> 修改</button>';
                result+= '<button class="btn btn-danger btn-sm delTcConfigDetail" style="margin-left: 10px" onclick="TcListJs.delTemplate('+data +')"><span class="glyphicon glyphicon-remove"></span> 删除</button></div>';
                return result;
            }
        }
    ];

    DtTablePaging.mainTableInfo.initComplete = function () {
        $(".dataTables_length").append('<button type="button" style="margin-left: 20px; margin-bottom: 5px" class="btn btn-info btn-sm" onclick="TcListJs.addOrEditTemplate()"><span class="glyphicon glyphicon-plus"></span> 添加</button>');
    };
    $('#tcSource').dataTable(DtTablePaging.mainTableInfo);

    //查看数据源信息
    $('#tcSource').on("click",".showDbSourceInfo" ,function(data){
        $("#templateInfo").html('');
        var id = $(this).attr("sqlSourceId");
        $.post('/dbConfig/getInfo', {id: id}, function (data) {
            $("#templateInfo").html(data);
            $("#showDataModal").modal("show");
            $("#showDataModal").draggable({handle: ".modal-header"});
        },'html');
    });
};



TcListJs.addOrEditTemplate = function (tcId) {
    $("#templateInfo").html('');
    $.post('/templateConfig/openInfo', {tcId: tcId}, function (data) {
        $("#templateInfo").html(data);
        $("#showInfoModal").modal("show");
        $("#showInfoModal").draggable({handle: ".modal-header"});

        $('#sqlSourceId').selectpicker();

        //每次打开弹层都需要刷新内容，采用先删除后创建的方式
        $('.CodeMirror').remove();
        //编辑sql
        TcListJs.codeMirror = CodeMirror.fromTextArea(document.getElementById('sqlSentence'), {
            mode: 'text/x-mysql',
            theme: 'rubyblue',
            indentWithTabs: true,
            smartIndent: true,
            lineNumbers: true,
            styleActiveLine: true,
            matchBrackets: true,
            autoRefresh: true
        });
        TcListJs.codeMirror.setSize('auto','240px');
    },'html');
};

TcListJs.saveTemplate = function () {
    $("#sqlSentence").text(TcListJs.codeMirror.getValue());
    var params = $('#tcDataForm').serialize();

    $.post("saveEdit", params,function(data){
        var result = JSON.parse(data);
        if(result.success){
            location.reload();
        }else{
            alert("保存失败！")
        }
    });
};

TcListJs.saveTemplateBtn = function () {
    $.post("saveEdit", {content: TcListJs.contentMirror.getValue(),tcId : $("#tcId").val()},function(data){
        var result = JSON.parse(data);
        if(result.success){
            location.reload();
        }else{
            alert("保存失败！")
        }
    });
};

TcListJs.saveDesignBtn = function () {
    $.post("saveEdit", {content: TcListJs.contentEditor.html(),tcId : $("#tcId").val()},function(data){
        var result = JSON.parse(data);
        if(result.success){
            location.reload();
        }else{
            alert("保存失败！")
        }
    });
};

TcListJs.saveSqlBtn = function () {
    $.post("saveEdit", {sqlSentence: TcListJs.sqlMirror.getValue(),tcId : $("#tcId").val()},function(data){
        var result = JSON.parse(data);
        if(result.success){
            location.reload();
        }else{
            alert("保存失败！")
        }
    });
};

TcListJs.delTemplate = function (tcId) {
    var msg=confirm("确定删除数据吗?");
    if(msg==false)
    {
        return false;
    }

    $.post("delInfo", {tcId: tcId},function(data){
        location.reload();
    });
};


