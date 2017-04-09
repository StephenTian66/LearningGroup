import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;

import javax.imageio.ImageIO;


public class Mosaic {

	String openUrl; // 原始图片打开路径
    String saveUrl; // 新图保存路径
    String saveName; // 新图名称
    String suffix; // 新图类型 只支持jpg,png
    
    public void getInfo() {	
		System.out.println("请输入原图片地址，处理后图片的保存地址，处理后图片的名字，图片格式");
	    Scanner sc = new Scanner(System.in);
	    this.openUrl = sc.next();
	    this.saveUrl = sc.next();
	    this.saveName = sc.next();
	    this.suffix = sc.next();
	    sc.close();	    
	}

	/**
     * 马赛克化.
     * @param size  马赛克尺寸，即每个矩形的长宽
     * @return
     * @throws Exception
     */
    public boolean mosaic() throws Exception {
    	int size;
        File file = new File(openUrl);
        if (!file.isFile()) {
            throw new Exception("ImageDeal>>>" + file + " 不是一个图片文件!");
        }
        BufferedImage bi = ImageIO.read(file); // 读取该图片
        BufferedImage spinImage = new BufferedImage(bi.getWidth(),
                bi.getHeight(), BufferedImage.TYPE_INT_RGB);
        //取合适的马赛克尺寸    
        if(Math.sqrt(bi.getWidth()*bi.getHeight())/100 >= 5)
        	size = bi.getWidth()/100;
        else
        	size = 8;
        
        if (bi.getWidth() < size || bi.getHeight() < size || size <= 0) { // 马赛克格尺寸太大或太小
            return false;
        }
        
        int xcount = 0; // x方向绘制个数
        int ycount = 0; // y方向绘制个数
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
        int x = 0;   //坐标
        int y = 0;
        // 绘制马赛克(绘制矩形并填充颜色)
        Graphics gs = spinImage.getGraphics();
        for (int i = 0; i < xcount; i++) {
            for (int j = 0; j < ycount; j++) {
                //马赛克矩形格大小
                 int mwidth = size;
                 int mheight = size;
                 if(i==xcount-1){   //横向最后一个比较特殊，可能不够一个size
                     mwidth = bi.getWidth()-x;
                 }
                 if(j == ycount-1){  //同理
                     mheight =bi.getHeight()-y;
                 }
              // 矩形颜色取中心像素点RGB值
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
                y = y + size;// 计算下一个矩形的y坐标
            }
            y = 0;// 还原y坐标
            x = x + size;// 计算x坐标
        }
        gs.dispose();
        File sf = new File(saveUrl, saveName + "." + suffix);
        ImageIO.write(spinImage, suffix, sf); // 保存图片
        return true;
    }
}
