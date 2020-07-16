package com.xuncai.parking.common.util.img;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * 轻快云平台，图片处理工具类
 * @author
 * 2015-12-23
 */
public class ImgUtil {

	private Logger log = LoggerFactory.getLogger(ImgUtil.class);
	
	/* *
	 * 图片压缩处理
	 * @param inputFile 源图片路径
	 * @param outputFile 目标图片路径
	 * @param outputWidth 目标图片宽度
	 * @param outputHeight 目标图片的高度 db为false时 outputHeight 需大于0
	 * @param quality 图片清晰度 0 - 1
	 * @param db 是否等比压缩   true等比（重点看目标图片宽度，高度失效）  false:不等比，按指定的宽高
	 * @return 是否成功
	 */
	public boolean compressPic(String inputFile,String outputFile,int outputWidth,int outputHeight,float quality,boolean db) {
			
		log.info("图片压缩处理开始执行，参数为inputFile：" + inputFile + ",outputFile:" + outputFile + ",outputWidth:" + outputWidth + ",outputHeight:" + outputHeight + ",quality:" + quality + ",db:" + db);
		
		try {
			// 获得源文件
			if (!new File(inputFile).exists()) {
				
				log.info("源文件不存在");
				
				return false;
			
			}
			
			log.info("源文件存在，继续执行");
			
			Image img = ImageIO.read(new File(inputFile));
			
			// 判断图片格式是否正确
			if (img.getWidth(null) == -1) {
				
				log.info("源文件不是图片文件");
				
				return false;
			
			} else {
				
				if(outputWidth <= 0){
					
					log.info("参数中宽不能小于等于0");
					
					return false;
					
				}else{
					
					//源图片宽
					int newWidth = img.getWidth(null);
					
					//源图片高
					int newHeight = img.getHeight(null);
					
					log.info("源文件是图片文件,获取到的宽：" + newWidth + ",高：" + newHeight);
						
					if(db){
						
						log.info("等比压缩获取宽高");
						
						//等比压缩，高度不起作用处理
						outputHeight = Config.H_DEFAULT;
						
						// 为等比缩放计算输出的图片宽度及高度
						double rate1 = ((double) img.getWidth(null)) / (double) outputWidth;
						double rate2 = ((double) img.getHeight(null)) / (double) outputHeight;
							
						// 根据缩放比率大的进行缩放控制
						double rate = rate1;
						rate = rate1 > rate2 ? rate1 : rate2;
							
						newWidth = (int) (img.getWidth(null) / rate);
						newHeight = (int) (img.getHeight(null) / rate);
						
						
					}else{
						
						log.info("按传参尺寸压缩");
						
						if(outputHeight <= 0){
							
							log.info("参数中高不能小于等于0");
							
							return false;
							
						}
						
						newWidth = outputWidth;
						
						newHeight = outputHeight;
						
					}
					
					 
					
					log.info("计算获取得到的目标图片的宽度为：" + newWidth + ",高度为：" + newHeight);
					
					// 得到文件的扩展名(无扩展名时将得到全名)
					String t_ext = inputFile.substring(inputFile.lastIndexOf(".") + 1).trim().toLowerCase();
					
					log.info("获取到的扩展名为：" + t_ext);
					
					//获取目标路径文件夹
					String x_path = outputFile.substring(0,outputFile.lastIndexOf("/"));
					
					log.info("目标路径文件夹为：" + x_path);
					
					File x_file = new File(x_path);
					
					boolean flag = false;
					
					if(!x_file.exists()){
						
						flag = x_file.mkdirs();
						
						log.info("目标路径文件夹不存在，进行创建:" + flag);
						
					}else{
						
						flag = true;
						
						log.info("目标路径文件夹已存在，无需创建");
					}
					
					if(!flag){
						
						log.info("目标路径文件夹创建失败");
						return false;
						
					}
					
					if(Config.PNG.equals(t_ext)){
						
						log.info(t_ext + "图片处理");
						
						BufferedImage tempImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);  
						Graphics2D g2D = (Graphics2D) tempImage.getGraphics();  
				        g2D.drawImage(img.getScaledInstance(newWidth, newHeight,Image.SCALE_SMOOTH), 0, 0, null);  
				        
				        ImageIO.write(tempImage, Config.PNG, new File(outputFile));
				        
					}else{
						
						log.info(t_ext + "图片处理");
						
						BufferedImage tag = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
						tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight,Image.SCALE_SMOOTH), 0, 0, Color.WHITE, null);
						FileOutputStream out = new FileOutputStream(new File(outputFile));
						
						// JPEGImageEncoder可适用于其他图片类型的转换
						JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
						JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
						
						// 压缩质量
						jep.setQuality(quality, true);
						
						encoder.setJPEGEncodeParam(jep);
						encoder.encode(tag);
						out.close();
						
					}
					
					log.info("图片压缩完成");
					
				}
				
				
			}
			
		} catch (IOException ex) {
			
			log.error("图片压缩异常", ex);
			
			return false;
		}
		return true;
	}
	
	/* *
	 * 图片打水印 
	 * @param bgImage 背景图
	 * @param waterImg 水印图
	 * @return 新图片路径
	 * 注：当水印图片宽度比源图片宽度-5像素大时，不予打印水印
	 * */
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

				// 水印画到画板
				g.drawImage(waterImage, x, y, width_water, height_water, null);
				g.dispose();

				File newImg = new File(bgImage);
				flag = ImageIO.write(bufferedImage, kuozhan, newImg);
			}else{
			
				log.info("水印图片宽度比源图片宽度-5像素大，不打印水印");
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		return flag;
	}
	
	/* *
	 * 返回bufferedImage 
	 * @param image image对象
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
			bimage = gc.createCompatibleImage(image.getWidth(null), image
					.getHeight(null), transparency);
		} catch (HeadlessException e) {
		}
		if (bimage == null) {
			int type = BufferedImage.TYPE_INT_RGB;
			bimage = new BufferedImage(image.getWidth(null), image
					.getHeight(null), type);
		}
		Graphics g = bimage.createGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return bimage;
	}
	
	/* *
	 * 图片裁剪
	 * @param imagePath 源图片路径
	 * @param newPath 目标图片路径
	 * @param targetSize 目标尺寸 若想按图片尺寸裁剪，不进行缩放，targetSize可以设的无限大，但不能超过int支持位数
	 * @return 是否剪切成功
	 */
	public boolean cutImagePath(String imagePath, String newPath ,int targetSize) {

		log.info("图片裁剪开始执行，获取到的源图片路径为：" + imagePath + ",目标图片路径：" + newPath + ",目标尺寸：" + targetSize);

		if (null != imagePath && null != newPath) {
			
			log.info("源图片路径不为null");
			
			File f = new File(imagePath);
			
			if(!f.exists()){
				
				log.info("源图片不存在");
				
				return false;
				
			}
			
			if(targetSize <= 0){
				
				log.info("目标图片尺寸不可以小于等于0");
				return false;
				
			}
			
			boolean flag = false;
			
			if (newPath.contains("/")) {

				String np = newPath.substring(0, newPath.lastIndexOf("/"));
				
				log.info("获取到的文件保存路径为：" + np);
				
				File f_dirs = new File(np);
				
				if(!f_dirs.exists()){
					
					flag = f_dirs.mkdirs();
					
					log.info("创建目标文件保存的路径，结果：" + flag);
					
				}else{
					
					log.info("目标文件保存的文件夹已存在");
					
					flag = true;
					
				}
				
			}
			
			
			if(flag){
				
				log.info("目标图片文件路径创建成功");
				
				int cutSize = 0;
				
				String postFix = imagePath
						.substring(imagePath.lastIndexOf(".") + 1);

				File image = new File(imagePath);

				InputStream inputStream = null;

				try {
					inputStream = new FileInputStream(image);

					// 用ImageIO读取字节流
					BufferedImage bufferedImage = ImageIO.read(inputStream);

					BufferedImage distin = null;

					// 返回源图片的宽度。
					int srcW = bufferedImage.getWidth();
					// 返回源图片的高度。
					int srcH = bufferedImage.getHeight();
					
					log.info("获取到源图片的宽：" + srcW + ",高：" + srcH);

					
					if (srcW < srcH) { //宽小于高 取宽
						
						cutSize = srcW;
					
					} else if (srcW > srcH) { //宽大于高 取高
						
						cutSize = srcH;
					
					} else { //宽等于高
						
						cutSize = srcW;
					
					}

					log.info("获取到的目标图片的宽高为：" + cutSize);
					
					int x = 0, y = 0;

					// 使截图区域居中
					x = srcW / 2 - cutSize / 2;
					y = srcH / 2 - cutSize / 2;

					srcW = srcW / 2 + cutSize / 2;
					srcH = srcH / 2 + cutSize / 2;

					int type_int = BufferedImage.TYPE_INT_RGB;
					
					if(Config.PNG.equals(postFix)){
						
						type_int = BufferedImage.TYPE_INT_ARGB;
						
					}
					
					// 生成图片
					distin = new BufferedImage(cutSize, cutSize,
							type_int);

					Graphics2D g = (Graphics2D)distin.getGraphics();
					g.drawImage(bufferedImage, 0, 0, cutSize, cutSize, x, y, srcW,
							srcH, null);
					
					File newFile = new File(newPath);

					ImageIO.write(distin, postFix, newFile);

					if (targetSize < cutSize) {
						
						log.info("目标尺寸比获取的正方形尺寸小，进行缩放");
						
						// 如果目标尺寸比正方形边长短，再缩放到目标尺寸
						ImgUtil.resizeImgWithOldName(newPath, targetSize,
								targetSize, true);
					}

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (null != inputStream) {
						try {
							inputStream.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				
				return true;
				
			}else{
				
				log.info("目标图片文件路径创建失败");
				
				return false;
				
			}
			

		}else{
			
			log.info("源图片或目标图片路径为null");
			return false;
			
		}

	}
	
	/* *
	 * 图片缩放，生成后图片覆盖原图片 
	 * @param filePath 图片路径
	 * @param height  缩放到高度
	 * @param width 缩放宽度
	 * @param fill  比例足时是否填白 true为填白，二维码是黑白色，这里调用时建议设为true
	 */
	public static void resizeImgWithOldName(String filePath, int width,
			int height, boolean fill) {

		try {

			double ratio = 0; // 缩放比例
			File f = new File(filePath);

			String postFix = filePath.substring(filePath.lastIndexOf(".") + 1);
			;

			BufferedImage bi = ImageIO.read(f);
			Image itemp = bi.getScaledInstance(width, height,
					BufferedImage.SCALE_SMOOTH);

			if (height != 0 && width != 0) {
				// 计算比例
				if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
					if (bi.getHeight() > bi.getWidth()) {
						ratio = (new Integer(height)).doubleValue()
								/ bi.getHeight();
					} else {
						ratio = (new Integer(width)).doubleValue()
								/ bi.getWidth();
					}
					AffineTransformOp op = new AffineTransformOp(
							AffineTransform.getScaleInstance(ratio, ratio),
							null);
					itemp = op.filter(bi, null);
				}
			}

			if (fill) {
				BufferedImage image = new BufferedImage(width, height,
						BufferedImage.TYPE_INT_RGB);
				Graphics2D g = image.createGraphics();

				g.setColor(Color.white);
				g.fillRect(0, 0, width, height);
				if (width == itemp.getWidth(null)) {
					g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2,
							itemp.getWidth(null), itemp.getHeight(null),
							Color.white, null);
				} else {
					g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0,
							itemp.getWidth(null), itemp.getHeight(null),
							Color.white, null);
				}

				g.dispose();
				itemp = image;
			}

			ImageIO.write((BufferedImage) itemp, postFix, f);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/* *
	 * 复制
	 * @param path_x 源路径
	 * @param path_o 目标路径
	 * @return 是否成功
	 */
	 public static boolean copyPic(String path_x,String path_o){
	    	
	    	boolean iden=false;
	    	
	    	BufferedInputStream inBuff = null;
	        BufferedOutputStream outBuff = null;
	        
	        byte[] b = null;
	        try {
	            // 新建文件输入流并对它进行缓冲
				inBuff = new BufferedInputStream(new FileInputStream(path_o));
				
	            // 新建文件输出流并对它进行缓冲
	            outBuff = new BufferedOutputStream(new FileOutputStream(path_x));

	            // 缓冲数组
	            b = new byte[1024*1024];
	            
	            int len;
	            while ((len = inBuff.read(b)) != -1) {
	                outBuff.write(b, 0, len);
	            }
	            // 刷新此缓冲的输出流
	            outBuff.flush();
	            iden=true;
	        }catch (Exception e) {
				e.printStackTrace();
				iden=false;
			}
	        finally {
	            // 关闭流
	        	try {
					inBuff.close();
	                outBuff.close();
	                b = null;
	        	} catch (IOException e) {
					e.printStackTrace();
				}
	        }
	    	
	    	return iden;
	    }
	
	 /* *
	  * 根据图片的大小设置压缩度进行压缩
	  * @param y_name 源路径
	  * @param u_name 目标路径
	  * @param width 目标宽度
	  * @param flag_quality 图片位置标识 缩略图 ：1   主题图、轮播图 ：2   内容编辑器图片：3
	  * @return 是否成功
	  */
	public boolean resizeImgByQuality(String y_name, String u_name, int width, int flag_quality){
		
			log.info("图片压缩度设置方法，开始执行");
			log.info("1111");
			
			log.info("得到的参数为：源文件名称：" + y_name + ",目标文件名称：" + u_name + ",位置：" + flag_quality);
			
			String t_ext = null;
			
			if(y_name.contains(".")){
				
				// 得到文件的扩展名(无扩展名时将得到全名)
				t_ext = y_name.substring(y_name.lastIndexOf(".") + 1).trim().toLowerCase();
				
			}else{
				
				log.info("源文件不含有扩展名，无法判断其类型");
				
				return false;
			}
			
			
			if(null != t_ext){
				
				//验证文件类型是否符合要求  限jpg、png、gif
				if(!Config.JPG.equals(t_ext) && !Config.PNG.equals(t_ext) && !Config.GIF.equals(t_ext)&& !Config.JPEG.equals(t_ext)){
					
					log.info("源文件类型不符合要求");
					
					return false;
					
				}
				
			}else{
				
				log.info("未获取到扩展名");
				
				return false;
			}
			
			//gif图处理
			if(Config.GIF.equals(t_ext)){
				
				boolean iden = copyPic(u_name, y_name);
				
				log.info("对gif图仅复制到目标路径，不进行压缩处理，复制状态：" + iden);
				
				return iden;
				
			}
			
			
			boolean flag = false;
			
			//初始化变量
			float quality = Config.QUALITY;
			
			File f = new File(y_name);
			
			long sizeImg = f.length();
			
			log.info("源文件的大小为：" + sizeImg);
			
			//缩略图、首页块图
			if(1 == flag_quality){
				
				log.info("首页块图、缩略图获取压缩质量");
				
				if(!Config.PNG.equals(t_ext)){
					
					if(sizeImg > Config.KB_20 && sizeImg <= Config.KB_50){
						
						//图片大小 大于 20KB 小于50KB
						
						quality = Config.QUALITY_96;
					
					}
				}
				if(sizeImg > Config.KB_50 && sizeImg <= Config.KB_150){
						
						//图片大小 大于 50KB 小于150KB
						
						quality = Config.QUALITY_93;
				}
			}
			
			//主题图、轮播图
			if(2 == flag_quality){
				
				log.info("轮播图、主题图获取压缩质量");
				
				if(sizeImg > Config.KB_50 && sizeImg <= Config.KB_150){
					
					//图片大小 大于 50KB 小于150KB
					
					quality = Config.QUALITY_93;
				
				}
				
			}
			
			
			
			if(sizeImg > Config.KB_150 && sizeImg <= Config.KB_300){
				
				//图片大小 大于 150KB 小于300KB
				
				quality = Config.QUALITY_90;
			
			}else if(sizeImg > Config.KB_300 && sizeImg<= Config.KB_500){
				
				//图片大小 大于 300KB 小于500KB
				
				quality = Config.QUALITY_80;
			
			}else if(sizeImg > Config.KB_500 && sizeImg <= Config.KB_1024){
				
				//图片大小 大于 500KB 小于1024KB
				
				quality = Config.QUALITY_75;
			
			}else if(sizeImg > Config.KB_1024){
				
				//图片大小 大于 1MB
				
				quality = Config.QUALITY_70;
			
			}
			
			log.info("得到的压缩质量为：" + quality);
			
			if(quality < Config.QUALITY){
			
				//压缩处理
				flag = this.compressPic(y_name, u_name, width, 0,quality,true);
				
			}else{
				
				//复制
				flag = copyPic(u_name, y_name);
			
			}
			
			log.info("压缩后返回状态：" + flag);
			
			return flag;
		}
	
	
	 /* *
	  * 根据图片的大小设置压缩度进行压缩
	  * @param y_name 源路径
	  * @param u_name 目标路径
	  * @param width 目标宽度
	  * @param flag_quality 图片位置标识 缩略图 ：1   主题图、轮播图 ：2   内容编辑器图片：3
	  * @return 是否成功
	  */
	public boolean resizeImgByQuality2(String y_name, String u_name, int width, int flag_quality){
		
			log.info("图片压缩度设置方法，开始执行");
			
			log.info("得到的参数为：源文件名称：" + y_name + ",目标文件名称：" + u_name + ",位置：" + flag_quality);
			
			String t_ext = null;
			
			if(y_name.contains(".")){
				
				// 得到文件的扩展名(无扩展名时将得到全名)
				t_ext = y_name.substring(y_name.lastIndexOf(".") + 1).trim().toLowerCase();
				
			}else{
				
				log.info("源文件不含有扩展名，无法判断其类型");
				
				return false;
			}
			
			
			if(null != t_ext){
				
				//验证文件类型是否符合要求  限jpg、png、gif
				if(!Config.JPG.equals(t_ext) && !Config.PNG.equals(t_ext) && !Config.GIF.equals(t_ext) && !Config.JPEG.equals(t_ext)){
					
					log.info("源文件类型不符合要求");
					
					return false;
					
				}
				
			}else{
				
				log.info("未获取到扩展名");
				
				return false;
			}
			
			//gif图处理
			if(Config.GIF.equals(t_ext)){
				
				boolean iden = copyPic(u_name, y_name);
				
				log.info("对gif图仅复制到目标路径，不进行压缩处理，复制状态：" + iden);
				
				return iden;
				
			}
			
			
			boolean flag = false;
			
			//初始化变量
			float quality = Config.QUALITY;
			
			File f = new File(y_name);
			
			long sizeImg = f.length();
			
			log.info("源文件的大小为：" + sizeImg);
			
			//缩略图、首页块图
			if(1 == flag_quality){
				
				log.info("首页块图、缩略图获取压缩质量");
				
				if(!Config.PNG.equals(t_ext)){
					
					if(sizeImg > Config.KB_20 && sizeImg <= Config.KB_50){
						
						//图片大小 大于 20KB 小于50KB
						
						quality = Config.QUALITY_96;
					
					}
				}
				if(sizeImg > Config.KB_50 && sizeImg <= Config.KB_150){
						
						//图片大小 大于 50KB 小于150KB
						
						quality = Config.QUALITY_93;
				}
			}
			
			//主题图、轮播图
			if(2 == flag_quality){
				
				log.info("轮播图、主题图获取压缩质量");
				
				if(sizeImg > Config.KB_50 && sizeImg <= Config.KB_150){
					
					//图片大小 大于 50KB 小于150KB
					
					quality = Config.QUALITY_93;
				
				}
				
			}
			
			
			
			if(sizeImg > Config.KB_150 && sizeImg <= Config.KB_300){
				
				//图片大小 大于 150KB 小于300KB
				
				quality = Config.QUALITY_90;
			
			}else if(sizeImg > Config.KB_300 && sizeImg<= Config.KB_500){
				
				//图片大小 大于 300KB 小于500KB
				
				quality = Config.QUALITY_80;
			
			}else if(sizeImg > Config.KB_500 && sizeImg <= Config.KB_1024){
				
				//图片大小 大于 500KB 小于1024KB
				
				quality = Config.QUALITY_75;
			
			}else if(sizeImg > Config.KB_1024){
				
				//图片大小 大于 1MB
				
				quality = Config.QUALITY_70;
			
			}
			
			log.info("得到的压缩质量为：" + quality);
			

			flag = this.compressPic(y_name, u_name, width, 0,quality,true);
			
			log.info("压缩后返回状态：" + flag);
			
			return flag;
		}
	
	 /* *
	  * 根据图片的大小设置生成图片--无压缩
	  * @param y_name 源路径
	  * @param u_name 目标路径
	  * @param width 目标宽度
	  * @param flag_quality 图片位置标识 缩略图 ：1   主题图、轮播图 ：2   内容编辑器图片：3
	  * @return 是否成功
	  */
	public boolean resizeImgByQualityTwo(String y_name, String u_name, int flag_quality){
		
			log.info("图片压缩度设置方法，开始执行");
			log.info("得到的参数为：源文件名称：" + y_name + ",目标文件名称：" + u_name + ",位置：" + flag_quality);
			
			//变量
			String t_ext = null;
			
			//得到文件的扩展名(无扩展名时将得到全名)
			if(y_name.contains(".")){
				t_ext = y_name.substring(y_name.lastIndexOf(".") + 1).trim().toLowerCase();
				
			}else{
				log.info("源文件不含有扩展名，无法判断其类型");
				return false;
			}

			//验证文件类型是否符合要求  限jpg、png、gif
			if(null != t_ext){
				if(!Config.JPG.equals(t_ext) && !Config.PNG.equals(t_ext) && !Config.GIF.equals(t_ext)){
					log.info("源文件类型不符合要求");
					return false;
				}
			}else{
				log.info("未获取到扩展名");
				return false;
			}
			
			//图片复制
			boolean flag = copyPic(u_name, y_name);
			
			//返回
			return flag;
		}
	
	
	 /* *
	  * 根据图片的大小获取压缩质量
	  * @param y_name 源路径
	  * @param flag_quality 图片位置标识 缩略图 ：1   主题图、轮播图 ：2   内容编辑器图片：3
	  * @return 是否成功
	  */
	public float resizeImgByQuality(String y_name, int flag_quality){
		log.info("图片压缩度设置方法，开始执行");
		
		log.info("得到的参数为：源文件名称：" + y_name + ",位置：" + flag_quality);
		String t_ext = y_name.substring(y_name.lastIndexOf(".") + 1).trim().toLowerCase();
		
		//初始化变量
		float quality = Config.QUALITY;
		
		//gif图处理
		if(Config.GIF.equals(t_ext)){
			
			log.info("对gif图仅复制到目标路径，不进行压缩处理，压缩质量：" + quality);
			
			return quality;
			
		}	
		
		
		File f = new File(y_name);
		
		long sizeImg = f.length();
		
		log.info("源文件的大小为：" + sizeImg);
		
		//缩略图、首页块图
		if(1 == flag_quality){
			
			log.info("首页块图、缩略图获取压缩质量");
			
			if(!Config.PNG.equals(t_ext)){
				
				if(sizeImg > Config.KB_20 && sizeImg <= Config.KB_50){
					
					//图片大小 大于 20KB 小于50KB
					
					quality = Config.QUALITY_96;
				
				}else if(sizeImg > Config.KB_50 && sizeImg <= Config.KB_150){
					
					//图片大小 大于 50KB 小于150KB
					
					quality = Config.QUALITY_93;
					
				}
			}
		}
		
		//主题图、轮播图
		if(2 == flag_quality){
			log.info("轮播图、主题图获取压缩质量");
			if(sizeImg > Config.KB_50 && sizeImg <= Config.KB_150){
				//图片大小 大于 50KB 小于150KB
				quality = Config.QUALITY_93;
			}
		}
		if(sizeImg > Config.KB_150 && sizeImg <= Config.KB_300){
			//图片大小 大于 150KB 小于300KB
			quality = Config.QUALITY_90;
		}else if(sizeImg > Config.KB_300 && sizeImg<= Config.KB_500){
			//图片大小 大于 300KB 小于500KB
			quality = Config.QUALITY_80;
		}else if(sizeImg > Config.KB_500 && sizeImg <= Config.KB_1024){
			//图片大小 大于 500KB 小于1024KB
			quality = Config.QUALITY_75;
		}else if(sizeImg > Config.KB_1024){
			//图片大小 大于 1MB
			quality = Config.QUALITY_70;
		}
		
		log.info("得到的压缩质量为：" + quality);
		
		return quality;
	}
	 
	public static void main(String [] args){
		 
		 String o_path = "d://img/1242-2208.png";
		 
		 String n_path = "d://750x1334.png";
		 
		 boolean flag = new ImgUtil().compressPic(o_path, n_path, 750, 1334, 1, false);
		 
		 System.out.println("执行到这儿..." + flag);
		 
	 }
}
