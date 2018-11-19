var vm = new Vue({
	el:'#pageShow',
	data:{
		bookList:null
	},
	methods: {
		getBookList:function(){
			axios.post('getBookList').then(function (response) {
				vm.bookList = response.data.result;
			    console.log(response);
		    }).catch(function (error) {
			    console.log(error);
			});
		}
	}
});

vm.getBookList();