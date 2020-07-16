package com.xuncai.parking.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Iterator;


/**
 * 图片压缩库
 * 
 * @author ningwang
 *
 */
public class ImgCompress {

	private Logger logger = LoggerFactory.getLogger(ImgCompress.class);

	// 路径地址
	private String kOriImgPath;

	private String filePath; // 文件名

	private String fileType; // 文件类型

	private String waterMarkName;// 水印名称

	private String manageType; // 压缩完成后续操作
	
	private String waterImgPostion; // 水印位置 默认为右下角 right,center

	public ImgCompress(String fileName, String fileType, String waterMarkName) {
		this.filePath = fileName;
		this.fileType = fileType;
		this.waterMarkName = waterMarkName;
	}

	public ImgCompress(String fileName, String fileType, String waterMarkName,
			String manageType) {
		this.filePath = fileName;
		this.fileType = fileType;
		this.waterMarkName = waterMarkName;
		this.manageType = manageType;
	}
	
	public ImgCompress(String fileName, String fileType, String waterMarkName,
			String manageType,String waterImgPostion) {
		this.filePath = fileName;
		this.fileType = fileType;
		this.waterMarkName = waterMarkName;
		this.manageType = manageType;
		this.waterImgPostion = waterImgPostion;
	}


	

	/**
	 * 富文本上传图片压缩图片处理
	 */
	private void manageOperate() {
		if (manageType == null || "".equals(manageType)) {
			return;
		} else if ("cropnews".equals(manageType)) {
			// 截取新闻缩略图
			editorCropImg();
		} else if ("croptopic".equals(manageType)) {
			// 截取话题缩略图
			editorTopicCropImg();
		} else if ("share".equals(manageType)) {
			// 截取分享缩略图
			shareCropImg();
		} else if ("cropnews_share".equals(manageType)) {
			// 截取新闻和分享缩略图
			editorCropImg();
			shareCropImg();
		} else if ("croptopic_share".equals(manageType)) {
			// 截取话题和分享缩略图
			editorTopicCropImg();
			shareCropImg();
		}
	}

	// #富文本截取缩略图
	private void editorCropImg() {
		String dst_img = filePath.substring(0, filePath.lastIndexOf("."))
				+ "_thum.jpeg";
		clipResizeImg(filePath, dst_img, 330, 236);
	}

	// #话题截取缩略图
	private void editorTopicCropImg() {
		String dst_img = filePath.substring(0, filePath.lastIndexOf("."))
				+ "_thum.jpeg";
		clipResizeImg(filePath, dst_img, 200, 200);
	}

	// #分享缩略图
	private void shareCropImg() {
		String dst_img = filePath.substring(0, filePath.lastIndexOf("."))
				+ "_share.jpeg";
		clipResizeImg(filePath, dst_img, 300, 300);
	}

	/**
	 * 备份图片
	 * 
	 * @return
	 */
	private Boolean copyImg() {
		// 首先判断图片是否已经存在
		String copyImgFolder = filePath.substring(0, filePath.lastIndexOf("/")) + "/oriImg_bak/";
		String copyFileName = filePath.substring(filePath.lastIndexOf("/") + 1,
				filePath.length());
		File file = new File(copyImgFolder);
		// 如果文件夹不存在则创建
		if (!file.exists() && !file.isDirectory()) {
			file.mkdir();
			logger.info("创建备份文件夹：" + copyImgFolder);
		}

		// 判断备份图片是否存在
		File copyfile = new File(copyImgFolder + copyFileName);
		// 不存在拷贝文件

		if (!copyfile.exists()) {
			try {
				copyFileUsingFileChannels(new File(filePath),copyfile);
				logger.info("备份图片" + filePath + "成功");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.info("备份图片失败。" + e.getMessage());
				e.printStackTrace();
			}
			return true;
		}else{
			logger.info("备份图片" + filePath + "已经存在");
		}
		return false;
	}
	




