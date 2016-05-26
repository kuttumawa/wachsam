package es.io.wachsam.model;

import com.google.gson.annotations.Expose;

public class Link {
        @Expose
		int source;
        @Expose
		int target;
        @Expose
		int value;
		public Link(int source, int target, int value) {
			super();
			this.source = source;
			this.target = target;
			this.value = value;
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

}
