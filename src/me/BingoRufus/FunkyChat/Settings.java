package me.bingorufus.funkychat;

public class Settings {

	private boolean reverse = false;
	private boolean upsidedown = false;




	public boolean reverse() {
		return reverse;
	}

	public boolean upsidedown() {
		return upsidedown;
	}

	public void setReverse(boolean rev) {
		this.reverse = rev;
	}

	public void setUpsidedown(boolean ud) {
		this.upsidedown = ud;
	}

}
