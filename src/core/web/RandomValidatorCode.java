package core.web;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RandomValidatorCode {
	
	private static RandomValidatorCode randomValidatorCode = null;

	private static final String RANDOMCODEKEY = "RANDOMVALIDATORCODEKEY";
	private Random random = new Random();
	private String randomStr = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	private int width;
	private int height;
	private int lineNum;
	private int strNum;
	
	public static RandomValidatorCode getInstance() {
		if (randomValidatorCode == null) {
			return new RandomValidatorCode();
		}
		return randomValidatorCode;
	}
	
	/**
	 * @title init
	 * @description 初始化
	 * @param @param width 图片宽
	 * @param @param height 图片高
	 * @param @param lineNum 干扰线数量
	 * @param @param strNum 随机字符数量
	 * @return void
	 * @date 2014年4月18日
	 */
	public void init(int width, int height, int lineNum, int strNum) {
		this.width = width;
		this.height = height;
		this.lineNum = lineNum;
		this.strNum = strNum;
	}
	
	/**
	 * @title getRandomCode
	 * @description 生成随机图片，并存入session
	 * @param @param request
	 * @param @param response
	 * @return void
	 * @date 2014年4月18日
	 */
	public void getRandomCode(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		Graphics g = image.getGraphics();
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 18));
		g.setColor(getRanColor(110, 133));
		for (int i=0; i<=lineNum; i++) {
			drawLine(g);
		}
		String randStr = "";
		for (int i=1; i<strNum; i++) {
			randStr = drawStr(g, randomStr, i);
		}
		session.removeAttribute(RANDOMCODEKEY);
		session.setAttribute(RANDOMCODEKEY, randStr);
		g.dispose();
		try {
			ImageIO.write(image, "JPEG", response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @title getFont
	 * @description 获得字体
	 * @param @return
	 * @return Font
	 * @date 2014年4月18日
	 */
	private Font getFont() {
		return new Font("Fixedsys", Font.CENTER_BASELINE, 18);
	}
	
	/**
	 * @title getRanColor
	 * @description 获取随机颜色
	 * @param @param frontColor
	 * @param @param bgColor
	 * @param @return
	 * @return Color
	 * @date 2014年4月18日
	 */
	private Color getRanColor(int frontColor, int bgColor) {
		if (frontColor > 255) {
			frontColor = 255;
		}
		if (bgColor > 255) {
			bgColor = 255;
		}
		int r = frontColor + random.nextInt(bgColor - frontColor - 16);
		int g = frontColor + random.nextInt(bgColor - frontColor - 14);
		int b = frontColor + random.nextInt(bgColor - frontColor - 18);
		return new Color(r, g, b);
	}
	
	/**
	 * @title drawStr
	 * @description 绘制随机字符串
	 * @param @param g
	 * @param @param randStr
	 * @param @param i
	 * @param @return
	 * @return String
	 * @date 2014年4月18日
	 */
	private String drawStr(Graphics g, String randStr, int i) {
		g.setFont(getFont());
		g.setColor(new Color(random.nextInt(101), random.nextInt(111), random.nextInt(121)));
		String rand = String.valueOf(getRandomStr(random.nextInt(randomStr.length())));
		randStr += rand;
		g.translate(random.nextInt(3), random.nextInt(3));
		g.drawString(rand, 13*i, 16);
		return randStr;
	}
	
	/**
	 * @title drawLine
	 * @description 绘制干扰线
	 * @param @param g
	 * @return void
	 * @date 2014年4月18日
	 */
	private void drawLine(Graphics g) {
		int x = random.nextInt(width);
		int y = random.nextInt(height);
		int xl = random.nextInt(13);
		int yl = random.nextInt(15);
		g.drawLine(x, y, x+xl, y+yl);
	}

	/**
	 * @title getRandomStr
	 * @description 获取随机字符
	 * @param @param pos
	 * @param @return
	 * @return String
	 * @date 2014年4月18日
	 */
	private String getRandomStr(int pos) {
		return String.valueOf(randomStr.charAt(pos));
	}

}
