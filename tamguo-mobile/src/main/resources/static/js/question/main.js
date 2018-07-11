var vm = new Vue({
	el:'#app',
	data : {
	   docked: false,
       open: false,
       position: 'left',
       panel: '',
       total:0,
       question:{},
       topUrl:"",
       nextUrl:null,
       warnOpen:false,
       warnMessage:'',
       courseList:[]
	},
	methods:{
		findCourseList(subjectId){
			axios({method: 'post',url: mainHttp + 'chapter/findCourseList.html?subjectId='+subjectId}).then(function(response){
      		    if(response.data.code == 0){
      		    	vm.courseList = response.data.result;
      		    	for(var i=0 ; i<vm.courseList.length ; i++){
      		    		vm.courseList[i].url = mainHttp + "chapter/" + vm.courseList[i].subjectId+"/"+vm.courseList[i].uid+".html";
      		    	}
				}
      	  	});
		},
		getQuestion(chapterId , current , size){
			axios({method: 'post',url: mainHttp + 'question/'+subjectId+'/'+chapterId+'-'+current+'-'+size+'.html'}).then(function(response){
      		    if(response.data.code == 0){
      		    	if(response.data.result.records.length > 0){
      		    		vm.question = response.data.result.records[0];
      		    	}
      		    	vm.total = response.data.result.total;
      		    	
      		    	
      		    	// 处理上一页下一页
      		    	var previous = parseInt(current)-1;
      		    	var next = parseInt(current)+1;
      		    	if(previous <= 0){
      		    		previous = 1;
      		    	}
      		    	if(next >= vm.total){
      		    		next = vm.total;
      		    	}
      		    	vm.topUrl = mainHttp + 'question/'+subjectId+'/'+chapterId+'-'+previous+'-'+size+'.html';
      		    	vm.nextUrl = mainHttp + 'question/'+subjectId+'/'+chapterId+'-'+next+'-'+size+'.html';
				}
      	  	});
		}
	}
});

vm.findCourseList(subjectId);
vm.getQuestion(chapterId , current , size);