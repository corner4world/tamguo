var validateCategory = (rule, value, callback) => {
	if(/^(undefined|null|\s)$/.test(vm.book.categoryIds[0])){
	 	  callback(new Error('请选择分类!'));
	}else{
	  vm.handleItemChange(value);
	  callback();
	}
}

// Vue
var vm = new Vue({
	el:'#container',
	data:{
		bookDomainName:null,
		totalCount:0,
		bookList:[],
		categoryList:[],
		book:{},
		imageUrl:null,
		categoryIds:null,
		bookDialogVisible:false,
		rules: {
          name: [
            { required: true, message: '请输入书籍名称', trigger: 'blur' },
            { min: 6 , message: '长度大于6个字符', trigger: 'blur' }
          ],
          categoryIds: [
            { required: true, message: '请选择分类', trigger: 'change'},
            { validator: validateCategory, trigger: ['blur','change'] }
          ]
        },
	},
	methods: {
		// 处理改变
		handleItemChange:function(val){
			console.log(val);
			// 获取书籍分类
			axios.post('getBookCategory.html?parentId='+val).then(function (response) {
			    console.log(response);
			    for(var i=0 ; i<vm.categoryList.length; i ++){
			    	var category = vm.categoryList[i];
			    	if(category.value == val){
			    		category.children = response.data.result;
			    	}
			    }
			}).catch(function (error) {
			    console.log(error);
			});
		},
		//  书籍分类
		getBookCategory:function(){
			// 获取书籍分类
			axios.post('getBookCategory.html').then(function (response) {
			    console.log(response);
			    vm.categoryList = response.data.result;
			}).catch(function (error) {
			    console.log(error);
			});
		},
		// 获取书籍列表
		getBookList:function(){
			axios.post('getBookList.html').then(function (response) {
			    console.log(response);
			    vm.bookList = response.data.result;
			    vm.totalCount = vm.bookList.length;
			}).catch(function (error) {
			    console.log(error);
			});
		},
		// 保存书籍
		saveBook:function(){
			this.$refs['book'].validate((valid) => {
		          if (valid) {
		        	  vm.book.bookImage = vm.imageUrl;
		        	  axios.post('saveBook',vm.book).then(function (response) {
			  			    console.log(response);
			  			    if(response.data.code == 0){
			  			    	vm.bookDialogVisible = false;
			  			    	window.location.reload();
			  			    }else{
			  			    	this.$message.error(response.data.message);
			  			    }
			  		  }).catch(function (error) {
			  			    console.log(error);
			  		  });
		          }
	        });
		},
	    showEditDialog:function(id){
	    	axios.get('book/'+id + '.html').then(function (response) {
  			    console.log(response);
  			    if(response.data.code == 0){
  			    	vm.bookDialogVisible = true;
  			    	vm.book = response.data.result;
  			    	vm.imageUrl = vm.book.bookImage;
  			    }else{
  			    	this.$message.error(response.data.message);
  			    }
	  		  }).catch(function (error) {
	  			    console.log(error);
	  		  });
	    },
		handleAvatarSuccess(res, file) {
	        this.imageUrl = res.result.filePath;
	    },
        beforeAvatarUpload(file) {
	    	const isJPG = (file.type === 'image/jpeg' || file.type === 'image/png');
	    	const isLt2M = file.size / 1024 / 1024 < 2;
	    	if (!isJPG) {
	    		this.$message.error('上传头像图片只能是图片格式!');
	    	}
	    	if (!isLt2M) {
	    		this.$message.error('上传头像图片大小不能超过 2MB!');
	    	}
	    	return isJPG && isLt2M;
	    }
	}
});
vm.getBookList();
vm.getBookCategory();
vm.bookDomainName = bookDomainName;