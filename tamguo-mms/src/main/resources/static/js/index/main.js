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
		
	}
});