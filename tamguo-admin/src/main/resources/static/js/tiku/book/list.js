$(function () {
    $("#jqGrid").jqGrid({
        url: mainHttp + 'book/queryPage.html',
        datatype: "json",
        colModel: [			
			{ label: '书籍ID', name: 'uid', width: 40, key: true , hidden:true},
			{ label: '科目名称', name: 'courseName', width: 60 },
			{ label: '书籍名称', name: 'name', width: 60 },
			{ label: '题目数量', name: 'questionNum', width: 60 },
			{ label: '知识点数量', name: 'pointNum', width: 100 },
			{ label: '排序', name: 'orders', width: 60 }
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
            root: "list",
            page: "currPage",
            total: "totalPage",
            records: "totalCount"
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

var setting = {
	data: {
		simpleData: {
			enable: true,
			idKey: "uid",
			pIdKey: "parentId",
			rootPId: -1
		},		
		key: {
			url:"nourl"
		}
	},
	view: {
		addHoverDom: addHoverDom,
		removeHoverDom: removeHoverDom,
		selectedMulti: false
	},
	edit: {
		enable: true,
		showRemoveBtn: showRemoveBtn	
	}
};
var newCount = 1;
function addHoverDom(treeId, treeNode) {
	var sObj = $("#" + treeNode.tId + "_span");
	if(treeNode.level <3){
		if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
		var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
			+ "' title='add node' onfocus='this.blur();'></span>";
		sObj.after(addStr);
		var btn = $("#addBtn_"+treeNode.tId);
		if (btn) btn.bind("click", function(){
			var zTree = $.fn.zTree.getZTreeObj("menuTree");
			zTree.addNodes(treeNode, {uid:(100 + newCount), parentId:treeNode.id, name:"章节" + (newCount++)});
			return false;
		});
	}
};
function removeHoverDom(treeId, treeNode) {
	$("#addBtn_"+treeNode.tId).unbind().remove();
};  
function showRemoveBtn(treeId, treeNode) { 
    if(treeNode.courseId != null){
    	return false;
    }else{
    	true;
    }
}
var ztree;
var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		subjectList:null,
		courseList:null,
		q:{
			name:null
		},
		book:{
			name:null,
			subjectId:null,
			courseId:null,
			pointNum:null,
			questionNum:null,
			orders:0,
			chapterList:[]
		}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		getMenu: function(bookId){
			//加载菜单树
			return axios.get(mainHttp + "course/getChapterTree/"+bookId+".html");
		},
		getSubjectList: function(){
			return axios.get(mainHttp + "subject/getSubject.html");
		},
		getCouseList: function(){
			return axios.get(mainHttp + "course/findBySubjectId.html?subjectId="+vm.book.subjectId);
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.book = {uid:null,name:null,subjectId:null,courseId:null,pointNum:null,questionNum:null,orders:0};
			axios.all([vm.getMenu() , vm.getSubjectList()]).then(axios.spread(function (mResponse,sResponse) {
				ztree = $.fn.zTree.init($("#menuTree"), setting, mResponse.data.result);
				
				vm.subjectList = sResponse.data.result;
            }));
		},
		update: function (event) {
			var bookId = getSelectedRow();
			if(bookId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
			axios.all([vm.getMenu(bookId) , vm.getSubjectList(), vm.getBook(bookId)]).then(axios.spread(function (mResponse , sResponse, cResponse) {
            	ztree = $.fn.zTree.init($("#menuTree"), setting, mResponse.data.result);
            	
            	vm.subjectList = sResponse.data.result;
            	vm.book = cResponse.data.result;
            	
            	// 获取科目
				axios.all([vm.getCouseList() , vm.getBook(bookId)]).then(axios.spread(function (cResponse , bResponse) {
					vm.courseList = cResponse.data.result;
					vm.book = bResponse.data.result;
	            }));
				
            }));
		},
		getBook:function(bookId){
			return axios.get(mainHttp + "book/info/"+bookId+".html");
		},
		del: function (event) {
			var bookIds = getSelectedRows();
			if(bookIds == null){
				return ;
			}
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: mainHttp + "book/delete.html",
				    data: JSON.stringify(bookIds),
				    success: function(r){
				    	if(r.code === 0){
							alert('操作成功', function(index){
								vm.reload();
							});
						}else{
							alert(r.message);
						}
					}
				});
			});
		},
		saveOrUpdate: function (event) {
			var url = vm.book.uid == null ? mainHttp + "book/save.html" : mainHttp + "book/update.html";
			// 获取章节
			var node = ztree.getNodes();
		    var nodes = ztree.transformToArray(node);
		    Vue.set(vm.book, 'chapterList', nodes);
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.book),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.message);
					}
				}
			});
		},
		menuTree: function(){
			layer.open({
				type: 1,
				offset: '50px',
				skin: 'layui-layer-molv',
				title: "选择菜单",
				area: ['300px', '450px'],
				shade: 0,
				shadeClose: false,
				content: jQuery("#menuLayer"),
				btn: ['确定', '取消'],
				btn1: function (index) {
					var node = ztree.getSelectedNodes();
					//选择上级菜单
					vm.menu.parentId = node[0].uid;
					vm.menu.parentName = node[0].name;
					
					layer.close(index);
	            }
			});
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
				postData:{'name': vm.q.name},
                page:page
            }).trigger("reloadGrid");
		},
		changeSubject:function(){
			axios.all([this.getCouseList()]).then(axios.spread(function (cResponse) {
				vm.courseList = cResponse.data.result;
            }));
		}
	},
	watch:{
		  // 数据修改时触发
	      subjectList: function() {
	        this.$nextTick(function(){
		        $('#subjectId').selectpicker('refresh');
	        })
	      },
	      courseList: function() {
	        this.$nextTick(function(){
		        $('#courseId').selectpicker('refresh');
	        })
	      }
	}
});