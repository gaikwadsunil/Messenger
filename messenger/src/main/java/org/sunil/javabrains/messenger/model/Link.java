package org.sunil.javabrains.messenger.model;

public class Link {
	
	private String link;
	private String rel;
	
	public Link(){}
	
	public Link(String url, String rel) {
		// TODO Auto-generated constructor stub
		this.link=url;
		this.rel=rel;
	}

	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getRel() {
		return rel;
	}
	public void setRel(String rel) {
		this.rel = rel;
	}
	
	

}
