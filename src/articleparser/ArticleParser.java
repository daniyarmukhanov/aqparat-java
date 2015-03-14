/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package articleparser;

import de.l3s.boilerpipe.BoilerpipeExtractor;
import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.ArticleExtractor;
import de.l3s.boilerpipe.extractors.CommonExtractors;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;

public class ArticleParser {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
       zakonkz();
      tengrinewskz();
       bnewskz();


    }

    private static void zakonkz() throws Exception {
        MySQLAccess dao = new MySQLAccess();
        RSSFeedParser parser = new RSSFeedParser("http://st.zakon.kz/rss/rss_all.xml");
        Feed feed = parser.readFeed();
        boolean saved = false;
        String lastguid = dao.getLastNews("3");
        for (FeedMessage message : feed.getMessages()) {
            if (message.getGuid().equals(lastguid)) {
                break;
            }
            if (!saved) {
                dao.changeLastNews("3", message.getGuid());
                saved = true;
            }
            System.out.println(message.getGuid());
            URL url = new URL(message.getGuid());
            String text = ArticleExtractor.INSTANCE.getText(url);
            text.replace(";", "");
            String res = "3";
            String lang = "2";
            dao.writeDataBase(message.getGuid(), message.getDescription(), message.getTitle(), text, res, lang, "no");
        }
    }

    private static void tengrinewskz() throws Exception {
        MySQLAccess dao = new MySQLAccess();
        RSSFeedParser parser = new RSSFeedParser("http://tengrinews.kz/news.rss");
        Feed feed = parser.readFeed();
        boolean saved = false;
        String lastguid = dao.getLastNews("4");
        for (FeedMessage message : feed.getMessages()) {
            if (message.getGuid().equals(lastguid)) {
                break;
            }
            if (!saved) {
                dao.changeLastNews("4", message.getGuid());
                saved = true;
            }
            System.out.println(message.getGuid());
            URL url = new URL(message.getGuid());
            String text = ArticleExtractor.INSTANCE.getText(url);
            text.replace(";", "");
            String res = "4";
            String lang = "2";
            dao.writeDataBase(message.getGuid(), message.getDescription(), message.getTitle(), text, res, lang, message.getPhoto());
        }
    }

    private static void bnewskz()throws Exception {
        MySQLAccess dao = new MySQLAccess();
        RSSFeedParser parser = new RSSFeedParser("http://vlast.kz/rss.php");
        Feed feed = parser.readFeed();
        boolean saved = false;
        String lastguid = dao.getLastNews("2");
        for (FeedMessage message : feed.getMessages()) {
            if (message.getLink().equals(lastguid)) {
                break;
            }
            if (!saved) {
                dao.changeLastNews("2", message.getLink());
                saved = true;
            }
            System.out.println(message.getLink());
            URL url = new URL(message.getLink());
            String text = ArticleExtractor.INSTANCE.getText(url);
            text.replace(";", "");
            String res = "2";
            String lang = "2";
            dao.writeDataBase(message.getLink(), message.getDescription(), message.getTitle(), text, res, lang, "no");
        }
    }
}
