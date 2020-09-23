import com.alibaba.fastjson.JSONObject;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {
    private static Map<Integer, String> cmdMap = new TreeMap<>();
    private static Map<Integer, String> extraMap = new TreeMap<>();
    static {
        cmdMap.put(0, "手动输入命令");
        // 仓库命令
        cmdMap.put(1, "move_item");
        cmdMap.put(2, "import_item");
        cmdMap.put(3, "export_item");

        extraMap.put(0, "");
        extraMap.put(1, "from");
        extraMap.put(2, "to");
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 获取端口并连接
        System.out.println("请输入SocketServer的端口：");
        int port = sc.nextInt();
        SocketSender socket = new SocketSender(port);

        // 获取任务号
        System.out.println("请输入任务号：");
        int taskNo = sc.nextInt();

        // 获取命令
        System.out.println("请输入需要发送的命令：");
        printChoice(cmdMap);
        int cmdChoice = sc.nextInt();

        // 获取extra字段
        System.out.println("请输入extra字段：");
        printChoice(extraMap);
        int extraChoice = sc.nextInt();

        // 构造消息体
        JSONObject msg = new JSONObject();
        msg.put(SocketMessage.FIELD_TASK_NO, taskNo);
        msg.put(SocketMessage.FIELD_CMD, getCmd(cmdChoice));
        msg.put(SocketMessage.FIELD_EXTRA, getExtra(extraChoice));


        // 人工确认字段无误
        System.out.println("请检查命令：");
        System.out.println(msg.toJSONString());

        // 根据结果确认发送
        int sendOrCancel = sc.nextInt();
        if(sendOrCancel > 0) {
            socket.sendMessageToMachine(msg);
            System.out.println("发送成功");
        } else {
            System.out.println("取消发送");
        }

    }
    private static String getCmd(int cmdNo) {
        return cmdMap.getOrDefault(cmdNo, "");
    }

    private static String getExtra(int extraNo) {
        return extraMap.getOrDefault(extraNo, "");
    }

    private static void printChoice(Map<Integer, String> map) {
        map.forEach((key, value) -> System.out.println(key + " " + value));
    }
}
