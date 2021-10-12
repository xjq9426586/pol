package nullTest;

import java.util.Date;
import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;

public class student {
    private Integer id;
    private String name;
    private List<teacher> tList;
    private teacher t;
    private boolean isH;
    private Date birthday;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return Optional.fromNullable(name).or("");
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<teacher> gettList() {
        return tList;
    }

    public void settList(List<teacher> tList) {
        this.tList = tList;
    }

    public static void main(String[] args) {
        student s = new student();
        teacher t = new teacher();
	/*	s.setT(t);
		Optional.fromNullable(s.getT()).or(new teacher()).setName("1223");
		System.out.println(s.getT().getName());*/


        ImmutableMap<String, String> map = ImmutableMap.of("a", "123", "b", "345");
        String str = Joiner.on(",").withKeyValueSeparator(":").join(map);
        System.out.println(str);
    }

    public boolean isH() {
        return isH;
    }

    public void setH(boolean isH) {
        this.isH = isH;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public teacher getT() {
        return t;
    }

    public void setT(teacher t) {
        this.t = t;
    }

}
