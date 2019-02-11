$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/drivemonitor/list',
        datatype: "json",
        colModel: [
            { label: 'id', name: 'id', index: 'Id', width: 50, key: true},
            { label: '状态', name: 'status', index: 'status', width: 60 ,
                formatter: function(value, options, row){
                    if(value==''||value==null){
                        return '';
                    }
                    for(var i=0;i<vm.statusList.length;i++){
                        if(value==vm.statusList[i].value){
                            return vm.statusList[i].name;
                        }
                    }
                }
            },
            { label: '进出区标志', name: 'ioFlag', index: 'io_flag', width: 100 ,
                formatter: function(value, options, row){
                    if(value==''||value==null){
                        return '';
                    }
                    for(var i=0;i<vm.ioFlagList.length;i++){
                        if(value==vm.ioFlagList[i].value){
                            return vm.ioFlagList[i].name;
                        }
                    }
                }
            },
            { label: '关区代码', name: 'customs', index: 'customs', width: 90

            },
            { label: '进出区流向', name: 'ioFlow', index: 'io_flow', width: 100 ,
                formatter: function(value, options, row){
                    if(value==''||value==null){
                        return '';
                    }
                    for(var i=0;i<vm.ioFlowList.length;i++){
                        if(value==vm.ioFlowList[i].value){
                            return vm.ioFlowList[i].name;
                        }
                    }
                }
            },
            { label: '卡口类型', name: 'gateType', index: 'gate_type', width: 100 ,
                formatter: function(value, options, row){
                    if(value==''||value==null){
                        return '';
                    }
                    for(var i=0;i<vm.gateTypeList.length;i++){
                        if(value==vm.gateTypeList[i].value){
                            return vm.gateTypeList[i].name;
                        }
                    }
                }
            },
            { label: '核放单编号', name: 'billNo', index: 'bill_no', width: 180 },
            { label: '车辆编号', name: 'veNo', index: 've_no', width: 80 },
            /*{ label: '进场时间', name: 'entryTime', index: 'entry_time', width: 120,
             formatter: function(str, options, row){
             if(str!=null){
             if(str.length>=14){
             var year=str.slice(0,4);
             var month=str.slice(4,6);
             var date=str.slice(6,8);
             var hour=str.slice(8,10);
             var minute=str.slice(10,12);
             var second=str.slice(12,14);
             return year+'-'+month+'-'+date+' '+hour+':'+minute+':'+second;
             }else if(str.length>=8 && str.length<14){
             var year=str.slice(0,4);
             var month=str.slice(4,6);
             var date=str.slice(6,8);
             return year+'-'+month+'-'+date;
             }
             }
             return "";
             }
             },*/
            { label: '发送状态', name: 'sendstatus', index: 'sendstatus', width: 70,
                formatter: function(value, options, row){
                    if(value==''||value==null){
                        return '';
                    }
                    for(var i=0;i<vm.sendstatusList.length;i++){
                        if(value==vm.sendstatusList[i].value){
                            return vm.sendstatusList[i].name;
                        }
                    }
                }
            },
            { label: '接收状态', name: 'receivestatus', index: 'receivestatus', width: 90,
                formatter: function(value, options, row){

                    if(value==''||value==null){
                        return '';
                    }
                    for(var i=0;i<vm.recvstatusList.length;i++){
                        if(value==vm.recvstatusList[i].value){
                            return vm.recvstatusList[i].name;
                        }
                    }

                }
            },
            { label: '经营单位编号', name: 'tradeCo', index: 'trade_co', width: 120 },
            { label: '经营单位名称', name: 'tradeName', index: 'trade_name', width: 220 },
            { label: '备注', name: 'note', index: 'note', width: 80 },
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,

        shrinkToFit:false, //设置水平导航栏
        autoScroll: true, //设置水平导航栏

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
       /* gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }*/
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		driveMonitor: {},
        sendstatusList:[
            {name:"<font color='red'>未上传</font>",value:'9'},
            {name:"<font color='green'>已上传</font>",value:'1'},
            {name:"<font color='red'>上传失败</font>",value:'2'}
        ],
        recvstatusList:[
            {name:"<font color='red'>初始未处理</font>",value:'9'},
            {name:"<font color='green'>文件已被接收</font>",value:'1'},
            {name:"<font color='red'>文件未被接收</font>",value:'2'}
        ],
        statusList:[
            {name:"<font color='red'>C:查验</font>",value:'C'},
            {name:"<font color='green'>S:放行</font>",value:'S'}
        ],
        ioFlagList:[
            {name:"I:入区",value:'I'},
            {name:"O:出区",value:'O'}
        ],
        ioFlowList:[
            {name:"0:国内",value:'0'},
            {name:"1:境外",value:'1'}
        ],
        gateTypeList:[
            {name:"1:一线外卡口",value:'1'},
            {name:"2:二线外卡口",value:'2'},
            {name:"3:内卡口",value:'3'}
        ]

	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.driveMonitor = {
			    status:"S",
                ioFlag:"I",
                customs:"3516",
                ioFlow:"0",
                gateType:"2",
                tradeCo:"351286006E",
                tradeName:"福建跨境通电子商务有限公司"
			};
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
			var url = vm.driveMonitor.id == null ? "sys/drivemonitor/save" : "sys/drivemonitor/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.driveMonitor),
			    success: function(r){
			    	if(r.code === 0){
						alert(r.msg, function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
        savelocal: function (event) {
            var url = vm.driveMonitor.id == null ? "sys/drivemonitor/savelocal" : "sys/drivemonitor/savelocal";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.driveMonitor),
                success: function(r){
                    if(r.code === 0){
                        alert(r.msg, function(index){
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
				    url: baseURL + "sys/drivemonitor/delete",
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
        updatestate: function (event) {
            var ids = getSelectedRows();
            if(ids == null){
                return ;
            }

            confirm('是否重新检查选中的核放单的接收情况？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/drivemonitor/updatestate",
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
        resend: function (event) {
            var ids = getSelectedRows();
            if(ids == null){
                return ;
            }

            confirm('确定要重新发送到FTP吗？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/drivemonitor/resend",
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
			$.get(baseURL + "sys/drivemonitor/info/"+id, function(r){
                vm.driveMonitor = r.driveMonitor;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});