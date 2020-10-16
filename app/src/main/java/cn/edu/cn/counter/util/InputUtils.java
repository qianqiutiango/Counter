package cn.edu.cn.counter.util;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InputUtils {
    // 将输入流转换为String
    public static String inputStream2String(InputStream inputStream)
            throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream, "gb2312");
        while (true) {
            int rsz = in.read(buffer, 0, buffer.length);
            if (rsz < 0)
                break;
            out.append(buffer, 0, rsz);
        }
        return out.toString();
    }

    public static List getData() {
        List listItems = new ArrayList<HashMap<String,String>>();
        Log.i("MyTag","获取汇率");
        //处理数据实验
        String url = "https://www.usd-cny.com/bankofchina.htm";

        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("TAG", "run: " + doc.title());
        Elements tables = doc.getElementsByTag("table");
        Element table6 = tables.get(0);
        //获取TD中的数据
        Elements tds = table6.getElementsByTag("td");

        for(int i=0;i<tds.size();i+=6){
            Element td1 = tds.get(i);
            Element td2 = tds.get(i+5);
            String str1 = td1.text();
            String val = td2.text();
            HashMap<String,String> map = new HashMap<>();
            map.put("ItemTitle",str1);
            map.put("ItemDetail",val);


            listItems.add(map);
            //获取数据并返回……
        }
        return listItems;
    }
}
