package log;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
public class test {
	@Resource
	private Ia a;
	@Test
	public void test1() {
		a.sys();
	}
}
