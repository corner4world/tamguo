var vm = new Vue({
	el:'#app',
	data() {
	      return {
	    	loading:false,
	        courses:[],
	        member:{
	        	name:null
	        },
	        rules: {
	        	username: [
	              { required: true, message: '请输入用户名', trigger: 'blur' }
	            ],
	        	nickName: [
	              { required: true, message: '请输入用户名', trigger: 'blur' }
	            ]
	        }
	      };
	    },
	    methods: {
		      handleAvatarSuccess(res, file) {
		    	  vm.member.avatar = res.filePath;
		    	  vm.member.imageUrl = res.fileDomain + res.filePath;
		      },
		      beforeAvatarUpload(file) {
			        const isJPG = file.type === 'image/jpeg';
			        const isLt2M = file.size / 1024 / 1024 < 2;
		
			        if (!isJPG) {
			          this.$message.error('上传头像图片只能是 JPG 格式!');
			        }
			        if (!isLt2M) {
			          this.$message.error('上传头像图片大小不能超过 2MB!');
			        }
			        return isJPG && isLt2M;
		      },
		      onSubmit:function(){
		    	  loading = true;
		    	  this.$refs['member'].validate((valid) => {
			          if (valid) {
			            axios({method: 'post',url: mainHttp + 'account/update.html',data: vm.member}).then(function(response){
			      		    if(response.data.code == 0){
			      		    	vm.loading = false;
			      		    	vm.$message({message: "修改成功",duration:500,type: 'success',onClose:function(){
			      		    		window.location.reload();
			      		    	}});
							}else{
								vm.loading = false;
								vm.$message.error(response.data.message);
								vm.$refs['member'].validate();
							}
			      	  	});
			          } else {
			            console.log('error submit!!');
			            return false;
			          }
		          });
		      },
		      getMember:function(){
		    	  axios.get(mainHttp + 'getCurrentMember').then(function(response){
		    		  vm.member = response.data.result;
		    		  console.log(vm.member.avatar);
		    		  vm.member.imageUrl = mainHttp + vm.member.avatar;
		    	  });  
		      }
	    }
});
vm.getMember();