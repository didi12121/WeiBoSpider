package weibo;

public class Weibo {
	public void setItemid(String itemid) {
		this.itemid = itemid;
	}
	String itemid=null;
	String content=null;
	boolean isvideo=false;
	public boolean isIsvideo() {
		return isvideo;
	}
	public Weibo(String itemid,String content) {
		this.itemid=itemid;
		this.content=content;
	}
	public void setIsvideo(boolean isvideo) {
		this.isvideo = isvideo;
	}
	public String getItemid() {
		return itemid;
	}
	public String getContent() {
		return content;
	}
}
