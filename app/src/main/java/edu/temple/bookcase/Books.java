package edu.temple.bookcase;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;
/**
 * CIS 3515 - Lab 9 BookCase-Audio
 * Toi Do 12/2/2019
 */
public class Books implements Parcelable {
    private int id, duration;
    private String title, author, coverURL, published;

    public Books(JSONObject jsonBook) throws JSONException {
        this.id = jsonBook.getInt("book_id");
        this.title = jsonBook.getString("title");
        this.author = jsonBook.getString("author");
        this.published = jsonBook.getString("published");
        this.coverURL = jsonBook.getString("cover_url");
        this.duration = jsonBook.getInt("duration");
    }

    protected Books(Parcel in) {
        id = in.readInt();
        title = in.readString();
        author = in.readString();
        published = in.readString();
        coverURL = in.readString();
    }

    public static final Creator<Books> CREATOR = new Creator<Books>() {
        @Override
        public Books createFromParcel(Parcel in) {
            return new Books(in);
        }

        @Override
        public Books[] newArray(int size) {
            return new Books[size];
        }
    };

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    public String getAuthor() {
        return author;
    }

    public void setPublished(String published) {
        this.published = published;
    }
    public String getPublished() {
        return published;
    }

    public void setCoverURL(String coverURL) {
        this.coverURL = coverURL;
    }
    public String getCoverURL() {
        return coverURL;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
    public int getDuration() {
        return duration;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(coverURL);
        dest.writeString(published);
    }

}
