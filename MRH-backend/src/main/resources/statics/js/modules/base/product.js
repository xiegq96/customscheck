$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + '/baseProduct/queryPage',
        datatype: "json",
        colModel: [
			{ label: '订单编号', name: 'orderNo', index: 'order_no', width: 80 },
			{ label: '商品名称', name: 'gname', index: 'gname', width: 80 },
			{ label: '商品购买链接', name: 'itemLink', index: 'item_link', width: 80 },
		],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "result.list",
            page: "result.currPage",
            total: "result.totalPage",
            records: "result.totalCount"
        },
        prmNames : {
            page:"currPage",
            rows:"pageSize",
           // order: "order"
        },

        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        },



    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
        q:{
            orderNo: null
        },
		showList: true,
		title: null,
		product: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.product = {};
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
		//	var url = vm.dict.id == null ? "sys/dict/save" : "sys/dict/update";
			$.ajax({
				type: "POST",
			    url: baseURL + '/baseProduct/addProduct',
                contentType: "application/json",
			    data: JSON.stringify(vm.product),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "/baseProduct/deleteProductByIds",
                    contentType: "application/json",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(id){
			$.get(baseURL + "sys/dict/info/"+id, function(r){
                vm.dict = r.dict;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                postData:{'orderNo': vm.q.orderNo},
                page:page
            }).trigger("reloadGrid");
		}
	}
});