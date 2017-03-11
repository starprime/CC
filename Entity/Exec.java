
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;


public class Exec {

    public static void main(String args[]) {
        String cmnd="echo haha";
        for (String s: args) {
            System.out.println(s);
            cmnd=s;
        }
        StringBuffer sb = new StringBuffer();

        Process p;
        try {
            p = Runtime.getRuntime().exec(cmnd);
            p.waitFor();

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine())!= null) {
                sb.append(line + "\n");
            }

        }
        catch(IOException e1) {}
        catch(InterruptedException e2) {}

        System.out.println("finished.\n\n"+sb.toString());
    }
}

