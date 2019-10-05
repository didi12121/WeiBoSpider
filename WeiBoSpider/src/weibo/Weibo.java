package weibo;

public class Weibo {
	String itemid=null;
	String content=null;
	public Weibo(String itemid,String content) {
		this.itemid=itemid;
		this.content=content;
	}
	public String getItemid() {
		return itemid;
	}
	public String getContent() {
		return content;
	}
}
