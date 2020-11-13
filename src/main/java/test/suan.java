package test;

import com.jeedev.msdp.utlis.DateUtil;
import io.swagger.models.auth.In;
import org.apache.commons.lang.StringUtils;
import socket.StringUtil;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class suan {

    public static void main(String[] args) {
        String s = "dsadsadas<peter>dsadasdas<lionel>\"www.163.com\"<kenny><>";
        Pattern p = Pattern.compile("(<[^>]*>)");
        Matcher m = p.matcher(s);
        List<String> result=new ArrayList<String>();
        while(m.find()){
            result.add(m.group());
        }
        for(String s1:result){
            System.out.println(s1);
        }

    }
}
