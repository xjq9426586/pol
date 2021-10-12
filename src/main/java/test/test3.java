package test;

import com.jeedev.msdp.utlis.StringUtil;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class test3 {
    public static List textSplit(InputStream is, String regex1, String regex2, List<String> keys) {
        List<String> list = new ArrayList();
        List<List> alist = new ArrayList();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        List<String> c = br.lines()
                .flatMap(line -> Arrays.stream(line.split(regex1))

                )
                .collect(Collectors.toList());

//		for (int i = 0; i < c.size(); i++) {
//			if(!StringUtil.isEmpty(c.get(i))){
//			if(countStr(c.get(i), "@|@") != 5 && countStr(c.get(i), "@|@") != 6){
//				String s = c.get(i) + c.get(i + 1);
//				//System.out.println(s);
//				i += 1;
//				list.add(s);
//			}else {
//				list.add(c.get(i));
//			}
//			}
//		}
//		System.out.println(list);
//		List<Map<String, Object>> reList = new ArrayList<>();
//		list.forEach(item -> {
//			Map<String, Object> map = new HashMap<>();
//			List<String> dataList = Arrays.asList(item.split(regex2));
//			dataList.forEach(e -> {
//				map.put(keys.get(dataList.indexOf(e)), e);
//			});
//			reList.add(map);
//		});
        return c;
    }

    private static int countStr(String str, String sToFind) {
        int num = 0;
        while (str.contains(sToFind)) {
            str = str.substring(str.indexOf(sToFind) + sToFind.length());
            num++;
        }
        return num;
    }

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(textSplit(new FileInputStream(new File("D:\\tansun-tcp-app-pc-msdp.log")),
                " ", "@\\|@", Arrays.asList("ob", "ss", "we", "ty", "rt")));

    }
}
