var DtTablePaging = window.DtTablePaging || {};

DtTablePaging.mainTableInfo = {
    "processing": true,
    "serverSide": true,
    "ordering": false, //排序操作在服务端进行,所以可以关了。
    "searching": false,//禁用datatables搜索
    "scrollX": true,
    "autoWidth": false,
    "pageLength":25,
    "ajax": {
        "type": "POST",
        "dataSrc": function (json) {
            json.draw = json.data.draw;
            json.recordsTotal = json.data.recordsTotal;
            json.recordsFiltered = json.data.recordsFiltered;
            return json.data.data;
        }/*,
        "data": function (d) {
            var param = {
                "DRAW": d.draw,
                "START": d.start,
                "LIMIT": d.length
            };
            return param;
        }*/
    },
    "language": {
        "info":"第 _START_ 到第 _END_ 条数据；共 _TOTAL_ 条记录",
        "processing":"正在加载数据...",
        "zeroRecords":"没有查询到数据",
        "infoEmpty":"数据为空",
        "lengthMenu":"展示 _MENU_ 条",
        "paginate": {
            "first": "首页",
            "last": "尾页",
            "next": "下一页",
            "previous": "上一页"
        }
    }
};

