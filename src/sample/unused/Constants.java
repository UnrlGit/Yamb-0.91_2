package sample.unused;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Constants {
    public static final int RMI_PORT = 5555;
    public static final int NETWORK_SOCKET_PORT = 4321;
    public static final String CONFIG_PATH     = "config";





    public static int loadConfig(String fileName) throws NamingException {

        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.fscontext.RefFSContextFactory");
        env.put(Context.PROVIDER_URL, "file:" + CONFIG_PATH);

        Context context = new InitialContext(env);
        Object in = context.lookup(fileName);
        File file = (File) in;

        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            return Integer.parseInt(br.readLine());
        } catch (IOException ex) {
            Logger.getLogger(Constants.class.getName()).log(Level.SEVERE, null, ex);
        }

        return -1;
    }
}
