package my.compary.cdi.lab.news;

import javax.enterprise.event.Observes;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class NewsPaper implements Consumer<String> {

    private static final Logger LOGGER = Logger.getLogger(NewsPaper.class.getName());

    @Override
    //TODO: Annotate the parameter news with the annotation that will enable notifications, as an Observer
    public void accept(String news) {
        LOGGER.info("We got the news, we'll publish it on a newspaper: " + news);
    }
}
