<%@ page contentType="image/jpeg" import="java.awt.*,java.awt.image.*,java.util.*,javax.imageio.*" %>
<%@ page import="com.sun.image.codec.jpeg.JPEGCodec,com.sun.image.codec.jpeg.JPEGImageEncoder"%>
<%!
Color getRandColor(int fc,int bc){
  Random random = new Random();
  if(fc>255) fc=255;
  if(bc>255) bc=255;
  int r=fc+random.nextInt(bc-fc);
  int g=fc+random.nextInt(bc-fc);
  int b=fc+random.nextInt(bc-fc);
  return new Color(r,g,b);
  }
%>
<%
response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires", 0);
int width=70, height=17;
BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

Graphics g = image.getGraphics();

Random random = new Random();

g.fillRect(0, 0, width, height);

g.setFont(new Font("Times New Roman",Font.PLAIN,22));

g.setColor(getRandColor(160,200));
for (int i=0;i<155;i++)
{
  int x = random.nextInt(width);
  int y = random.nextInt(height);
  int xl = random.nextInt(12);
  int yl = random.nextInt(12);
  g.drawLine(x,y,x+xl,y+yl);
}

String codeList = "1234567890";

String sRand="";

for (int i=0;i<4;i++){
int a=random.nextInt(codeList.length()-1);
  String rand=codeList.substring(a,a+1);
  sRand+=rand;
  g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
  g.drawString(rand,13*i+6,16);
}

session.setAttribute("sess_code",sRand);
//session.setAttribute("sess_code","");
response.reset();
g.dispose();
//ImageIO.write(image, "JPEG", response.getOutputStream());
JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(response.getOutputStream());
encoder.encode(image); 
out.clear();
out = pageContext.pushBody();
%>