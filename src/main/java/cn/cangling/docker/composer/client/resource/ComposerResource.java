package cn.cangling.docker.composer.client.resource;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * image and css resource
 */
public interface ComposerResource extends ClientBundle {
    ComposerResource INSTANCE = GWT.create(ComposerResource.class);
    @Source("grid.png")
    ImageResource grid();

    @Source("hard.png")
    ImageResource hard();
    @Source("hard_red.png")
    ImageResource hardRed();

    @Source("net.png")
    ImageResource net();
    @Source("net_red.png")
    ImageResource netRed();

    @Source("port.png")
    ImageResource port();
    @Source("port_red.png")
    ImageResource portRed();

    @Source("mqtt.png")
    ImageResource mqtt();

    @Source("Kafka.png")
    ImageResource kafka();
    @Source("MongoDB.png")
    ImageResource mongo();

    @Source("postgres.png")
    ImageResource postgres();

    @Source("redis.png")
    ImageResource redis();

    @Source("GeoServer.png")
    ImageResource geoserver();

    @Source("Nginx.png")
    ImageResource nginx();

    @Source("open.png")
    ImageResource open();

    @Source("save.png")
    ImageResource save();


}
