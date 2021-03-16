package fr.edf.tools.daemon.powershell;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

import fr.edf.tools.daemon.powershell.utils.Constants;

/**
 * main() of Spring Boot application
 * 
 * @author Mathieu Delrocq
 *
 */
@SpringBootApplication
public class RestServiceApplication {

    private static final Logger logger = LoggerFactory.getLogger(RestServiceApplication.class);

    public static void main(String[] args) {
        try {
            // Get or generate api token
            String token = getToken();
            logger.info("#######################################################");
            logger.info("");
            logger.info("ACCESS TOKEN :");
            logger.info(token);
            logger.info("");
            logger.info("#######################################################");
            logger.info("The token should be filled in the header parameter '" + Constants.TOKEN_HEADER_NAME + "'.");

            // Set token in application properties
            System.setProperty(Constants.TOKEN_HEADER_VALUE_PROP, token);

            // Run application
            SpringApplication.run(RestServiceApplication.class, args);

        } catch (IOException e) {
            logger.error("An error occured during the start of powershell-daemon", e);
        }
    }

    /**
     * Get or "token.txt" file. If the file does not exist, generate a new one.
     * 
     * @return "token.txt" as {@link File}
     * @throws IOException
     */
    private static File getTokenFile() throws IOException {
        Resource tokenResource = new FileSystemResource(Constants.TOKEN_FILE);
        logger.debug("checking if token.txt exist...");
        if (!tokenResource.exists()) {
            logger.debug("token.txt doesn't exist, generating new file...");
            new File(Constants.TOKEN_FILE).createNewFile();
        }
        return tokenResource.getFile();
    }

    /**
     * Get access token for API call or generate one if not exist. The token is
     * saved in "token.txt" in the root path of the application. If the file
     * "token.txt" doesn't exist, it will be generated with a new token value.
     * 
     * @return token as {@link String}
     * @throws IOException : if an error occured with "token.txt"
     */
    private static String getToken() throws IOException {
        File tokenFile = getTokenFile();
        String token = new String(Files.readAllBytes(tokenFile.toPath()));
        if (!StringUtils.hasText(token)) {
            logger.info("Generating new token ...");
            token = RandomStringUtils.random(40, true, true);
            logger.debug("Writing token...");
            FileWriter writer = new FileWriter(tokenFile);
            writer.write(token);
            writer.close();
        }
        return token;
    }

}