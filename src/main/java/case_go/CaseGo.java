package case_go;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.apache.spark.mllib.random.LogNormalGenerator;
import utils.MysqlUtil;

import java.io.*;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

public class CaseGo {

    private static Logger logger = LoggerFactory.getLogger(CaseGo.class);

    public void initDict() {
        MysqlUtil.init();
    }

    public void output(String fileName, ArrayList<String> list) {
        File f = new File(fileName);
        BufferedOutputStream bos = null;
        OutputStreamWriter writer = null;
        BufferedWriter bw = null;
        try {
            // 文件名存在的话，往后面添加到文件末尾；每天生成一个新的文件
            OutputStream os = new FileOutputStream(f, true);
            bos = new BufferedOutputStream(os);
            writer = new OutputStreamWriter(bos);
            bw = new BufferedWriter(writer);
            for (int i = 0; i < list.size(); i++) {
                bw.write(list.get(i) + "\n");
            }
            bw.flush();
        } catch (FileNotFoundException e) {
            logger.info("[exception] => FileNotFoundException ");
        } catch (IOException e) {
            logger.info("[exception] => IOException");
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
                if (writer != null) {
                    writer.close();
                }
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void readContentFromDB(ArrayList<String> arrayList, String source, String timeStamp,
                                   String endTimeStamp) {

        String query = "select content from case_case where source = ? and updated_at >= ? and " +
            "updated_at <= ? ";
        int count = 0;
        try {
            PreparedStatement preparedStatement = MysqlUtil.select(query);
            preparedStatement.setString(1, source);
            preparedStatement.setLong(2, Long.parseLong(timeStamp));
            preparedStatement.setLong(3, Long.parseLong(endTimeStamp));
            ResultSet resultSet  = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Blob content = resultSet.getBlob(1);
                if (content != null) {
                    InputStream inputStream = content.getBinaryStream();
                    ByteArrayInputStream byteArrayInputStream = (ByteArrayInputStream) inputStream;
                    byte [] bytes = new byte[byteArrayInputStream.available()];
                    byteArrayInputStream.read(bytes, 0, bytes.length);
                    String contentString = new String(bytes, "utf-8");
                    arrayList.add(contentString);
                    count ++;
                    inputStream.close();
                }
            }
            System.out.println("get all data count:" + count);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        if (args.length < 4) {
            System.err.println("args: [Source] [timeStamp] [endTimeStamp] [SavePath]");
            return;
        }
        String source = args[0];// "LIELUOBO_1.0";
        String timeStamp = args[1];// "1515340800000";
        String endStamp = args[2];// "1515945600000";
        String path = args[3];// "/Users/devops/workspace/shell/case_go/case_go_1.8-1.15.txt";
        CaseGo caseGo = new CaseGo();
        caseGo.initDict();
        ArrayList<String> arrayList = new ArrayList<>();
        caseGo.readContentFromDB(arrayList,source , timeStamp, endStamp);
        caseGo.output(path, arrayList);

    }
}
