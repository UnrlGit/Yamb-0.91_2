package sample.common;

import sample.unused.Constants;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JNDI {
    public static final String RMI_PORT_FILE     = "rmiport.txt";
    public static final String PATH = "/src/sample/common";
    public static final String BASE_PATH = Paths.get(".").toAbsolutePath().normalize().toString();


    public static File loadConfig() throws NamingException {
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.fscontext.RefFSContextFactory");
        env.put(Context.PROVIDER_URL, "file:/"+ BASE_PATH + PATH);

        Context context = new InitialContext(env);
        Object obj = context.lookup(RMI_PORT_FILE);
        File file = (File) obj;
        return file;
    }

    public static int readFileInt(File file){
        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            return Integer.parseInt(br.readLine());
        } catch (IOException ex) {
            Logger.getLogger(Constants.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
}
