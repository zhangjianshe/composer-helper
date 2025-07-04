package cn.cangling.docker.composer.client.resource;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.TextResource;

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

    @Source("save_file.svg")
    TextResource save_svg();

    @Source("open_file.svg")
    TextResource open_svg();

    @Source("align_left.svg")
    TextResource align_left();

    @Source("align_right.svg")
    TextResource align_right();

    @Source("align_middle.svg")
    TextResource align_middle();

    @Source("align_center.svg")
    TextResource align_center();

    @Source("align_top.svg")
    TextResource align_top();

    @Source("align_bottom.svg")
    TextResource align_bottom();

    @Source("dist_hori.svg")
    TextResource dist_hori();

    @Source("dist_vert.svg")
    TextResource dist_vert();

    @Source("same_size.svg")
    TextResource same_size();

    @Source("same_width.svg")
    TextResource same_width();

    @Source("same_height.svg")
    TextResource same_height();

    @Source("docker_helper.svg")
    TextResource dockerHelper();

    @Source("default_graph.json")
    TextResource defaultGraph();

}
