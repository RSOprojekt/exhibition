package si.fri.rso.gallery.exhibition.config;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;

import javax.enterprise.context.ApplicationScoped;

@ConfigBundle("rest-properties")
@ApplicationScoped
public class RestProperties {

    @ConfigValue(watch = true)
    private Boolean infoMode;

    private Boolean broken;


    public Boolean getInfoMode() {
        return infoMode;
    }

    public void setInfoMode(Boolean infoMode) {
        this.infoMode = infoMode;
    }

    public Boolean getBroken() {
        return broken;
    }

    public void setBroken(Boolean broken) {
        this.broken = broken;
    }
}
