import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;

import javax.imageio.ImageIO;


public class Mosaic {

	String openUrl; // ԭʼͼƬ��·��
    String saveUrl; // ��ͼ����·��
    String saveName; // ��ͼ����
    String suffix; // ��ͼ���� ֻ֧��jpg,png
    
    public void getInfo() {	
		System.out.println("������ԭͼƬ��ַ�������ͼƬ�ı����ַ�������ͼƬ�����֣�ͼƬ��ʽ");
	    Scanner sc = new Scanner(System.in);
	    this.openUrl = sc.next();
	    this.saveUrl = sc.next();
	    this.saveName = sc.next();
	    this.suffix = sc.next();
	    sc.close();	    
	}

	/**
     * �����˻�.
     * @param size  �����˳ߴ磬��ÿ�����εĳ���
     * @return
     * @throws Exception
     */
    public boolean mosaic() throws Exception {
    	int size;
        File file = new File(openUrl);
        if (!file.isFile()) {
            throw new Exception("ImageDeal>>>" + file + " ����һ��ͼƬ�ļ�!");
        }
        BufferedImage bi = ImageIO.read(file); // ��ȡ��ͼƬ
        BufferedImage spinImage = new BufferedImage(bi.getWidth(),
                bi.getHeight(), BufferedImage.TYPE_INT_RGB);
        //ȡ���ʵ������˳ߴ�    
        if(Math.sqrt(bi.getWidth()*bi.getHeight())/100 >= 5)
        	size = bi.getWidth()/100;
        else
        	size = 8;
        
        if (bi.getWidth() < size || bi.getHeight() < size || size <= 0) { // �����˸�ߴ�̫���̫С
            return false;
        }
        
        int xcount = 0; // x������Ƹ���
        int ycount = 0; // y������Ƹ���
        if (bi.getWidth() % size == 0) {
            xcount = bi.getWidth() / size;
        } else {
            xcount = bi.getWidth() / size + 1;
        }
        if (bi.getHeight() % size == 0) {
            ycount = bi.getHeight() / size;
        } else {
            ycount = bi.getHeight() / size + 1;
        }
        int x = 0;   //����
        int y = 0;
        // ����������(���ƾ��β������ɫ)
        Graphics gs = spinImage.getGraphics();
        for (int i = 0; i < xcount; i++) {
            for (int j = 0; j < ycount; j++) {
                //�����˾��θ��С
                 int mwidth = size;
                 int mheight = size;
                 if(i==xcount-1){   //�������һ���Ƚ����⣬���ܲ���һ��size
                     mwidth = bi.getWidth()-x;
                 }
                 if(j == ycount-1){  //ͬ��
                     mheight =bi.getHeight()-y;
                 }
              // ������ɫȡ�������ص�RGBֵ
                int centerX = x;
                int centerY = y;
                if (mwidth % 2 == 0) {
                    centerX += mwidth / 2;
                } else {
                    centerX += (mwidth - 1) / 2;
                }
                if (mheight % 2 == 0) {
                    centerY += mheight / 2;
                } else {
                    centerY += (mheight - 1) / 2;
                }
                Color color = new Color(bi.getRGB(centerX, centerY));
                gs.setColor(color);
                gs.fillRect(x, y, mwidth, mheight);
                y = y + size;// ������һ�����ε�y����
            }
            y = 0;// ��ԭy����
            x = x + size;// ����x����
        }
        gs.dispose();
        File sf = new File(saveUrl, saveName + "." + suffix);
        ImageIO.write(spinImage, suffix, sf); // ����ͼƬ
        return true;
    }
}
