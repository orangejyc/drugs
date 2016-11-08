package org.ansoya.drugs;

import org.junit.Test;

/**
 * Created by Administrator on 2016/11/8.
 */
public class CharsetTest {

    @Test
    public void test() {
        try {
            String source1 = "201603110023100026020335，维生素C注射液，兽药字（2011）070012795，华牧兽药，043182658811";
            String source = "201511230006000300170751｣ｬﾋﾄｼｾﾇ獸｢ﾉ萪ｺ｣ｬﾊﾞﾒｩﾗﾖ｣ｨ2014｣ｩ220164666£¬³É¶¼¿ÆÈñ£¬028-83969611";

            String target = new String(source.getBytes("UTF-8"), "gb2312");
            System.out.println(target);
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }

    }


    @Test
    public void test1() {
        try {
            //String source="201603110023100026020335，维生素C注射液，兽药字（2011）070012795，华牧兽药，043182658811";
            String source = "201511230006000300170751，四季青注射液，兽药字（2014）220164666，成都科锐，028-83969611";
            String source1 = "201511230006000300170751｣ｬﾋﾄｼｾﾇ獸｢ﾉ萪ｺ｣ｬﾊﾞﾒｩﾗﾖ｣ｨ2014｣ｩ220164666£¬³É¶¼¿ÆÈñ£¬028-83969611";

            String gb2312 = new String(source.getBytes(), "gb2312");
            System.out.println(gb2312);
            String GBK = new String(source.getBytes(), "GBK");
            System.out.println(GBK);
            String GB18030 = new String(source.getBytes(), "GB18030");
            System.out.println(GB18030);
            String utf8 = new String(source.getBytes(), "utf-8");
            System.out.println(utf8);

            String ISO88591 = new String(source.getBytes(), "ISO-8859-1");
            System.out.println(ISO88591);
            String BIG5 = new String(source.getBytes(), "BIG5");
            System.out.println(BIG5);

            String ShiftJIS = new String(source.getBytes(), "Shift-JIS");
            System.out.println(ShiftJIS);
            System.out.println(new String(ShiftJIS.getBytes(),"GB2312"));

            String JIS = new String(source.getBytes(), "JIS");
            System.out.println(JIS);

            String MS932 = new String(source.getBytes(), "Shift_JIS");
            System.out.println(MS932);



        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }

    }

    @Test
    public void test2() {
        System.out.println(getEncoding("201511230006000300170751｣ｬﾋﾄｼｾﾇ獸｢ﾉ萪ｺ｣ｬﾊﾞﾒｩﾗﾖ｣ｨ2014｣ｩ220164666£¬³É¶¼¿ÆÈñ£¬028-83969611"));
    }

    public String getEncoding(String str) {
        String encode = "GB2312";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s = encode;
                return s;
            }
        } catch (Exception exception) {
        }
        encode = "ISO-8859-1";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s1 = encode;
                return s1;
            }
        } catch (Exception exception1) {
        }
        encode = "UTF-8";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s2 = encode;
                return s2;
            }
        } catch (Exception exception2) {
        }
        encode = "GBK";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s3 = encode;
                return s3;
            }
        } catch (Exception exception3) {
        }
        return "";
    }


}
