package cs.teilar.gr.earlywarningsystem.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author Ioannis Frontzos
 * @version 1.0.0
 * @since 16/11/2015
 */
public class ShellExecuter {

    public String executor(String[] command) {
        StringBuffer output = new StringBuffer();
        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}
