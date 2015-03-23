package io.sample.bean.game;

import java.util.List;

public class EntryBean {

	public ImName getImName() {
		return imName;
	}
	public void setImName(ImName imName) {
		this.imName = imName;
	}
	public List<ImImage> getImImageList() {
		return imImageList;
	}
	public void setImImageList(List<ImImage> imImageList) {
		this.imImageList = imImageList;
	}
	public Summary getSummary() {
		return summary;
	}
	public void setSummary(Summary summary) {
		this.summary = summary;
	}
	public ImPrice getImPrice() {
		return imPrice;
	}
	public void setImPrice(ImPrice imPrice) {
		this.imPrice = imPrice;
	}
	public ImContentType getImContentType() {
		return imContentType;
	}
	public void setImContentType(ImContentType imContentType) {
		this.imContentType = imContentType;
	}
	public Rights getRights() {
		return rights;
	}
	public void setRights(Rights rights) {
		this.rights = rights;
	}
	public Title getTitle() {
		return title;
	}
	public void setTitle(Title title) {
		this.title = title;
	}
	public Link getLink() {
		return link;
	}
	public void setLink(Link link) {
		this.link = link;
	}
	public Id getId() {
		return id;
	}
	public void setId(Id id) {
		this.id = id;
	}
	public ImArtist getImArtist() {
		return imArtist;
	}
	public void setImArtist(ImArtist imArtist) {
		this.imArtist = imArtist;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public ImReleaseDate getImReleaseDate() {
		return imReleaseDate;
	}
	public void setImReleaseDate(ImReleaseDate imReleaseDate) {
		this.imReleaseDate = imReleaseDate;
	}
	private ImName imName;
	private List<ImImage> imImageList;
	private Summary summary;
	private ImPrice imPrice;
	private ImContentType imContentType;
	private Rights rights;
	private Title title;
	private Link link;
	private Id id;
	private ImArtist imArtist;
	private Category category;
	private ImReleaseDate imReleaseDate;

}