	/**
	 * 裁剪图片
	 * 
	 * @param bgImage
	 * @param waterImg
	 * @return
	 */
	private void clipResizeImg(String filePath, String dstPath, int dst_w,
			int dst_h) {
		int ori_w; // 原图宽度
		int ori_h; // 原图高度

		int newWidth; // 最终生成图片宽度
		int newHeight; // 最终生成图片高度

		int cropX, cropY; // 截取开始横竖坐标
		int width, height;// 开始坐标

		try {
			Image img = ImageIO.read(new File(filePath));
			ori_w = img.getWidth(null);
			ori_h = img.getHeight(null);
			logger.info("图片宽度:" + ori_w + "--图片高度:" + ori_h);

			float dst_scale = (float) (dst_w) / dst_h; // #目标高宽比
			float ori_scale = (float) (ori_w) / ori_h; // #原图高宽比

			if (ori_scale <= dst_scale) {
				// #过高
				width = ori_w;
				height = (int) (width / dst_scale);
				cropX = 0;
				cropY = (ori_h - height) / 2;
			} else {
				// #过宽
				height = ori_h;
				width = (int) (height * dst_scale);
				cropX = (ori_w - width) / 2;
				cropY = 0;
			}
			logger.info("width:" + width + "height:" + height + '\n');

			// #压缩比例
			float ratio = (float) (dst_w) / width;
			float hradio = (float) (dst_h) / height;

			// # 如果目标图片尺寸大于现有尺寸 针对于小的进行压缩
			if (ratio > 1 || hradio > 1) {
				if (ratio > 1) {
					newWidth = width;
					newHeight = (int) (((float) dst_h / dst_w) * newWidth);
				} else {
					newHeight = height;
					newWidth = (int) (((float) dst_w / dst_h) * newHeight);
				}

			} else {
				newWidth = (int) (width * ratio);
				newHeight = (int) (height * ratio);
			}

			// 裁剪图片
			File imageFile = new File(filePath);
			if (!imageFile.exists()) {
				throw new IOException("Not found the images:" + filePath);
			}

			String format = getFormatName(imageFile);
			if(format == null){
				 format = filePath.substring(filePath.lastIndexOf(".") + 1,
						filePath.length());
			}
			BufferedImage image = ImageIO.read(imageFile);
			image = image.getSubimage(cropX, cropY, width, height);
			BufferedImage zoomImage = zoom(image, newWidth, newHeight);
			ImageIO.write(zoomImage, format, new File(dstPath));
			logger.info("图片截取完成" + '\n');
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 获取图片格式类型
	 */
	public String getFormatName(File file){
		 try {
	            // Create an image input stream on the image
	            ImageInputStream iis = ImageIO.createImageInputStream(file);
	            // Find all image readers that recognize the image format
	            Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
	            if (!iter.hasNext()) {
	                // No readers found
	                return null;
	            }
	            // Use the first reader
	            ImageReader reader = iter.next();
	            // Close stream
	            iis.close();
	            // Return the format name
	            return reader.getFormatName();
	        } catch (IOException e) {
	            //
	        }
	        
	        // The image could not be read
	        return null;
	}
	
	/**
	 * 压缩图片
	 * 
	 * @param sourceImage
	 *            待压缩图片
	 * @param width
	 *            压缩图片高度
	 * @param heigt
	 *            压缩图片宽度
	 */
	private static BufferedImage zoom(BufferedImage sourceImage, int width,
			int height) {
		BufferedImage zoomImage = new BufferedImage(width, height,
				sourceImage.getType());
		Image image = sourceImage.getScaledInstance(width, height,
				Image.SCALE_SMOOTH);
		Graphics gc = zoomImage.getGraphics();
		gc.setColor(Color.WHITE);
		gc.drawImage(image, 0, 0, null);
		return zoomImage;
	}

	/* *
	 * 图片打水印
	 * 
	 * @param bgImage 背景图
	 * 
	 * @param waterImg 水印图
	 * 
	 * @return 新图片路径 注：当水印图片宽度比源图片宽度-5像素大时，不予打印水印
	 */
	public boolean addImageWater(String bgImage, String waterImg) {
		System.out.println(bgImage);
		System.out.println(waterImg);
		int x = 0;
		int y = 0;

		boolean flag = false;

		try {
			// 底图，关键就在这句，使用Toolkit获取图片信息，而不使用imageIO.read()方法
			Image image = Toolkit.getDefaultToolkit().getImage(bgImage);
			BufferedImage bufferedImage = toBufferedImage(image);
			File file = new File(bgImage);
			String fileName = file.getName();

			// 扩展名
			String kuozhan = null;
			String[] kuozhanArray = fileName.split("\\.");

			if (kuozhanArray.length > 0) {
				kuozhan = kuozhanArray[kuozhanArray.length - 1];
			}

			// 底图宽
			int width = image.getWidth(null);
			// 底图高
			int height = image.getHeight(null);

			Graphics2D g = bufferedImage.createGraphics();
			g.drawImage(image, 0, 0, width, height, null);

			// 水印文件

			Image waterImage = ImageIO.read(new File(waterImg));
			int width_water = waterImage.getWidth(null);
			int height_water = waterImage.getHeight(null);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					1));

			if (width_water <= width - 5) {
				
				// 水印起始横坐标
				int widthDiff = width - width_water;
				// 水印起始纵坐标
				int heightDiff = height - height_water;


				if(waterImgPostion != null && "center".equals(waterImgPostion)){
					x = widthDiff/2;
					y = heightDiff/2;
				}else{
					// 横坐标让出5px
					if (widthDiff - 5 > 0) {
						x = widthDiff - 5;
					} else {
						x = widthDiff;
					}
					// 纵坐标让出5px
					if (heightDiff - 5 > 0) {
						y = heightDiff - 5;
					} else {
						y = heightDiff;
					}
				}
				// 水印画到画板
				g.drawImage(waterImage, x, y, width_water, height_water, null);
				g.dispose();

				File newImg = new File(bgImage);
				flag = ImageIO.write(bufferedImage, kuozhan, newImg);
			} else {
				logger.info("水印图片宽度比源图片宽度-5像素大，不打印水印");

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return flag;
	}

	/* *
	 * 返回bufferedImage
	 * 
	 * @param image image对象
	 * 
	 * @return bufferedImage对象
	 */
	public static BufferedImage toBufferedImage(Image image) {

		if (image instanceof BufferedImage) {
			return (BufferedImage) image;
		}

		image = new ImageIcon(image).getImage();
		BufferedImage bimage = null;
		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		try {
			int transparency = Transparency.OPAQUE;
			GraphicsDevice gs = ge.getDefaultScreenDevice();
			GraphicsConfiguration gc = gs.getDefaultConfiguration();
			bimage = gc.createCompatibleImage(image.getWidth(null),
					image.getHeight(null), transparency);
		} catch (HeadlessException e) {
		}
		if (bimage == null) {
			int type = BufferedImage.TYPE_INT_RGB;
			bimage = new BufferedImage(image.getWidth(null),
					image.getHeight(null), type);
		}
		Graphics g = bimage.createGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return bimage;
	}

	
	
	
	/**
	 * 拷贝图片方法
	 * @param source
	 * @param dest
	 * @throws IOException
	 */
	private static void copyFileUsingFileChannels(File source, File dest) throws IOException {    
	        FileChannel inputChannel = null;    
	        FileChannel outputChannel = null;    
	    try {
	        inputChannel = new FileInputStream(source).getChannel();
	        outputChannel = new FileOutputStream(dest).getChannel();
	        outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
	    } finally {
	        inputChannel.close();
	        outputChannel.close();
	    }
	}
	
	
	public static void main(String args[]) throws IOException,
			InterruptedException {
		ImgCompress imgCompress = new ImgCompress("/Users/ningwang/Downloads/fa5c2f51ebb84aa2a6b320f23a70d8ab.jpg","editorNoCompress","","cropnews");
		//imgCompress.exec();
//		String string = imgCompress.getFormatName(new File("/Users/ningwang/Downloads/11.jpg"));
//		System.out.print("string:" + string);
	}
	
	
}
