var vm = new Vue({
	el:'#app',
	data : {
		courseList:[],
		chapterList:[],
		secondLevelChapterList:[],
		thirdLevelChapterList:[],
		docked: false,
	    open: true,
	    position: 'left',
	    panel: '',
	    index:1,
	    secondLevelName:null,
	    thirdLevelName:null,
	    mainHttp:mainHttp
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
		findChapterList(courseId){
			axios({method: 'post',url: mainHttp + 'chapter/findChapterList.html?courseId='+courseId}).then(function(response){
      		    if(response.data.code == 0){
      		    	vm.chapterList = response.data.result;
				}
      	  	});
		},
		toSecondLevelChapterList(chapter){
			vm.index = 2;
			vm.secondLevelChapterList = chapter.childChapterList;
			vm.secondLevelName = chapter.name
		},
		toThirdLevelChapterList(chapter){
			vm.index = 3;
			vm.thirdLevelChapterList = chapter.childChapterList;
			vm.thirdLevelName = chapter.name;
			for(var i=0 ; i<vm.thirdLevelChapterList.length ; i++){
				vm.thirdLevelChapterList[i].url = mainHttp + 'question/'+subjectId+'/'+vm.thirdLevelChapterList[i].uid+'-1-1.html';
			}
			
		}
	}
});

vm.findCourseList(subjectId);
vm.findChapterList(courseId);