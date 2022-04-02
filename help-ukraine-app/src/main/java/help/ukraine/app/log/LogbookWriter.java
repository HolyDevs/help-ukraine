package help.ukraine.app.log;

import lombok.extern.log4j.Log4j2;
import org.zalando.logbook.Correlation;
import org.zalando.logbook.HttpLogWriter;
import org.zalando.logbook.Precorrelation;

@Log4j2
public class LogbookWriter implements HttpLogWriter {

    @Override
    public boolean isActive() {
        return log.isInfoEnabled();
    }

    @Override
    public void write(Precorrelation precorrelation, String request) {
        log.info(request);
    }

    @Override
    public void write(Correlation correlation, String response) {
        log.info(response);
    }
}
