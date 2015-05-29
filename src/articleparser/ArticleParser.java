/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package articleparser;

import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.ArticleExtractor;
import de.l3s.boilerpipe.extractors.CommonExtractors;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.SQLException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ArticleParser {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
          try {
            RSSReader reader = RSSReader.getInstance();  
            reader.setURL(new URL("http://www.bnews.kz/ru/news/rss/"), "bnews.kz");  
            reader.writeFeed();  
            reader.setURL(new URL("http://baq.kz/kk/rss"), "baq.kz");  
            reader.writeFeed(); 
            reader.setURL(new URL("http://www.sports.kz/rss"), "sports.kz");  
            reader.writeFeed(); 
            reader.setURL(new URL("http://st.zakon.kz/rss/rss_all.xml"), "zakon.kz");  
            reader.writeFeed(); 
            reader.setURL(new URL("http://inform.kz/rss/kaz.xml"), "inform.kz");  
            reader.writeFeed(); 
            reader.setURL(new URL("http://today.kz/feed/"), "today.kz");  
            reader.writeFeed(); 
            reader.setURL(new URL("http://forbes.kz/rss/articles"), "forbes.kz");  
            reader.writeFeed(); 
            reader.setURL(new URL("http://kapital.kz/feed"), "kapital.kz");  
            reader.writeFeed(); 
            reader.setURL(new URL("http://liter.kz/ru/rss.xml"), "liter.kz");  
            reader.writeFeed(); 
            reader.setURL(new URL("http://vesti.kz/news.rss"), "vesti.kz");  
            reader.writeFeed(); 
            reader.setURL(new URL("http://vlast.kz/rss.php"), "vlast.kz");  
            reader.writeFeed(); 
            reader.setURL(new URL("http://tengrinews.kz/news.rss"), "tengrinews.kz");  
            reader.writeFeed(); 
            reader.setURL(new URL("http://makala.kz/feed/"), "makala.kz");  
            reader.writeFeed(); 
            reader.setURL(new URL("http://news.nur.kz/rss/all.rss"), "nur.kz");  
            reader.writeFeed(); 
        } catch (Exception e) {
            e.printStackTrace();
        }

//
//        URL url = new URL("http://kaznet.me/media/");
//        String text = "";
//        String imagelink, title, link, resource, res_link, time, shared, fulltext;
//        title="Хорошие новости";
//        MySQLAccess dao = new MySQLAccess();
//        try {
//            URLConnection conn = url.openConnection();
//            BufferedReader br = new BufferedReader(
//                    new InputStreamReader(conn.getInputStream()));
//            String inputLine;
//            while ((inputLine = br.readLine()) != null) {
//                text = text + "\n" + inputLine;
//            }
//            br.close();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        Document doc = Jsoup.parse(text);
//        Element itemscontent = doc.getElementById("items-content");
//        Elements itemsblock = itemscontent.getAllElements();
//        Element articleblock = itemsblock.first();
//        Elements articles = articleblock.getElementsByClass("article-item");
//        for (int i = 0; i < 1; i++) {
//
//
//            Element article = articles.get(i);
//            Elements left0 = article.getElementsByClass("article-item-l");
//            Element left = left0.first();
//            Elements img0 = left.getElementsByTag("img");
//            Element img = img0.get(0);
//            imagelink = img.absUrl("src");
//            Element right = article.getElementsByClass("article-item-r").first();
//            title = right.getElementsByClass("article-title").first().getElementsByTag("a").first().text();
//            link = right.getElementsByClass("article-title").first().getElementsByTag("a").first().attr("title");
//            Element info = right.getElementsByClass("article-info").first();
//            time = info.getElementsByTag("span").first().text();
//            resource = info.getElementsByTag("span").get(1).getElementsByTag("a").first().text();
//            res_link = info.getElementsByTag("span").get(1).getElementsByTag("img").first().attr("src");
//            shared = info.getElementsByClass("cnt-total").first().text().substring(7);
//            fulltext = ArticleExtractor.INSTANCE.getText(new URL(link));
//            dao.writeBest(i + 1, title, link, imagelink, resource, res_link, time, shared, fulltext);
//        }
//        String rawData = "{\"data\": {\"alert\": \""+title+"\", \"title\":\"Популярная новость на сегодня\"}, \"where\":{}}";
//        String type = "application/json";
//        URL u = new URL("https://api.parse.com/1/push");
//        HttpURLConnection conn = (HttpURLConnection) u.openConnection();
//        conn.setDoOutput(true);
//        conn.setRequestMethod("POST");
//        conn.addRequestProperty("Content-Type", type);
//        conn.addRequestProperty("X-Parse-Application-Id", "Zqebwike5ccyVwYAJ0jNjEi5ExiQU33YJuhBkq35");
//        conn.addRequestProperty("X-Parse-REST-API-Key", "XO5yAaavqEjmrzFZlbSeH4JbJ56YNpFXwUmCW1O8");
//        OutputStream os = conn.getOutputStream();
//        os.write(rawData.getBytes());
//        System.out.println(conn.getResponseCode());

    }
//    private static void zakonkz() throws Exception {
//        MySQLAccess dao = new MySQLAccess();
//        RSSFeedParser parser = new RSSFeedParser("http://st.zakon.kz/rss/rss_all.xml");
//        Feed feed = parser.readFeed();
//        boolean saved = false;
//        String lastguid = dao.getLastNews("3");
//        for (FeedMessage message : feed.getMessages()) {
//            if (message.getGuid().equals(lastguid)) {
//                break;
//            }
//            if (!saved) {
//                dao.changeLastNews("3", message.getGuid());
//                saved = true;
//            }
//            System.out.println(message.getGuid());
//            URL url = new URL(message.getGuid());
//            String text = ArticleExtractor.INSTANCE.getText(url);
//            System.out.println(text);
//            //text = text.replaceAll("'", "");
//            //text = text.replace("`", "");
//            String desc = message.getDescription();
//            desc = desc.replace("`", "");
//            desc = desc.replace("'", "");
//            String title = message.getTitle();
//            title = title.replace("`", "");
//            title = title.replace("'", "");
//            String res = "3";
//            String lang = "2";
//            dao.writeDataBase(message.getGuid(), desc, title, text, res, lang, "no");
//        }
//    }
}
