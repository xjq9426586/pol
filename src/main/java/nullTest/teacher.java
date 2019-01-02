package nullTest;

import com.google.common.base.Optional;

public class teacher {
	private Integer id;
	private String name;
	public teacher(String string) {
		// TODO Auto-generated constructor stub
	}
	public teacher() {
		// TODO Auto-generated constructor stub
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return Optional.fromNullable(name).or("0");
	}
	public void setName(String name) {
		this.name = name;
	}
}
