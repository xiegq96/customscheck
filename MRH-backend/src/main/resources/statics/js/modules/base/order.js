$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + '/baseOrder/queryPage',
        datatype: "json",
        colModel: [
            { label: '系统序号', name: 'guid', index: 'guid', width: 80 },
			{ label: '订单编号', name: 'orderNo', index: 'order_no', width: 80 },
			{ label: '收款账号', name: 'recpAccount', index: 'gname', width: 80 },
			{ label: '收款企业代码', name: 'recpCoe', index: 'recp_code', width: 80 },
            { label: '收款企业名称', name: 'recpName', index: 'recp_name', width: 80 },
            {
                label: '操作', name: 'operate', index: 'operate', width: 110,
                formatter: function (cellvalue, options, row) {

                    return "<button class='btn btn-default' style='color: #00a65a' size='small' onclick='btn_detail(\""+ row.orderNo + "\")''>查看详情</a></button>";
                },
            }
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
        }
    });
});

function btn_detail(orderNo,event) {
    if(orderNo == null){
        var orderNo = getSelectedRow("#jqGrid","orderNo")
    }
    $.ajax({
        type: "POST",
        url: baseURL + '/baseOrder/selectProductByOrderNo/' + orderNo,
        contentType: "application/json",
        success: function(r){
            vm.proList = r.result
            vm.detail = true
            if(r.code === 0){
                layer.open({
                    type: 1,
                    skin: 'layui-layer-molv',
                    title: "详情页面",
                    area: ['50%', '80%'],
                    btn: ['返回'],
                    content: $('#detail')
                })
                $("#jqGrid").trigger("reloadGrid"); // 取消表格行的选中状态
            }else{
                alert(r.msg);
            }

        }
    })
}

var vm = new Vue({
	el:'#rrapp',
	data:{
        q:{
            orderNo: null
        },
		showList: true,
		showDetails: false,
		proList: [],
		detail: false,
		title: null,
		/*dict: {}*/
		order: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.order = {};
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
			//var url = vm.dict.id == null ? "sys/dict/save" : "sys/dict/update";
			$.ajax({
				type: "POST",
			    url: baseURL + '/baseOrder/addOrder',
                contentType: "application/json",
			    data: JSON.stringify(vm.order),
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
				var orderNos = []
				for(var i = 0;i < ids.length;i++){
					//  获取行数据
                    var rowData = $("#jqGrid").getRowData(ids[i])
					orderNos.push(rowData.orderNo)
				}
				$.ajax({
					type: "POST",
				    url: baseURL + "/baseOrder/deleteOrder/" + orderNos,
                    contentType: "application/json",
				    data: JSON.stringify(orderNos),
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