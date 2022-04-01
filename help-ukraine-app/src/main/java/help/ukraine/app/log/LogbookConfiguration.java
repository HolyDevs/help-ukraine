package help.ukraine.app.log;

import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.*;
import org.zalando.logbook.json.JsonHttpLogFormatter;

@Configuration
@Log4j2
public class LogbookConfiguration {
    @Bean
    public Logbook logbook() {
        Strategy strategy = new BodyOnlyIfStatusAtLeastStrategy(HttpStatus.SC_BAD_REQUEST);
        if (log.isDebugEnabled() || log.isTraceEnabled()) {
            strategy = new DefaultStrategy();
        }
        return Logbook.builder()
                .headerFilter(HeaderFilters.removeHeaders())
                .sink(new DefaultSink(
                        new JsonHttpLogFormatter(),
                        new LogbookWriter()))
                .strategy(strategy).build();
    }
}
