/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package articleparser;

/**
 *
 * @author Данияр
 */

/*
 * Represents one RSS message
 */
public class FeedMessage {

  String title;
  String description;
  String link;
  String author;
  String guid;
  String photo;
  String fulltext;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getGuid() {
    return guid;
  }

  public void setGuid(String guid) {
    this.guid = guid;
  }

  @Override
  public String toString() {
    return "FeedMessage [title=" + title + ", description=" + description
        + ", link=" + link + ", author=" + author + ", guid=" + guid
        + "]";
  }

    /**
     * @return the photo
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * @param photo the photo to set
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * @return the fulltext
     */
    public String getFulltext() {
        return fulltext;
    }

    /**
     * @param fulltext the fulltext to set
     */
    public void setFulltext(String fulltext) {
        this.fulltext = fulltext;
    }

} 
