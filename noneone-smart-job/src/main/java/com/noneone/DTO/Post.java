
package com.noneone.DTO;

import java.io.Serializable;
import java.util.Date;



/**
 * 博客内容
 * @author leisure
 * 
 */
public class Post implements Serializable {
	private static final long serialVersionUID = 7144425803920583495L;

	
	private long id;

	private String content;
	private int group;

	
	private String title;

	
	private String summary;

	
	private String tags;

	
	private long authorId; // 作者

	
	private String editor; // 编辑器

	/**
	 * 文章最后AttachId
	 * - 冗余字段
	 */
	private long lastImageId;

	private Date created;

	/**
	 * 图片统计
	 */
	private int images;

	/**
	 * 推荐状态
	 */
	private int featured;

	/**
	 * 喜欢数
	 */
	private int favors;

	/**
	 * 评论数
	 */
	private int comments;

	/**
	 * 阅读数
	 */
	private int views;

	/**
	 * 文章状态
	 */
	private int status;
	

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public long getLastImageId() {
		return lastImageId;
	}

	public void setLastImageId(long lastImageId) {
		this.lastImageId = lastImageId;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(long authorId) {
		this.authorId = authorId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getFeatured() {
		return featured;
	}

	public void setFeatured(int featured) {
		this.featured = featured;
	}

	public int getFavors() {
		return favors;
	}

	public void setFavors(int favors) {
		this.favors = favors;
	}

	public int getComments() {
		return comments;
	}

	public void setComments(int comments) {
		this.comments = comments;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public int getImages() {
		return images;
	}

	public void setImages(int images) {
		this.images = images;
	}

}