package gov.ce.fortaleza.lembrete.bootstrap;

import org.springframework.boot.CommandLineRunner;

/**
 * Created by berkson
 * Date: 30/11/2021
 * Time: 20:42
 */
public class Bootstrap implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        createContractTypes();
    }

    private void createContractTypes() {

    }
}
