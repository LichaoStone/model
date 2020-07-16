package com.xuncai.parking.common.util.img;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
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
 * @author
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
	 * 执行方法
	 */
	public void exec() {
		logger.info("filePath:" + filePath + "-- fileType:" + fileType
				+ "--waterMarkName:" + waterMarkName + "--manageType:"
				+ manageType);
		// 1.目标文本编辑图图片
		copyImg();
		// 2.判断fileType类型 然后进行不同类型处理

		int dst_w;
		int dst_h;
		float save_quality;
		if (fileType.equals("750UserImg")) {
			// 2.1 #如果是活动专区/抽奖互动/现场直播/快拍分享/精彩专题的主题图/封面图；单图广告/轮播广告图；
			dst_w = 750;
			dst_h = 750;
			save_quality = (float) 0.8;
			resizeImg(filePath, filePath, dst_w, dst_h, save_quality);
		} else if (fileType.equals("editorOriImg")) {
			// 2.2 #富文本图片
			dst_w = 750;
			dst_h = 750;
			save_quality = (float) 0.8;
			resizeImg(filePath, filePath, dst_w, dst_h, save_quality);
		} else if (fileType.equals("330UserThuImg")) {
			// 2.3用户上传压缩330缩略图
			dst_w = 330;
			dst_h = 236;
			save_quality = (float) 0.9;
			resizeImg(filePath, filePath, dst_w, dst_h, save_quality);
		} else if (fileType.equals("editorCompress")) {
			// 2.4富文本上传图片 需要走压缩
			dst_w = 750;
			dst_h = 750;
			save_quality = (float) 0.8;
			resizeImg(filePath, filePath, dst_w, dst_h, save_quality);
			manageOperate();
		} else if (fileType.equals("editorNoCompress")) {
			// 2.5富文本上传图片 不需要走压缩
			manageOperate();
		}
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
	 * 压缩图片
	 */
	private void resizeImg(String filePath, String dstPath, int dst_w,
			int dst_h, float save_q) {
		logger.info("图片压缩处理开始执行，参数为inputFile：" + filePath + ",outputFile:"
				+ dstPath + ",outputWidth:" + dst_w + ",outputHeight:" + dst_h
				+ ",quality:" + save_q);

		// 获得源文件
		if (!new File(filePath).exists()) {
			logger.info("源文件不存在");
			return;
		}
		int ori_w; // 原图宽度
		int ori_h; // 原图高度

		int newWidth; // 最终生成图片宽度
		int newHeight; // 最终生成图片高度

		//
		try {
			Image img = ImageIO.read(new File(filePath));
			ori_w = img.getWidth(null);
			ori_h = img.getHeight(null);
			logger.info("图片宽度:" + ori_w + "--图片高度:" + ori_h + "--图片宽高比：" + (float) (ori_w) / ori_h );


			// 缩略图压缩规则
			if ((ori_w > 0 && ori_w <= dst_w) && (ori_h > 0 && ori_h <= dst_h)) {
				// a，图片宽或者高均小于或等于1280时图片尺寸保持不变，但仍然经过图片压缩处理，得到小文件的同尺寸图片
				logger.info("图片宽或者高均小于或等于" + dst_w
						+ "时图片尺寸保持不变，但仍然经过图片压缩处理，得到小文件的同尺寸图片");
				newWidth = ori_w;
				newHeight = ori_h;
			} else if (((ori_w > 0 && ori_w > dst_w) || (ori_h > 0 && ori_h > dst_h))
					&& (((float) (ori_w) / ori_h <= 2) && ((float) (ori_h) / ori_w <= 2))) {
				// #b，宽或者高大于1280，但是图片宽度高度比小于或等于2，则将图片宽或者高取大的等比压缩至1280
				logger.info("宽或者高大于" + dst_w
						+ "，但是图片宽度高度比小于或等于2，则将图片宽或者高取大的等比压缩至" + dst_w);
				if (ori_w >= ori_h) {
					newWidth = dst_w;
					newHeight = (int) (((float) (ori_h) / ori_w) * newWidth);
				} else {
					newHeight = dst_h;
					newWidth = (int) (((float) (ori_w) / ori_h) * newHeight);
				}
			} else if (((ori_w > 0 && ori_w > dst_w) && (ori_h > 0 && ori_h > dst_h))
					&& (((float) (ori_w) / ori_h > 2) || ((float) (ori_h)
							/ ori_w > 2))) {
				// #c，宽或者高大于1280，但是图片宽高比大于2时，并且宽以及高均大于1280，则宽或者高取小的等比压缩至1280
				logger.info("宽或者高大于" + dst_w
						+ "，但是图片宽高比大于2时，并且宽以及高均大于750，则宽或者高取小的等比压缩至" + dst_w);
				if (ori_w >= ori_h) {
					newHeight = dst_h;
					newWidth = (int) (((float) (ori_w) / ori_h) * newHeight);
				} else {
					newWidth = dst_w;
					newHeight = (int) (((float) (ori_h) / ori_w) * newWidth);
				}
			} else {
				// #d，宽或者高大于1280，但是图片宽高比大于2时，并且宽或者高其中一个小于1280，则压缩至同尺寸的小文件图片
				// 只压缩质量
				logger.info("宽或者高大于" + dst_w + "，但是图片宽高比大于2时，并且宽或者高其中一个小于"
						+ dst_w + "，则压缩至同尺寸的小文件图片");
				newWidth = ori_w;
				newHeight = ori_h;
			}

			logger.info("计算获取得到的目标图片的宽度为：" + newWidth + ",高度为：" + newHeight);

			// 得到文件的扩展名(无扩展名时将得到全名)
			String t_ext = filePath.substring(filePath.lastIndexOf(".") + 1)
					.trim().toLowerCase();

			logger.info("获取到的扩展名为：" + t_ext);

			if (Config.PNG.equals(t_ext)) {
				logger.info(t_ext + "图片处理");
				BufferedImage tempImage = new BufferedImage(newWidth,
						newHeight, BufferedImage.TYPE_INT_ARGB);
				Graphics2D g2D = (Graphics2D) tempImage.getGraphics();
				g2D.drawImage(img.getScaledInstance(newWidth, newHeight,
						Image.SCALE_SMOOTH), 0, 0, null);
				ImageIO.write(tempImage, Config.PNG, new File(dstPath));

			} else {
				logger.info(t_ext + "图片处理");
				BufferedImage tag = new BufferedImage(newWidth, newHeight,
						BufferedImage.TYPE_INT_RGB);
				tag.getGraphics().drawImage(
						img.getScaledInstance(newWidth, newHeight,
								Image.SCALE_SMOOTH), 0, 0, Color.WHITE, null);
				FileOutputStream out = new FileOutputStream(new File(dstPath));

				// JPEGImageEncoder可适用于其他图片类型的转换
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
				// 压缩质量
				jep.setQuality(save_q, true);
				encoder.setJPEGEncodeParam(jep);
				encoder.encode(tag);
				out.close();
			}
			// 判断是否添加水印
			if (waterMarkName != null && !"".equals(waterMarkName)) {
				logger.info("开始添加水印   水印图片地址:" + waterMarkName);
				addImageWater(dstPath, waterMarkName);
				logger.info("水印添加完成 ");
			}
			logger.info("图片压缩完成");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("图片压缩异常", e);
			return;
		}
	}

	/**
	 * 裁剪图片
	 * @param filePath
	 * @param dstPath
	 * @param dst_w
	 * @param dst_h
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
	 * @param height
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
		ImgCompress imgCompress = new ImgCompress("C:/Users/Administrator/Desktop/test/images/461c5f2f616607e360bec9882cf2b84.png","editorOriImg","","");
		imgCompress.exec();
//		String string = imgCompress.getFormatName(new File("/Users/ningwang/Downloads/11.jpg"));
//		System.out.print("string:" + string);
	}
	
	
}
