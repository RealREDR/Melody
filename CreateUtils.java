package utils.createworkflow;


import weaver.workflow.webservices.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建流程的工具类
 *
 */
public class CreateUtils {

    /**
     *
     * @param mainifo  需要传入一个maininfo表
     * @param detailinfos 需要传入一个明细表
     * @param create 显示创建人
     * @param cjr 流程创建人       一般情况下两个创建人传相同id 但是数据类型不同
     * @return
     */
    public static String createinfo(WorkflowMainTableInfo mainifo, WorkflowDetailTableInfo[] detailinfos, String create, int cjr){
        //添加工作流id
        WorkflowBaseInfo wbi = new WorkflowBaseInfo();
        wbi.setWorkflowId("373");//workflowid 流程id
        WorkflowRequestInfo wri = new WorkflowRequestInfo();
        //流程基本信息
        wri.setCreatorId(create);//创建人id
        wri.setRequestLevel("0");
        //0 正常，1重要，2紧急
        wri.setRequestName("资产盘点反馈");//流程标题
        wri.setWorkflowMainTableInfo(mainifo);//添加主字段数据
        wri.setWorkflowDetailTableInfos(detailinfos);//添加明细数据
        wri.setWorkflowBaseInfo(wbi);
        WorkflowServiceImpl service=new WorkflowServiceImpl();
        String requestid = service.doCreateWorkflowRequest(wri,cjr);
        return requestid;
    }

    /**
     *     创建主表工具 WorkflowRequestTableField[]
     * @param wrti
     * @return
     */
    public static WorkflowMainTableInfo createmainifo(WorkflowRequestTableField[] wrti){
        //主字段
        WorkflowRequestTableRecord[] wrtri = new WorkflowRequestTableRecord[1];
        //主字段只有一行数据
        wrtri[0] = new WorkflowRequestTableRecord();
        wrtri[0].setWorkflowRequestTableFields(wrti);
        WorkflowMainTableInfo wmi = new WorkflowMainTableInfo();
        wmi.setRequestRecords(wrtri);
        return wmi;
    }

    /**
     * 创建明细表工具
     * @param wrtri WorkflowRequestTableRecord[] wrtri
     * @return
     */
    public static WorkflowDetailTableInfo[] createDetailifo(WorkflowRequestTableRecord[] wrtri){
        WorkflowDetailTableInfo[] WorkflowDetailTableInfo= new WorkflowDetailTableInfo[1];//指定明细表的个数，多个明细表指定多个，顺序按照明细的顺序
        WorkflowDetailTableInfo[0] = new WorkflowDetailTableInfo();
        WorkflowDetailTableInfo[0].setWorkflowRequestTableRecords(wrtri);
        return WorkflowDetailTableInfo;
    }


    /**
     * demo
     */
    public void demo(){

        String[] maininfostrs ={"这里传每个字段的名字","字段1","字段2"};
        Map<String,String> map = new HashMap<>();    // map或json中获取键值对
        WorkflowRequestTableField[] maininfs = new WorkflowRequestTableField[maininfostrs.length];
        for (int i = 0; i < maininfostrs.length; i++) {
            maininfs[i] = new WorkflowRequestTableField();
            maininfs[i].setFieldName(maininfostrs[i]);//
            maininfs[i].setFieldValue(map.get(maininfostrs[i]));//
            maininfs[i].setView(true);//字段是否可见
            maininfs[i].setEdit(true);//字段是否可编辑
        }
        WorkflowMainTableInfo mainTableInfo = createmainifo(maininfs); //创建主表

        //创建 n 个 明细表
        int n =10;
        String[] details = {"这里谢名字表字段名"};
        Map<String,String> detailmap = new HashMap<>(); // 这里明细表字符串
        WorkflowRequestTableRecord[] detail = new WorkflowRequestTableRecord[n];
        for (int i = 0; i < n; i++) {
            WorkflowRequestTableField[] wrti = new WorkflowRequestTableField[details.length];
            for (int j = 0; j < details.length; j++) {
                wrti[j] = new WorkflowRequestTableField();
                wrti[j].setFieldName(details[j]);//数量
                wrti[j].setFieldValue(detailmap.get(details[j]));
                wrti[j].setView(true);
                wrti[j].setEdit(true);
            }
            detail[i] = new WorkflowRequestTableRecord();
            detail[i].setWorkflowRequestTableFields(wrti);
        }
        WorkflowDetailTableInfo[] detailifo = createDetailifo(detail); //创建明细

        createinfo(mainTableInfo, detailifo,"1",1); //创建流程

    }


}
