$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + '/baseExchange/queryPage',
        datatype: "json",
        colModel: [
            { label: '系统序号', name: 'guid', index: 'guid', width: 90},
			{ label: '原始请求', name: 'initalRequest', index: 'inital_request', width: 120,
                cellEdit: true, formatter: function(cellvalue, options, row) {
                    var oldStrlen = cellvalue.length
                    var newStr = ''
                    if(oldStrlen > 20){
                        newStr = cellvalue.substring(0,15)+'...'
                        newStr="<div title='"+cellvalue+"'>"+newStr+"</div>";
                    }else{
                        newStr = cellvalue
                    }
                    return newStr
                	}
                },
			{ label: '原始响应', name: 'initalResponse', index: 'inital_response', width: 120,
                cellEdit: true, formatter: function(cellvalue, options, row) {
					var oldStrlen = cellvalue.length
					var newStr = ''
					if(oldStrlen > 20){
						newStr = cellvalue.substring(0,15)+'...'
                        newStr="<div title='"+cellvalue+"'>"+newStr+"</div>";
                    }else{
						newStr = cellvalue
					}
					return newStr
                }
			},
			{ label: '电商平台代码', name: 'ebpCode', index: 'ebp_code', width: 120 },
            { label: '支付企业代码', name: 'payCode', index: 'pay_code', width: 120 },
            { label: '交易流水号', name: 'payTransactionId', index: 'pay_transaction_id', width: 80 },
            { label: '交易金额', name: 'totalAmount', index: 'total_amount', width: 80 },
            { label: '币制', name: 'currency', index: 'currency', width: 80 },
            { label: '验核机构', name: 'verDept', index: 'ver_dept', width: 80 },
            { label: '支付类型', name: 'payType', index: 'pay_type', width: 80 },
            { label: '交易成功时间', name: 'tradingTime', index: 'trading_time', width: 130 },
			{ label: '备注', name: 'note', index: 'note', width: 100 },
            {
                label: '操作', name: 'operate', index: 'operate', width: 110,
                formatter: function (cellvalue, options, row) {

                    return "<button class='btn btn-default' style='color: #00a65a' size='small' onclick='btn_detail(\""+ row.guid + "\")''>查看详情</a></button>";
                },
            }
		],
		viewrecords: true,
        height: 385,
        shrinkToFit:false, //设置水平导航栏
        autoScroll: true, //设置水平导航栏

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

      /*  gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }*/


    });
});

function btn_detail(guid,event) {
	if(guid == null){
		var guid = getSelectedRow("#jqGrid","guid")
	}
    console.log("传入的参数："+guid)
	$.ajax({
        type: "POST",
        //url: baseURL + '/baseExchange/getExchangeListByGuid' + '?guid=' +guid,
        url: baseURL + '/baseExchange/getExchangeListByGuid/' + guid,
        contentType: "application/json",
       /* data: JSON.stringify({guid: guid}),
        dataType: "json",*/
		//data: guid,
        success: function(r){
        	vm.baseInfo = r.result
			//console.log(vm.baseInfo.guid)
			//vm.proList = r.list[0].payExchangeInfoLists
			//console.log(vm.proList.length)
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
            guid: null
        },
		detail: false,
		showList: true,
		title: null,
        proListVisible: true,
		baseInfo: [],
        proList: [],
		exchange: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.exchange = {};
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
			  //  url: baseURL + url,
                url: baseURL + '/baseExchange/addExchange',
                contentType: "application/json",
			    data: JSON.stringify(vm.exchange),
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
                var guids = []
                for(var i = 0;i < ids.length;i++){
                    //  获取行数据
                    var rowData = $("#jqGrid").getRowData(ids[i])
                    guids.push(rowData.guid)
                }
				$.ajax({
					type: "POST",
				    url: baseURL + "/baseExchange/deleteExchange/" + guids,
                    contentType: "application/json",
				    data: JSON.stringify(guids),
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
                postData:{'guid': vm.q.guid},
                page:page
            }).trigger("reloadGrid");
		}
	}
});