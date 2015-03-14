/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package articleparser;

import de.l3s.boilerpipe.BoilerpipeExtractor;
import de.l3s.boilerpipe.extractors.ArticleExtractor;
import de.l3s.boilerpipe.extractors.CommonExtractors;
import java.net.URL;

public class ArticleParser {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        MySQLAccess dao = new MySQLAccess();
        RSSFeedParser parser = new RSSFeedParser("http://st.zakon.kz/rss/rss_all.xml");
        Feed feed = parser.readFeed();
        for (FeedMessage message : feed.getMessages()) {
            System.out.println(message.getGuid());
            URL url = new URL(message.getGuid());
              String text = ArticleExtractor.INSTANCE.getText(url);
              dao.writeDataBase(message.getGuid(), message.getDescription(), message.getTitle(), text);
        }
    }
}
