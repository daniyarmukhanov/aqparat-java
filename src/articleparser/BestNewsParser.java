/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package articleparser;

import com.sun.jndi.toolkit.url.Uri;
import de.l3s.boilerpipe.extractors.ArticleExtractor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Данияр
 */
public class BestNewsParser {

    public static void gyy(String fds[]) throws Exception {
        URL url = new URL("http://kaznet.me/media/");
        String text = "";
        String imagelink, title,link,resource, res_link,time,shared,fulltext;
        MySQLAccess dao = new MySQLAccess();
        try {
            URLConnection conn = url.openConnection();
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                text = text + "\n" + inputLine;
            }
            br.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Document doc = Jsoup.parse(text);
        Element itemscontent = doc.getElementById("items-content");
        Elements itemsblock = itemscontent.getAllElements();
        Element articleblock = itemsblock.first();
        Elements articles = articleblock.getElementsByClass("article-item");
        for (int i = 0; i < 10; i++) {
            
        
        Element article=articles.get(i);
        Elements left0=article.getElementsByClass("article-item-l");
        Element left=left0.first();
        Elements img0=left.getElementsByTag("img");
        Element img=img0.get(0);
        imagelink=img.absUrl("src");
        Element right=article.getElementsByClass("article-item-r").first();
        title=right.getElementsByClass("article-title").first().getElementsByTag("a").first().text();
        link=right.getElementsByClass("article-title").first().getElementsByTag("a").first().attr("title");
        Element info=right.getElementsByClass("article-info").first();
        time=info.getElementsByTag("span").first().text();
        resource=info.getElementsByTag("span").get(1).getElementsByTag("a").first().text();
        res_link=info.getElementsByTag("span").get(1).getElementsByTag("img").first().attr("src");
        shared=info.getElementsByClass("cnt-total").first().text().substring(7);
        fulltext=ArticleExtractor.INSTANCE.getText(new URL(link));
        dao.writeBest(i+1, title, link, imagelink, resource, res_link, time, shared, fulltext);
        }
        
    }

    private static void SOP(String toString) {
        System.out.println(toString);
    }
}
