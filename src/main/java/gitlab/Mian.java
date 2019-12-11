//package gitlab;
//
//import org.apache.commons.io.FileUtils;
//import org.gitlab.api.GitlabAPI;
//import org.gitlab.api.models.GitlabProject;
//import org.gitlab.api.models.GitlabTag;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.util.List;
//

// 和es冲突


//public class Mian {
//    public static void main(String[] args) throws IOException {
//        GitlabAPI gitlabAPI;
//        File file = new File("F:\\dolphin\\etc\\dolphin\\gitlab\\key");
//        if(!file.exists()){
//            throw new FileNotFoundException("gitlab密钥未找到");
//        }
//        String key = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
//        gitlabAPI = GitlabAPI.connect("http://gitlab.ctsp.kedacom.com", key);
//
//        GitlabProject project;
//        List<GitlabTag> tags = null;
//        project = gitlabAPI.getProject("dolphin","dops-backend");
//        tags = gitlabAPI.getTags(project);
//
//        System.out.println(tags);
//    }
//}
