public class Test {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub	
		
		Mosaic mosaic_test = new Mosaic();
		mosaic_test.getInfo();
		//输入示例    e://1.jpg     e://   1-mosaic    jpg   
		mosaic_test.mosaic();
		
		System.out.println("马赛克化完成，进入 " + mosaic_test.saveUrl +" 查看");
	}

}
