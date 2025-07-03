package cn.cangling.docker.composer.client.composer.model;

import cn.cangling.docker.composer.client.resource.ComposerResource;
import com.google.gwt.user.client.ui.Image;
import elemental2.dom.HTMLImageElement;
import jsinterop.base.Js;

public class Images {
    private static HTMLImageElement hard;
    private static HTMLImageElement hardRed;
    private static HTMLImageElement net;
    private static HTMLImageElement netRed;
    private static HTMLImageElement port;
    private static HTMLImageElement portRed;
    private static HTMLImageElement redis;
    private static HTMLImageElement kafka;
    private static HTMLImageElement postgres;
    private static HTMLImageElement geoserver;
    private static HTMLImageElement mongo;
    private static HTMLImageElement nginx;
    private static HTMLImageElement mqtt;

    public static HTMLImageElement getHard() {
        if (hard == null) {
            Image image = new Image();
            image.setResource(ComposerResource.INSTANCE.hard());
            hard = Js.uncheckedCast(image.getElement());
        }
        return hard;
    }
    public static HTMLImageElement getHardRed() {
        if (hardRed == null) {
            Image image = new Image();
            image.setResource(ComposerResource.INSTANCE.hardRed());
            hardRed = Js.uncheckedCast(image.getElement());
        }
        return hardRed;
    }
    public static HTMLImageElement getNet() {
        if (net == null) {
            Image image = new Image();
            image.setResource(ComposerResource.INSTANCE.net());
            net = Js.uncheckedCast(image.getElement());
        }
        return net;
    }
    public static HTMLImageElement getNetRed() {
        if (netRed == null) {
            Image image = new Image();
            image.setResource(ComposerResource.INSTANCE.netRed());
            netRed = Js.uncheckedCast(image.getElement());
        }
        return netRed;
    }
    public static HTMLImageElement getPort() {
        if (port == null) {
            Image image = new Image();
            image.setResource(ComposerResource.INSTANCE.port());
            port = Js.uncheckedCast(image.getElement());
        }
        return port;
    }
    public static HTMLImageElement getPortRed() {
        if (portRed == null) {
            Image image = new Image();
            image.setResource(ComposerResource.INSTANCE.portRed());
            portRed = Js.uncheckedCast(image.getElement());
        }
        return portRed;
    }

    public static HTMLImageElement getRedis() {
        if (redis == null) {
            Image image = new Image();
            image.setResource(ComposerResource.INSTANCE.redis());
            redis = Js.uncheckedCast(image.getElement());
        }
        return redis;
    }
    public static HTMLImageElement getKafka() {
        if (kafka == null) {
            Image image = new Image();
            image.setResource(ComposerResource.INSTANCE.kafka());
            kafka = Js.uncheckedCast(image.getElement());
        }
        return kafka;
    }
    public static HTMLImageElement getPostgres() {
        if (postgres == null) {
            Image image = new Image();
            image.setResource(ComposerResource.INSTANCE.postgres());
            postgres = Js.uncheckedCast(image.getElement());
        }
        return postgres;
    }
    public static HTMLImageElement getGeoserver() {
        if (geoserver == null) {
            Image image = new Image();
            image.setResource(ComposerResource.INSTANCE.geoserver());
            geoserver = Js.uncheckedCast(image.getElement());
        }
        return geoserver;
    }
    public static HTMLImageElement getMongo() {
        if (mongo == null) {
            Image image = new Image();
            image.setResource(ComposerResource.INSTANCE.mongo());
            mongo = Js.uncheckedCast(image.getElement());
        }
        return mongo;
    }
    public static HTMLImageElement getNginx() {
        if (nginx == null) {
            Image image = new Image();
            image.setResource(ComposerResource.INSTANCE.nginx());
            nginx = Js.uncheckedCast(image.getElement());
        }
        return nginx;
    }
    public static HTMLImageElement getMqtt() {
        if (mqtt == null) {
            Image image = new Image();
            image.setResource(ComposerResource.INSTANCE.mqtt());
            mqtt = Js.uncheckedCast(image.getElement());
        }
        return mqtt;
    }
}
