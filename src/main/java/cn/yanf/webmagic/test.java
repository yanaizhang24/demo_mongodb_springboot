package cn.yanf.webmagic;

/**
 * Created by �Ϸ� on 2016/5/25 0025.
 */
public class test {
    public static String y="";
    public  static void x(int n) {

        for(int i=1;i<=n;i++) {
            if ("".equals(y)) {
                y=""+i;
            }else {
                y=y+"."+i;
            }
            System.out.println(y);
            if(n-i>0){x(n-i);}
            if(y.length()>=3){
                y=y.substring(0,y.length()-3);
            }else{
                y="";
            }

        }
    }
    public static void main(String[] args){
        x(4);
    }
}
