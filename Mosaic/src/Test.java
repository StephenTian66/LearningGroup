public class Test {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub	
		
		Mosaic mosaic_test = new Mosaic();
		mosaic_test.getInfo();
		//����ʾ��    e://1.jpg     e://   1-mosaic    jpg   
		mosaic_test.mosaic();
		
		System.out.println("�����˻���ɣ����� " + mosaic_test.saveUrl +" �鿴");
	}

}
