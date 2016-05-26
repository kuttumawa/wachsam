package es.io.wachsam.model;

import com.google.gson.annotations.Expose;

public class Node {
	Long id;
	@Expose
	String name;
	@Expose
	int group;
	public Node(Long id,String name, int group) {
		super();
		this.id=id;
		this.name = name;
		this.group = group;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
