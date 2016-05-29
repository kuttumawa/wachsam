package es.io.wachsam.model;

import com.google.gson.annotations.Expose;

public class Link {
        @Expose
		int source;
        @Expose
		int target;
        @Expose
		int value;
        @Expose
		String text;
		public Link(int source, int target, int value,String text) {
			super();
			this.source = source;
			this.target = target;
			this.value = value;
			this.text =text;
		}
		public Link() {
			super();
		}
		public int getSource() {
			return source;
		}
		public void setSource(int source) {
			this.source = source;
		}
		public int getTarget() {
			return target;
		}
		public void setTarget(int target) {
			this.target = target;
		}
		public int getValue() {
			return value;
		}
		public void setValue(int value) {
			this.value = value;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}

}
