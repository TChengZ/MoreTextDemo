#使用方法
  textview.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {\n" +
  "     @Override\n" +
  "     public boolean onPreDraw() {\n" +
  "       textview.getViewTreeObserver().removeOnPreDrawListener(this);\n" +
  "       TextUtil.setEllipsize(...)\n" +
  "       return true;\n" +
  "     }\n" +
  " });"

![](https://github.com/TChengZ/MoreTextDemo/blob/master/app/gif/moretext.gif) 
