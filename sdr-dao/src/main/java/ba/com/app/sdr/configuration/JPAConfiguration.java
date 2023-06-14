package ba.com.app.sdr.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = { "ba.com.app.sdr.dao.model" })
public class JPAConfiguration {

}
