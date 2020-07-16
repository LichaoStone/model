package com.xuncai.parking.common.util;

import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.MemoryCacheImageInputStream;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.*;
import java.util.Iterator;

/**
 * 作者：
 * 创建日期： 2018-05-11
 * 此类用途：图片工具类
 */
public final class ImageUtil { 
	
    public static final String PNG="png";  
    public static final String JPG="jpg";  
    public static final String BMP="bmp";  
    public static final String GIF="gif";  
    
    @SuppressWarnings("deprecation")
	public static String zoom(HttpServletRequest request,byte[] imageByte, int width, String formart,String uploadImagePath) {		
		
    	if (StringUtils.isBlank(formart)) {
			formart=JPG;			
		}
    	String fileDir = request.getRealPath("/")+uploadImagePath ;
    	File folder  = new File(fileDir);
		if (!folder.exists() || !folder.isDirectory()) {
			folder.mkdirs();
		}
		String picTo = uploadImagePath+"/"+PkCreat.getTablePk()+"."+formart;
		try {
			InputStream input = new ByteArrayInputStream(imageByte);
            //BufferedImage bi = ImageIO.read(input);
            Thumbnails.of(input).scale(1f).toFile(request.getRealPath("/")+picTo);
			input.close();
//			RenderedImage   rendImage = getScaleImage(imageByte,formart.toUpperCase(), width);
//			//以下是将图形保存为标准图片格式
//			ImageIO.write(rendImage,formart.toUpperCase(),new File(request.getRealPath("/")+picTo)); 
		} catch (IOException e) {
			 
		}
		picTo = picTo.replace("datafiles", "");
		return picTo;
	}
    public static final BufferedImage getSharperPicture(BufferedImage originalPic){
        int imageWidth = originalPic.getWidth();
        int imageHeight = originalPic.getHeight();

        BufferedImage newPic = new BufferedImage(imageWidth, imageHeight,
                BufferedImage.TYPE_3BYTE_BGR);
        float[] data =
                { -1.0f, -1.0f, -1.0f, -1.0f, 10.0f, -1.0f, -1.0f, -1.0f, -1.0f };

        Kernel kernel = new Kernel(3, 3, data);
        ConvolveOp co = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        co.filter(originalPic, newPic);
        return newPic;
    }

    /** 
     * 构建一个image对象 
     *  
     * @param img 
     * @return 
     * @throws IOException 
     */  
    public static ImageInfo getImageInfo(byte[] img) throws IOException {  
        ByteArrayInputStream bais = new ByteArrayInputStream(img);  
        MemoryCacheImageInputStream is=new MemoryCacheImageInputStream(bais);  
        Iterator<ImageReader> it=ImageIO.getImageReaders(is);  
        ImageReader r=null;  
        while(it.hasNext()){  
            r=it.next();  
            break;  
        }  
        if(r==null){  
            return null;  
        }  
        ImageInfo i=new ImageInfo();  
        i.setType(r.getFormatName().toLowerCase());  
        int index=r.getMinIndex();  
        /** 
         * 对于ImageReader的线程安全是不确定的 
         */  
        synchronized (r) {  
            r.setInput(is);  
            i.setHeight(r.getHeight(index));  
            i.setWidth(r.getWidth(index));  
        }  
        return i;  
    }  
    public static BufferedImage getImage(byte[] img) throws IOException {  
        ByteArrayInputStream bais = new ByteArrayInputStream(img);  
        BufferedImage src = ImageIO.read(bais);  
        return src;  
    }  
    /** 
     * 等比例缩放 
     * @param img 
     * @param width 
     * @return 
     * @throws IOException 
     */  
    public static BufferedImage getScaleImage(byte[] img,String type,int width) throws IOException {  
        ByteArrayInputStream bais = new ByteArrayInputStream(img);  
        BufferedImage src = ImageIO.read(bais); 
        int w=src.getWidth();  
        int h=src.getHeight(); 
        if (width <= 0) {
        	width = w  ;
		}
        int height=(int) (((float)width/w)*h);  
        Image im = src.getScaledInstance(width, height,BufferedImage.SCALE_SMOOTH);
        BufferedImage bi=new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  
        bi.getGraphics().drawImage(im, 0, 0,width,height,null);  
        return bi;  
    }  
    public static byte[] getScaleImageBytes(byte[] img,String type,int width) throws IOException {  
        BufferedImage bi=getScaleImage(img, type, width);  
        ByteArrayOutputStream out=new ByteArrayOutputStream();  
        ImageIO.write(bi, type, out);  
        return out.toByteArray();  
    }  
    /** 
     * 获取文件类型,没找到返回null,这方法太高效了,可能不准确,  
     * @param byte1 
     * @return 
     */  
    public static String fastParseFileType(byte[] byte1) {  
        if ((byte1[0] == 71) && (byte1[1] == 73) && (byte1[2] == 70)  
                && (byte1[3] == 56) && ((byte1[4] == 55) || (byte1[4] == 57))  
                && (byte1[5] == 97)) {  
            return GIF;  
        }  
        if ((byte1[6] == 74) && (byte1[7] == 70) && (byte1[8] == 73)  
                && (byte1[9] == 70)) {  
            return JPG;  
        }  
        if ((byte1[0] == 66) && (byte1[1] == 77)) {  
            return BMP;  
        }  
        if ((byte1[1] == 80) && (byte1[2] == 78) && (byte1[3] == 71)) {  
            return PNG;  
        }  
        return null;  
    }
    public static class ImageInfo{  
        private String type;  
        private int width;  
        private int height;  
        public String getType() {  
            return type;  
        }  
        public void setType(String type) {  
            this.type = type;  
        }  
        public int getWidth() {  
            return width;  
        }  
        public void setWidth(int width) {  
            this.width = width;  
        }  
        public int getHeight() {  
            return height;  
        }  
        public void setHeight(int height) {  
            this.height = height;  
        }  
    } 
    
}  