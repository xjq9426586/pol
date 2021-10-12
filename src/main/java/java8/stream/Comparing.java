package java8.stream;

import com.alibaba.fastjson.JSONArray;
import com.jeedev.msdp.utlis.MapUtil;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: xujunqian
 * @Date: 2021/6/1 11:29
 * @Description:
 */
public class Comparing {
    public static void main(String[] args) {
        String s = "[{\"tskSrc\":\"03\",\"cstTp\":\"04\",\"expdId\":null,\"cstNm\":\"成都小草帽科技有限公司\",\"cstNumb\":\"CSTC20215309\",\"aprvSt\":\"1\",\"hdlInst\":\"11002101\",\"id\":\"167b418f553143ef9f62ef54c2590ae7\",\"hdlTime\":\"2021-01-26 10:25:57\",\"hdlUser\":\"YH051803\",\"ovrlsttnJrnlNo\":null,\"lwrsInd\":null,\"mdfMod\":\"2\",\"iouNumb\":\"HT202101253311000137\",\"updateUser\":\"YH051803\",\"updateTime\":\"2021-06-01 10:05:22.0\",\"bsnNumb\":\"TZ01202106012371\",\"version\":0,\"ntcNumb\":null,\"mdfRsn\":null,\"delInd\":\"0\",\"createTime\":\"2021-06-01 10:05:22.0\",\"createInst\":\"11002101\",\"aplyTm\":\"2021-06-01 10:05:22\",\"txnsrlno\":null,\"tenantId\":\"1001\",\"updateInst\":\"11002101\",\"astChnl\":\"OP01\",\"createUser\":\"YH051803\",\"curPcsgPsn\":\"象屿支行对公对私业务客户经理\"},{\"tskSrc\":\"03\",\"cstTp\":\"01\",\"expdId\":null,\"cstNm\":\"hhl零售二\",\"cstNumb\":\"CSTP20219376\",\"aprvSt\":\"10\",\"hdlInst\":\"11002101\",\"id\":\"c2f1859493a24fe9a37ff45cdc986acf\",\"hdlTime\":\"2021-05-24 15:50:04\",\"hdlUser\":\"YH051803\",\"ovrlsttnJrnlNo\":\"4f53e6278243432190b478e6fbbf04\",\"lwrsInd\":null,\"mdfMod\":\"2\",\"iouNumb\":\"HT202105213759000221\",\"updateUser\":\"YH051803\",\"updateTime\":\"2021-05-25 16:16:35.0\",\"bsnNumb\":\"TZ01202105242365\",\"version\":0,\"ntcNumb\":\"BP2021052400000002\",\"mdfRsn\":\"1\",\"delInd\":\"0\",\"createTime\":\"2021-05-24 15:54:57.0\",\"createInst\":\"11002101\",\"aplyTm\":\"2021-05-24 15:54:57\",\"txnsrlno\":\"4f53e6278243432190b478e6fbbf04\",\"tenantId\":\"1001\",\"updateInst\":\"11002101\",\"astChnl\":\"OP01\",\"createUser\":\"YH051803\",\"curPcsgPsn\":\"象屿支行对公对私业务客户经理\"},{\"tskSrc\":\"03\",\"cstTp\":\"01\",\"expdId\":null,\"cstNm\":\"hhl零售二\",\"cstNumb\":\"CSTP20219376\",\"aprvSt\":\"10\",\"hdlInst\":\"11002101\",\"id\":\"ded338c7c57a401fb961dc44fe28c6f7\",\"hdlTime\":\"2021-05-24 15:50:04\",\"hdlUser\":\"YH051803\",\"ovrlsttnJrnlNo\":\"9ec48d5f457144279394aa1bcd4050\",\"lwrsInd\":null,\"mdfMod\":\"2\",\"iouNumb\":\"HT202105213759000221\",\"updateUser\":\"YH051803\",\"updateTime\":\"2021-05-24 16:09:08.0\",\"bsnNumb\":\"TZ01202105242364\",\"version\":0,\"ntcNumb\":\"BP2021052400000001\",\"mdfRsn\":\"1\",\"delInd\":\"0\",\"createTime\":\"2021-05-24 15:53:01.0\",\"createInst\":\"11002101\",\"aplyTm\":\"2021-05-24 15:53:01\",\"txnsrlno\":\"9ec48d5f457144279394aa1bcd4050\",\"tenantId\":\"1001\",\"updateInst\":\"11002101\",\"astChnl\":\"OP01\",\"createUser\":\"YH051803\",\"curPcsgPsn\":\"象屿支行对公对私业务客户经理\"},{\"tskSrc\":\"03\",\"cstTp\":\"04\",\"expdId\":null,\"cstNm\":\"秦1客户\",\"cstNumb\":\"CSTC20999999\",\"aprvSt\":\"10\",\"hdlInst\":\"11002101\",\"id\":\"4054d43ae0ba4a63a5dd7e28a23c6ec2\",\"hdlTime\":\"2021-05-20 00:00:00\",\"hdlUser\":\"YH051803\",\"ovrlsttnJrnlNo\":\"44359786b74145daa6f94673bed5ec\",\"lwrsInd\":null,\"mdfMod\":\"2\",\"iouNumb\":\"HT202105203748000220\",\"updateUser\":\"YH051803\",\"updateTime\":\"2021-05-21 20:04:22.0\",\"bsnNumb\":\"TZ01202105212363\",\"version\":0,\"ntcNumb\":\"BP2021052100000006\",\"mdfRsn\":\"123\",\"delInd\":\"0\",\"createTime\":\"2021-05-21 19:56:20.0\",\"createInst\":\"11002101\",\"aplyTm\":\"2021-05-21 19:56:20\",\"txnsrlno\":\"44359786b74145daa6f94673bed5ec\",\"tenantId\":\"1001\",\"updateInst\":\"11002101\",\"astChnl\":\"OP01\",\"createUser\":\"YH051803\",\"curPcsgPsn\":\"象屿支行对公对私业务客户经理\"},{\"tskSrc\":\"03\",\"cstTp\":\"04\",\"expdId\":null,\"cstNm\":\"秦1客户\",\"cstNumb\":\"CSTC20999999\",\"aprvSt\":\"11\",\"hdlInst\":\"11002101\",\"id\":\"5028c560c6264a548050edd2bb4c9129\",\"hdlTime\":\"2021-05-14 00:00:00\",\"hdlUser\":\"YH051803\",\"ovrlsttnJrnlNo\":null,\"lwrsInd\":null,\"mdfMod\":\"2\",\"iouNumb\":\"HT202105143708000219\",\"updateUser\":\"YH051803\",\"updateTime\":\"2021-05-21 19:37:59.0\",\"bsnNumb\":\"TZ01202105212362\",\"version\":0,\"ntcNumb\":\"BP2021052100000004\",\"mdfRsn\":\"123\",\"delInd\":\"0\",\"createTime\":\"2021-05-21 19:30:21.0\",\"createInst\":\"11002101\",\"aplyTm\":\"2021-05-21 19:30:21\",\"txnsrlno\":null,\"tenantId\":\"1001\",\"updateInst\":\"11002101\",\"astChnl\":\"OP01\",\"createUser\":\"YH051803\",\"curPcsgPsn\":\"象屿支行对公对私业务客户经理\"},{\"tskSrc\":\"03\",\"cstTp\":\"04\",\"expdId\":null,\"cstNm\":\"对公客户0525\",\"cstNumb\":\"CSTC20205251\",\"aprvSt\":\"1\",\"hdlInst\":\"11002101\",\"id\":\"8961e677f2534b84a4948ff214e58df4\",\"hdlTime\":\"2021-01-15 14:26:24\",\"hdlUser\":\"YH051803\",\"ovrlsttnJrnlNo\":null,\"lwrsInd\":null,\"mdfMod\":\"2\",\"iouNumb\":\"HT202012162969000113\",\"updateUser\":\"YH051803\",\"updateTime\":\"2021-05-13 11:06:03.0\",\"bsnNumb\":\"TZ01202105132359\",\"version\":0,\"ntcNumb\":null,\"mdfRsn\":null,\"delInd\":\"0\",\"createTime\":\"2021-05-13 11:06:03.0\",\"createInst\":\"11002101\",\"aplyTm\":\"2021-05-13 11:06:03\",\"txnsrlno\":null,\"tenantId\":\"1001\",\"updateInst\":\"11002101\",\"astChnl\":\"OP01\",\"createUser\":\"YH051803\",\"curPcsgPsn\":\"象屿支行对公对私业务客户经理\"},{\"tskSrc\":\"03\",\"cstTp\":\"04\",\"expdId\":null,\"cstNm\":\"hhl001\",\"cstNumb\":\"CSTC20205222\",\"aprvSt\":\"10\",\"hdlInst\":\"11002101\",\"id\":\"f6f35272a57048c8b7412acce13664a0\",\"hdlTime\":\"2021-05-12 14:12:08\",\"hdlUser\":\"YH051803\",\"ovrlsttnJrnlNo\":\"07bf90ef84f54206935f60de2ba645\",\"lwrsInd\":null,\"mdfMod\":\"2\",\"iouNumb\":\"HT202105113689000196\",\"updateUser\":\"YH051803\",\"updateTime\":\"2021-05-12 16:40:52.0\",\"bsnNumb\":\"TZ01202105122338\",\"version\":0,\"ntcNumb\":\"BP2021051200000002\",\"mdfRsn\":\"1\",\"delInd\":\"0\",\"createTime\":\"2021-05-12 14:39:42.0\",\"createInst\":\"11002101\",\"aplyTm\":\"2021-05-12 14:39:42\",\"txnsrlno\":\"07bf90ef84f54206935f60de2ba645\",\"tenantId\":\"1001\",\"updateInst\":\"11002101\",\"astChnl\":\"OP01\",\"createUser\":\"YH051803\",\"curPcsgPsn\":\"象屿支行对公对私业务客户经理\"},{\"tskSrc\":\"03\",\"cstTp\":\"04\",\"expdId\":null,\"cstNm\":\"成都小烧烤公司\",\"cstNumb\":\"CSTC20215317\",\"aprvSt\":\"1\",\"hdlInst\":\"11002101\",\"id\":\"8c446a8a4aa7490795c734f455b07212\",\"hdlTime\":\"2021-02-03 10:49:04\",\"hdlUser\":\"YH051803\",\"ovrlsttnJrnlNo\":null,\"lwrsInd\":null,\"mdfMod\":\"2\",\"iouNumb\":\"HT202102023343000142\",\"updateUser\":\"YH051803\",\"updateTime\":\"2021-05-10 15:14:45.0\",\"bsnNumb\":\"TZ01202105102337\",\"version\":0,\"ntcNumb\":null,\"mdfRsn\":null,\"delInd\":\"0\",\"createTime\":\"2021-05-10 15:14:45.0\",\"createInst\":\"11002101\",\"aplyTm\":\"2021-05-10 15:14:45\",\"txnsrlno\":null,\"tenantId\":\"1001\",\"updateInst\":\"11002101\",\"astChnl\":\"OP01\",\"createUser\":\"YH051803\",\"curPcsgPsn\":\"象屿支行对公对私业务客户经理\"},{\"tskSrc\":\"03\",\"cstTp\":\"01\",\"expdId\":null,\"cstNm\":\"张友仁\",\"cstNumb\":\"CSTP20219256\",\"aprvSt\":\"1\",\"hdlInst\":\"11002101\",\"id\":\"baffb90fadb94493a11ebc626a26c75e\",\"hdlTime\":\"2021-02-24 16:37:44\",\"hdlUser\":\"YH051803\",\"ovrlsttnJrnlNo\":null,\"lwrsInd\":null,\"mdfMod\":\"2\",\"iouNumb\":\"HT202102223390000152\",\"updateUser\":\"YH051803\",\"updateTime\":\"2021-04-20 10:07:30.0\",\"bsnNumb\":\"TZ01202104202331\",\"version\":0,\"ntcNumb\":null,\"mdfRsn\":null,\"delInd\":\"0\",\"createTime\":\"2021-04-20 10:07:30.0\",\"createInst\":\"11002101\",\"aplyTm\":\"2021-04-20 10:07:30\",\"txnsrlno\":null,\"tenantId\":\"1001\",\"updateInst\":\"11002101\",\"astChnl\":\"OP01\",\"createUser\":\"YH051803\",\"curPcsgPsn\":\"象屿支行对公对私业务客户经理\"},{\"tskSrc\":\"03\",\"cstTp\":\"04\",\"expdId\":null,\"cstNm\":\"成都小烧烤公司\",\"cstNumb\":\"CSTC20215317\",\"aprvSt\":\"1\",\"hdlInst\":\"11002101\",\"id\":\"67f5d529771d4c608781fcd81cbeb502\",\"hdlTime\":\"2021-04-15 17:00:42\",\"hdlUser\":\"YH051803\",\"ovrlsttnJrnlNo\":null,\"lwrsInd\":null,\"mdfMod\":\"2\",\"iouNumb\":\"HT202104153612000173\",\"updateUser\":\"YH051803\",\"updateTime\":\"2021-04-20 10:07:21.0\",\"bsnNumb\":\"TZ01202104202330\",\"version\":0,\"ntcNumb\":null,\"mdfRsn\":null,\"delInd\":\"0\",\"createTime\":\"2021-04-20 10:07:21.0\",\"createInst\":\"11002101\",\"aplyTm\":\"2021-04-20 10:07:21\",\"txnsrlno\":null,\"tenantId\":\"1001\",\"updateInst\":\"11002101\",\"astChnl\":\"OP01\",\"createUser\":\"YH051803\",\"curPcsgPsn\":\"象屿支行对公对私业务客户经理\"}]";
        JSONArray jsonArray = JSONArray.parseArray(s);
        jsonArray.forEach(System.out::println);
        List<Object> collect = jsonArray.stream().sorted((m1, m2) -> {
            int st1 = MapUtil.getInteger((Map) m1, "aprvSt");
            int st2 = MapUtil.getInteger((Map) m2, "aprvSt");
            if (st1 == st2) {
                return 0;
            }
            if(st1 == 1 && st2 == 4){
                return -1;
            }
            if(st1 == 1 && st2 == 10){
                return -1;
            }
            if(st1 == 10 && st2 == 11){
                return -1;
            }
            if(st1 == 1 && st2 == 11){
                return -1;
            }
            return 1;
        }).collect(Collectors.toList());
        System.out.println("");
        collect.forEach(System.out::println);
    }

}
