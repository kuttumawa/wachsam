package es.io.wachsam.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class NodeAndLinks {
	@Expose
	List<Node> nodes=new ArrayList<Node>();
	@Expose
	List<Link> links=new ArrayList<Link>();
	public List<Node> getNodes() {
		return nodes;
	}
	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}
	public List<Link> getLinks() {
		return links;
	}
	public void setLinks(List<Link> links) {
		this.links = links;
	}
	
	

}
