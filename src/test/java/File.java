import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

/**
 * @author qingyun
 * @version 1.0
 * @date 2021/10/28 8:59
 */
public class File {
    public static void main(String[] args) throws IOException {
        HashMap<String, String> FileTypeAndFileName = new HashMap<>();
        java.io.File file = new java.io.File("G:\\OpenOss\\data");
        java.io.File[] fileNameLists = file.listFiles();
        for (java.io.File fileNameList : fileNameLists) {
            FileTypeAndFileName.put(fileNameList.toString(), Files.probeContentType(fileNameList.toPath()));
            System.out.println(fileNameList);
        }
    }
}
