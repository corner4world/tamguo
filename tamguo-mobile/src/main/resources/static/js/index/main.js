var vm = new Vue({
	el:'#app',
	data : {
		menus:[]
	},
	methods:{
		findAllMenus(){
			axios({method: 'post',url: mainHttp + 'menu/findAllMenus.html'}).then(function(response){
      		    if(response.data.code == 0){
      		    	vm.menus = response.data.result;
				}
      	  	});
		}
	}
});

vm.findAllMenus();