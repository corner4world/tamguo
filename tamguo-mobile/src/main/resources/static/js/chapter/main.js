var vm = new Vue({
	el:'#app',
	data : {
		courseList:[],
		chapterList:[],
		docked: false,
	    open: true,
	    position: 'left',
	    panel: ''
	},
	methods:{
		findCourseList(subjectId){
			axios({method: 'post',url: mainHttp + 'chapter/findCourseList.html?subjectId='+subjectId}).then(function(response){
      		    if(response.data.code == 0){
      		    	vm.courseList = response.data.result;
				}
      	  	});
		},
		findChapterList(courseId){
			axios({method: 'post',url: mainHttp + 'chapter/findChapterList.html?courseId='+courseId}).then(function(response){
      		    if(response.data.code == 0){
      		    	vm.courseList = response.data.result;
				}
      	  	});
		}
	}
});

vm.findCourseList(subjectId);
vm.findChapterList(courseId);