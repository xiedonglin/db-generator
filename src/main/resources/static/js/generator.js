$(function () {
    $("#jqGrid").jqGrid({
        url: 'sys/generator/list',
        datatype: "json",
        colModel: [			
			{ label: 'Table', name: 'tablename', width: 50, key: true },
			{ label: 'Table注释', name: 'tablecomment', width: 50},
			{ label: '主键数', name: 'keycount', width: 30},
			{ label: '数据量', name: 'rowcount', width: 30}
        ],
		viewrecords: true,
        height: 1000,
        rowNum: 1000,
		rowList : [30,1000],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			tableName: null
		}
	},
	methods: {
		query: function () {
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{'schemaname': vm.q.schemaname},
                page:1 
            }).trigger("reloadGrid");
		},
		generator: function() {
			var schemanames = getSelectedRows();
			if(schemanames == null){
				return ;
			}
			location.href = "sys/generator/code?schemas=" + JSON.stringify(schemanames);
		}
	}
});

