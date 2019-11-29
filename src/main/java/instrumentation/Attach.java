package instrumentation;


import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import java.io.IOException;
import java.util.Properties;

public class Attach {
    public static void main(String[] args) throws InterruptedException, IOException, AttachNotSupportedException {
        Attach attach = new Attach();
        attach.attach();
//        attach.attach();
    }

    private void showThread() throws InterruptedException {
        while (true) {
            getThread();
            Thread.sleep(10000);
        }
    }

    private void getThread() {
        for (VirtualMachineDescriptor descriptor : VirtualMachine.list()) {
            System.out.println(descriptor);
        }
    }

    private void attach() throws IOException, AttachNotSupportedException {
        VirtualMachine virtualMachine = VirtualMachine.attach("70924");
        Properties agentProperties = virtualMachine.getAgentProperties();
        System.out.println(agentProperties);
        System.out.println();
    }


}
